package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.ImageUtil;

/**
 * Represents the "load" command, which loads the image at the file path to the model and assigns it
 * a name.
 */
public class Load implements IIMECommand {

  private final String imageName;
  private final String filePath;

  /**
   * Constructor for this class.
   *
   * @param filePath  filepath to load image from
   * @param imageName name to assign image
   */
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
    try {
      model.load(ImageUtil.readPPM(filePath), imageName);
    } catch (Exception e) {
      throw new IllegalStateException("Load file not found.");
    }
  }
}
