package cs3500.ime.model;

import cs3500.ime.model.image.IImage;

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
   */
  void load(IImage image, String name);

  /**
   * Return if the image of the given name is loaded.
   *
   * @param name name of image
   * @return if loaded
   */
  boolean isLoaded(String name);

  /**
   * Brightens/darkens the given image and creates a new one.
   *
   * @param imageName    image to brighten
   * @param value        value to brighten/darken by
   * @param newImageName new name to give the image
   * @throws IllegalArgumentException iff brightness value is outside of range [-255, 255]
   */
  void brighten(String imageName, int value, String newImageName) throws IllegalArgumentException;

  /**
   * Flips the image horizontally.
   *
   * @param imageName     image to flip
   * @param destImageName new name to give the image
   * @throws IllegalArgumentException if the image is not loaded
   */
  void horizontalFlip(String imageName, String destImageName) throws IllegalArgumentException;

  /**
   * Flips the image vertically.
   *
   * @param imageName     image to flip
   * @param destImageName new name to give the image
   * @throws IllegalArgumentException if the image is not loaded
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
   * @throws IllegalArgumentException if the file is not loaded
   */
  void greyScale(String imageName, String destImageName, GreyscaleComponent component)
      throws IllegalArgumentException;
}
