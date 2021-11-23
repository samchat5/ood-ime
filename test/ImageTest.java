import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.image.IImage;
import cs3500.ime.model.image.Image;
import cs3500.ime.model.image.pixel.IPixel;
import cs3500.ime.model.image.pixel.Pixel;
import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

/**
 * Tests for Image.
 */
public class ImageTest {

  private final IImage randomGreyscaleImage;
  private final IImage emptyImage;
  private final double[][] blurKernel = new double[][]{{1. / 16, 1. / 8, 1. / 16},
      {1. / 8, 1. / 4, 1. / 8}, {1. / 16, 1. / 8, 1. / 16}};
  private final double[][] sharpenKernel = new double[][]{
      {-1. / 8, -1. / 8, -1. / 8, -1. / 8, -1. / 8}, {-1. / 8, 1. / 4, 1. / 4, 1. / 4, -1. / 8},
      {-1. / 8, 1. / 4, 1, 1. / 4, -1. / 8}, {-1. / 8, 1. / 4, 1. / 4, 1. / 4, -1. / 8},
      {-1. / 8, -1. / 8, -1. / 8, -1. / 8, -1. / 8}};
  private final double[][] sepiaKernel = new double[][]{{0.393, 0.769, 0.189}, {0.349, 0.686,
      0.168}, {0.272, 0.534, 0.131}};

  /**
   * Constructor that initializes test images used by many tests.
   */
  public ImageTest() {
    randomGreyscaleImage = generateRandomGreyScaleImage();
    emptyImage = new Image(0, 0, new Pixel[][]{});
  }


  private IImage generateRandomGreyScaleImage() {
    IPixel[][] pixelArray = new IPixel[100][100];
    Random rand = new Random();
    for (int i = 0; i < 100; i++) {
      for (int j = 0; j < 100; j++) {
        pixelArray[i][j] = new Pixel(rand.nextInt(256));
      }
    }
    return new Image(100, 100, pixelArray);
  }

  private IImage generateRandomColorImage(int height, int width) {
    IPixel[][] pixelArray = new Pixel[height][width];
    Random rand = new Random();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixelArray[i][j] = new Pixel(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
      }
    }
    return new Image(height, width, pixelArray);
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
    for (GreyscaleComponent c : GreyscaleComponent.values()) {
      assertEquals(emptyImage.getComponent(c), emptyImage);
    }

