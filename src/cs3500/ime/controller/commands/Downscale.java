package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;

public class Downscale extends ACommand {

  public Downscale(String imageName, String destImageName, int newWidth, int newHeight) {
    super((IIMEModel m) -> m.downscale(imageName, destImageName, newWidth, newHeight),
        "Illegal downscale command.");
  }
}
