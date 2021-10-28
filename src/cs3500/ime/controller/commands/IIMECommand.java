package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;

/**
 * Interface representing a possible command given to an IIMEController. Has one method, {@code
 * run()}, that takes in a model and calls a method on it, passing in private fields based on each
 * concrete Command class.
 */
public interface IIMECommand {

  /**
   * Runs this command on the model object.
   *
   * @param model model to run command on
   * @throws IllegalStateException iff the command fails
   */
  void run(IIMEModel model) throws IllegalStateException;
}