    // test on static color image
    IImage color = new Image(2, 2,
        new IPixel[][]{{new Pixel(12, 44, 198), new Pixel(244, 109, 77)},
            {new Pixel(87, 65, 176), new Pixel(98, 2, 199)}});
    assertEquals(color.getComponent(GreyscaleComponent.RED), new Image(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{12, 244}, {87, 98}})));
    assertEquals(color.getComponent(GreyscaleComponent.GREEN), new Image(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{44, 109}, {65, 2}})));
    assertEquals(color.getComponent(GreyscaleComponent.BLUE), new Image(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{198, 77}, {176, 199}})));
    assertEquals(color.getComponent(GreyscaleComponent.LUMA), new Image(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{48, 135}, {77, 36}})));
    assertEquals(color.getComponent(GreyscaleComponent.VALUE), new Image(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{198, 244}, {176, 199}})));
    assertEquals(color.getComponent(GreyscaleComponent.INTENSITY), new Image(2, 2,
        pixelArrayFromChannelArrays(new int[][]{{84, 143}, {109, 99}})));

    // test on random color image
    int[][] reds = generateRandomPixelValues(50, 50);
    int[][] greens = generateRandomPixelValues(50, 50);
    int[][] blues = generateRandomPixelValues(50, 50);

    IImage colorImage = new Image(50, 50, pixelArrayFromChannelArrays(reds, greens, blues));
    IImage redChannel = new Image(50, 50, pixelArrayFromChannelArrays(reds));
    IImage greenChannel = new Image(50, 50, pixelArrayFromChannelArrays(greens));
    IImage blueChannel = new Image(50, 50, pixelArrayFromChannelArrays(blues));

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

    assertEquals(colorImage.getComponent(GreyscaleComponent.INTENSITY), new Image(50, 50,
        pixelArrayFromChannelArrays(intensityArr)));
    assertEquals(colorImage.getComponent(GreyscaleComponent.LUMA), new Image(50, 50,
        pixelArrayFromChannelArrays(lumaArr)));
    assertEquals(colorImage.getComponent(GreyscaleComponent.VALUE), new Image(50, 50,
        pixelArrayFromChannelArrays(valueArr)));
  }

  @Test
  public void testBrighten() {
    // test brightening on Image with near-maxed out brightness
    IImage brightImage = new Image(2, 2, new IPixel[][]{{new Pixel(250, 229, 123),
        new Pixel(144, 239, 56)}, {new Pixel(100, 167, 240), new Pixel(56, 212, 199)}});
    assertEquals(brightImage.brighten(20), new Image(2, 2, new IPixel[][]{{new Pixel(255, 249,
        143), new Pixel(164, 255, 76)}, {new Pixel(120, 187, 255), new Pixel(76, 232, 219)}}));
    assertNotEquals(brightImage.brighten(20).brighten(-20), brightImage);

    // test darkening on Image with mined out brightness
    IImage darkImage = new Image(2, 2, new IPixel[][]{{new Pixel(14, 223, 189), new Pixel(24, 123
        , 178)}, {new Pixel(52, 245, 255), new Pixel(18, 177, 189)}});
    assertEquals(darkImage.brighten(-20),
        new Image(2, 2, new IPixel[][]{{new Pixel(0, 203, 169), new Pixel(4, 103
            , 158)}, {new Pixel(32, 225, 235), new Pixel(0, 157, 169)}}));
    assertNotEquals(darkImage.brighten(-20).brighten(20), darkImage);

    // test brighten/darken random greyscale image
    int[][] greyVals = generateRandomPixelValues(50, 45);
    int[][] greyValsBrightened = new int[50][45];
    int[][] greyValsDarkened = new int[50][45];
    IImage grey = new Image(50, 45, pixelArrayFromChannelArrays(greyVals));

    for (int i = 0; i < 50; i++) {
      for (int j = 0; j < 45; j++) {
        greyValsBrightened[i][j] = Math.min(greyVals[i][j] + 26, 255);
        greyValsDarkened[i][j] = Math.max(greyVals[i][j] - 26, 0);
      }
    }

    IImage greyBright = new Image(50, 45, pixelArrayFromChannelArrays(greyValsBrightened));
    IImage greyDark = new Image(50, 45, pixelArrayFromChannelArrays(greyValsDarkened));

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

    IImage colorImage = new Image(50, 50, pixelArrayFromChannelArrays(reds, greens, blues));

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

    IImage colorBright = new Image(50, 50,
        pixelArrayFromChannelArrays(redsBrightened, greensBrightened, bluesBrightened));
    IImage colorDark = new Image(50, 50,
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
    IImage oneBy11Grey = new Image(1, 11, new IPixel[][]{{new Pixel(212), new Pixel(52),
        new Pixel(23), new Pixel(44), new Pixel(123), new Pixel(243), new Pixel(143), new Pixel(99),
        new Pixel(35), new Pixel(198), new Pixel(67)}});
    IImage oneBy11Color = new Image(1, 11,
        new IPixel[][]{{new Pixel(145, 244, 88), new Pixel(194, 177, 201), new Pixel(205, 184, 207),
            new Pixel(79, 29, 224), new Pixel(42, 133, 119), new Pixel(119, 98, 137),
            new Pixel(15, 138, 58), new Pixel(30, 92, 142), new Pixel(68, 87, 25),
            new Pixel(167, 33, 139), new Pixel(1, 185, 173)}});

    IImage oneBy11GreyFlipped = new Image(1, 11, new IPixel[][]{
        {new Pixel(67), new Pixel(198), new Pixel(35), new Pixel(99), new Pixel(143),
            new Pixel(243), new Pixel(123), new Pixel(44), new Pixel(23), new Pixel(52),
            new Pixel(212)}});
    IImage oneBy11ColorFlipped = new Image(1, 11,
        new IPixel[][]{{new Pixel(1, 185, 173), new Pixel(167, 33, 139), new Pixel(68, 87, 25),
            new Pixel(30, 92, 142), new Pixel(15, 138, 58), new Pixel(119, 98, 137),
            new Pixel(42, 133, 119), new Pixel(79, 29, 224), new Pixel(205, 184, 207),
            new Pixel(194, 177, 201), new Pixel(145, 244, 88)}});

    assertEquals(oneBy11Grey.horizontalFlip(), oneBy11GreyFlipped);
    assertEquals(oneBy11Color.horizontalFlip(), oneBy11ColorFlipped);

    // test flip on even width
    IImage oneBy10Grey = new Image(1, 10, new IPixel[][]{{new Pixel(212), new Pixel(52),
        new Pixel(23), new Pixel(44), new Pixel(123), new Pixel(143), new Pixel(99),
        new Pixel(35), new Pixel(198), new Pixel(67)}});
    IImage oneBy10Color = new Image(1, 10,
        new IPixel[][]{{new Pixel(145, 244, 88), new Pixel(194, 177, 201), new Pixel(205, 184, 207),
            new Pixel(79, 29, 224), new Pixel(42, 133, 119), new Pixel(119, 98, 137),
            new Pixel(30, 92, 142), new Pixel(68, 87, 25),
            new Pixel(167, 33, 139), new Pixel(1, 185, 173)}});

    IImage oneBy10GreyFlipped = new Image(1, 10, new IPixel[][]{
        {new Pixel(67), new Pixel(198), new Pixel(35), new Pixel(99), new Pixel(143),
            new Pixel(123), new Pixel(44), new Pixel(23), new Pixel(52),
            new Pixel(212)}});
    IImage oneBy10ColorFlipped = new Image(1, 10,
        new IPixel[][]{{new Pixel(1, 185, 173), new Pixel(167, 33, 139), new Pixel(68, 87, 25),
            new Pixel(30, 92, 142), new Pixel(119, 98, 137), new Pixel(42, 133, 119),
            new Pixel(79, 29, 224), new Pixel(205, 184, 207), new Pixel(194, 177, 201),
            new Pixel(145, 244, 88)}});

    assertEquals(oneBy10Grey.horizontalFlip(), oneBy10GreyFlipped);
    assertEquals(oneBy10Color.horizontalFlip(), oneBy10ColorFlipped);

    // test flip on Nx1 image returns same image, even and odd
    IImage elevenBy1Grey = new Image(11, 1,
        new IPixel[][]{{new Pixel(212)}, {new Pixel(52)}, {new Pixel(23)}, {new Pixel(44)},
            {new Pixel(123)}, {new Pixel(243)}, {new Pixel(143)}, {new Pixel(99)}, {new Pixel(35)},
            {new Pixel(198)}, {new Pixel(67)}});
    IImage elevenBy1Color = new Image(11, 1,
        new IPixel[][]{{new Pixel(145, 244, 88)}, {new Pixel(194, 177, 201)}, {new Pixel(205, 184,
            207)},
            {new Pixel(79, 29, 224)}, {new Pixel(42, 133, 119)}, {new Pixel(119, 98, 137)},
            {new Pixel(15, 138, 58)}, {new Pixel(30, 92, 142)}, {new Pixel(68, 87, 25)},
            {new Pixel(167, 33, 139)}, {new Pixel(1, 185, 173)}});
    IImage tenBy1Grey = new Image(10, 1,
        new IPixel[][]{{new Pixel(212)}, {new Pixel(52)}, {new Pixel(23)}, {new Pixel(44)},
            {new Pixel(123)}, {new Pixel(143)}, {new Pixel(99)}, {new Pixel(35)}, {new Pixel(198)},
            {new Pixel(67)}});
    IImage tenBy1Color = new Image(10, 1,
        new IPixel[][]{{new Pixel(145, 244, 88)}, {new Pixel(194, 177, 201)},
            {new Pixel(205, 184, 207)}, {new Pixel(79, 29, 224)}, {new Pixel(42, 133, 119)},
            {new Pixel(15, 138, 58)}, {new Pixel(30, 92, 142)}, {new Pixel(68, 87, 25)},
            {new Pixel(167, 33, 139)}, {new Pixel(1, 185, 173)}});

    assertEquals(elevenBy1Grey.horizontalFlip(), elevenBy1Grey);
    assertEquals(elevenBy1Color.horizontalFlip(), elevenBy1Color);
    assertEquals(tenBy1Grey.horizontalFlip(), tenBy1Grey);
    assertEquals(tenBy1Color.horizontalFlip(), tenBy1Color);

    // test flip on mirrored (even AND odd width) image returns same image
    Image mirrorGreyOdd = new Image(1, 5, new IPixel[][]{{new Pixel(123), new Pixel(245),
        new Pixel(198), new Pixel(245), new Pixel(123)}});
    Image mirrorGreyEven = new Image(1, 4, new IPixel[][]{{new Pixel(123), new Pixel(245),
        new Pixel(245), new Pixel(123)}});

    Image mirrorColorOdd = new Image(1, 5,
        new IPixel[][]{{new Pixel(221, 145, 0), new Pixel(165,
            47, 28), new Pixel(78, 212, 189), new Pixel(165, 47, 28), new Pixel(221, 145, 0)}});
    Image mirrorColorEven = new Image(1, 4,
        new IPixel[][]{{new Pixel(221, 145, 0), new Pixel(165,
            47, 28), new Pixel(165, 47, 28), new Pixel(221, 145, 0)}});

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
    IImage elevenBy1Grey = new Image(11, 1,
        new IPixel[][]{{new Pixel(212)}, {new Pixel(52)}, {new Pixel(23)}, {new Pixel(44)},
            {new Pixel(123)}, {new Pixel(243)}, {new Pixel(143)}, {new Pixel(99)},
            {new Pixel(35)},
            {new Pixel(198)}, {new Pixel(67)}});
    IImage elevenBy1Color = new Image(11, 1,
        new IPixel[][]{{new Pixel(145, 244, 88)}, {new Pixel(194, 177, 201)},
            {new Pixel(205, 184, 207)}, {new Pixel(79, 29, 224)}, {new Pixel(42, 133, 119)},
            {new Pixel(119, 98, 137)}, {new Pixel(15, 138, 58)}, {new Pixel(30, 92, 142)},
            {new Pixel(68, 87, 25)}, {new Pixel(167, 33, 139)}, {new Pixel(1, 185, 173)}});

    IImage elevenBy1GreyFlipped = new Image(11, 1, new IPixel[][]{
        {new Pixel(67)}, {new Pixel(198)}, {new Pixel(35)}, {new Pixel(99)}, {new Pixel(143)},
        {new Pixel(243)}, {new Pixel(123)}, {new Pixel(44)}, {new Pixel(23)}, {new Pixel(52)},
        {new Pixel(212)}});
    IImage elevenBy1ColorFlipped = new Image(11, 1,
        new IPixel[][]{{new Pixel(1, 185, 173)}, {new Pixel(167, 33, 139)},
            {new Pixel(68, 87, 25)},
            {new Pixel(30, 92, 142)}, {new Pixel(15, 138, 58)}, {new Pixel(119, 98, 137)},
            {new Pixel(42, 133, 119)}, {new Pixel(79, 29, 224)}, {new Pixel(205, 184, 207)},
            {new Pixel(194, 177, 201)}, {new Pixel(145, 244, 88)}});

    assertEquals(elevenBy1Grey.verticalFlip(), elevenBy1GreyFlipped);
    assertEquals(elevenBy1Color.verticalFlip(), elevenBy1ColorFlipped);

    // test flip on even width
    IImage tenBy1Grey = new Image(10, 1,
        new IPixel[][]{{new Pixel(212)}, {new Pixel(52)}, {new Pixel(23)}, {new Pixel(44)},
            {new Pixel(123)}, {new Pixel(143)}, {new Pixel(99)}, {new Pixel(35)},
            {new Pixel(198)},
            {new Pixel(67)}});
    IImage tenBy1Color = new Image(10, 1,
        new IPixel[][]{{new Pixel(145, 244, 88)}, {new Pixel(194, 177, 201)},
            {new Pixel(205, 184, 207)}, {new Pixel(79, 29, 224)}, {new Pixel(42, 133, 119)},
            {new Pixel(15, 138, 58)}, {new Pixel(30, 92, 142)}, {new Pixel(68, 87, 25)},
            {new Pixel(167, 33, 139)}, {new Pixel(1, 185, 173)}});

    IImage tenBy1GreyFlipped = new Image(10, 1,
        new IPixel[][]{{new Pixel(67)}, {new Pixel(198)}, {new Pixel(35)}, {new Pixel(99)},
            {new Pixel(143)}, {new Pixel(123)}, {new Pixel(44)}, {new Pixel(23)}, {new Pixel(52)},
            {new Pixel(212)}});
    IImage tenBy1ColorFlipped = new Image(10, 1,
        new IPixel[][]{{new Pixel(1, 185, 173)}, {new Pixel(167, 33, 139)},
            {new Pixel(68, 87, 25)},
            {new Pixel(30, 92, 142)}, {new Pixel(15, 138, 58)}, {new Pixel(42, 133, 119)},
            {new Pixel(79, 29, 224)}, {new Pixel(205, 184, 207)}, {new Pixel(194, 177, 201)},
            {new Pixel(145, 244, 88)}});

    assertEquals(tenBy1Grey.verticalFlip(), tenBy1GreyFlipped);
    assertEquals(tenBy1Color.verticalFlip(), tenBy1ColorFlipped);

    // test flip on 1XN image returns same image, even and odd
    IImage oneBy11Grey = new Image(1, 11, new IPixel[][]{{new Pixel(212), new Pixel(52),
        new Pixel(23), new Pixel(44), new Pixel(123), new Pixel(243), new Pixel(143),
        new Pixel(99),
        new Pixel(35), new Pixel(198), new Pixel(67)}});
    IImage oneBy11Color = new Image(1, 11,
        new IPixel[][]{
            {new Pixel(145, 244, 88), new Pixel(194, 177, 201), new Pixel(205, 184, 207),
                new Pixel(79, 29, 224), new Pixel(42, 133, 119), new Pixel(119, 98, 137),
                new Pixel(15, 138, 58), new Pixel(30, 92, 142), new Pixel(68, 87, 25),
                new Pixel(167, 33, 139), new Pixel(1, 185, 173)}});

    IImage oneBy10Grey = new Image(1, 10, new IPixel[][]{{new Pixel(212), new Pixel(52),
        new Pixel(23), new Pixel(44), new Pixel(123), new Pixel(143), new Pixel(99),
        new Pixel(35), new Pixel(198), new Pixel(67)}});
    IImage oneBy10Color = new Image(1, 10,
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
    Image mirrorGreyOdd = new Image(5, 1, new IPixel[][]{{new Pixel(123)}, {new Pixel(245)},
        {new Pixel(198)}, {new Pixel(245)}, {new Pixel(123)}});
    Image mirrorGreyEven = new Image(4, 1, new IPixel[][]{{new Pixel(123)}, {new Pixel(245)},
        {new Pixel(245)}, {new Pixel(123)}});

    Image mirrorColorOdd = new Image(5, 1,
        new IPixel[][]{{new Pixel(221, 145, 0)}, {new Pixel(165,
            47, 28)}, {new Pixel(78, 212, 189)}, {new Pixel(165, 47, 28)},
            {new Pixel(221, 145, 0)}});
    Image mirrorColorEven = new Image(4, 1,
        new IPixel[][]{{new Pixel(221, 145, 0)}, {new Pixel(165,
            47, 28)}, {new Pixel(165, 47, 28)}, {new Pixel(221, 145, 0)}});

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
    IImage greyScale = new Image(2, 2, new IPixel[][]{{new Pixel(233), new Pixel(125)},
        {new Pixel(123), new Pixel(187)}});
    IImage color = new Image(2, 2,
        new IPixel[][]{{new Pixel(12, 44, 198), new Pixel(244, 109, 77)},
            {new Pixel(87, 65, 176), new Pixel(98, 2, 199)}});

    assertEquals(greyScale.toString(),
        "2 2\n255\n233\n233\n233\n125\n125\n125\n123\n123\n123\n187\n187\n187\n");
    assertEquals(color.toString(), "2 2\n255\n12\n44\n198\n244\n109\n77\n87\n65\n176\n98\n2\n199"
        + "\n");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidHeight() {
    new Image(1, 2, new IPixel[][]{{new Pixel(233), new Pixel(125)},
        {new Pixel(123), new Pixel(187)}});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWidth() {
    new Image(2, 1, new IPixel[][]{{new Pixel(233), new Pixel(125)},
        {new Pixel(123), new Pixel(187)}});
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWidthZero() {
    new Image(0, 1, new IPixel[][]{});
  }

  @Test
  public void testFilter() {
    IPixel[][] testArr = {{new Pixel(174, 241, 145), new Pixel(4, 115, 243), new Pixel(134, 56,
        208)}, {new Pixel(231, 6, 0), new Pixel(55, 163, 160), new Pixel(27, 155, 158)},
        {new Pixel(119, 223, 123), new Pixel(114, 145, 13), new Pixel(39, 196, 228)}};
    IPixel[][] blurred = pixelArrayFromChannelArrays(
        new int[][]{{76, 62, 40}, {108, 89, 42}, {76, 71, 30}},
        new int[][]{{85, 96, 57}, {96, 138, 106}, {84, 119, 96}},
        new int[][]{{76, 134, 112}, {69, 135, 130}, {42, 77, 88}});
    assertEquals(new Image(3, 3, testArr).applyFilter(blurKernel), new Image(3, 3, blurred));

    IPixel[][] testArr2 = pixelArrayFromChannelArrays(
        new int[][]{{106, 125, 199, 33, 60}, {12, 86, 99, 241, 90}, {231, 230, 61, 244, 210},
            {241, 94, 103, 147, 3}, {125, 28, 24, 15, 11}},
        new int[][]{{43, 140, 46, 237, 78}, {166, 75, 94, 198, 130}, {187, 190, 160, 124, 130},
            {165, 48, 199, 111, 209}, {115, 175, 20, 114, 203}},
        new int[][]{{164, 240, 53, 49, 79}, {96, 224, 77, 57, 236}, {207, 140, 120, 43, 110},
            {136, 159, 59, 58, 47}, {89, 150, 216, 111, 231}});
    IPixel[][] sharpen = pixelArrayFromChannelArrays(
        new int[][]{{59, 120, 189, 85, 49}, {106, 213, 241, 255, 210}, {255, 255, 182, 255, 255},
            {255, 249, 145, 206, 82}, {134, 58, 0, 0, 0}},
        new int[][]{{53, 109, 81, 255, 150}, {235, 196, 203, 255, 219}, {224, 255, 150, 255, 179},
            {254, 240, 225, 255, 255}, {117, 201, 0, 196, 232}},
        new int[][]{{229, 255, 65, 64, 114}, {255, 255, 139, 132, 255}, {249, 255, 47, 27, 95},
            {223, 255, 119, 161, 89}, {107, 229, 209, 173, 216}});
    assertEquals(new Image(5, 5, testArr2).applyFilter(sharpenKernel), new Image(5, 5, sharpen));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFilter() {
    this.emptyImage.applyFilter(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFilterKernel() {
    this.emptyImage.applyFilter(new double[][]{{0, 0}, {1, 1}, {2, 2}});
  }

  @Test
  public void testColorTransform() {
    IImage color = new Image(2, 2,
        new IPixel[][]{{new Pixel(12, 44, 198), new Pixel(244, 109, 77)},
            {new Pixel(87, 65, 176), new Pixel(98, 2, 199)}});
    assertEquals(color.applyTransform(new double[][]{{1, 0, 0}, {1, 0, 0}, {1, 0, 0}}),
        new Image(2, 2, pixelArrayFromChannelArrays(new int[][]{{12, 244}, {87, 98}})));
    assertEquals(color.applyTransform(new double[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 0}}),
        new Image(2, 2, pixelArrayFromChannelArrays(new int[][]{{44, 109}, {65, 2}})));
    assertEquals(color.applyTransform(new double[][]{{0, 0, 1}, {0, 0, 1}, {0, 0, 1}}),
        new Image(2, 2, pixelArrayFromChannelArrays(new int[][]{{198, 77}, {176, 199}})));
    assertEquals(color.applyTransform(
            new double[][]{{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722},
                {0.2126, 0.7152, 0.0722}}),
        new Image(2, 2, pixelArrayFromChannelArrays(new int[][]{{48, 135}, {77, 36}})));
    assertEquals(color.applyTransform(
            new double[][]{{1. / 3, 1. / 3, 1. / 3}, {1. / 3, 1. / 3, 1. / 3},
                {1. / 3, 1. / 3, 1. / 3}}),
        new Image(2, 2, pixelArrayFromChannelArrays(new int[][]{{84, 143}, {109, 99}})));
    assertEquals(color.applyTransform(sepiaKernel), new Image(2, 2,
        new IPixel[][]{{new Pixel(75, 67, 52), new Pixel(194, 172, 134)},
            {new Pixel(117, 104, 81), new Pixel(77, 69, 53)}}));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullTransformKernel() {
    this.emptyImage.applyTransform(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTransformKernel() {
    this.emptyImage.applyTransform(new double[][]{{1}});
  }

  @Test
  public void testGetHeight() {
    assertEquals(this.emptyImage.getHeight(), 0);
  }

  @Test
  public void testGetWidth() {
    assertEquals(this.emptyImage.getWidth(), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfTopBoundsGetPixel() {
    this.generateRandomColorImage(5, 5).getPixelAt(-1, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfBottomBoundsGetPixel() {
    this.generateRandomColorImage(5, 5).getPixelAt(5, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfLeftBoundsGetPixel() {
    this.generateRandomColorImage(5, 5).getPixelAt(0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutOfRightBoundsGetPixel() {
    this.generateRandomColorImage(5, 5).getPixelAt(0, 5);
  }

  @Test
  public void testGetPixel() {
    int[][] red = this.generateRandomPixelValues(6, 6);
    int[][] green = this.generateRandomPixelValues(6, 6);
    int[][] blue = this.generateRandomPixelValues(6, 6);
    IImage image = new Image(6, 6, pixelArrayFromChannelArrays(red, green, blue));
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        assertEquals(new Pixel(red[i][j], green[i][j], blue[i][j]), image.getPixelAt(i, j));
      }
    }
  }
}