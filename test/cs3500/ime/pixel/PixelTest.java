package cs3500.ime.pixel;

import static org.junit.Assert.assertEquals;

import cs3500.ime.GreyscaleComponent;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import org.junit.Test;

/**
 * Tests for pixel class.
 */
public class PixelTest {

  private final IPixel white = new Pixel(255);
  private final IPixel yellow = new Pixel(255, 255, 0);
  private final IPixel magenta = new Pixel(255, 0, 255);
  private final IPixel cyan = new Pixel(0, 255, 255);
  private final IPixel red = new Pixel(255, 0, 0);
  private final IPixel green = new Pixel(0, 255, 0);
  private final IPixel blue = new Pixel(0, 0, 255);
  private final IPixel black = new Pixel(0);

  @Test
  public void testToString() {
    assertEquals(white.toString(), "255\n255\n255\n");
    assertEquals(yellow.toString(), "255\n255\n0\n");
    assertEquals(magenta.toString(), "255\n0\n255\n");
    assertEquals(cyan.toString(), "0\n255\n255\n");
    assertEquals(red.toString(), "255\n0\n0\n");
    assertEquals(green.toString(), "0\n255\n0\n");
    assertEquals(blue.toString(), "0\n0\n255\n");
    assertEquals(black.toString(), "0\n0\n0\n");
  }

  @Test
  public void testConstructor() {
    assertEquals(white.getComponent(GreyscaleComponent.RED), new Pixel(255));
    assertEquals(white.getComponent(GreyscaleComponent.GREEN), new Pixel(255));
    assertEquals(white.getComponent(GreyscaleComponent.BLUE), new Pixel(255));

    assertEquals(yellow.getComponent(GreyscaleComponent.RED), new Pixel(255));
    assertEquals(yellow.getComponent(GreyscaleComponent.GREEN), new Pixel(255));
    assertEquals(yellow.getComponent(GreyscaleComponent.BLUE), new Pixel(0));

    assertEquals(magenta.getComponent(GreyscaleComponent.RED), new Pixel(255));
    assertEquals(magenta.getComponent(GreyscaleComponent.GREEN), new Pixel(0));
    assertEquals(magenta.getComponent(GreyscaleComponent.BLUE), new Pixel(255));

    assertEquals(cyan.getComponent(GreyscaleComponent.RED), new Pixel(0));
    assertEquals(cyan.getComponent(GreyscaleComponent.GREEN), new Pixel(255));
    assertEquals(cyan.getComponent(GreyscaleComponent.BLUE), new Pixel(255));

    assertEquals(black.getComponent(GreyscaleComponent.RED), new Pixel(0));
    assertEquals(black.getComponent(GreyscaleComponent.GREEN), new Pixel(0));
    assertEquals(black.getComponent(GreyscaleComponent.BLUE), new Pixel(0));

    assertEquals(red.getComponent(GreyscaleComponent.RED), new Pixel(255));
    assertEquals(red.getComponent(GreyscaleComponent.GREEN), new Pixel(0));
    assertEquals(red.getComponent(GreyscaleComponent.BLUE), new Pixel(0));

    assertEquals(green.getComponent(GreyscaleComponent.RED), new Pixel(0));
    assertEquals(green.getComponent(GreyscaleComponent.GREEN), new Pixel(255));
    assertEquals(green.getComponent(GreyscaleComponent.BLUE), new Pixel(0));

    assertEquals(blue.getComponent(GreyscaleComponent.RED), new Pixel(0));
    assertEquals(blue.getComponent(GreyscaleComponent.GREEN), new Pixel(0));
    assertEquals(blue.getComponent(GreyscaleComponent.BLUE), new Pixel(255));
  }

