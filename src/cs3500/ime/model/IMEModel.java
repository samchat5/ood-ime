package cs3500.ime.model;

import cs3500.ime.GreyscaleComponent;
import cs3500.ime.image.IImage;
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
   */
  @Override
  public void load(IImage image, String name) {
    imageMap.put(name, image);
  }

  /**
   * Return if the image of the given name is loaded.
   *
   * @param name name of image
   * @return if loaded
   */
  @Override
  public boolean isLoaded(String name) {
    return imageMap.containsKey(name);
  }

  /**
   * Brightens/darkens the given image and creates a new one.
   *
   * @param imageName    image to brighten
   * @param value        value to brighten/darken by
   * @param newImageName new name to give the image
   * @throws IllegalArgumentException iff brightness value is outside of range [-255, 255] or the
   *                                  image is not loaded
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
   * @throws IllegalArgumentException if the image is not loaded
   */
  @Override
  public void horizontalFlip(String imageName, String destImageName)
      throws IllegalArgumentException {

  }

  /**
   * Flips the image vertically.
   *
   * @param imageName     image to flip
   * @param destImageName new name to give the image
   * @throws IllegalArgumentException if the image is not loaded
   */
  @Override
  public void verticalFlip(String imageName, String destImageName) throws IllegalArgumentException {

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
    return null;
  }

  /**
   * Creates a new image from a given one's greyscale component (given a certain component type).
   *
   * @param imageName     image to greyscale
   * @param destImageName new name to give to image
   * @param component     component type the user wants (e.g. the red channel or the image or the
   * @throws IllegalArgumentException if the image is not loaded
   */
  @Override
  public void greyScale(String imageName, String destImageName, GreyscaleComponent component)
      throws IllegalArgumentException {

  }
}
