package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;

/**
 * Represents the "vertical-flip" command, with the given arguments image name and destination image
 * name passed to the constructor.
 */
public class VerticalFlip extends ACommand {

  /**
   * Constructor for this class.
   *
   * @param imageName     image to flip
   * @param destImageName name to give to flipped image
   */
  public VerticalFlip(String imageName, String destImageName) {
    super((IIMEModel m) -> m.verticalFlip(imageName, destImageName), "Invalid vertical flip "
        + "command.");
  }
}
