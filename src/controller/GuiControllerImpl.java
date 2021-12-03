package controller;

import static model.ImageUtil.readFile;
import static model.ImageUtil.writeToFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import model.Image;
import model.ImageModel;
import view.GuiView;

/**
 * Represents a GUI implementation of the image controller. Makes sure the data is taken from the
 * user and pushed to the model. Also ensures that the view is showing the proper messages.
 */
public class GuiControllerImpl implements ImageController {

  private final ImageModel model;
  private final GuiView view;
  private final ArrayList<String> knownCommands;

  /**
   * Acts as the constructor for the ImageControllerImpl class. Connects the model, view, and user
   * inputs.
   *
   * @param model represents the model being used.
   * @param view  represents the view being used.
   * @throws IllegalArgumentException if any of the arguments are null.
   */
  public GuiControllerImpl(ImageModel model, GuiView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("The model, view, and readable cannot be null.");
    }

    this.model = model;
    this.view = view;
    this.knownCommands = new ArrayList<>();
    knownCommands.add("load");
    knownCommands.add("save");
    knownCommands.add("make-red");
    knownCommands.add("make-blue");
    knownCommands.add("make-green");
    knownCommands.add("value-component");
    knownCommands.add("change-intensity");
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
   * Starts the program and prompts the controller to accept values from the view through the
   * command callback Consumer in the view.
   */
  public void begin() {
    this.view.setCommandCallback(this::accept);
  }

  /**
   * Accepts string from the Command Callback in the view and passes it to the ProcessGivenCommand
   * method and refreshes the view.
   *
   * @param s the command from the view.
   */
  public void accept(String s) {
    processGivenCommand(s);
    view.refresh();
  }

  private void processGivenCommand(String s) {
    Scanner input = new Scanner(s); //creating scanner that reads the string
    String nextInput = ""; //represents the next input from the readable

    if (!hasQuit(nextInput)) {
      nextInput = input.next();

      //if quit then call run method from quit class
      if (nextInput.equalsIgnoreCase("q") ||
          nextInput.equalsIgnoreCase("quit")) {
        new Quit().run();
      } else {
        if (validCommand(nextInput)) {
          if (nextInput.equals("load") || nextInput.equals("save")) {
            String afterCommand = input.next();
            System.out.println(nextInput + "-> " + afterCommand);
            loadSaveHandler(nextInput, afterCommand);
          } else {
            chooseClass(nextInput);
          }
        } else {
          tryCatchRenderMessage("Invalid input " + nextInput + " is not a valid input.");
        }
      }
    }
  }

  //Deals with the load and save commands
  private void loadSaveHandler(String command, String path) {
    if (command.equals("load")) {
      new Load(path).run();
    } else {
      new Save(path).run();
    }
  }

  //If a command is not load and save, chooses the class that has the method for that command
  private void chooseClass(String givenCommand) {
    int twentyfive = 25;

    switch (givenCommand) {
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
      case "change-intensity":
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
        new Brighten(twentyfive).run();
        break;
      case "darken":
        new Darken(twentyfive).run();
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
        throw new IllegalStateException("Should never get to this point");
    }
  }

  //checks if the person has quit
  private boolean hasQuit(String str) {
    return str.equalsIgnoreCase("q") || str.equalsIgnoreCase("quit");
  }


  //handles try-catch for the renderMessage method
  private void tryCatchRenderMessage(String msg) {
    try {
      this.view.renderMessage(msg);
    } catch (IOException ex) {
      throw new IllegalStateException("Error transmitting output to appendable");
    }
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

  private static class Quit implements Runnable {

    @Override
    public void run() {
      System.exit(0);
    }
  }

  private class Load implements Runnable {

    final String filepath;

    /**
     * Sets the filepath to the user inputted string. The filepath reveals the location of the
     * image.
     *
     * @param filepath is the location of the image's ppm file.
     */
    public Load(String filepath) {
      this.filepath = filepath;
    }

    @Override
    public void run() {
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
    }
  }

  private class RedComponent implements Runnable {


    @Override
    public void run() {
      model.makeRed();
    }
  }

  private class BlueComponent implements Runnable {

    @Override
    public void run() {
      model.makeBlue();
    }
  }

  private class GreenComponent implements Runnable {

    @Override
    public void run() {
      model.makeGreen();
    }
  }

  private class Value implements Runnable {

    @Override
    public void run() {
      model.imageValue();
    }
  }

  private class Intensity implements Runnable {

    @Override
    public void run() {
      model.imageIntensity();
    }
  }

  private class Luma implements Runnable {

    @Override
    public void run() {
      model.imageLuma();
    }
  }

  private class HorizontalFlip implements Runnable {

    @Override
    public void run() {
      model.flipHorizontal();

    }
  }

  private class VerticalFlip implements Runnable {

    @Override
    public void run() {
      model.flipVertical();

    }
  }

  private class Brighten implements Runnable {

    final int amount;

    /**
     * Creates an instance of Brighten with the amount equal to the given amount.
     *
     * @param amount the amount to brighten by.
     */
    Brighten(int amount) {
      this.amount = amount;
    }

    @Override
    public void run() {
      model.brighten(amount);
    }
  }

  private class Darken implements Runnable {

    final int amount;

    /**
     * Creates an instance of Darken with the amount equal to the given amount.
     *
     * @param amount the amount to darken by.
     */
    Darken(int amount) {
      this.amount = amount;
    }

    @Override
    public void run() {
      model.darken(amount);
    }
  }

  private class Blur implements Runnable {

    @Override
    public void run() {
      model.gaussianBlur();
    }
  }

  private class Sharpen implements Runnable {

    @Override
    public void run() {
      model.sharpen();
    }
  }

  private class Sepia implements Runnable {

    @Override
    public void run() {
      model.sepia();
    }
  }

  private class Greyscale implements Runnable {

    @Override
    public void run() {
      model.greyscale();
    }
  }

}
