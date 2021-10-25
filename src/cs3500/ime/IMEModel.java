package cs3500.ime;

import java.util.Map;

/**
 * Concrete class representing a model for this IME program, mapping image names to the images
 * themselves and executing commands on them given to it from the controller.
 */
public class IMEModel implements IIMEModel {

  private Map<String, IImage> imageMap;

  /**
   * Load an image to the image mapping.
   *
   * @param filePath filepath to load from
   * @param name     name to give image
   */
  @Override
  public void load(String filePath, String name) {

  }

  /**
   * Brightens/darkens the given image and creates a new one.
   *
   * @param imageName    image to brighten
   * @param value        value to brighten/darken by
   * @param newImageName new name to give the image
   * @throws IllegalArgumentException iff brightness value is outside of range [-255, 255]
   */
  @Override
  public void brighten(String imageName, int value, String newImageName)
      throws IllegalArgumentException {

  }

  /**
   * Flips the image horizontally.
   *
   * @param imageName     image to flip
   * @param destImageName new name to give the image
   */
  @Override
  public void horizontalFlip(String imageName, String destImageName) {

  }

  /**
   * Flips the image vertically.
   *
   * @param imageName     image to flip
   * @param destImageName new name to give the image
   */
  @Override
  public void verticalFlip(String imageName, String destImageName) {

  }

  /**
   * Saves the image to the given filepath.
   *
   * @param imagePath filepath to save to
   * @param imageName image to save
   */
  @Override
  public void save(String imagePath, String imageName) {

  }

  /**
   * Creates a new image from a given one's greyscale component (given a certain component type).
   *
   * @param imageName     image to greyscale
   * @param destImageName new name to give to image
   * @param component     component type the user wants (e.g. the red channel or the image or the
   */
  @Override
  public void greyScale(String imageName, String destImageName, GreyscaleComponent component) {

  }
}
