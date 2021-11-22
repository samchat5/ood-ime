package cs3500.ime.view;

import java.awt.*;
import java.awt.image.Raster;

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
      // Doing red component as a test

      int[] counter = new int[256];
      int max = 0;
      // loop over height and width, populate counter
      for (int i = 0; i < rasterHeight; i++) {
        for (int j = 0; j < rasterWidth; j++) {
          int redValue = raster.getPixel(j,i,new int[3])[0];
          counter[redValue]++;
          max = Math.max(max, counter[redValue]);
        }
      }
      // keep track of max count
      // draw histogram of size 255 x MAX_COUNT
      g.drawRect(0,0,255,max);
      // draw pixels over x coord, 0 to 256 based on MAX_COUNT - counts
      for (int i = 0; i < 255; i++) {
        g.drawRect(i, counter[i], 1,1); // Draw a 1 by 1 rectangle at the correct spot
                                                    // Essentially should draw a pixel
      }
    }
}
