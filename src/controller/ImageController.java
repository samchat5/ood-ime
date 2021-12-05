package controller;

/**
 * Represents a controller for an image processor.
 */
public interface ImageController {

  /**
   * Represents the method used to run the image processor. Takes in user inputs and applies to
   * related actions to the image provided.
   */
  void begin() throws IllegalStateException;
}
