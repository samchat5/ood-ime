package cs3500.ime.controller.commands;

import cs3500.ime.image.IImage;
import cs3500.ime.image.ImageUtil;
import cs3500.ime.model.IIMEModel;

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
      IImage image = model.save(imageName);
      ImageUtil.writePPM(filePath, image);
    } catch (Exception e) {
      throw new IllegalStateException("Save file not found.");
    }
  }
}
