package controller.HW7;

import controller.GuiControllerImpl;
import java.util.Random;
import model.HW7.MosaicModel;
import view.GuiView;

public class MosaicGuiController extends GuiControllerImpl {

  /**
   * Acts as the constructor for the ImageControllerImpl class. Connects the model, view, and user
   * inputs.
   *
   * @param model represents the model being used.
   * @param view  represents the view being used.
   * @throws IllegalArgumentException if any of the arguments are null.
   */
  public MosaicGuiController(MosaicModel model, GuiView view)
      throws IllegalArgumentException {
    super(model, view);
    this.knownCommands.put("mosaic", (String s) -> new Mosaic(Integer.parseInt(s),
        new Random().nextInt(), model).run());
  }

}
