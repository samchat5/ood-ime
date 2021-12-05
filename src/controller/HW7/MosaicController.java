package controller.HW7;

import controller.ImageControllerImpl;
import java.util.Random;
import model.HW7.MosaicModel;
import view.ImageView;

public class MosaicController extends ImageControllerImpl {


  /**
   * Acts as the constructor for the ImageControllerImpl class. Connects the model, view, and user
   * inputs.
   *
   * @param model represents the model being used.
   * @param view  represents the view being used.
   * @param read  represents the location of the users inputs.
   * @throws IllegalArgumentException if any of the arguments are null.
   */
  public MosaicController(MosaicModel model, ImageView view, Readable read)
      throws IllegalArgumentException {
    super(model, view, read);
    this.knownCommands.put("mosaic", (String s) -> new Mosaic(Integer.parseInt(s),
        new Random().nextInt(), model, this::tryCatchRenderMessage).run());
    this.menu.add("To mosaic an image type: mosaic seedCount");
  }
}