package cs3500.ime.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.Raster;
import java.util.Arrays;
import java.util.OptionalDouble;
import javax.swing.JPanel;

/**
 * Represents the Histogram panel in our GUI. Simply draws the red, green, blue, and intensity value
 * frequencies for the currently loaded images, passed to the histogram as a raster.
 */
public class Histogram extends JPanel {

  private final Raster raster;
  private final int rasterHeight;
  private final int rasterWidth;

  /**
   * Histogram constructor -- takes in a Raster object obtained from a BufferedImage object and uses
   * is to paint the histogram.
   *
   * @param raster image's raster object
   */
  public Histogram(Raster raster) {
    this.raster = raster;
    this.rasterHeight = raster.getHeight();
    this.rasterWidth = raster.getWidth();
  }

  @Override
  public void paint(Graphics g) {
    int[] redCounter = new int[256];
    int[] greenCounter = new int[256];
    int[] blueCounter = new int[256];
    int[] greyCounter = new int[256];
    int max = 0;

    double canvasScaleX = getSize().getWidth() / 256;
    double canvasScaleY = getSize().getHeight() / 256;
    ((Graphics2D) g).scale(canvasScaleX, canvasScaleY);

    for (int i = 0; i < rasterHeight; i++) {
      for (int j = 0; j < rasterWidth; j++) {
        int[] vals = raster.getPixel(j, i, new int[3]);
        OptionalDouble grey = Arrays.stream(vals).average();
        max = Math.max(max, ++redCounter[vals[0]]);
        max = Math.max(max, ++greenCounter[vals[1]]);
        max = Math.max(max, ++blueCounter[vals[2]]);
        max = Math.max(max, ++greyCounter[grey.isPresent() ? (int) grey.getAsDouble() : 0]);
      }
    }

    double scalingFactor = 256f / (double) max;
    int[] redPosList = Arrays.stream(redCounter).map((x) -> (int) (256 - (scalingFactor * x)))
        .toArray();
    int[] greenPosList = Arrays.stream(greenCounter).map((x) -> (int) (256 - (scalingFactor * x)))
        .toArray();
    int[] bluePosList = Arrays.stream(blueCounter).map((x) -> (int) (256 - (scalingFactor * x)))
        .toArray();
    int[] greyPosList = Arrays.stream(greyCounter).map((x) -> (int) (256 - (scalingFactor * x)))
        .toArray();

    for (int i = 0; i < 255; i++) {
      g.setColor(Color.RED);
      g.drawLine(i, redPosList[i], i + 1, redPosList[i + 1]);
      g.setColor(Color.GREEN);
      g.drawLine(i, greenPosList[i], i + 1, greenPosList[i + 1]);
      g.setColor(Color.BLUE);
      g.drawLine(i, bluePosList[i], i + 1, bluePosList[i + 1]);
      g.setColor(Color.GRAY);
      g.drawLine(i, greyPosList[i], i + 1, greyPosList[i + 1]);
    }
  }
}
