package controller;

import static model.ImageUtil.readFile;
import static model.ImageUtil.writeToFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import model.Image;
import model.ImageModel;
import view.ImageView;

/**
 * Represents the implementation of the image controller. Makes sure the data is taken from the user
 * and pushed to the model. Also ensures that the view is showing the proper messages.
 */
public class ImageControllerImpl implements ImageController {

  protected final Map<String, Consumer<String>> knownCommands;
  protected final List<String> menu;
  private final ImageModel model;
  private final ImageView view;
  private final Readable read;

  /**
   * Acts as the constructor for the ImageControllerImpl class. Connects the model, view, and user
   * inputs.
   *
   * @param model represents the model being used.
   * @param view  represents the view being used.
   * @param read  represents the location of the users inputs.
   * @throws IllegalArgumentException if any of the arguments are null.
   */
  public ImageControllerImpl(ImageModel model, ImageView view, Readable read)
      throws IllegalArgumentException {
    if (model == null || view == null || read == null) {
      throw new IllegalArgumentException("The model, view, and readable cannot be null.");
    }

    this.model = model;
    this.view = view;
    this.read = read;
    this.menu = new ArrayList<>(
        Arrays.asList("Instructions:", "To load an image type: load filepath",
            "To save an image type: save filepath",
            "To show the red component of an image type: make-red filepath",
            "To show the blue component of an image type: make-blue filepath",
            "To show the green component of an image type: make-green filepath",
            "To show the value component of an image type: value-component "
                + "filepath", "To blur an image type: blur filepath",
            "To sharpen an image type: sharpen filepath",
            "To make an image sepia type: sepia filepath",
            "To make an image greyscale type: greyscale filepath",
            "To do a horizontal flip type: horizontal-flip filepath",
            "To do a vertical flip type: vertical-flip filepath",
            "To brighten an image type: brighten amount",
            "To darken an image type: darken amount"));
    this.knownCommands = new HashMap<>();
    knownCommands.put("load", (String s) -> {
      System.out.println("load -> " + s);
      new Load(s).run();
    });
    knownCommands.put("save", (String s) -> {
      System.out.println("save -> " + s);
      new Save(s).run();
    });
    knownCommands.put("make-red", (String s) -> new RedComponent().run());
    knownCommands.put("make-blue", (String s) -> new BlueComponent().run());
    knownCommands.put("make-green", (String s) -> new GreenComponent().run());
    knownCommands.put("value-component", (String s) -> new Value().run());
    knownCommands.put("change-intensity", (String s) -> new Intensity().run());
    knownCommands.put("luma-component", (String s) -> new Luma().run());
    knownCommands.put("horizontal-flip", (String s) -> new HorizontalFlip().run());
    knownCommands.put("vertical-flip", (String s) -> new VerticalFlip().run());
    knownCommands.put("brighten", (String s) -> new Brighten(Integer.parseInt(s)).run());
    knownCommands.put("darken", (String s) -> new Darken(Integer.parseInt(s)).run());
    knownCommands.put("quit", (String s) -> new Quit().run());
    knownCommands.put("q", (String s) -> new Quit().run());
    knownCommands.put("blur", (String s) -> new Blur().run());
    knownCommands.put("sharpen", (String s) -> new Sharpen().run());
    knownCommands.put("greyscale", (String s) -> new Greyscale().run());
    knownCommands.put("sepia", (String s) -> new Sepia().run());
  }

  /**
   * Represents the method used to run the image processor. Takes in user inputs and applies to
   * related actions to the image provided.
   */
  @Override
  public void begin() throws IllegalStateException {
    Scanner input = new Scanner(this.read); //creating scanner that reads the string
    String nextInput; // represents the next input from the readable
    menu();

    while (input.hasNext()) {
      nextInput = input.next();
      if (validCommand(nextInput)) {
        this.knownCommands.get(nextInput).accept(input.hasNext() ? input.next() : "");
      } else {
        tryCatchRenderMessage("Invalid input \n" + nextInput + " is not a valid "
            + "input. Please re-enter: \n");
      }
    }
  }

