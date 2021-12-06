package model.hw7;

import model.ImageModel;

/**
 * Represents a model with the added functionality for mosaicking an image. Extends off the previous
 * interface for the model, {@code ImageModel}.
 */
public interface MosaicModel extends ImageModel {

  /**
   * Mosaic the current image.
   *
   * @param seedCount the number of seeds to use for the mosaic.
   * @param randSeed  the random seed to use for the mosaic.
   * @throws IllegalStateException if the seed count is <= 0, or if the image is not loaded.
   */
  void mosaic(int seedCount, int randSeed) throws IllegalStateException;
}
