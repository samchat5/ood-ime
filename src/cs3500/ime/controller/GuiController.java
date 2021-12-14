package cs3500.ime.controller;

import cs3500.ime.controller.commands.BlurCommand;
import cs3500.ime.controller.commands.Brighten;
import cs3500.ime.controller.commands.Downscale;
import cs3500.ime.controller.commands.GreyScale;
import cs3500.ime.controller.commands.HorizontalFlip;
import cs3500.ime.controller.commands.IIMECommand;
import cs3500.ime.controller.commands.Load;
import cs3500.ime.controller.commands.Save;
import cs3500.ime.controller.commands.SepiaCommand;
import cs3500.ime.controller.commands.SharpenCommand;
import cs3500.ime.controller.commands.VerticalFlip;
import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.Image;
import cs3500.ime.model.image.ImageUtil;
import cs3500.ime.model.image.pixel.IPixel;
import cs3500.ime.model.image.pixel.Pixel;
import cs3500.ime.view.IGuiView;
import java.awt.Point;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.IntStream;

/**
 * Concrete class from our GUI controller. Similar to IMEController, but doesn't take in a readable,
 * and the known commands don't need arguments for the image names, since only one image is being
 * operated on at a time.
 */
public class GuiController extends AIMEController implements IGuiController {

  /**
   * Constructor for this GuiController.
   *
   * @param model the model object to call commands on
   * @param view  the view that this controller takes input from
   * @throws IllegalArgumentException if the parameters are null
   */
  public GuiController(IIMEModel model, IGuiView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Null model or view.");
    }
    this.model = model;
    this.view = view;
    this.commands = new Stack<>();

    knownCommands.put("brighten", (Scanner s) -> new Brighten(s.nextInt(), "image", "image"));
    knownCommands.put("vertical-flip", (Scanner s) -> new VerticalFlip("image", "image"));
    knownCommands.put("horizontal-flip", (Scanner s) -> new HorizontalFlip("image", "image"));
    knownCommands.put("value-component", (Scanner s) -> maskCallback(
        new GreyScale("image", view.isPreviewEnabled() ? "preview" : "image", "mask",
            GreyscaleComponent.VALUE)));
    knownCommands.put("luma-component", (Scanner s) -> maskCallback(
        new GreyScale("image", view.isPreviewEnabled() ? "preview" : "image", "mask",
            GreyscaleComponent.LUMA)));
    knownCommands.put("intensity-component", (Scanner s) -> maskCallback(
        new GreyScale("image", view.isPreviewEnabled() ? "preview" : "image", "mask",
            GreyscaleComponent.INTENSITY)));
    knownCommands.put("red-component", (Scanner s) -> maskCallback(
        new GreyScale("image", view.isPreviewEnabled() ? "preview" : "image", "mask",
            GreyscaleComponent.RED)));
    knownCommands.put("blue-component", (Scanner s) -> maskCallback(
        new GreyScale("image", view.isPreviewEnabled() ? "preview" : "image", "mask",
            GreyscaleComponent.BLUE)));
    knownCommands.put("green-component", (Scanner s) -> maskCallback(
        new GreyScale("image", view.isPreviewEnabled() ? "preview" : "image", "mask",
            GreyscaleComponent.GREEN)));
    knownCommands.put("sepia", (Scanner s) -> maskCallback(
        new SepiaCommand("image", view.isPreviewEnabled() ? "preview" : "image", "mask")));
    knownCommands.put("sharpen", (Scanner s) -> maskCallback(
        new SharpenCommand("image", view.isPreviewEnabled() ? "preview" : "image", "mask")));
    knownCommands.put("blur", (Scanner s) -> maskCallback(
        new BlurCommand("image", view.isPreviewEnabled() ? "preview" : "image", "mask")));
    knownCommands.put("load", (Scanner s) -> new Load(s.nextLine().strip(), "image"));
    knownCommands.put("save", (Scanner s) -> new Save(s.nextLine().strip(), "image"));
    knownCommands.put("downscale", (Scanner s) -> new Downscale("image", "image", s.nextInt(),
        s.nextInt()));
  }

  private IIMECommand maskCallback(IIMECommand command) {
    if (!model.isLoaded("mask")) {
      setFullMask();
    }
    return command;
  }

  @Override
  public void run() throws IllegalStateException {
    IGuiView view = (IGuiView) this.view;
    if (!view.isPreviewEnabled()) {
      setFullMask();
    }
    super.run();
    view.loadImage(
        ImageUtil.getBufferedImage(model.save(view.isPreviewEnabled() ? "preview" : "image")));
  }

  private void setFullMask() {
    if (model.isLoaded("image")) {
      int width = model.save("image").getWidth();
      int height = model.save("image").getHeight();
      model.load(new Image(height, width, IntStream.range(0, height).mapToObj((int y) ->
          IntStream.range(0, width).mapToObj((int x) -> new Pixel(255))
              .toArray(IPixel[]::new)).toArray(IPixel[][]::new)), "mask");
    }
  }

  @Override
  public void setScanner(Scanner s) throws IllegalArgumentException {
    if (s == null) {
      throw new IllegalArgumentException("Null scanner.");
    }
    this.scanner = s;
  }

  @Override
  public void setMask(Point topLeft) throws IllegalArgumentException {
    if (topLeft == null) {
      throw new IllegalArgumentException("Null point.");
    }
    if (!model.isLoaded("image")) {
      throw new IllegalArgumentException("No image loaded.");
    }
    int width = model.save("image").getWidth();
    int height = model.save("image").getHeight();
    if (topLeft.getX() < 0 || topLeft.getX() >= width || topLeft.getY() < 0
        || topLeft.getY() >= height) {
      throw new IllegalArgumentException("Point out of bounds.");
    }
    model.load(new Image(height, width, IntStream.range(0, height).mapToObj(
            (int y) -> IntStream.range(0, width).mapToObj(
                (int x) -> topLeft.x <= x && x < topLeft.x + 200 && topLeft.y <= y
                    && y < topLeft.y + 200))
        .map(row -> row.map(col -> col ? new Pixel(255) : new Pixel(0)).toArray(Pixel[]::new))
        .toArray(Pixel[][]::new)), "mask");
  }
}