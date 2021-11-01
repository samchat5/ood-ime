package cs3500.ime.controller.commands;

import cs3500.ime.GreyscaleComponent;
import cs3500.ime.model.IIMEModel;

/**
 * Represents the "x-component" command, where x is one of the 6 greyscale components found in
 * {@code GreyscaleComponent}, with the given arguments passed to the constructor.
 */
public class GreyScale implements IIMECommand {

  private final GreyscaleComponent component;
  private final String destImageName;
  private final String imageName;

  /**
   * Constructor for this class.
   *
   * @param imageName     image to greyscale
   * @param destImageName name to give new greyscaled image
   * @param component     greyscale component to get from the given image
   */
  public GreyScale(String imageName, String destImageName, GreyscaleComponent component) {
    this.imageName = imageName;
    this.destImageName = destImageName;
    this.component = component;
  }

  /**
   * Runs a greyscaling command on the model object.
   *
   * @param model model to run command on
   * @throws IllegalStateException iff the command fails
   */
  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    try {
      model.greyScale(imageName, destImageName, component);
    } catch (Exception e) {
      throw new IllegalStateException("Invalid " + component.toString() + " component command.");
    }
  }
}
