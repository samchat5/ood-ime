# Mosaicking

## Unimplemented Features + Bugs From Past Design

The mosaicking command is written slightly different from the original specification

- This is because the model does not support multiple images, and has no concept of image names (
  more detail given in our [code review](CodeReview.md))
- As a result, to use the mosaicking command the same way the assignment asked, you write
  ```
  load filepath
  mosaic seed-count
  save filepath
  ```
  for each image you want to mosaic, in order.
- In the GUI, a `NullPointerException` is uncaught when mosaicking an unloaded image in the original
  design due to the decision to create a temporary file (explained in [code review](CodeReview.md)
  as well)

Specifically, the ability to load multiple images would require significant refactoring to the
design, and the functionality still remained mostly the same. So, **no major implementation details
are missing in our mosaicking design**.

## Controller

For the controller, some refactoring was needed to be able to extend a new `MosaicController`
class (as mentioned in the [code review](CodeReview.md)). We noticed that there was some code
duplication between the commands, since they were all private classes among the two different
controllers. So, we made the new `Mosaic` command package-private, so that both controller could use
it without having to implement that same thing twice. The command simply takes in a seedCount and
model, as well as a random seed. We decided not to generate this seed "on the fly" within the
command class, in the case that we may want to use the same static seed for each operation, for
whatever reason. This was useful during our testing to get deterministic inputs and outputs for the
program. All the run command does is call the mosaic method on the model, print that it's mosaicking
if provided a `messageCallback`, and prints an error message to the callback.

The two Mosaic controllers are very similar. From the changes we made, a lot of the similarities
were abstracted out, and they only have different constructors. These constructors take in a model
and view, and add "mosaic" to the known commands, with a lambda that runs the Mosaic command.

## Model

This is where the meat of the program and logic is. From a high level, we first generate random
seeds, making sure there's no overlap. Then, we create a map from each seed to its cluster. This is
done by going through the image pixel-by-pixel, finding the seed with the minimum distance from it,
and adding it to that seed's cluster. We then create a map from each seed to the average color of
its cluster. This involves going through the seed array, finding its cluster, averaging the colors,
and adding it to our map. Finally, we create a new image by iterating over the seeds once more, and
setting each pixel in its cluster to the average cluster color. Of course, this is incredibly
inefficient.

For *n* seeds, generating them takes *O(n)* time. For an image of size *x* by *y*, finding the
clusters takes *O(xy)* iterations, with each step taking *O(n)* time for a total of *O(xyn)*.
Finding the average colors has a main loop of *O(n)*
iterations, but over all iterations, we check *O(xy)* pixels, for a total of *O(xy)*. Overwriting
the points also takes *O(xy)* time, for the same reason. This gives the algorithm a total runtime
complexity of *O(n + xyn + xy + xy) = O(xyn)*. It's quasi-polynomial time, but it's easy to see how
it can get out of hand quickly. There exist some fast algorithms for generating Voronoi diagrams
(which is basically what we are doing here), in *O(n log n)*, which would lower our total complexity
down to around *O(n log n + xy)*, but they are incredibly complicated. Our implementation is good
enough for the purposes of this assignment.

## View

The view updates are fairly simple. We extend a new `MosaicGuiView` class that only adds a new
button to the GUI, and adds a listener to it that sends the appropriate command to the controller.
The only completely new feature we needed to add was the ability to choose many seeds you want, and
the previous implementation didn't include a similar feature for brightening/darkening. So, we added
a new `SpinnerOptionPane` class, that is similar to what we implemented ourselves in HW6, with a few
modifications.

## Script

We've included a script to show off mosaicking, you can find it
in [res/updated-script.txt](res/updated-script.txt). To run it with the jar in the command line,
write `java -jar Image-Processor.jar -file updated-script.txt` **while in the `res/` directory**. As
always `java -jar Image-Processor.jar` will open the GUI, and `java -jar Image-Processor.jar -text`
opens up the text view.

## Testing

We were given the tests written by the previous group, but much of it was broken and code coverage
was poor. Still, we didn't write new tests for the previous implementation, and only added tests
specifically regarding our mosaicking feature. For example, the tests for checking errors in the
controller were broken, but we only added test to see those errors were caught *for the mosaicking
operation*, not for all commands. These tests provide full coverage of all newly added
functionality.

## Other Notes

Almost all of our new code is inside the hw7 subpackages in each directory, to make it easier to
separate old logic from the new.

