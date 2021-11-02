package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;

/**
 * Represents the "vertical-flip" command, with the given arguments image name and destination image
 * name passed to the constructor.
 */
public class VerticalFlip implements IIMECommand {

  private final String destImageName;
  private final String imageName;

  /**
   * Constructor for this class.
   *
   * @param imageName     image to flip
   * @param destImageName name to give to flipped image
   */
  public VerticalFlip(String imageName, String destImageName) {
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Runs a vertical flip on the model object.
   *
   * @param model model to run command on
   * @throws IllegalStateException iff the command fails
   */
  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    try {
      model.verticalFlip(imageName, destImageName);
    } catch (Exception e) {
      throw new IllegalStateException("Invalid vertical flip command.");
    }
  }
}
