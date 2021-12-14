package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.IImage;
import cs3500.ime.model.image.ImageUtil;

/**
 * Represents the "save" command, which saves the given image to the given filepath. Handles file IO
 * when calling {@code ImageUtil} methods.
 */
public class Save extends ACommand {

  /**
   * Constructor for this class.
   *
   * @param filePath  where to save the image to
   * @param imageName image to save
   */
  public Save(String filePath, String imageName) {
    super((IIMEModel model) -> {
      String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
      IImage image = model.save(imageName);
      if (ext.equalsIgnoreCase("ppm")) {
        ImageUtil.writePPM(filePath, image);
      } else {
        ImageUtil.writeImage(filePath, image);
      }
    });
  }
}
