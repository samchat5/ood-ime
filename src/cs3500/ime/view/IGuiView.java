package cs3500.ime.view;

import cs3500.ime.controller.IGuiController;
import java.awt.image.BufferedImage;

/**
 * Interface for GUI view. Extends the {@code displayMessage()} method from IIMEView, and adds the
 * following new methods to help communication between the controller and GUI, and to load a given
 * image to the display.
 */
public interface IGuiView extends IIMEView {

  /**
   * Loads the given image to the image panel.
   *
   * @param image BufferedImage to load
   */
  void loadImage(BufferedImage image);

  /**
   * Accepts a controller object that a view uses in event listeners to handle user interactions.
   *
   * @param features IGuiController object to use as controller for this GUI
   */
  void acceptFeatures(IGuiController features);

  boolean isPreviewEnabled();
}
