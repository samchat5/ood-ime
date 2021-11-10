# IME : Image Manipulation and Enhancement

## Changelog: HW5

### Model

The major changes we made in this homework were to the Model. For this assignment, **we made the
explicit decision to not create new interfaces for the Image and Pixel classes**. The one
stipulation that we *did* make sure to follow was that all existing implementations of our design
would **not** be broken as a result of our updates. That is, minimal to no editing of existing
methods in concrete classes (see [Piazza post](https://piazza.com/class/kstd2rfiays6tw?cid=825)).
The reasoning for this was that our code is still very much in early development, and this "update"
only introduced minor changes to our current design. So, we did not feel it necessary to needlessly
overcomplicate the design of our program by adding, say, a `BetterPixel` class that extends `Pixel`
with new methods for color transformation. Instead, we opted to add new methods to our `IPixel`
and `IImage` interfaces that `Pixel` and `Image` (previously `PPMImage`, changed since this class
now represents all Images) would have to implement. Specifically, these methods
were `applyColorTransform()` in `IPixel`, and `applyTransform()`
and `applyFilter()` in `IImage`. Since these methods purely add functionality to our program, they
would not break any existing implementations. Furthermore, a new method `getValues()` was added
to `IPixel` that returns an `int[]` representing the channel values of the pixel, which was useful
in implementing filtering. Getter methods have the tendency to be "overused," so from the start we
made an effort to only add them if a need for them arose. Within `IImage`, we also added getter
methods for `getPixelAt()`, `getHeight()`, and `getWidth()`. The first of these makes sure to *not*
return a reference to the Pixel object, but rather a new Pixel object with the same properties as
the original. All of these getter methods were created for image IO purposes, as seen in `ImageUtil`
, since writing to the new `png` and `jpg` file formats required us to get the height, width, and
pixels of an Image.

Two new interface and abstract classes were created for `IColorTransform` and `IFilter`,
representing the color transform and filter operations, respectively. We followed a design pattern
similar to the "visitor pattern," with these classes, where each new operation is its own concrete
class, and has a method `applyTransform()/applyFilter()` that takes in an `IImage`
object and passes it the kernel used in the operation. These objects then "accept the visitor" and
apply the transform/filter using the kernel. The only difference between each operation, really, is
that they have different kernels, which is a property inherent to the object. So, all concrete
classes extend an abstract class `AColorTransform/AFiter` which checks for correct kernels, and is
called using `super()` in the constructors for the subclasses.

The new `Sepia` color transform was added, along with a different way to do luma greyscaling
using `Luma`. We did **not** want to make this the only way to do luma greyscaling, and the user can
still use the command `luma-component` instead of the new `greyscale` to do so. We did this to
follow our rule that no current implementation would be broken as a result of our changes. As
such `luma-component` can be considered "deprecated," favoring the use of `greyscale` instead, but
it will never be removed. The classes `Red/Blue/GreenComponent` were also created to show how
different greyscaling can be implemented with transforms. Value greyscaling cannot be implemented
with a transform, and intensity greyscaling *can*, but it may or may not result in the same image
due to floating point precision error (if one were to do it, it would be a kernel where all values
are 1/3). These are there for showcase, and a user cannot use these transforms directly, yet.

The `IIMEModel` interface now has two new methods for transforming and filtering images, and in
`IMEModel` it is implemented very similarly to past methods. Again, we didn't feel it necessary to
overcomplicate/over-engineer our design with a new model class that extends `IMEModel`, and these
methods don't break any existing implementations.

`ImageUtil` now has two new methods to read/write from non-PPM images. The main design decision
issue we ran into here was whether to create some new method `read/writeAllImages()` that would call
the respective `read/writePPM()` or `read/writeImage()` methods based on the extension. We decided
against this, since exiting implementations that use `read/writePPM()` would have to refactor,
causing potential bugs.

### Controllers

In the controller, 4 new commands were added: `BlurCommand`, `LumaTransform`, `SepiaCommand`,
and `SharpenCommand`, each representing the 4 new commands a user can now type in. Like many of the
other commands, they all take in a model and call the appropriate methods on it. The classes
`Save` and `Load` were edited very slightly to check whether the file extension was `ppm` or not. If
not, it called the new `read/writeImage()` methods in `ImageUtil`. Once again, even if it required
editing the methods, no implementations would be broken by these changes, and it only added
additional functionality. The IMEController `knownCommands` map was also updated with the 4 new
operations.

### Main IME class

The main entry point for the program was modified so that it can now take in a command line argument
with the filepath to a script with image commands. If no argument is provided, it defaults to our
own `Commands.txt` script.

### Testing

New testing was done for all the new methods/classes, including file IO for the new file types. Old
tests were also updated to sparingly use `.ppm` files to reduce on our project zip size when
uploading to Bottlenose, since we can now just use `.png` :)

## Design

### Model

One of the first decisions we made was to make an Image class, as that is the main data we are
manipulating and working with. We know that an image is really just a 2D array of pixels, so each
image should be composed of a 2D array of some "Pixel" objects. Each pixel has some value associated
with it, and at the very least can be represented with 3 red, green, and blue values. As such we
designed the `PPMImage` class, that implements an `IImage` interface, and is composed of `Pixel`
objects, which implement the `IPixel` interface. Of course, we could have different types of images,
e.g. `PNGImage`s in the future, so the `IImage` interface only specifies that each concrete class
include the operations you can do on an image. As of now, the only implementation is the `PPMImage`
class, which only needs that 2D array, height, and width. Similarly, we can have different types of
pixels that extend `IPixel`, e.g. PNG files have transparency and thus an extra `alpha` field, so
the `IPixel` interface also only specifies the operations you can do on all pixels. We didn't find
the need to create a separate `GreyPixel` class, as any gray pixel can be fully represented with
a `Pixel` where all the RGB values are the same. For grey pixels, we have a convenience constructor
that only takes in one value, and sets all RGB channels to that same value by calling the
main `Pixel` constructor.

There is also an enum we created called `GreyscaleComponent`, that represents the different
components a user can get from an image to get its greyscale counterpart. Right now, it supports 6
values: LUMA, INTENSITY, VALUE, RED, GREEN, and BLUE. It's very easy to add new values to this enum,
making it quite future-proof.

The `Pixel` subclass only contains an invariant, that all RGB values get clamped to [0, 255]. This
is insured by the constructor, which checks for any out of range values. All methods in
`Pixel` return a new object, as well, so there's no mutation of the values, i.e. they can remain
`final`, thus fully enforcing this invariant. Other implementations of `IPixel` may enforce other
restrictions on these values, say having an image with n-bit color depth, for which the range could
be different. To anticipate this, we've structured our classes such that `IPixel`
doesn't enforce the same constraints on all subclass, but rather, subclasses enforce their own
invariants.

Lastly, we designed the `IIMEModel` interface, which is only implemented, as of now, by the
`IMEModel` class. The interface, as before specified the operations one can do on an image, and
nothing else, such that subclasses have the flexibility to impose more restraints. The
`IMEModel` class has only one field, and its constructor doesn't take in any values. This field is
the `imageMap`, and contains mappings from the names of images, as `Strings`, to the image objects
themselves. Naturally, this field is private, and the only thing a user can find out about this data
is whether an image of a given name is loaded into this map with the method `isLoaded`. Right now,
in the `brighten` method, `IMEModel` *does* impose a restriction on the brightness value
to [-255, 255]. Of course, if we're working with images where they can be brightened by more/less
than this range, this "check" can be easily removed. But for *now*, we thought it would be best to
explicitly stop users from brightening outside this range, since we're only dealing with 8-bit color
depth. There is also the `ImageUtil` class, that builds off of the provided code dealing with PPM
image IO, and offers `static` utility methods to read/write from PPM files.

### View

Since our application has no GUI right now, the View for our program is incredibly simple. The
`IIMEView` interface only supports one method, `displayMessage`, that displays some message to the
user. We found that no matter what, every view should display *some* sort of message to the user,
and this would be the method to do so. Right now, the only implementation of this interface is
`IMETextView`, which takes in an `Appendable` to write messages to. In the main `IME` class, this
defaults to STDIN and STDOUT.

### Controller

The Controller of our program makes use of the Command Design pattern. Inside the `commands`
subdirectory, there are classes representing the commands a user can enter. Some of these classes
represent multiple commands, like `GreyScale`, since the operation on all the different
`x-component` commands are so similar. Each class in `commands` takes in the necessary values it
needs to call the respective method on the model, and implements the `run()` method from
`IIMECommand` that takes in the model to run the method on, throwing `IllegalStateException`s on all
failures. The main controller, `IMEController` that implements `IIMEController` takes in a model,
view, and a readable to read input from. The interface only asks subclasses to implement one
method, `run`. In the constructor for `IMEController`, we instantiate the passed values to the
fields, and also instantiate the fields `knownCommands` and `commands`. The former is a mapping from
the `String` for each command a user can enter, to a lambda function that creates the
respective `IIMECommand` subclass and runs it. This is all part of the command design pattern. On
top of that, the `commands` field is a stack of commands the user has entered, representing a sort
of "history" of operations. Right now it doesn't do anything, but it's created in anticipation of
some sort of "undo" or "view history" functionality, for which this stack would be quite useful.

The `run` method in `IMEController` is fairly standard. It takes in input from the scanner and
parses it properly and passes the values to the correct command (using the `knownCommands`)
field. If a command is unknown, it displays so the user through the view, as well as if a command is
used incorrectly (syntactically, like switching arguments around, or otherwise). It only throws an
error if it runs out of inputs, or there's an `IOException`.

## Image

The image we used for testing the program was taken by me, Samridh Chaturvedi, and I give full
rights to its use in this program/project.

I have an RGB keyboard and a Rubik's cube lying around, so I thought that variety of colors would be
a good test for the program :)

