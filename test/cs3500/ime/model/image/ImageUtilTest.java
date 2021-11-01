package cs3500.ime.model.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.ime.model.image.pixel.IPixel;
import cs3500.ime.model.image.pixel.Pixel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.After;
import org.junit.Test;

/**
 * Tests for ImageUtil class.
 */
public class ImageUtilTest {

  @Test(expected = IllegalArgumentException.class)
  public void testReadNull() {
    ImageUtil.readPPM(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadUnknownFile() {
    ImageUtil.readPPM("unknown.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReadInvalidPPMFile() {
    try {
      String out = "P3\n1 1\n255\na\na\na\n";
      FileOutputStream fos = new FileOutputStream("test/out.ppm");
      fos.write(out.getBytes(StandardCharsets.UTF_8));
      fos.close();
      ImageUtil.readPPM("test/out.ppm");
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testReadNormal() {
    try {
      String out = "P3\n2 2\n255\n123\n123\n123\n0\n0\n0\n4\n4\n4\n97\n97\n97\n";
      FileOutputStream fos = new FileOutputStream("test/out.ppm");
      fos.write(out.getBytes(StandardCharsets.UTF_8));
      fos.close();
      assertEquals(ImageUtil.readPPM("test/out.ppm"), new PPMImage(2, 2,
          new Pixel[][]{{new Pixel(123), new Pixel(0)}, {new Pixel(4), new Pixel(97)}}));
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteNullString() {
    ImageUtil.writePPM(null, new PPMImage(0, 0, new IPixel[][]{}));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteNullImage() {
    ImageUtil.writePPM("test/out.ppm", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteWriteProtectedFile() {
    try {
      FileOutputStream fos = new FileOutputStream("test/out.ppm");
      fos.write("P3\n1 1\n255\n255\n255\n255".getBytes(StandardCharsets.UTF_8));
      fos.close();
      new File("test/out.ppm").setReadOnly();
      ImageUtil.writePPM("test/out.ppm", new PPMImage(0, 0, new IPixel[][]{}));
    } catch (IOException e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testWriteUnknownFile() {
    ImageUtil.writePPM("some/dir/that/dont/exist.ppm", new PPMImage(0, 0, new IPixel[][]{}));
  }

  @Test
  public void testWriteNormal() {
    ImageUtil.writePPM("test/out.ppm", new PPMImage(2, 2,
        new Pixel[][]{{new Pixel(123), new Pixel(0)}, {new Pixel(4), new Pixel(97)}}));
    IImage im = ImageUtil.readPPM("test/out.ppm");
    assertEquals(im, new PPMImage(2, 2,
        new Pixel[][]{{new Pixel(123), new Pixel(0)}, {new Pixel(4), new Pixel(97)}}));
  }

  @After
  public void tearDown() {
    File f = new File("test/out.ppm");
    f.setWritable(true);
    f.delete();
  }
}
