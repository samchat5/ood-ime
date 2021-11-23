import static org.junit.Assert.assertEquals;

import cs3500.ime.model.image.IImage;
import cs3500.ime.model.image.Image;
import cs3500.ime.model.image.colortransform.BlueComponent;
import cs3500.ime.model.image.colortransform.GreenComponent;
import cs3500.ime.model.image.colortransform.IColorTransform;
import cs3500.ime.model.image.colortransform.Luma;
import cs3500.ime.model.image.colortransform.RedComponent;
import cs3500.ime.model.image.colortransform.Sepia;
import cs3500.ime.model.image.pixel.IPixel;
import cs3500.ime.model.image.pixel.Pixel;
import org.junit.Test;

/**
 * Test for all Color Transforms.
 */
public class ColorTransformTest {

  private final IImage color = new Image(2, 2,
      new IPixel[][]{{new Pixel(12, 44, 198), new Pixel(244, 109, 77)},
          {new Pixel(87, 65, 176), new Pixel(98, 2, 199)}});
  private final IColorTransform red = new RedComponent();
  private final IColorTransform green = new GreenComponent();
  private final IColorTransform blue = new BlueComponent();
  private final IColorTransform luma = new Luma();
  private final IColorTransform sepia = new Sepia();

  private IPixel[][] pixelArrayFromChannelArrays(int[][] red, int[][] green, int[][] blue)
      throws IllegalArgumentException {
    if (red.length != green.length || green.length != blue.length || red.length == 0
        || red[0].length != green[0].length || green[0].length != blue[0].length) {
      throw new IllegalArgumentException();
    }
    IPixel[][] ret = new IPixel[red.length][red[0].length];
    for (int i = 0; i < red.length; i++) {
      for (int j = 0; j < red[i].length; j++) {
        ret[i][j] = new Pixel(red[i][j], green[i][j], blue[i][j]);
      }
    }
    return ret;
  }


  private IPixel[][] pixelArrayFromChannelArrays(int[][] vals)
      throws IllegalArgumentException {
    return pixelArrayFromChannelArrays(vals, vals, vals);
  }

  @Test
  public void testColorTransforms() {
    // Red
    assertEquals(red.applyTransform(color),
        new Image(2, 2, pixelArrayFromChannelArrays(new int[][]{{12, 244}, {87, 98}})));

    // Green
    assertEquals(green.applyTransform(color),
        new Image(2, 2, pixelArrayFromChannelArrays(new int[][]{{44, 109}, {65, 2}})));

    // Blue
    assertEquals(blue.applyTransform(color),
        new Image(2, 2, pixelArrayFromChannelArrays(new int[][]{{198, 77}, {176, 199}})));

    // Luma
    assertEquals(luma.applyTransform(color),
        new Image(2, 2, pixelArrayFromChannelArrays(new int[][]{{48, 135}, {77, 36}})));

    // Sepia
    assertEquals(sepia.applyTransform(color), new Image(2, 2,
        new IPixel[][]{{new Pixel(75, 67, 52), new Pixel(194, 172, 134)},
            {new Pixel(117, 104, 81), new Pixel(77, 69, 53)}}));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRedNull() {
    red.applyTransform(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreenNull() {
    green.applyTransform(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlueNull() {
    blue.applyTransform(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLumaNull() {
    luma.applyTransform(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaNull() {
    sepia.applyTransform(null);
  }
}
