package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;

/**
 * Represents the "horizontal-flip" command, with the given arguments image name and destination
 * image name passed to the constructor.
 */
public class HorizontalFlip extends ACommand {

  /**
   * Constructor for this class.
   *
   * @param imageName     image to flip
   * @param destImageName name to give to flipped image
   */
  public HorizontalFlip(String imageName, String destImageName) {
    super((IIMEModel m) -> m.horizontalFlip(imageName, destImageName), "Invalid horizontal flip "
        + "command.");
  }
}
