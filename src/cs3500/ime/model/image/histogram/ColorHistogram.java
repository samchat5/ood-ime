package cs3500.ime.model.image.histogram;

import cs3500.ime.model.image.IImage;
import cs3500.ime.model.image.pixel.IPixel;
import java.util.HashMap;
import java.util.Map;

public class ColorHistogram implements IHistogram {

  private final Map<Integer, Integer> redValueFrequency;
  private final Map<Integer, Integer> greenValueFrequency;
  private final Map<Integer, Integer> blueValueFrequency;
  private final Map<Integer, Integer> intensityValueFrequency;

  public ColorHistogram(IImage img) {
    this.redValueFrequency = new HashMap<>();
    this.greenValueFrequency = new HashMap<>();
    this.blueValueFrequency = new HashMap<>();
    this.intensityValueFrequency = new HashMap<>();

    int imgHeight = img.getHeight();
    int imgWidth = img.getWidth();

    for (int i = 0; i < imgHeight; i++) {
      for (int j = 0; j < imgWidth; j++) {
        IPixel foo = img.getPixelAt(i, j);
        int[] pixelVals = foo.getValues();
        int redVal = pixelVals[0];
        int greenVal = pixelVals[1];
        int blueVal = pixelVals[2];
        int intensityVal = ((redVal + greenVal + blueVal) / 3);

        int currentRedValCount = redValueFrequency.getOrDefault(redVal, 0);
        int currentGreenValCount = greenValueFrequency.getOrDefault(greenVal, 0);
        int currentBlueValCount = blueValueFrequency.getOrDefault(blueVal, 0);
        int currentIntensityValCount = intensityValueFrequency.getOrDefault(intensityVal, 0);

        redValueFrequency.put(redVal, currentRedValCount + 1);
        greenValueFrequency.put(greenVal, currentGreenValCount + 1);
        blueValueFrequency.put(blueVal, currentBlueValCount + 1);
        intensityValueFrequency.put(intensityVal, currentIntensityValCount + 1);
      }
    }
  }
}
