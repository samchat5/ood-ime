package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.colortransform.Luma;

/**
 * Represents a command that creates a luma-greyscale object using TRANSFORMS using the command
 * "greyscale", rather than through "luma-component".
 */
public class LumaTransform extends ACommand {

  public LumaTransform(String imageName, String destImageName) {
    super((IIMEModel m) -> m.colorTransform(imageName, destImageName, new Luma()));
  }

  public LumaTransform(String imageName, String destImageName, String maskImageName) {
    super((IIMEModel m) -> m.colorTransform(imageName, destImageName, maskImageName, new Luma())
    );
  }
}
