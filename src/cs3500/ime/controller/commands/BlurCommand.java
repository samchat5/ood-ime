package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.filter.Blur;

/**
 * Represents the Blurring command. Takes in the image to blur, and the name of the newly blurred
 * image.
 */
public class BlurCommand extends ACommand {

  public BlurCommand(String imageName, String destImageName) {
    super((IIMEModel model) -> model.filter(imageName, destImageName, new Blur()), "Illegal blur "
        + "command.");
  }

  public BlurCommand(String imageName, String destImageName, String maskImageName) {
    super((IIMEModel model) -> model.filter(imageName, destImageName, maskImageName, new Blur()),
        "Illegal blur command.");
  }

  /**
   * Runs this command on the model object.
   *
   * @param model model to run command on
   * @throws IllegalStateException iff the command fails
   */
  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    super.run(model);
  }
}
