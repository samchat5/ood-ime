package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import java.io.FileNotFoundException;

public class Load implements IIMECommand {

  private final String imageName;
  private final String filePath;

  public Load(String filePath, String imageName) {
    this.filePath = filePath;
    this.imageName = imageName;
  }

  /**
   * Runs Load on the model object.
   *
   * @param model model to run command on
   * @throws IllegalStateException iff the command fails
   */
  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    try {
      model.load(imageName, filePath);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
