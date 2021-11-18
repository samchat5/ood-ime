package cs3500.ime.controller;

import cs3500.ime.controller.commands.BlurCommand;
import cs3500.ime.controller.commands.Brighten;
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
import cs3500.ime.model.image.ImageUtil;
import cs3500.ime.view.IGuiView;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

public class GuiController implements IGuiController {

  private final IIMEModel model;
  private final IGuiView view;
  private final Stack<IIMECommand> commands;
  private final Map<String, Function<Scanner, IIMECommand>> knownCommands = new HashMap<>();
  private int currentFileId = 0;
  private String currentFile;
  private Scanner scanner;

  public GuiController(IIMEModel model, IGuiView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Null model, readable or view.");
    }
    this.model = model;
    this.view = view;
    this.commands = new Stack<>();

    knownCommands.put("brighten", (Scanner s) -> new Brighten(s.nextInt(), this.currentFile,
        this.currentFile));
    knownCommands.put("vertical-flip", (Scanner s) -> new VerticalFlip(this.currentFile,
        this.currentFile));
    knownCommands.put("horizontal-flip", (Scanner s) -> new HorizontalFlip(this.currentFile,
        this.currentFile));
    knownCommands.put("value-component",
        (Scanner s) -> new GreyScale(this.currentFile,
            this.currentFile, GreyscaleComponent.VALUE));
    knownCommands.put("luma-component",
        (Scanner s) -> new GreyScale(this.currentFile,
            this.currentFile, GreyscaleComponent.LUMA));
    knownCommands.put("intensity-component",
        (Scanner s) -> new GreyScale(this.currentFile, this.currentFile,
            GreyscaleComponent.INTENSITY));
    knownCommands.put("red-component",
        (Scanner s) -> new GreyScale(this.currentFile, this.currentFile, GreyscaleComponent.RED));
    knownCommands.put("blue-component",
        (Scanner s) -> new GreyScale(this.currentFile, this.currentFile, GreyscaleComponent.BLUE));
    knownCommands.put("green-component",
        (Scanner s) -> new GreyScale(this.currentFile, this.currentFile, GreyscaleComponent.GREEN));
    knownCommands.put("sepia", (Scanner s) -> new SepiaCommand(this.currentFile, this.currentFile));
    knownCommands.put("sharpen",
        (Scanner s) -> new SharpenCommand(this.currentFile, this.currentFile));
    knownCommands.put("blur", (Scanner s) -> new BlurCommand(this.currentFile, this.currentFile));
  }

  @Override
  public void run() throws IllegalStateException {
    try {
      while (scanner.hasNext()) {
        IIMECommand c;
        String in = scanner.next();
        Function<Scanner, IIMECommand> cmd = knownCommands.get(in);
        if (cmd == null) {
          this.view.displayMessage("Unknown command.\n");
          scanner.nextLine();
        } else {
          try {
            c = cmd.apply(scanner);
            commands.add(c);
            c.run(model);
            this.view.loadImage(ImageUtil.getBufferedImage(model.save(this.currentFile)));
          } catch (NoSuchElementException e) {
            this.view.displayMessage("Invalid command usage.\n");
            scanner.nextLine();
          } catch (Exception e) {
            this.view.displayMessage(e.getMessage() + "\n");
          }
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException();
    }
  }

  @Override
  public void setScanner(Scanner s) {
    this.scanner = s;
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

  @Override
  public void saveImage(String absolutePath) {
    try {
      try {
        new Save(absolutePath, this.currentFile).run(model);
      } catch (IllegalStateException e) {
        this.view.displayMessage(e.getMessage());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
