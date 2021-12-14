package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.colortransform.Sepia;

/**
 * Represents the Sepia command. Takes in the image to do a sepia color-transform on, and the name
 * of the new sepia image.
 */
public class SepiaCommand extends ACommand {

  public SepiaCommand(String imageName, String destImageName) {
    super((IIMEModel model) ->
        model.colorTransform(imageName, destImageName, new Sepia()));
  }

  public SepiaCommand(String imageName, String destImageName, String maskImageName) {
    super((IIMEModel model) -> model.colorTransform(imageName, destImageName, maskImageName,
        new Sepia()));
  }
}
