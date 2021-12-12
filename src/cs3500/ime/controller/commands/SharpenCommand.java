package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.filter.Sharpen;

/**
 * Represents the Sharpening command. Takes in the name of the image to sharpen, and the new name of
 * the sharpened image.
 */
public class SharpenCommand extends ACommand {

  public SharpenCommand(String imageName, String destImageName) {
    super((IIMEModel model) -> model.filter(imageName, destImageName, new Sharpen()),
        "Illegal Sharpen command.");
  }

  public SharpenCommand(String imageName, String destImageName, String maskImageName) {
    super((IIMEModel model) -> model.filter(imageName, destImageName, maskImageName, new Sharpen()),
        "Illegal Sharpen command.");
  }
}
