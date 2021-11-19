package cs3500.ime.model.image.histogram;

import cs3500.ime.model.image.IImage;
import cs3500.ime.model.image.pixel.IPixel;
import java.util.HashMap;
import java.util.Map;

public class GreyHistogram implements IHistogram {

  private final Map<Integer, Integer> valueFrequency;

  public GreyHistogram(IImage img) {

    this.valueFrequency = new HashMap<>();

    int imgHeight = img.getHeight();
    int imgWidth = img.getWidth();

    for (int i = 0; i < imgHeight; i++) {
      for (int j = 0; j < imgWidth; j++) {
        IPixel foo = img.getPixelAt(i, j);
        int[] pixelVals = foo.getValues();
        int pixelVal = pixelVals[0];
        int currentValCount = valueFrequency.getOrDefault(pixelVal, 0);
        valueFrequency.put(pixelVal, currentValCount + 1);
      }
    }
  }
}