## Class Diagram

You can find the class diagram below, or
at [`/src/cs3500/ime/diagram.jpg`](./src/cs3500/ime/diagram.jpg).

![diagram](/src/cs3500/ime/diagram.jpg)

## Test Script

### Commands to load example image, modify it and save the output

Run `IME.jar` inside `res/`. If not already there, make sure the directory `res/Test`
exists.

```
load res/PPMImages/testOG.ppm OG
load res/PNGImages/testOG.png OG-png
brighten 10 OG OG-brighter
vertical-flip OG OG-vertical
horizontal-flip OG OG-horizontal
value-component OG OG-value
blur OG OG-blurred
sharpen OG OG-sharpened
greyscale OG OG-greyscaled
sepia OG OG-sepia
luma-component OG OG-luma
intensity-component OG OG-intensity
red-component OG OG-red
blue-component OG OG-blue
green-component OG OG-green
save res/Test/OG-brighter.ppm OG-brighter
save res/Test/OG-vertical.ppm OG-vertical
save res/Test/OG-horizontal.ppm OG-horizontal
save res/Test/OG-value.ppm OG-value
save res/Test/OG-blurred.ppm OG-blurred
save res/Test/OG-sharpened.ppm OG-sharpened
save res/Test/OG-greyscaled.ppm OG-greyscaled
save res/Test/OG-sepia.ppm OG-sepia
save res/Test/OG-brighter.png OG-brighter
save res/Test/OG-brighter.jpg OG-brighter
save res/Test/OG-brighter.jpeg OG-brighter
save res/Test/OG-luma.png OG-luma
save res/Test/OG-intensity.png OG-intensity
save res/Test/OG-red.png OG-red
save res/Test/OG-blue.png OG-blue
save res/Test/OG-green.png OG-green
save res/Test/OG.jpg OG-png
save res/Test/OG.jpeg OG-png
save res/Test/OG.ppm OG-png
save res/Test/OG.png OG-png
quit
```

## Credits

Used https://dyclassroom.com/image-processing-project/how-to-create-a-random-pixel-image-in-java
as a reference for manipulating bufferedImages. This use is permitted for non-commercial use.