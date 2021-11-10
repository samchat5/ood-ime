package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.colortransform.Luma;

/**
 * Represents a command that creates a luma-greyscale object using TRANSFORMS using the command
 * "greyscale", rather than through "luma-component".
 */
public class LumaTransform implements IIMECommand {

  private final String destImageName;
  private final String imageName;

  public LumaTransform(String imageName, String destImageName) {
    this.imageName = imageName;
    this.destImageName = destImageName;
  }

  /**
   * Runs this command on the model object.
   *
   * @param model model to run command on
   * @throws IllegalStateException iff the command fails
   */
  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    try {
      model.colorTransform(imageName, destImageName, new Luma());
    } catch (Exception e) {
      throw new IllegalStateException("Invalid greyscale command.");
    }
  }
}
