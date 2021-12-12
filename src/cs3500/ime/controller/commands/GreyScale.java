package cs3500.ime.controller.commands;

import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.IIMEModel;

/**
 * Represents the "x-component" command, where x is one of the 6 greyscale components found in
 * {@code GreyscaleComponent}, with the given arguments passed to the constructor.
 */
public class GreyScale extends ACommand {

  /**
   * Constructor for this class.
   *
   * @param imageName     image to greyscale
   * @param destImageName name to give new greyscaled image
   * @param component     greyscale component to get from the given image
   * @throws IllegalArgumentException if component is null
   */
  public GreyScale(String imageName, String destImageName, GreyscaleComponent component) {
    super((IIMEModel model) -> model.greyScale(imageName, destImageName, component),
        String.format("Invalid %s component command.", component == null ? "" : component));
  }

  public GreyScale(String imageName, String destImageName,
      String maskImageName, GreyscaleComponent component) {
    super((IIMEModel model) -> model.greyScale(imageName, destImageName, maskImageName, component),
        String.format("Invalid %s component command.", component == null ? "" : component));
  }
}
