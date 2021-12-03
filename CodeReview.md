# Design Critique

This software uses the Model-View-Controller paradigm well. Each Implementation has an underlying 
interface (no loose classes). 

# Implementation Critique



# Documentation Critique

There seems to be a good amount of pertinent comments throughout the codebase and the javadocs
describe each method and class very well. The documentation for an end user to run the software is 
very technical and somewhat hard to understand at first.

# Design/code strengths

There is a clear connection between all the interlocking classes and modules that make up this image
processor. There do not seem to be any extraneous classes clogging up the codebase. 

# Design/code weaknesses

The codebase seems to be somewhat unorganized with Image, Pixel and and other unrelated all in the
same base package. Splitting up the code into smaller submodules would make browsing the codebase 
easier. Several exceptions are improperly handled/caught (Ex. Null Pointer Exception being thrown 
when loading an image before opening). A GUI user cannot specify how much to darken/brighten an 
image. All operations greyscale an image (An image rotation also greyscales image inadvertently).

# Suggestions for improvement

Using the command design patter would make it easier to edit certain image operations and add new 
ones with ease. As it is right now, it is directly tied to the model implementation code. 