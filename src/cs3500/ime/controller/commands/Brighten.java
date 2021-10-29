package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;

public class Brighten implements IIMECommand {

  private final String newImageName;
  private final int value;
  private final String imageName;

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
      throw new IllegalStateException(e.getMessage());
    }
  }
}
