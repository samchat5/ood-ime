package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.IImage;
import cs3500.ime.model.image.ImageUtil;

/**
 * Represents the "save" command, which saves the given image to the given filepath. Handles file IO
 * when calling {@code ImageUtil} methods.
 */
public class Save implements IIMECommand {

  private final String imageName;
  private final String filePath;

  /**
   * Constructor for this class.
   *
   * @param filePath  where to save the image to
   * @param imageName image to save
   */
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
      throw new IllegalStateException("Save file/image not found.");
    }
  }
}