  @Test
  public void testBrightenDarken() {
    IPixel brightRedPixel = new Pixel(255, 60, 15);
    IPixel brightGreenPixel = new Pixel(2, 255, 213);
    IPixel brightBluePixel = new Pixel(13, 44, 255);

    IPixel lessBrightRedPixel = new Pixel(250, 60, 15);
    IPixel lessBrightGreenPixel = new Pixel(2, 249, 213);
    IPixel lessBrightBluePixel = new Pixel(13, 44, 200);

    IPixel darkRedPixel = new Pixel(0, 60, 15);
    IPixel darkGreenPixel = new Pixel(55, 0, 213);
    IPixel darkBluePixel = new Pixel(13, 44, 0);

    IPixel lessDarkRedPixel = new Pixel(2, 60, 15);
    IPixel lessDarkGreenPixel = new Pixel(55, 2, 213);
    IPixel lessDarkBluePixel = new Pixel(13, 44, 2);

    assertEquals(brightRedPixel.brighten(10), new Pixel(255, 70, 25));
    assertEquals(brightGreenPixel.brighten(36), new Pixel(38, 255, 249));
    assertEquals(brightBluePixel.brighten(118), new Pixel(131, 162, 255));

    assertEquals(lessBrightRedPixel.brighten(10), new Pixel(255, 70, 25));
    assertEquals(lessBrightGreenPixel.brighten(36), new Pixel(38, 255, 249));
    assertEquals(lessBrightBluePixel.brighten(118), new Pixel(131, 162, 255));

    assertEquals(darkRedPixel.brighten(-10), new Pixel(0, 50, 5));
    assertEquals(darkGreenPixel.brighten(-50), new Pixel(5, 0, 163));
    assertEquals(darkBluePixel.brighten(-12), new Pixel(1, 32, 0));

    assertEquals(lessDarkRedPixel.brighten(-10), new Pixel(0, 50, 5));
    assertEquals(lessDarkGreenPixel.brighten(-50), new Pixel(5, 0, 163));
    assertEquals(lessDarkBluePixel.brighten(-12), new Pixel(1, 32, 0));

    assertEquals(brightRedPixel.brighten(10).brighten(-10), new Pixel(245, 60, 15));
    assertEquals(brightGreenPixel.brighten(10).brighten(-10), new Pixel(2, 245, 213));
    assertEquals(brightBluePixel.brighten(10).brighten(-10), new Pixel(13, 44, 245));

    assertEquals(darkRedPixel.brighten(-10).brighten(10), new Pixel(10, 60, 15));
    assertEquals(darkGreenPixel.brighten(-10).brighten(10), new Pixel(55, 10, 213));
    assertEquals(darkBluePixel.brighten(-10).brighten(10), new Pixel(13, 44, 10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAllNegative() {
    new Pixel(-5, Integer.MIN_VALUE, -256);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeRed() {
    new Pixel(-5, 255, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeGreen() {
    new Pixel(255, -5, 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeBlue() {
    new Pixel(225, 255, -5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBigRed() {
    new Pixel(300, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBigGreen() {
    new Pixel(0, 300, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBigBlue() {
    new Pixel(0, 0, 300);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAllBig() {
    new Pixel(Integer.MAX_VALUE, 256, 300);
  }

  @Test
  public void testGetIndivColorComponents() {
    IPixel randomColor = new Pixel(195, 225, 65);
    IPixel randomGrey = new Pixel(75);

    assertEquals(white.getComponent(GreyscaleComponent.RED), white);
    assertEquals(white.getComponent(GreyscaleComponent.GREEN), white);
    assertEquals(white.getComponent(GreyscaleComponent.BLUE), white);
    assertEquals(white.getComponent(GreyscaleComponent.VALUE), white);
    assertEquals(white.getComponent(GreyscaleComponent.INTENSITY), white);

    assertEquals(yellow.getComponent(GreyscaleComponent.RED), white);
    assertEquals(yellow.getComponent(GreyscaleComponent.GREEN), white);
    assertEquals(yellow.getComponent(GreyscaleComponent.BLUE), black);
    assertEquals(yellow.getComponent(GreyscaleComponent.VALUE), white);
    assertEquals(yellow.getComponent(GreyscaleComponent.INTENSITY), new Pixel(170));
    assertEquals(yellow.getComponent(GreyscaleComponent.LUMA), new Pixel(236));

    assertEquals(magenta.getComponent(GreyscaleComponent.RED), white);
    assertEquals(magenta.getComponent(GreyscaleComponent.GREEN), black);
    assertEquals(magenta.getComponent(GreyscaleComponent.BLUE), white);
    assertEquals(magenta.getComponent(GreyscaleComponent.VALUE), white);
    assertEquals(magenta.getComponent(GreyscaleComponent.INTENSITY), new Pixel(170));
    assertEquals(magenta.getComponent(GreyscaleComponent.LUMA), new Pixel(72));

    assertEquals(cyan.getComponent(GreyscaleComponent.RED), black);
    assertEquals(cyan.getComponent(GreyscaleComponent.GREEN), white);
    assertEquals(cyan.getComponent(GreyscaleComponent.BLUE), white);
    assertEquals(cyan.getComponent(GreyscaleComponent.VALUE), white);
    assertEquals(cyan.getComponent(GreyscaleComponent.INTENSITY), new Pixel(170));
    assertEquals(cyan.getComponent(GreyscaleComponent.LUMA), new Pixel(200));

    assertEquals(black.getComponent(GreyscaleComponent.RED), black);
    assertEquals(black.getComponent(GreyscaleComponent.GREEN), black);
    assertEquals(black.getComponent(GreyscaleComponent.BLUE), black);
    assertEquals(black.getComponent(GreyscaleComponent.VALUE), black);
    assertEquals(black.getComponent(GreyscaleComponent.INTENSITY), black);
    assertEquals(black.getComponent(GreyscaleComponent.LUMA), black);

    assertEquals(red.getComponent(GreyscaleComponent.RED), white);
    assertEquals(red.getComponent(GreyscaleComponent.GREEN), black);
    assertEquals(red.getComponent(GreyscaleComponent.BLUE), black);
    assertEquals(red.getComponent(GreyscaleComponent.VALUE), white);
    assertEquals(red.getComponent(GreyscaleComponent.INTENSITY), new Pixel(85));
    assertEquals(red.getComponent(GreyscaleComponent.LUMA), new Pixel(54));

    assertEquals(green.getComponent(GreyscaleComponent.RED), black);
    assertEquals(green.getComponent(GreyscaleComponent.GREEN), white);
    assertEquals(green.getComponent(GreyscaleComponent.BLUE), black);
    assertEquals(green.getComponent(GreyscaleComponent.VALUE), white);
    assertEquals(green.getComponent(GreyscaleComponent.INTENSITY), new Pixel(85));
    assertEquals(green.getComponent(GreyscaleComponent.LUMA), new Pixel(182));

    assertEquals(blue.getComponent(GreyscaleComponent.RED), black);
    assertEquals(blue.getComponent(GreyscaleComponent.GREEN), black);
    assertEquals(blue.getComponent(GreyscaleComponent.BLUE), white);
    assertEquals(blue.getComponent(GreyscaleComponent.VALUE), white);
    assertEquals(blue.getComponent(GreyscaleComponent.INTENSITY), new Pixel(85));
    assertEquals(blue.getComponent(GreyscaleComponent.LUMA), new Pixel(18));

    assertEquals(randomColor.getComponent(GreyscaleComponent.RED), new Pixel(195));
    assertEquals(randomColor.getComponent(GreyscaleComponent.GREEN), new Pixel(225));
    assertEquals(randomColor.getComponent(GreyscaleComponent.BLUE), new Pixel(65));
    assertEquals(randomColor.getComponent(GreyscaleComponent.VALUE), new Pixel(225));
    assertEquals(randomColor.getComponent(GreyscaleComponent.INTENSITY), new Pixel(161));
    assertEquals(randomColor.getComponent(GreyscaleComponent.LUMA), new Pixel(207));

    assertEquals(randomGrey.getComponent(GreyscaleComponent.RED), randomGrey);
    assertEquals(randomGrey.getComponent(GreyscaleComponent.GREEN), randomGrey);
    assertEquals(randomGrey.getComponent(GreyscaleComponent.BLUE), randomGrey);
    assertEquals(randomGrey.getComponent(GreyscaleComponent.VALUE), randomGrey);
    assertEquals(randomGrey.getComponent(GreyscaleComponent.INTENSITY), randomGrey);
    assertEquals(randomGrey.getComponent(GreyscaleComponent.LUMA), randomGrey);
  }

  @Test
  public void testGreyScaleRandomPixels() {
    Random r = new Random();
    for (int i = 0; i < 100; i++) {
      int red = r.nextInt(256);
      int green = r.nextInt(256);
      int blue = r.nextInt(256);

      double lumaRed = red * 0.2126;
      double lumaGreen = green * 0.7152;
      double lumaBlue = blue * 0.0722;

      IPixel p = new Pixel(red, green, blue);
      assertEquals(p.getComponent(GreyscaleComponent.RED), new Pixel(red));
      assertEquals(p.getComponent(GreyscaleComponent.GREEN), new Pixel(green));
      assertEquals(p.getComponent(GreyscaleComponent.BLUE), new Pixel(blue));
      assertEquals(p.getComponent(GreyscaleComponent.VALUE),
          new Pixel(Collections.max(Arrays.asList(red, green, blue))));
      assertEquals(p.getComponent(GreyscaleComponent.INTENSITY),
          new Pixel(Arrays.stream(new int[]{red, green, blue}).sum() / 3));
      assertEquals(p.getComponent(GreyscaleComponent.LUMA),
          new Pixel((int) (lumaRed + lumaGreen + lumaBlue)));
    }
  }
}