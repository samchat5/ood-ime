package model.HW7;

import model.MockModel;

public class MockMosaicModel extends MockModel implements MosaicModel {

  public MockMosaicModel(StringBuilder log) {
    super(log);
  }

  @Override
  public void mosaic(int seedCount, int randSeed) throws IllegalStateException {
    this.log.append(String.format("mosaic %d", seedCount));
  }
}
