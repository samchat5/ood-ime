package cs3500.ime.controller;

import java.awt.Point;
import java.util.Scanner;

/**
 * Interface for our GuiController. Only adds one more method compared to our IIMEController class,
 * setScanner(), that takes in a scanner to read from. Used by the view to pass input to the
 * controller, which it uses to process and run commands.
 */
public interface IGuiController extends IIMEController {

  void setScanner(Scanner s) throws IllegalArgumentException;

  void setMask(Point topLeft) throws IllegalArgumentException;
}
