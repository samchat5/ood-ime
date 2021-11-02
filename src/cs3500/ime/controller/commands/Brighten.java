package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;

/**
 * Represents the "brighten" command, with the given value, image name, and destination image name
 * arguments passed to the constructor.
 */
public class Brighten implements IIMECommand {

  private final String newImageName;
  private final int value;
  private final String imageName;

  /**
   * Constructor for this class.
   *
   * @param value        value to brighten image by
   * @param imageName    name of image to brighten
   * @param newImageName image name to save new image to
   */
  public Brighten(int value, String imageName, String newImageName) {
    this.imageName = imageName;
    this.value = value;
    this.newImageName = newImageName;
  }

  /**
   * Runs the brightening command on the model object.
   *
   * @param model model to run command on
   * @throws IllegalStateException iff the command fails
   */
  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    try {
      model.brighten(imageName, value, newImageName);
    } catch (Exception e) {
      throw new IllegalStateException("Invalid brightening command.");
    }
  }
}
