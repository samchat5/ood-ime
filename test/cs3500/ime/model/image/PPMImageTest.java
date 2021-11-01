package cs3500.ime.model.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.image.pixel.IPixel;
import cs3500.ime.model.image.pixel.Pixel;
import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

/**
 * Tests for PPMImage
 */
public class PPMImageTest {

  private final IImage randomGreyscaleImage;
  private final IImage emptyImage;

  /**
   * Constructor that initializes test images used by many tests.
   */
  public PPMImageTest() {
    randomGreyscaleImage = generateRandomGreyScaleImage(100, 100);
    emptyImage = new PPMImage(0, 0, new Pixel[][]{});
  }

  private IImage generateRandomGreyScaleImage(int height, int width) {
    IPixel[][] pixelArray = new IPixel[height][width];
    Random rand = new Random();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixelArray[i][j] = new Pixel(rand.nextInt(256));
      }
    }
    return new PPMImage(height, width, pixelArray);
  }

  private IImage generateRandomColorImage(int height, int width) {
    IPixel[][] pixelArray = new Pixel[height][width];
    Random rand = new Random();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixelArray[i][j] = new Pixel(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
      }
    }
    return new PPMImage(height, width, pixelArray);
  }

  private int[][] generateRandomPixelValues(int height, int width) {
    int[][] ret = new int[height][width];
    Random rand = new Random();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        ret[i][j] = rand.nextInt(256);
      }
    }
    return ret;
  }

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
  public void testGetComponent() {
    // test all component enum types

    // test on already greyscale image returns same image, for all component types EXCEPT Luma
    // due to floating point precision
    for (GreyscaleComponent c : Arrays.copyOfRange(GreyscaleComponent.values(), 1, 6)) {
      assertEquals(randomGreyscaleImage.getComponent(c), randomGreyscaleImage);
    }

    // test on empty image
    IImage emptyImage = new PPMImage(0, 0, new Pixel[][]{});
    for (GreyscaleComponent c : GreyscaleComponent.values()) {
      assertEquals(emptyImage.getComponent(c), emptyImage);
    }

    // test on static color image
    IImage color = new PPMImage(2, 2,
        new IPixel[][]{{new Pixel(12, 44, 198), new Pixel(244, 109, 77)},
            {new Pixel(87, 65, 176), new Pixel(98, 2, 199)}});
    assertEquals(color.getComponent(GreyscaleComponent.RED), new PPMImage(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{12, 244}, {87, 98}})));
    assertEquals(color.getComponent(GreyscaleComponent.GREEN), new PPMImage(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{44, 109}, {65, 2}})));
    assertEquals(color.getComponent(GreyscaleComponent.BLUE), new PPMImage(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{198, 77}, {176, 199}})));
    assertEquals(color.getComponent(GreyscaleComponent.LUMA), new PPMImage(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{48, 135}, {77, 36}})));
    assertEquals(color.getComponent(GreyscaleComponent.VALUE), new PPMImage(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{198, 244}, {176, 199}})));
    assertEquals(color.getComponent(GreyscaleComponent.INTENSITY), new PPMImage(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{84, 143}, {109, 99}})));

    // test on random color image
    int[][] reds = generateRandomPixelValues(50, 50);
    int[][] greens = generateRandomPixelValues(50, 50);
    int[][] blues = generateRandomPixelValues(50, 50);

    IImage colorImage = new PPMImage(50, 50, pixelArrayFromChannelArrays(reds, greens, blues));
    IImage redChannel = new PPMImage(50, 50, pixelArrayFromChannelArrays(reds));
    IImage greenChannel = new PPMImage(50, 50, pixelArrayFromChannelArrays(greens));
    IImage blueChannel = new PPMImage(50, 50, pixelArrayFromChannelArrays(blues));

    assertEquals(colorImage.getComponent(GreyscaleComponent.RED), redChannel);
    assertEquals(colorImage.getComponent(GreyscaleComponent.GREEN), greenChannel);
    assertEquals(colorImage.getComponent(GreyscaleComponent.BLUE), blueChannel);

    int[][] valueArr = new int[50][50];
    int[][] lumaArr = new int[50][50];
    int[][] intensityArr = new int[50][50];

    for (int i = 0; i < 50; i++) {
      for (int j = 0; j < 50; j++) {
        valueArr[i][j] = Math.max(greens[i][j], Math.max(reds[i][j], blues[i][j]));
        lumaArr[i][j] = (int) (reds[i][j] * 0.2126 + greens[i][j] * 0.7152 + blues[i][j] * 0.0722);
        intensityArr[i][j] = (reds[i][j] + greens[i][j] + blues[i][j]) / 3;
      }
    }

    assertEquals(colorImage.getComponent(GreyscaleComponent.INTENSITY), new PPMImage(50, 50,
        pixelArrayFromChannelArrays(intensityArr)));
    assertEquals(colorImage.getComponent(GreyscaleComponent.LUMA), new PPMImage(50, 50,
        pixelArrayFromChannelArrays(lumaArr)));
    assertEquals(colorImage.getComponent(GreyscaleComponent.VALUE), new PPMImage(50, 50,
        pixelArrayFromChannelArrays(valueArr)));
  }

  @Test
  public void testBrighten() {
    // test brightening on Image with near-maxed out brightness
    IImage brightImage = new PPMImage(2, 2, new IPixel[][]{{new Pixel(250, 229, 123),
        new Pixel(144, 239, 56)}, {new Pixel(100, 167, 240), new Pixel(56, 212, 199)}});
    assertEquals(brightImage.brighten(20), new PPMImage(2, 2, new IPixel[][]{{new Pixel(255, 249,
        143), new Pixel(164, 255, 76)}, {new Pixel(120, 187, 255), new Pixel(76, 232, 219)}}));
    assertNotEquals(brightImage.brighten(20).brighten(-20), brightImage);

    // test darkening on Image with mined out brightness
    IImage darkImage = new PPMImage(2, 2, new IPixel[][]{{new Pixel(14, 223, 189), new Pixel(24, 123
        , 178)}, {new Pixel(52, 245, 255), new Pixel(18, 177, 189)}});
    assertEquals(darkImage.brighten(-20),
        new PPMImage(2, 2, new IPixel[][]{{new Pixel(0, 203, 169), new Pixel(4, 103
            , 158)}, {new Pixel(32, 225, 235), new Pixel(0, 157, 169)}}));
    assertNotEquals(darkImage.brighten(-20).brighten(20), darkImage);

    // test brighten/darken random greyscale image
    int[][] greyVals = generateRandomPixelValues(50, 45);
    int[][] greyValsBrightened = new int[50][45];
    int[][] greyValsDarkened = new int[50][45];
    IImage grey = new PPMImage(50, 45, pixelArrayFromChannelArrays(greyVals));

    for (int i = 0; i < 50; i++) {
      for (int j = 0; j < 45; j++) {
        greyValsBrightened[i][j] = Math.min(greyVals[i][j] + 26, 255);
        greyValsDarkened[i][j] = Math.max(greyVals[i][j] - 26, 0);
      }
    }

    IImage greyBright = new PPMImage(50, 45, pixelArrayFromChannelArrays(greyValsBrightened));
    IImage greyDark = new PPMImage(50, 45, pixelArrayFromChannelArrays(greyValsDarkened));

    assertEquals(grey.brighten(26), greyBright);
    assertEquals(grey.brighten(-26), greyDark);

    // test brighten/darken on random color image
    int[][] reds = generateRandomPixelValues(50, 50);
    int[][] redsBrightened = new int[50][50];
    int[][] redsDarkened = new int[50][50];

    int[][] greens = generateRandomPixelValues(50, 50);
    int[][] greensBrightened = new int[50][50];
    int[][] greensDarkened = new int[50][50];

    int[][] blues = generateRandomPixelValues(50, 50);
    int[][] bluesBrightened = new int[50][50];
    int[][] bluesDarkened = new int[50][50];

    IImage colorImage = new PPMImage(50, 50, pixelArrayFromChannelArrays(reds, greens, blues));

    for (int i = 0; i < 50; i++) {
      for (int j = 0; j < 50; j++) {
        redsBrightened[i][j] = Math.min(reds[i][j] + 26, 255);
        greensBrightened[i][j] = Math.min(greens[i][j] + 26, 255);
        bluesBrightened[i][j] = Math.min(blues[i][j] + 26, 255);
        redsDarkened[i][j] = Math.max(reds[i][j] - 26, 0);
        greensDarkened[i][j] = Math.max(greens[i][j] - 26, 0);
        bluesDarkened[i][j] = Math.max(blues[i][j] - 26, 0);
      }
    }

    IImage colorBright = new PPMImage(50, 50,
        pixelArrayFromChannelArrays(redsBrightened, greensBrightened, bluesBrightened));
    IImage colorDark = new PPMImage(50, 50,
        pixelArrayFromChannelArrays(redsDarkened, greensDarkened, bluesDarkened));

    assertEquals(colorImage.brighten(26), colorBright);
    assertEquals(colorImage.brighten(-26), colorDark);

    // test on empty image
    assertEquals(emptyImage.brighten(Integer.MAX_VALUE), emptyImage);
    assertEquals(emptyImage.brighten(Integer.MIN_VALUE), emptyImage);
  }

  @Test
  public void testHorizontalFlip() {
    // test flip on odd width
    IImage oneBy11Grey = new PPMImage(1, 11, new IPixel[][]{{new Pixel(212), new Pixel(52),
        new Pixel(23), new Pixel(44), new Pixel(123), new Pixel(243), new Pixel(143), new Pixel(99),
        new Pixel(35), new Pixel(198), new Pixel(67)}});
    IImage oneBy11Color = new PPMImage(1, 11,
        new IPixel[][]{{new Pixel(145, 244, 88), new Pixel(194, 177, 201), new Pixel(205, 184, 207),
            new Pixel(79, 29, 224), new Pixel(42, 133, 119), new Pixel(119, 98, 137),
            new Pixel(15, 138, 58), new Pixel(30, 92, 142), new Pixel(68, 87, 25),
            new Pixel(167, 33, 139), new Pixel(1, 185, 173)}});

    IImage oneBy11GreyFlipped = new PPMImage(1, 11, new IPixel[][]{
        {new Pixel(67), new Pixel(198), new Pixel(35), new Pixel(99), new Pixel(143),
            new Pixel(243), new Pixel(123), new Pixel(44), new Pixel(23), new Pixel(52),
            new Pixel(212)}});
    IImage oneBy11ColorFlipped = new PPMImage(1, 11,
        new IPixel[][]{{new Pixel(1, 185, 173), new Pixel(167, 33, 139), new Pixel(68, 87, 25),
            new Pixel(30, 92, 142), new Pixel(15, 138, 58), new Pixel(119, 98, 137),
            new Pixel(42, 133, 119), new Pixel(79, 29, 224), new Pixel(205, 184, 207),
            new Pixel(194, 177, 201), new Pixel(145, 244, 88)}});

    assertEquals(oneBy11Grey.horizontalFlip(), oneBy11GreyFlipped);
    assertEquals(oneBy11Color.horizontalFlip(), oneBy11ColorFlipped);

    // test flip on even width
    IImage oneBy10Grey = new PPMImage(1, 10, new IPixel[][]{{new Pixel(212), new Pixel(52),
        new Pixel(23), new Pixel(44), new Pixel(123), new Pixel(143), new Pixel(99),
        new Pixel(35), new Pixel(198), new Pixel(67)}});
    IImage oneBy10Color = new PPMImage(1, 10,
        new IPixel[][]{{new Pixel(145, 244, 88), new Pixel(194, 177, 201), new Pixel(205, 184, 207),
            new Pixel(79, 29, 224), new Pixel(42, 133, 119), new Pixel(119, 98, 137),
            new Pixel(30, 92, 142), new Pixel(68, 87, 25),
            new Pixel(167, 33, 139), new Pixel(1, 185, 173)}});

    IImage oneBy10GreyFlipped = new PPMImage(1, 10, new IPixel[][]{
        {new Pixel(67), new Pixel(198), new Pixel(35), new Pixel(99), new Pixel(143),
            new Pixel(123), new Pixel(44), new Pixel(23), new Pixel(52),
            new Pixel(212)}});
    IImage oneBy10ColorFlipped = new PPMImage(1, 10,
        new IPixel[][]{{new Pixel(1, 185, 173), new Pixel(167, 33, 139), new Pixel(68, 87, 25),
            new Pixel(30, 92, 142), new Pixel(119, 98, 137), new Pixel(42, 133, 119),
            new Pixel(79, 29, 224), new Pixel(205, 184, 207), new Pixel(194, 177, 201),
            new Pixel(145, 244, 88)}});

    assertEquals(oneBy10Grey.horizontalFlip(), oneBy10GreyFlipped);
    assertEquals(oneBy10Color.horizontalFlip(), oneBy10ColorFlipped);

    // test flip on Nx1 image returns same image, even and odd
    IImage elevenBy1Grey = new PPMImage(11, 1,
        new IPixel[][]{{new Pixel(212)}, {new Pixel(52)}, {new Pixel(23)}, {new Pixel(44)},
            {new Pixel(123)}, {new Pixel(243)}, {new Pixel(143)}, {new Pixel(99)}, {new Pixel(35)},
            {new Pixel(198)}, {new Pixel(67)}});
    IImage elevenBy1Color = new PPMImage(11, 1,
        new IPixel[][]{{new Pixel(145, 244, 88)}, {new Pixel(194, 177, 201)}, {new Pixel(205, 184,
            207)},
            {new Pixel(79, 29, 224)}, {new Pixel(42, 133, 119)}, {new Pixel(119, 98, 137)},
            {new Pixel(15, 138, 58)}, {new Pixel(30, 92, 142)}, {new Pixel(68, 87, 25)},
            {new Pixel(167, 33, 139)}, {new Pixel(1, 185, 173)}});
    IImage tenBy1Grey = new PPMImage(10, 1,
        new IPixel[][]{{new Pixel(212)}, {new Pixel(52)}, {new Pixel(23)}, {new Pixel(44)},
            {new Pixel(123)}, {new Pixel(143)}, {new Pixel(99)}, {new Pixel(35)}, {new Pixel(198)},
            {new Pixel(67)}});
    IImage tenBy1Color = new PPMImage(10, 1,
        new IPixel[][]{{new Pixel(145, 244, 88)}, {new Pixel(194, 177, 201)},
            {new Pixel(205, 184, 207)}, {new Pixel(79, 29, 224)}, {new Pixel(42, 133, 119)},
            {new Pixel(15, 138, 58)}, {new Pixel(30, 92, 142)}, {new Pixel(68, 87, 25)},
            {new Pixel(167, 33, 139)}, {new Pixel(1, 185, 173)}});

    assertEquals(elevenBy1Grey.horizontalFlip(), elevenBy1Grey);
    assertEquals(elevenBy1Color.horizontalFlip(), elevenBy1Color);
    assertEquals(tenBy1Grey.horizontalFlip(), tenBy1Grey);
    assertEquals(tenBy1Color.horizontalFlip(), tenBy1Color);

    // test flip on mirrored (even AND odd width) image returns same image
    PPMImage mirrorGreyOdd = new PPMImage(1, 5, new IPixel[][]{{new Pixel(123), new Pixel(245),
        new Pixel(198), new Pixel(245), new Pixel(123)}});
    PPMImage mirrorGreyEven = new PPMImage(1, 4, new IPixel[][]{{new Pixel(123), new Pixel(245),
        new Pixel(245), new Pixel(123)}});

    PPMImage mirrorColorOdd = new PPMImage(1, 5,
        new IPixel[][]{{new Pixel(221, 145, 0), new Pixel(165,
            47, 28), new Pixel(78, 212, 189), new Pixel(165, 47, 28), new Pixel(221, 145, 0)
        }});
    PPMImage mirrorColorEven = new PPMImage(1, 4,
        new IPixel[][]{{new Pixel(221, 145, 0), new Pixel(165,
            47, 28), new Pixel(165, 47, 28), new Pixel(221, 145, 0)
        }});

    assertEquals(mirrorGreyOdd.horizontalFlip(), mirrorGreyOdd);
    assertEquals(mirrorGreyEven.horizontalFlip(), mirrorGreyEven);
    assertEquals(mirrorColorOdd.horizontalFlip(), mirrorColorOdd);
    assertEquals(mirrorColorEven.horizontalFlip(), mirrorColorEven);

    // flip empty returns empty
    assertEquals(emptyImage.horizontalFlip(), emptyImage);
  }

  @Test
  public void testVerticalFlip() {
    // test flip on odd width
    IImage elevenBy1Grey = new PPMImage(11, 1,
        new IPixel[][]{{new Pixel(212)}, {new Pixel(52)}, {new Pixel(23)}, {new Pixel(44)},
            {new Pixel(123)}, {new Pixel(243)}, {new Pixel(143)}, {new Pixel(99)},
            {new Pixel(35)},
            {new Pixel(198)}, {new Pixel(67)}});
    IImage elevenBy1Color = new PPMImage(11, 1,
        new IPixel[][]{{new Pixel(145, 244, 88)}, {new Pixel(194, 177, 201)},
            {new Pixel(205, 184, 207)}, {new Pixel(79, 29, 224)}, {new Pixel(42, 133, 119)},
            {new Pixel(119, 98, 137)}, {new Pixel(15, 138, 58)}, {new Pixel(30, 92, 142)},
            {new Pixel(68, 87, 25)}, {new Pixel(167, 33, 139)}, {new Pixel(1, 185, 173)}});

    IImage elevenBy1GreyFlipped = new PPMImage(11, 1, new IPixel[][]{
        {new Pixel(67)}, {new Pixel(198)}, {new Pixel(35)}, {new Pixel(99)}, {new Pixel(143)},
        {new Pixel(243)}, {new Pixel(123)}, {new Pixel(44)}, {new Pixel(23)}, {new Pixel(52)},
        {new Pixel(212)}});
    IImage elevenBy1ColorFlipped = new PPMImage(11, 1,
        new IPixel[][]{{new Pixel(1, 185, 173)}, {new Pixel(167, 33, 139)},
            {new Pixel(68, 87, 25)},
            {new Pixel(30, 92, 142)}, {new Pixel(15, 138, 58)}, {new Pixel(119, 98, 137)},
            {new Pixel(42, 133, 119)}, {new Pixel(79, 29, 224)}, {new Pixel(205, 184, 207)},
            {new Pixel(194, 177, 201)}, {new Pixel(145, 244, 88)}});

    assertEquals(elevenBy1Grey.verticalFlip(), elevenBy1GreyFlipped);
    assertEquals(elevenBy1Color.verticalFlip(), elevenBy1ColorFlipped);

    // test flip on even width
    IImage tenBy1Grey = new PPMImage(10, 1,
        new IPixel[][]{{new Pixel(212)}, {new Pixel(52)}, {new Pixel(23)}, {new Pixel(44)},
            {new Pixel(123)}, {new Pixel(143)}, {new Pixel(99)}, {new Pixel(35)},
            {new Pixel(198)},
            {new Pixel(67)}});
    IImage tenBy1Color = new PPMImage(10, 1,
        new IPixel[][]{{new Pixel(145, 244, 88)}, {new Pixel(194, 177, 201)},
            {new Pixel(205, 184, 207)}, {new Pixel(79, 29, 224)}, {new Pixel(42, 133, 119)},
            {new Pixel(15, 138, 58)}, {new Pixel(30, 92, 142)}, {new Pixel(68, 87, 25)},
            {new Pixel(167, 33, 139)}, {new Pixel(1, 185, 173)}});

    IImage tenBy1GreyFlipped = new PPMImage(10, 1,
        new IPixel[][]{{new Pixel(67)}, {new Pixel(198)}, {new Pixel(35)}, {new Pixel(99)},
            {new Pixel(143)}, {new Pixel(123)}, {new Pixel(44)}, {new Pixel(23)}, {new Pixel(52)},
            {new Pixel(212)}});
    IImage tenBy1ColorFlipped = new PPMImage(10, 1,
        new IPixel[][]{{new Pixel(1, 185, 173)}, {new Pixel(167, 33, 139)},
            {new Pixel(68, 87, 25)},
            {new Pixel(30, 92, 142)}, {new Pixel(15, 138, 58)}, {new Pixel(42, 133, 119)},
            {new Pixel(79, 29, 224)}, {new Pixel(205, 184, 207)}, {new Pixel(194, 177, 201)},
            {new Pixel(145, 244, 88)}});

    assertEquals(tenBy1Grey.verticalFlip(), tenBy1GreyFlipped);
    assertEquals(tenBy1Color.verticalFlip(), tenBy1ColorFlipped);

    // test flip on 1XN image returns same image, even and odd
    IImage oneBy11Grey = new PPMImage(1, 11, new IPixel[][]{{new Pixel(212), new Pixel(52),
        new Pixel(23), new Pixel(44), new Pixel(123), new Pixel(243), new Pixel(143),
        new Pixel(99),
        new Pixel(35), new Pixel(198), new Pixel(67)}});
    IImage oneBy11Color = new PPMImage(1, 11,
        new IPixel[][]{
            {new Pixel(145, 244, 88), new Pixel(194, 177, 201), new Pixel(205, 184, 207),
                new Pixel(79, 29, 224), new Pixel(42, 133, 119), new Pixel(119, 98, 137),
                new Pixel(15, 138, 58), new Pixel(30, 92, 142), new Pixel(68, 87, 25),
                new Pixel(167, 33, 139), new Pixel(1, 185, 173)}});

    IImage oneBy10Grey = new PPMImage(1, 10, new IPixel[][]{{new Pixel(212), new Pixel(52),
        new Pixel(23), new Pixel(44), new Pixel(123), new Pixel(143), new Pixel(99),
        new Pixel(35), new Pixel(198), new Pixel(67)}});
    IImage oneBy10Color = new PPMImage(1, 10,
        new IPixel[][]{
            {new Pixel(145, 244, 88), new Pixel(194, 177, 201), new Pixel(205, 184, 207),
                new Pixel(79, 29, 224), new Pixel(42, 133, 119), new Pixel(119, 98, 137),
                new Pixel(30, 92, 142), new Pixel(68, 87, 25),
                new Pixel(167, 33, 139), new Pixel(1, 185, 173)}});

    assertEquals(oneBy11Grey.verticalFlip(), oneBy11Grey);
    assertEquals(oneBy11Color.verticalFlip(), oneBy11Color);
    assertEquals(oneBy10Grey.verticalFlip(), oneBy10Grey);
    assertEquals(oneBy10Color.verticalFlip(), oneBy10Color);

    // test flip on mirrored (even AND odd width) image returns same image
    PPMImage mirrorGreyOdd = new PPMImage(5, 1, new IPixel[][]{{new Pixel(123)}, {new Pixel(245)},
        {new Pixel(198)}, {new Pixel(245)}, {new Pixel(123)}});
    PPMImage mirrorGreyEven = new PPMImage(4, 1, new IPixel[][]{{new Pixel(123)}, {new Pixel(245)},
        {new Pixel(245)}, {new Pixel(123)}});

    PPMImage mirrorColorOdd = new PPMImage(5, 1,
        new IPixel[][]{{new Pixel(221, 145, 0)}, {new Pixel(165,
            47, 28)}, {new Pixel(78, 212, 189)}, {new Pixel(165, 47, 28)}, {new Pixel(221, 145, 0)
        }});
    PPMImage mirrorColorEven = new PPMImage(4, 1,
        new IPixel[][]{{new Pixel(221, 145, 0)}, {new Pixel(165,
            47, 28)}, {new Pixel(165, 47, 28)}, {new Pixel(221, 145, 0)
        }});

    assertEquals(mirrorGreyOdd.verticalFlip(), mirrorGreyOdd);
    assertEquals(mirrorGreyEven.verticalFlip(), mirrorGreyEven);
    assertEquals(mirrorColorOdd.verticalFlip(), mirrorColorOdd);
    assertEquals(mirrorColorEven.verticalFlip(), mirrorColorEven);

    // flip empty returns empty
    assertEquals(emptyImage.horizontalFlip(), emptyImage);
  }

  @Test
  public void testHorizontalVerticalFlip() {
    // flipping horizontal + vertical = vertical + horizontal
    IImage image = generateRandomColorImage(50, 50);
    assertEquals(image.horizontalFlip().verticalFlip(), image.verticalFlip().horizontalFlip());

    // flipping h + v + h + v = original
    assertEquals(image.horizontalFlip().verticalFlip().horizontalFlip().verticalFlip(), image);
  }

  @Test
  public void testConstructor() {
    IImage greyScale = new PPMImage(2, 2, new IPixel[][]{{new Pixel(233), new Pixel(125)},
        {new Pixel(123), new Pixel(187)}});
    IImage color = new PPMImage(2, 2,
        new IPixel[][]{{new Pixel(12, 44, 198), new Pixel(244, 109, 77)},
            {new Pixel(87, 65, 176), new Pixel(98, 2, 199)}});

    assertEquals(greyScale.toString(),
        "2 2\n255\n233\n233\n233\n125\n125\n125\n123\n123\n123\n187\n187\n187\n");
    assertEquals(color.toString(), "2 2\n255\n12\n44\n198\n244\n109\n77\n87\n65\n176\n98\n2\n199"
        + "\n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHeight() {
    new PPMImage(1, 2, new IPixel[][]{{new Pixel(233), new Pixel(125)},
        {new Pixel(123), new Pixel(187)}});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWidth() {
    new PPMImage(2, 1, new IPixel[][]{{new Pixel(233), new Pixel(125)},
        {new Pixel(123), new Pixel(187)}});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWidthZero() {
    new PPMImage(0, 1, new IPixel[][]{});
  }
}