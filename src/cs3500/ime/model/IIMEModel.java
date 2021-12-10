package cs3500.ime.model;

import cs3500.ime.model.image.IImage;
import cs3500.ime.model.image.colortransform.IColorTransform;
import cs3500.ime.model.image.filter.IFilter;

/**
 * Model for this IME program. Concrete implementations contain a mapping of image names to the
 * {@code IImage}s themselves, and the operations supported on those images.
 */
public interface IIMEModel {

  /**
   * Load an image to the image mapping.
   *
   * @param image image to load
   * @param name  name to give image
   * @throws IllegalArgumentException iff the image name is null
   */
  void load(IImage image, String name);

  /**
   * Return if the image of the given name is loaded.
   *
   * @param name name of image
   * @return if loaded
   * @throws IllegalArgumentException iff the name is null
   */
  boolean isLoaded(String name);

  /**
   * Brightens/darkens the given image and creates a new one.
   *
   * @param imageName    image to brighten
   * @param value        value to brighten/darken by
   * @param newImageName new name to give the image
   * @throws IllegalArgumentException iff brightness value is outside of range [-255, 255], the
   *                                  image is not loaded, or the parameters are null
   */
  void brighten(String imageName, int value, String newImageName) throws IllegalArgumentException;

  /**
   * Flips the image horizontally.
   *
   * @param imageName     image to flip
   * @param destImageName new name to give the image
   * @throws IllegalArgumentException if the image is not loaded, or the arguments are null
   */
  void horizontalFlip(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Flips the image vertically.
   *
   * @param imageName     image to flip
   * @param destImageName new name to give the image
   * @throws IllegalArgumentException if the image is not loaded, or the arguments are null
   */
  void verticalFlip(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Saves the image with the given name.
   *
   * @param imageName image to save
   * @return the Image object, sent to the controller to handle file IO
   * @throws IllegalArgumentException if the given image is not loaded
   */
  IImage save(String imageName);

  /**
   * Creates a new image from a given one's greyscale component (given a certain component type).
   *
   * @param imageName     image to greyscale
   * @param destImageName new name to give to image
   * @param component     component type the user wants (e.g. the red channel or the image or the
   *                      brightness)
   * @throws IllegalArgumentException if the image is not loaded, or the arguments are null
   */
  void greyScale(String imageName, String destImageName, GreyscaleComponent component)
      throws IllegalArgumentException;

  /**
   * Applies the given color transform to the image, and creates a new transformed image object of
   * the given name.
   *
   * @param imageName     image to transform
   * @param destImageName new image name
   * @param transform     transform to apply
   * @throws IllegalArgumentException if the image is not loaded, or the arguments are null
   */
  void colorTransform(String imageName, String destImageName, IColorTransform transform)
      throws IllegalArgumentException;

  /**
   * Applies the given filter to the image, and creates a new filtered image object of the given
   * name.
   *
   * @param imageName     image to filter
   * @param destImageName new image name
   * @param filter        filter to apply
   * @throws IllegalArgumentException if the image is not loaded, or the arguments are null
   */
  void filter(String imageName, String destImageName, IFilter filter)
      throws IllegalArgumentException;

  /**
   * Downscales the image and saves the given image to the new name.
   *
   * @param imageName     image to downscale
   * @param destImageName new image name
   * @param newWidth      new width of modified image
   * @param newHeight     new height of modified image
   * @throws IllegalArgumentException if the image is not loaded, or the arguments are null
   */
  void downscale(String imageName, String destImageName, int newWidth, int newHeight)
      throws IllegalArgumentException;
}

