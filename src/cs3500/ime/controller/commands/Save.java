package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import java.io.FileNotFoundException;

public class Save implements IIMECommand {

  private final String imageName;
  private final String filePath;

  public Save(String filePath, String imageName) {
    this.filePath = filePath;
    this.imageName = imageName;
  }

  /**
   * Runs Save on the model object.
   *
   * @param model model to run command on
   * @throws IllegalStateException iff the command fails
   */
  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    try {
      model.save(filePath, imageName);
    } catch (FileNotFoundException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
