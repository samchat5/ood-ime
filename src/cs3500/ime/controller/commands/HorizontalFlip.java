package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;

/**
 * Represents the "horizontal-flip" command, with the given arguments passed to the constructor.
 */
public class HorizontalFlip implements IIMECommand {

  private final String destImageName;
  private final String imageName;

  /**
   * Constructor for this class.
   *
   * @param imageName     image to flip
   * @param destImageName name to give to flipped image
   */
  public HorizontalFlip(String imageName, String destImageName) {
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Runs a horizontal flip on the model object.
   *
   * @param model model to run command on
   * @throws IllegalStateException iff the command fails
   */
  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    try {
      model.horizontalFlip(imageName, destImageName);
    } catch (Exception e) {
      throw new IllegalStateException("Invalid horizontal flip command.");
    }
  }
}
