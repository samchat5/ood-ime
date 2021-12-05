package controller.HW7;

import java.util.function.Consumer;
import model.HW7.MosaicModel;

/**
 * Implements the Mosaic command for the controller, passing the command to the model. Purposefully
 * package-private, so that only MosaicController and MosaicGuiController can use it.
 */
public class Mosaic implements Runnable {

  private final int seedCount;
  private final int randSeed;
  private final MosaicModel model;
  private Consumer<String> messageCallback;

  /**
   * Constructor for the Mosaic command, including a messageCallback function to print a message to
   * a view.
   *
   * @param seedCount       the number of seeds to use
   * @param randSeed        the seed used for random number generation
   * @param model           model to run command on
   * @param messageCallback function to print a message to a view
   */
  public Mosaic(int seedCount, int randSeed, MosaicModel model, Consumer<String> messageCallback) {
    this.seedCount = seedCount;
    this.randSeed = randSeed;
    this.model = model;
    this.messageCallback = messageCallback;
  }

  /**
   * Constructor for the Mosaic command. Callback function for a message is not provided.
   *
   * @param seedCount the number of seeds to use
   * @param randSeed  the seed used for random number generation
   * @param model     model to run command on
   */
  public Mosaic(int seedCount, int randSeed, MosaicModel model) {
    this.seedCount = seedCount;
    this.randSeed = randSeed;
    this.model = model;
  }

  @Override
  public void run() {
    model.mosaic(seedCount, randSeed);
    if (messageCallback != null) {
      messageCallback.accept("Mosaicking...");
    }
  }
}
