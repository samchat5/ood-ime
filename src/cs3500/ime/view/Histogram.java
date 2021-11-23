package cs3500.ime.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.Raster;
import java.util.Arrays;
import java.util.OptionalDouble;
import javax.swing.JPanel;

public class Histogram extends JPanel {

  private final Raster raster;
  private final int rasterHeight;
  private final int rasterWidth;

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

    double CANVAS_SCALE_X = getSize().getWidth() / 256;
    double CANVAS_SCALE_Y = getSize().getHeight() / 256;
    ((Graphics2D) g).scale(CANVAS_SCALE_X, CANVAS_SCALE_Y);

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
