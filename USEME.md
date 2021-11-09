# IME Program Manual

## Command Examples

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

## Supported Commands

### load

``load filePath imageName``

- `filePath`: filepath of image to load
- `imageName`: alias to give to loaded image

### save

``save filePath imageName``

- `filePath`: filepath to save image to
- `imageName`: name of image to save

### quit

``quit``

- Quits the program

### greyscale

``greyscale imageName destImageName``

- `imageName`: image to luma-greyscale
- `destImageName`: name to give to luma-greyscaled image

### sepia

``sepia imageName destImageName``

- `imageName`: name of image to sepia
- `destImageName`: name to give to new sepia image

### sharpen

``sharpen imageName destImageName``

- `imageName`: name of image to sharpen
- `destImageName`: name to give to new sharpened image

### blur

``blur imageName destImageName``

- `imageName`: name of image to blur
- `destImageName`: name of new blurred image

### brighten

``brighten value imageName destImageName``

- `value`: integer in the range [-255, 255], negative values represent darkening
- `imageName`: name of image to brighten
- `destImageName`: name of new brightened image

### red-component

``red-component imageName destImageName``

- `imageName`: name of image to red-greyscale
- `destImageName`: name of new red-greyscaled image

### green-component

``green-component imageName destImageName``

- `imageName`: name of image to green-greyscale
- `destImageName`: name of new green-greyscaled image

### blue-component

``blue-component imageName destImageName``

- `imageName`: name of image to blue-greyscale
- `destImageName`: name of new blue-greyscaled image

### luma-component

``luma-component imageName destImageName``

- `imageName`: name of image to luma-greyscale
- `destImageName`: name of new luma-greyscaled image

### value-component

``value-component imageName destImageName``

- `imageName`: name of image to value-greyscale
- `destImageName`: name of new value-greyscaled image

### intensity-component

``intensity-component imageName destImageName``

- `imageName`: name of image to intensity-greyscale
- `destImageName`: name of new intensity-greyscaled image

### horizontal-flip

``horizontal-flip imageName destImageName``

- `imageName`: name of image to horizontal-flip
- `destImageName`: name of new horizontally-flipped image

### vertical-flip

``vertical-flip imageName destImageName``

- `imageName`: name of image to vertical-flip
- `destImageName`: name of new vertically-flipped image