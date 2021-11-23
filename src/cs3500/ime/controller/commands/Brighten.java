package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;

/**
 * Represents the "brighten" command, with the given value, image name, and destination image name
 * arguments passed to the constructor.
 */
public class Brighten extends ACommand {

  /**
   * Constructor for this class.
   *
   * @param value        value to brighten image by
   * @param imageName    name of image to brighten
   * @param newImageName image name to save new image to
   */
  public Brighten(int value, String imageName, String newImageName) {
    super((IIMEModel model) -> model.brighten(imageName, value, newImageName), "Invalid "
        + "brightening command.");
  }
}