  private void menu() {
    for (String s : this.menu) {
      tryCatchRenderMessage(s);
    }
    tryCatchRenderMessage("To quit the program type: quit or q");
    tryCatchRenderMessage(
        "For example, to load kick.ppm located in the res folder type: \nload res/kick.ppm");
    tryCatchRenderMessage("Insert a valid command: ");
  }

  // Determines if the given string is a valid command, True if it's valid and false otherwise
  // A command is valid if it is in the list of known commands.
  private boolean validCommand(String command) {
    for (String knownCommand : this.knownCommands.keySet()) {
      if (knownCommand.equals(command)) {
        return true;
      }
    }
    return false;
  }

  // handles try-catch for the renderMessage method
  protected void tryCatchRenderMessage(String msg) {
    try {
      this.view.renderMessage(msg);
    } catch (IOException ex) {
      throw new IllegalStateException("Error transmitting output to appendable");
    }
  }

  private class Load implements Runnable {

    final String filepath;

    /**
     * Sets the filepath to the user inputted string. The filepath reveals the location of the
     * image.
     *
     * @param filepath is the location of the image's ppm fil
     */
    public Load(String filepath) {
      this.filepath = filepath;
    }

    @Override
    public void run() {
      tryCatchRenderMessage("Loading.....");
      Image givenImage = readFile(this.filepath);
      model.overWriteImage(givenImage);
    }
  }

  private class Save implements Runnable {

    final String filepath;

    /**
     * Saves the image to the given path. Overwrites the image if it already exists.
     *
     * @param path represents the location of the image.
     */
    public Save(String path) {
      this.filepath = path;
    }

    @Override
    public void run() {
      writeToFile(model.getImage(), filepath);
      tryCatchRenderMessage("Saving.....");
    }
  }

  private class RedComponent implements Runnable {


    @Override
    public void run() {
      model.makeRed();
      tryCatchRenderMessage("Making Red.....");
    }
  }

  private class BlueComponent implements Runnable {

    @Override
    public void run() {
      model.makeBlue();
      tryCatchRenderMessage("Making Blue.....");
    }
  }

  private class GreenComponent implements Runnable {

    @Override
    public void run() {
      model.makeGreen();
      tryCatchRenderMessage("Making Green.....");
    }
  }

  private class Value implements Runnable {

    @Override
    public void run() {
      model.imageValue();
      tryCatchRenderMessage("Modifying image by value.....");
    }
  }

  private class Intensity implements Runnable {

    @Override
    public void run() {
      model.imageIntensity();
      tryCatchRenderMessage("Modifying image by intensity...");
    }
  }

  private class Luma implements Runnable {

    @Override
    public void run() {
      model.imageLuma();
      tryCatchRenderMessage("Modifying image by luma.....");
    }
  }

  private class HorizontalFlip implements Runnable {

    @Override
    public void run() {
      model.flipHorizontal();
      tryCatchRenderMessage("Horizontally flipping image.....");

    }
  }

  private class VerticalFlip implements Runnable {

    @Override
    public void run() {
      model.flipVertical();
      tryCatchRenderMessage("Vertically flipping image.....");

    }
  }

  private class Brighten implements Runnable {

    final int amount;

    Brighten(int amount) {
      this.amount = amount;
    }

    @Override
    public void run() {
      model.brighten(amount);
      tryCatchRenderMessage("Brightening image....");
    }
  }

  private class Darken implements Runnable {

    final int amount;

    Darken(int amount) {
      this.amount = amount;
    }

    @Override
    public void run() {
      model.darken(amount);
      tryCatchRenderMessage("Darkening image....");
    }
  }

  private class Blur implements Runnable {

    @Override
    public void run() {
      model.gaussianBlur();
      tryCatchRenderMessage("Blurring image....");
    }
  }

  private class Sharpen implements Runnable {

    @Override
    public void run() {
      model.sharpen();
      tryCatchRenderMessage("Sharpening image....");
    }
  }

  private class Sepia implements Runnable {

    @Override
    public void run() {
      model.sepia();
      tryCatchRenderMessage("Adding Sepia filter....");
    }
  }

  private class Greyscale implements Runnable {

    @Override
    public void run() {
      model.greyscale();
      tryCatchRenderMessage("Adding greyscale filter....");
    }
  }

  private class Quit implements Runnable {

    @Override
    public void run() {
      tryCatchRenderMessage("Quitting.....");
    }

  }
}
