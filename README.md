# IME : Image Manipulation and Enhancement

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

You can find the class diagram at [`/src/diagram.uml`](./src/cs3500/ime/diagram.uml).

## Test Script

### Commands to load example image, modify it and save the output

Run IME main method

````
load res/PPMImages/testOG.ppm OG
brighten 10 OG OG-brighter
vertical-flip OG OG-vertical
horizontal-flip OG OG-horizontal
value-component OG OG-value
save res/Test/OG-brighter.ppm OG-brighter
save res/Test/OG-vertical.ppm OG-vertical
save res/Test/OG-horizontal.ppm OG-horizontal
save res/Test/OG-value.ppm OG-value
quit