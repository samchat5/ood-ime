package cs3500.ime.controller.commands;

import cs3500.ime.image.ImageUtil;
import cs3500.ime.model.IIMEModel;
import java.io.File;

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
   * @throws IllegalStateException iff the command fails (e.g. the file is not found)
   */
  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    if (!new File(filePath).exists()) {
      throw new IllegalStateException("Load file not found.");
    }
    model.load(ImageUtil.readPPM(filePath), imageName);
  }
}
