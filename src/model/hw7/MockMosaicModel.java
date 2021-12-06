package model.hw7;

import model.MockModel;

/**
 * Mock model used for testing. Logs "mosaic seed-count" to the given log whenever mosaicking is
 * called. Extends previous {@code MockModel} class.
 */
public class MockMosaicModel extends MockModel implements MosaicModel {

  public MockMosaicModel(StringBuilder log) {
    super(log);
  }

  @Override
  public void mosaic(int seedCount, int randSeed) throws IllegalStateException {
    this.log.append(String.format("mosaic %d", seedCount));
  }
}
