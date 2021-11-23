package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.ImageUtil;

/**
 * Represents the "load" command, which loads the image at the file path to the model and assigns it
 * a name. IO is done by calling methods in {@code ImageUtil}.
 */
public class Load extends ACommand {

  /**
   * Constructor for this class.
   *
   * @param filePath  filepath to load image from
   * @param imageName name to assign image
   */
  public Load(String filePath, String imageName) {
    super((IIMEModel model) -> {
      String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
      model.load(ext.equalsIgnoreCase("ppm") ? ImageUtil.readPPM(filePath)
          : ImageUtil.readImage(filePath), imageName);
    }, "Load file not found.");
  }
}
