package controller.hw7;

import java.util.function.Consumer;
import model.hw7.MosaicModel;

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
   * @throws IllegalArgumentException if model is null
   */
  public Mosaic(int seedCount, int randSeed, MosaicModel model, Consumer<String> messageCallback)
      throws IllegalArgumentException {
    this(seedCount, randSeed, model);
    this.messageCallback = messageCallback;
  }

  /**
   * Constructor for the Mosaic command. Callback function for a message is not provided.
   *
   * @param seedCount the number of seeds to use
   * @param randSeed  the seed used for random number generation
   * @param model     model to run command on
   * @throws IllegalArgumentException if model is null
   */
  public Mosaic(int seedCount, int randSeed, MosaicModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.seedCount = seedCount;
    this.randSeed = randSeed;
    this.model = model;
  }

  @Override
  public void run() {
    try {
      if (messageCallback != null) {
        messageCallback.accept("Mosaicking...");
      }
      model.mosaic(seedCount, randSeed);
    } catch (IllegalStateException e) {
      if (messageCallback != null) {
        messageCallback.accept("Mosaic failed: " + e.getMessage() +
            "\n");
      }
    }
  }
}
