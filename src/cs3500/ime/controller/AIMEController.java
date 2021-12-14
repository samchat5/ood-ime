package cs3500.ime.controller;

import cs3500.ime.controller.commands.IIMECommand;
import cs3500.ime.model.IIMEModel;
import cs3500.ime.view.IIMEView;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

/**
 * Abstract controller class for IIMEController. Since there's significant overlap between the run()
 * methods for the GuiController and IMEController, this method abstracts out the duplication, and
 * is inherited as a super class by both those classes.
 */
public abstract class AIMEController implements IIMEController {

  protected Map<String, Function<Scanner, IIMECommand>> knownCommands = new HashMap<>();
  protected IIMEModel model;
  protected IIMEView view;
  protected Stack<IIMECommand> commands;
  protected Scanner scanner;
  protected boolean hasQuit;

  @Override
  public void run() throws IllegalStateException {
    try {
      while (scanner.hasNext()) {
        IIMECommand c;
        String in = scanner.next();
        if (in.equalsIgnoreCase("quit")) {
          this.hasQuit = true;
          return;
        }
        Function<Scanner, IIMECommand> cmd = knownCommands.get(in);
        if (cmd == null) {
          this.view.displayMessage("Unknown command.\n");
          scanner.nextLine();
        } else {
          try {
            c = cmd.apply(scanner);
            commands.add(c);
            c.run(model);
          } catch (NoSuchElementException e) {
            this.view.displayMessage("Invalid command usage.\n");
            scanner.nextLine();
          } catch (IllegalStateException e) {
            this.view.displayMessage(e.getMessage() + "\n");
          }
        }
      }
    } catch (NoSuchElementException | IOException | IllegalArgumentException e) {
      throw new IllegalStateException();
    }
  }
}
