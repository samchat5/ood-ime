package controller.HW7;

import model.HW7.MosaicModel;

/**
 * Implements the Mosaic command for the controller, passing the command to the model. Purposefully
 * package-private, so that only MosaicController and MosaicGuiController can use it.
 */
class Mosaic implements Runnable {

  private final int seedCount;
  private final int randSeed;
  private final MosaicModel model;

  /**
   * Constructor for the Mosaic command.
   *
   * @param seedCount the number of seeds to use
   * @param randSeed  the seed used for random number generation
   * @param model     model to run command on
   */
  Mosaic(int seedCount, int randSeed, MosaicModel model) {
    this.seedCount = seedCount;
    this.randSeed = randSeed;
    this.model = model;
  }

  @Override
  public void run() {
    model.mosaic(seedCount, randSeed);
  }
}
