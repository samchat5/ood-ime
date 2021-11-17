package cs3500.ime.controller;

import cs3500.ime.controller.commands.IIMECommand;
import cs3500.ime.controller.commands.Load;
import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.ImageUtil;
import cs3500.ime.view.IGuiView;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class GuiController implements IGuiController {

  private final IIMEModel model;
  private final IGuiView view;
  private final Stack<IIMECommand> commands;
  private int currentFileId = 0;
  private String currentFile;

  public GuiController(IIMEModel model, IGuiView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Null model, readable or view.");
    }
    this.model = model;
    this.view = view;
    this.commands = new Stack<>();
    /*
    knownCommands.put("save", (String path) -> new Save(s.next(), s.next()));
    knownCommands.put("brighten", (Scanner s) -> new Brighten(s.nextInt(), s.next(), s.next()));
    knownCommands.put("vertical-flip", (Scanner s) -> new VerticalFlip(s.next(), s.next()));
    knownCommands.put("horizontal-flip", (Scanner s) -> new HorizontalFlip(s.next(), s.next()));
    knownCommands.put("value-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.VALUE));
    knownCommands.put("luma-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.LUMA));
    knownCommands.put("intensity-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.INTENSITY));
    knownCommands.put("red-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.RED));
    knownCommands.put("blue-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.BLUE));
    knownCommands.put("green-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.GREEN));
    knownCommands.put("sepia", (Scanner s) -> new SepiaCommand(s.next(), s.next()));
    knownCommands.put("sharpen", (Scanner s) -> new SharpenCommand(s.next(), s.next()));
    knownCommands.put("blur", (Scanner s) -> new BlurCommand(s.next(), s.next()));
    knownCommands.put("greyscale", (Scanner s) -> new LumaTransform(s.next(), s.next()));
    */
  }

  @Override
  public void run() throws IllegalStateException {

  }

  @Override
  public void loadImage(File imageFile) {
    try {
      try {
        this.currentFileId++;
        this.currentFile = "image" + this.currentFileId;
        new Load(imageFile.getAbsolutePath(), this.currentFile).run(model);
        this.view.loadImage(ImageUtil.getBufferedImage(model.save(currentFile)));
      } catch (Exception e) {
        this.view.displayMessage(e.getMessage());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
