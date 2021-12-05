# Design Critique

This software sticks to the MVC paradigm pretty well. Each Implementation has an underlying
interface (no loose classes), and all 3 components are strongly separated. The view is
well-designed, and is quite extensibility. The interfaces were robust, and the overall design is as
well. The biggest strengths and weaknesses are explained below in detail.

# Design/code strengths

As explained above, the overarching design stays true to the MVC paradigm. The view, in particular,
was designed to be extensible, and it needed little to no modification to implement our mosaic
command. The model is well written (for the most part, there are some flaws listed below), as
delegates the main commands away to the Pixel and Image subclasses, separate concerns and following
the single responsibility principle. The controller has its flaws, but it's good that the team
followed the command design pattern.

# Design/code weaknesses

While its good that each class/method has its own responsibility, it would have been nice to have
them separated into different subpackages. While this is a minor nitpick, the rest of the design has
several major flaws. The majority of these errors were found with the controller and the tests. Upon
receiving the code, the program with the GUI greyscaled all images *before* operating on them. Turns
out, this was due to the unimplemented Histogram behavior causing errors by directly operating on
the image rather than retrieving values from it. It was fully commented-out/removed, so it still
caused errors. Features were missing from the GUI, such as the ability to choose how much to
brighten/darken an image. There's no way to specify which files to operate on either.

Upon deeper inspection, this turned out to be an issue with the entire model not supporting more
than one image at a time. This is fine if working with only the GUI, but the same was true with the
text interface. As such, there was no concept of "image names" either, so we couldn't completely
implement the `mosaic seed-count image-name dest-image-name` command. The only argument you can
specify is `mosaic seed-count`. On top of that, we were confused about the commands supporting
the `filepath` argument. The model only supports one image at a time, so all images are loading
using `load`, so the `filepath` argument is superfluous, and the `image-name` arguments are not
considered or used by the controller.

In general, the testing was a bit of a mess. They seemed completely different from what was actually
implemented, (i.e. the controller commands being quite different from what you'd expect from the
code). Several tests failed, and some didn't even run. It seems like the two (the tests and the
implementation) were written out-of-sync or by 2 different people, which would have caused the
miscommunication. This made it very difficult to test proper functionality, and it's clear that if
tests were written properly, the implementation would have fewer bugs and issues too.

Overall, there are few overarching design issues, but digging deeper down into the code and design,
the classes themselves required quite a bit of refactoring and cleanup, features were missing or
incomplete, and the code suffered from poor test coverage. Specific issues with the code are
detailed below.

# Implementation Critique

(We should note that the main class and jar did not execute correctly, but this may have been a
system-level issue, and not a code-level issue. Modifications were made to the `ImageProcessor`
class)

There were *several* bugs in this code, *but* they were almost all uncaught errors and exceptions.
For example, the controller crashes upon an invalid file since it doesn't catch
`FileNotFoundException`. But, the received code gave us several warnings from IntelliJ regarding
code duplication, unused variables, and other bugs. The controller also couldn't properly process
all commands, such as the command "change intensity", which I believe was meant to be "
change-intensity" instead. As a result, the intensity operation did not work. While the code stuck
to the command design pattern well, there were a few ways it could be cleaner and more extensible.
Currently, the controller has a list of known commands, but it makes use of a large switch statement
to handle the commands. It would have been nice if the known commands were stored in a map to
lambdas that would handle the command. There is also *significant* code duplication in the
controller. The `run()` controller methods are almost identical class-to-class, with the only
difference being the method called on the model and the actual message being displayed. There's
duplication between the main loops in the GUI and Text controllers, too. A lot of these methods are
identical to the ones in the GUI controller, too. It may have been better to make these classic
non-static and (at least) package-public, and abstract out common functionality. As a result of the
duplication and having to stick to the current implementation pattern, our `Mosaic` command has some
duplication too.

Ultimately, we realized that it wouldn't require much of a refactor to follow a more extensible
design, so we made changes to the command design pattern implementation (namely creating a mapping
from a command to lambda). If we didn't do this, we would have had to copy and paste the code into
two new classes for the Mosaic controller, because our updates required using a new
`MosaicModel` interface that "decorated" the model implementation. Thus, we would have had to change
the type of the model, breaking all current implementations that used the previous
`RGBModel`. With our refactoring, we were able to extend easily off of the previous controller,
separate the new mosaic logic from the past implementation, and no implementations would be broken
since all new programs that have mosaicking would use the `MosaicModel` interface *with*
the `MosaicController`. Therefore, no existing code would have to be changed.

In the model, the only major bug or code issue we had been with the `ImageUtil` class not reading
from PPMs properly. They get the height and width of the image from the file correctly, but don't
read the max-value correctly. As a result, the images were all off by 1 channel at every pixel. It
was a quick fix, however. We also made a minor modification to the PPM write method. According to
the documentation from PPM files, it seems that the max-value is always 255 in our use case, but
`ImageUtil` tries to loop through the image and find the max-value of any channel in the image.
Naturally, an image with any value of 255 in any channel would not result in an incorrect file, but
small files or very dark images would be incorrect. *However*, there is some ambiguity in the
documentation for PPM files that we found, and it's pretty easy to see why anyone would be confused.

The view was well-designed and written, as explained above. Good job guys! It was very easy to
extend, work with, and debug.

The tests had a lot of issues. They often didn't test the correct functionality, and would not run
properly. There were no tests for edge cases, which there ended up being bugs for. In the controller
tests, for example, all used `FileBuffer` scanner input passed to the controller, for some reason.
We had to change it to be a normal `StringReader`, but we're not sure how this would have made it
through any step in the process without it not being caught, unless the tests were never run in the
first place. We ultimately fixed the tests in the process of learning the code base and trying to
work around the issues with it.

# Documentation Critique

There seems to be a good amount of pertinent comments throughout the codebase and the javadocs
describe each method and class very well. At times, the documentation might get a bit technical, but
if I were a developer trying to extend the program, I would have little trouble understanding the
interfaces and what I have to work with. The documentation helped us a ton, for example, and saved a
lot of time learning the code and the purpose of each method/class. Excellent job guys!

# Suggestions for improvement

The biggest takeaway, in my opinion, would be to have put a greater emphasis on tests. Almost all
the bugs would have been noticed during the test phase. The design itself was fairly good and stuck
to MVC and the design patterns learned in class, but the implementation made some code hard to
extend and build on at times. This was limiting for us when implementing the Mosaic command,
creating more unnecessary complexity.