package cs3500.ime.model;

import cs3500.ime.GreyscaleComponent;

/**
 * Model for this IME program. Concrete implementations contain a mapping of image names to the
 * {@code IImage}s themselves, and the operations supported on those images.
 */
public interface IIMEModel {

  /**
   * Load an image to the image mapping.
   *
   * @param filePath filepath to load from
   * @param name     name to give image
   */
  void load(String filePath, String name);

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
   */
  void horizontalFlip(String imageName, String destImageName);

  /**
   * Flips the image vertically.
   *
   * @param imageName     image to flip
   * @param destImageName new name to give the image
   */
  void verticalFlip(String imageName, String destImageName);

  /**
   * Saves the image to the given filepath.
   *
   * @param imagePath filepath to save to
   * @param imageName image to save
   */
  void save(String imagePath, String imageName);

  /**
   * Creates a new image from a given one's greyscale component (given a certain component type).
   *
   * @param imageName     image to greyscale
   * @param destImageName new name to give to image
   * @param component     component type the user wants (e.g. the red channel or the image or the
   *                      brightness)
   */
  void greyScale(String imageName, String destImageName, GreyscaleComponent component);
}