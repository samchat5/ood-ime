package controller;

import static model.ImageUtil.readFile;
import static model.ImageUtil.writeToFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Image;
import model.ImageModel;
import view.ImageView;

/**
 * Represents the implementation of the image controller. Makes sure the data is taken
 * from the user and pushed to the model. Also ensures that the view is showing the proper messages.
 */
public class ImageControllerImpl implements ImageController {

  private ImageModel model;
  private ImageView view;
  private Readable read;
  private ArrayList<String> knownCommands;

  /**
   * Acts as the constructor for the ImageControllerImpl class.
   * Connects the model, view, and user inputs.
   * @param model represents the model being used.
   * @param view represents the view being used.
   * @param read represents the location of the users inputs.
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
    this.knownCommands = new ArrayList<>();
    knownCommands.add("load");
    knownCommands.add("save");
    knownCommands.add("make-red");
    knownCommands.add("make-blue");
    knownCommands.add("make-green");
    knownCommands.add("value-component");
    knownCommands.add("change intensity");
    knownCommands.add("luma-component");
    knownCommands.add("horizontal-flip");
    knownCommands.add("vertical-flip");
    knownCommands.add("brighten");
    knownCommands.add("darken");
    knownCommands.add("quit");
    knownCommands.add("q");
    knownCommands.add("blur");
    knownCommands.add("sharpen");
    knownCommands.add("greyscale");
    knownCommands.add("sepia");
  }

  /**
   * Represents the method used to run the image processor.
   * Takes in user inputs and applies to related actions to the image provided.
   */
  public void begin() throws IllegalStateException {
    Scanner input = new Scanner(this.read); //creating scanner that reads from the readable
    String nextInput = ""; //represents the next input from the readable

    menu();

    while (!hasQuit(nextInput)) {
      //if there's another input then set it to next input
      if (input.hasNext()) {
        nextInput = input.next();

        //if quit then call run method from quit class
        if (nextInput.equalsIgnoreCase("q") ||
            nextInput.equalsIgnoreCase("quit")) {

          new Quit().run();
          break;
        } else {

          //check for valid inputs, if invalid ask to re-enter value
          if (validCommand(nextInput)) {
            String afterCommand = input.next();
            chooseClass(nextInput,afterCommand);

          } else {
            tryCatchRenderMessage("Invalid input \n" + nextInput + " is not a valid "
                + "input. Please re-enter: \n");
          }
        }
      }
    }
  }

  private void menu() {
    tryCatchRenderMessage("Instructions:");
    tryCatchRenderMessage("To load an image type: load filepath");
    tryCatchRenderMessage("To save an image type: save filepath");
    tryCatchRenderMessage("To show the red component of an image type: make-red filepath");
    tryCatchRenderMessage("To show the blue component of an image type: make-blue filepath");
    tryCatchRenderMessage("To show the green component of an image type: make-green filepath");
    tryCatchRenderMessage("To show the value component of an image type: value-component "
        + "filepath");
    tryCatchRenderMessage("To blur an image type: blur filepath");
    tryCatchRenderMessage("To sharpen an image type: sharpen filepath");
    tryCatchRenderMessage("To make an image sepia type: sepia filepath");
    tryCatchRenderMessage("To make an image greyscale type: greyscale filepath");
    tryCatchRenderMessage("To do a horizontal flip type: horizontal-flip filepath");
    tryCatchRenderMessage("To do a vertical flip type: vertical-flip filepath");
    tryCatchRenderMessage("To brighten an image type: brighten amount");
    tryCatchRenderMessage("To darken an image type: darken amount");
    tryCatchRenderMessage("To quit the program type: quit or q");
    tryCatchRenderMessage("For example, to load kick.ppm located in the res folder type: \n"
        + "load res/kick.ppm");
    tryCatchRenderMessage("Insert a valid command: ");
  }

  //Determines if the program is over. If the given string is equal to "q" or "quit" regardless of
  //the case then it'll return true. Otherwise, it will return false.
  private boolean hasQuit(String str) {
    return str.equalsIgnoreCase("q") || str.equalsIgnoreCase("quit");
  }

  //Determines if the given string is a valid command, True if it's valid and false otherwise
  //A command is valid if it is in the list of known commands.
  private boolean validCommand(String command) {
    for (String knownCommand : this.knownCommands) {
      if (knownCommand.equals(command)) {
        return true;
      }
    }
    return false;
  }

  private void chooseClass(String givenCommand, String next) {

    switch (givenCommand) {

      case "load":
        new Load(next).run();
        break;
      case "save":
        new Save(next).run();
        break;
      case "make-red":
        new RedComponent().run();
        break;
      case "make-blue":
        new BlueComponent().run();
        break;
      case "make-green":
        new GreenComponent().run();
        break;
      case "value-component":
        new Value().run();
        break;
      case "change intensity":
        new Intensity().run();
        break;
      case "luma-component":
        new Luma().run();
        break;
      case "horizontal-flip":
        new HorizontalFlip().run();
        break;
      case "vertical-flip":
        new VerticalFlip().run();
        break;
      case "brighten":
        new Brighten(Integer.parseInt(next)).run();
        break;
      case "darken":
        new Darken(Integer.parseInt(next)).run();
        break;
      case "blur":
        new Blur().run();
        break;
      case "sharpen":
        new Sharpen().run();
        break;
      case "sepia":
        new Sepia().run();
        break;
      case "greyscale":
        new Greyscale().run();
        break;
      default:
        throw new IllegalStateException("Error");
    }
  }

  //handles try-catch for the renderMessage method
  private void tryCatchRenderMessage(String msg) {
    try {
      this.view.renderMessage(msg);
    } catch (IOException ex) {
      throw new IllegalStateException("Error transmitting output to appendable");
    }
  }

  private class Load implements Runnable {
    String filepath;

    /**
     * Sets the filepath to the user inputted string.
     * The filepath reveals the location of the image.
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
    String filepath;

    /**
     * Saves the image to the given path.
     * Overwrites the image if it already exists.
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
    int amount;

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
    int amount;

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
      tryCatchRenderMessage("Quiting.....");
    }

  }


}
