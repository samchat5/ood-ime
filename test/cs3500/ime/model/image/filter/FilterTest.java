package cs3500.ime.model.image.filter;

import static org.junit.Assert.assertEquals;

import cs3500.ime.model.image.Image;
import cs3500.ime.model.image.pixel.IPixel;
import cs3500.ime.model.image.pixel.Pixel;
import org.junit.Test;

public class FilterTest {

  private final IFilter blur = new Blur();
  private final IFilter sharpen = new Sharpen();

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

  @Test
  public void testBlur() {
    IPixel[][] testArr = {{new Pixel(174, 241, 145), new Pixel(4, 115, 243), new Pixel(134, 56,
        208)}, {new Pixel(231, 6, 0), new Pixel(55, 163, 160), new Pixel(27, 155, 158)},
        {new Pixel(119, 223, 123), new Pixel(114, 145, 13), new Pixel(39, 196, 228)}};
    IPixel[][] blurred = pixelArrayFromChannelArrays(
        new int[][]{{76, 62, 40}, {108, 89, 42}, {76, 71, 30}},
        new int[][]{{85, 96, 57}, {96, 138, 106}, {84, 119, 96}},
        new int[][]{{76, 134, 112}, {69, 135, 130}, {42, 77, 88}});
    assertEquals(blur.applyFilter(new Image(3, 3, testArr)), new Image(3, 3, blurred));
  }

  @Test
  public void testSharpen() {
    IPixel[][] testArr = pixelArrayFromChannelArrays(
        new int[][]{{106, 125, 199, 33, 60}, {12, 86, 99, 241, 90}, {231, 230, 61, 244, 210},
            {241, 94, 103, 147, 3}, {125, 28, 24, 15, 11}},
        new int[][]{{43, 140, 46, 237, 78}, {166, 75, 94, 198, 130}, {187, 190, 160, 124, 130},
            {165, 48, 199, 111, 209}, {115, 175, 20, 114, 203}},
        new int[][]{{164, 240, 53, 49, 79}, {96, 224, 77, 57, 236}, {207, 140, 120, 43, 110},
            {136, 159, 59, 58, 47}, {89, 150, 216, 111, 231}});
    IPixel[][] sharpened = pixelArrayFromChannelArrays(
        new int[][]{{59, 120, 189, 85, 49}, {106, 213, 241, 255, 210}, {255, 255, 182, 255, 255},
            {255, 249, 145, 206, 82}, {134, 58, 0, 0, 0}},
        new int[][]{{53, 109, 81, 255, 150}, {235, 196, 203, 255, 219}, {224, 255, 150, 255, 179},
            {254, 240, 225, 255, 255}, {117, 201, 0, 196, 232}},
        new int[][]{{229, 255, 65, 64, 114}, {255, 255, 139, 132, 255}, {249, 255, 47, 27, 95},
            {223, 255, 119, 161, 89}, {107, 229, 209, 173, 216}});
    assertEquals(sharpen.applyFilter(new Image(5, 5, testArr)), new Image(5, 5, sharpened));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullBlur() {
    blur.applyFilter(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullSharpen() {
    sharpen.applyFilter(null);
  }

  // TODO: test invalid constructors
}
