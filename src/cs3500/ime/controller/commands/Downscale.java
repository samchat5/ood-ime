package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;

/**
 * Represents a command that downscales a given image. Takes in the image to downscale, the new
 * image name, and the new width and height.
 */
public class Downscale extends ACommand {

  public Downscale(String imageName, String destImageName, int newWidth, int newHeight) {
    super((IIMEModel m) -> m.downscale(imageName, destImageName, newWidth, newHeight)
    );
  }
}
