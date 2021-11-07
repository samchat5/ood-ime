package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.filter.Sharpen;

public class SharpenCommand implements IIMECommand {

  private final String destImageName;
  private final String imageName;

  public SharpenCommand(String imageName, String destImageName) {
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
      model.filter(imageName, destImageName, new Sharpen());
    } catch (Exception e) {
      throw new IllegalStateException("Illegal Sharpen command.");
    }
  }
}
