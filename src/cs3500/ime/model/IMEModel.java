package cs3500.ime.model;

import cs3500.ime.model.image.IImage;
import cs3500.ime.model.image.colortransform.IColorTransform;
import cs3500.ime.model.image.filter.IFilter;
import cs3500.ime.model.image.pixel.IPixel;
import cs3500.ime.model.image.pixel.Pixel;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete class representing a model for this IME program, mapping image names to the images
 * themselves and executing commands on them given to it from the controller.
 */
public class IMEModel implements IIMEModel {

  private final Map<String, IImage> imageMap;

  public IMEModel() {
    this.imageMap = new HashMap<>();
  }

  /**
   * Load an image to the image mapping.
   *
   * @param image image object to load into map
   * @param name  name to give image
   * @throws IllegalArgumentException iff the image name is null
   */
  @Override
  public void load(IImage image, String name) {
    if (image == null || name == null) {
      throw new IllegalArgumentException("Null name");
    }
    imageMap.put(name, image);
  }

  /**
   * Return if the image of the given name is loaded.
   *
   * @param name name of image
   * @return if loaded
   * @throws IllegalArgumentException iff the name is null
   */
  @Override
  public boolean isLoaded(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Null name");
    }
    return imageMap.containsKey(name);
  }

  /**
   * Brightens/darkens the given image and creates a new one.
   *
   * @param imageName    image to brighten
   * @param value        value to brighten/darken by
   * @param newImageName new name to give the image
   * @throws IllegalArgumentException iff brightness value is outside of range [-255, 255] or the
   *                                  image is not loaded, or the parameters are null
   */
  @Override
  public void brighten(String imageName, int value, String newImageName)
      throws IllegalArgumentException {
    if (imageName == null || newImageName == null) {
      throw new IllegalArgumentException("Null parameters");
    }
    if (!imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Specified image is not loaded");
    }
    if (value > 255 || value < -255) {
      throw new IllegalArgumentException("Given value is invalid");
    }
    IImage brightenedImage = imageMap.get(imageName).brighten(value);
    imageMap.put(newImageName, brightenedImage);
  }

  /**
   * Flips the image horizontally.
   *
   * @param imageName     image to flip
   * @param destImageName new name to give the image
   * @throws IllegalArgumentException if the image is not loaded or parameters are null
   */
  @Override
  public void horizontalFlip(String imageName, String destImageName)
      throws IllegalArgumentException {
    if (imageName == null || destImageName == null) {
      throw new IllegalArgumentException("Null parameters");
    }
    if (!imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Specified image is not loaded");
    }
    IImage flippedImage = imageMap.get(imageName).horizontalFlip();
    imageMap.put(destImageName, flippedImage);

  }

  /**
   * Flips the image vertically.
   *
   * @param imageName     image to flip
   * @param destImageName new name to give the image
   * @throws IllegalArgumentException if the image is not loaded or parameters are null
   */
  @Override
  public void verticalFlip(String imageName, String destImageName) throws IllegalArgumentException {
    if (imageName == null || destImageName == null) {
      throw new IllegalArgumentException("Null parameters");
    }
    if (!imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Specified image is not loaded");
    }
    IImage flippedImage = imageMap.get(imageName).verticalFlip();
    imageMap.put(destImageName, flippedImage);
  }

  /**
   * Saves the image with the given name.
   *
   * @param imageName image to save
   * @return the Image object, sent to the controller to handle file IO
   * @throws IllegalArgumentException if the given image is not loaded
   */
  @Override
  public IImage save(String imageName) {
    if (imageMap.containsKey(imageName)) {
      return this.imageMap.get(imageName);
    } else {
      throw new IllegalArgumentException("Unloaded image.");
    }
  }

  /**
   * Creates a new image from a given one's greyscale component (given a certain component type),
   * using a mask image to specify the region to apply the greyscale to.
   *
   * @param imageName     image to greyscale
   * @param destImageName new name to give to image
   * @param maskImageName image to use as a mask
   * @param component     component type the user wants (e.g. the red channel or the image)
   * @throws IllegalArgumentException if the images are not loaded, or the arguments are null
   */
  @Override
  public void greyScale(String imageName, String destImageName, String maskImageName,
      GreyscaleComponent component) throws IllegalArgumentException {
    if (imageName == null || destImageName == null || maskImageName == null || component == null) {
      throw new IllegalArgumentException("Invalid greyscale.");
    }
    if (!this.imageMap.containsKey(imageName) || !this.imageMap.containsKey(maskImageName)) {
      throw new IllegalArgumentException("Unloaded image(s).");
    }
    this.imageMap.put(destImageName, this.imageMap.get(imageName)
        .getComponent(component, generateMaskArray(this.imageMap.get(maskImageName))));
  }

  /**
   * Creates a new image from a given one's greyscale component (given a certain component type).
   *
   * @param imageName     image to greyscale
   * @param destImageName new name to give to image
   * @param component     component type the user wants (e.g. the red channel or the image or the
   * @throws IllegalArgumentException if the image is not loaded or the parameters are null
   */
  @Override
  public void greyScale(String imageName, String destImageName, GreyscaleComponent component)
      throws IllegalArgumentException {
    if (imageName == null || destImageName == null || component == null) {
      throw new IllegalArgumentException("Null parameters");
    }
    if (!imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Specified image is not loaded");
    }
    IImage greyScaledImage = imageMap.get(imageName).getComponent(component);
    imageMap.put(destImageName, greyScaledImage);
  }

  /**
   * Applies the given color transform to the image, and creates a new transformed image object of
   * the given name. Only applies it to the region of the image specified by the passed mask image.
   *
   * @param imageName     image to transform
   * @param destImageName new image name
   * @param maskImageName image to use as a mask
   * @param transform     transform to apply
   * @throws IllegalArgumentException if the images are not loaded, or the arguments are null
   */
  @Override
  public void colorTransform(String imageName, String destImageName, String maskImageName,
      IColorTransform transform) throws IllegalArgumentException {
    if (imageName == null || destImageName == null || maskImageName == null || transform == null) {
      throw new IllegalArgumentException("Invalid color transform.");
    }
    if (!this.imageMap.containsKey(imageName) || !this.imageMap.containsKey(maskImageName)) {
      throw new IllegalArgumentException("Unloaded image(s).");
    }
    this.imageMap.put(destImageName, transform.applyTransform(imageMap.get(imageName),
        generateMaskArray(imageMap.get(maskImageName))));
  }

  /**
   * Applies the given color transform to the image, and creates a new transformed image object of
   * the given name.
   *
   * @param imageName     image to transform
   * @param destImageName new image name
   * @param transform     transform to apply
   * @throws IllegalArgumentException if the image is not loaded, or the arguments are null
   */
  @Override
  public void colorTransform(String imageName, String destImageName, IColorTransform transform)
      throws IllegalArgumentException {
    if (imageName == null || destImageName == null || transform == null) {
      throw new IllegalArgumentException("Invalid color transform.");
    }
    if (!this.imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Unloaded image.");
    }
    this.imageMap.put(destImageName, transform.applyTransform(imageMap.get(imageName)));
  }

  /**
   * Applies the given filter to the image, and creates a new filtered image object of the given
   * name.
   *
   * @param imageName     image to filter
   * @param destImageName new image name
   * @param filter        filter to apply
   * @throws IllegalArgumentException if the image is not loaded, or the arguments are null
   */
  @Override
  public void filter(String imageName, String destImageName, IFilter filter)
      throws IllegalArgumentException {
    if (imageName == null || destImageName == null || filter == null) {
      throw new IllegalArgumentException("Invalid filter.");
    }
    if (!this.imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Unloaded image.");
    }
    this.imageMap.put(destImageName, filter.applyFilter(imageMap.get(imageName)));
  }

  /**
   * Applies the given filter to the image, and creates a new filtered image object of the given
   * name. Only applies it to the region of the image specified by the passed mask image.
   *
   * @param imageName     image to filter
   * @param destImageName new image name
   * @param maskImageName image to use as a mask
   * @param filter        filter to apply
   * @throws IllegalArgumentException if the images are not loaded, or the arguments are null
   */
  @Override
  public void filter(String imageName, String destImageName, String maskImageName, IFilter filter)
      throws IllegalArgumentException {
    if (imageName == null || destImageName == null || maskImageName == null || filter == null) {
      throw new IllegalArgumentException("Invalid filter.");
    }
    if (!this.imageMap.containsKey(imageName) || !this.imageMap.containsKey(maskImageName)) {
      throw new IllegalArgumentException("Unloaded image(s).");
    }
    this.imageMap.put(destImageName, filter.applyFilter(imageMap.get(imageName),
        generateMaskArray(imageMap.get(maskImageName))));
  }

  /**
   * Downscales the image and saves the given image to the new name.
   *
   * @param imageName     image to downscale
   * @param destImageName new image name
   * @param newWidth      new width of modified image
   * @param newHeight     new height of modified image
   * @throws IllegalArgumentException if the image is not loaded, or the arguments are null
   */
  @Override
  public void downscale(String imageName, String destImageName, int newWidth, int newHeight)
      throws IllegalArgumentException {
    if (imageName == null || destImageName == null) {
      throw new IllegalArgumentException("Invalid downscale.");
    }
    if (!this.imageMap.containsKey(imageName)) {
      throw new IllegalArgumentException("Unloaded image.");
    }
    try {
      this.imageMap.put(destImageName, this.imageMap.get(imageName).downscale(newWidth,
          newHeight));
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private boolean[][] generateMaskArray(IImage maskImage) {
    boolean[][] mask = new boolean[maskImage.getHeight()][maskImage.getWidth()];
    IPixel blackPixel = new Pixel(0, 0, 0);
    for (int i = 0; i < maskImage.getHeight(); i++) {
      for (int j = 0; j < maskImage.getWidth(); j++) {
        mask[i][j] = !maskImage.getPixelAt(i, j).equals(blackPixel);
      }
    }
    return mask;
  }
}
