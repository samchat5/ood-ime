import static model.ImageUtil.readFile;
import static model.ImageUtil.writeToFile;
import static org.junit.Assert.assertEquals;

import model.Image;
import model.Pixel;
import model.PixelRGB;
import org.junit.Test;

/**
 * Represents the tests for the imageUtils class. Ensures that all methods and constructors work
 * properly.
 */
public class ImageUtilTest {

  @Test
  public void readTest() {
    Image test = readFile("res/4by4.ppm");
    Image test2 = readFile("res/4by4.png");
    Image test3 = readFile("res/dfkjn.bmp");
    Image test4 = readFile("res/4by4.jpg");
    Pixel black = new PixelRGB(0, 0, 0);
    Pixel[][] arrPPM = {{black, black, black, new PixelRGB(15, 0, 15)},
        {black, new PixelRGB(0, 15, 7), black, black},
        {black, black, new PixelRGB(0, 15, 7), black},
        {new PixelRGB(15, 0, 15), black, black, black}};
    Image ppm = new Image(arrPPM);

    assertEquals(ppm, test);
    assertEquals(ppm, test2);
    assertEquals(ppm, test3);
    assertEquals(ppm, test4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void readPPMFileDoesntExist() {
    readFile("res/fail.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readPNGFileDoesntExist() {
    readFile("res/fail.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readBMPFileDoesntExist() {
    readFile("res/fail.bmp");
  }

  @Test(expected = IllegalArgumentException.class)
  public void readJPGFileDoesntExist() {
    readFile("res/fail.jpg");
  }

  @Test
  public void saveTestPPM() {
    Image test1 = readFile("res/4by4.ppm");
    Pixel black = new PixelRGB(0, 0, 0);
    Pixel[][] arrPPM = {{black, black, black, new PixelRGB(15, 0, 15)},
        {black, new PixelRGB(0, 15, 7), black, black},
        {black, black, new PixelRGB(0, 15, 7), black},
        {new PixelRGB(15, 0, 15), black, black, black}};
    Image ppm = new Image(arrPPM);
    assertEquals(ppm, test1);
    writeToFile(test1, "res/test.ppm");
    writeToFile(test1, "res/test.png");
    writeToFile(test1, "res/test.jpg");
    writeToFile(test1, "res/test.bmp");
    assertEquals(readFile("res/test.ppm"), ppm);
    assertEquals(readFile("res/test.png"), ppm);
    assertEquals(readFile("res/test.jpg"), ppm);
    assertEquals(readFile("res/test.bmp"), ppm);
  }

  @Test
  public void saveTestPNG() {
    Image test1 = readFile("res/4by4.png");
    Pixel black = new PixelRGB(0, 0, 0);
    Pixel[][] arrPPM = {{black, black, black, new PixelRGB(15, 0, 15)},
        {black, new PixelRGB(0, 15, 7), black, black},
        {black, black, new PixelRGB(0, 15, 7), black},
        {new PixelRGB(15, 0, 15), black, black, black}};
    Image png = new Image(arrPPM);
    assertEquals(png, test1);
    writeToFile(test1, "res/test.ppm");
    writeToFile(test1, "res/test.png");
    writeToFile(test1, "res/test.jpg");
    writeToFile(test1, "res/test.bmp");
    assertEquals(readFile("res/test.ppm"), png);
    assertEquals(readFile("res/test.png"), png);
    assertEquals(readFile("res/test.jpg"), png);
    assertEquals(readFile("res/test.bmp"), png);
  }

  @Test
  public void saveTestBMP() {
    Image test1 = readFile("res/dfkjn.bmp");
    Pixel black = new PixelRGB(0, 0, 0);
    Pixel[][] arrPPM = {{black, black, black, new PixelRGB(15, 0, 15)},
        {black, new PixelRGB(0, 15, 7), black, black},
        {black, black, new PixelRGB(0, 15, 7), black},
        {new PixelRGB(15, 0, 15), black, black, black}};
    Image bmp = new Image(arrPPM);
    assertEquals(bmp, test1);
    writeToFile(test1, "res/test.ppm");
    writeToFile(test1, "res/test.png");
    writeToFile(test1, "res/test.jpg");
    writeToFile(test1, "res/test.bmp");
    assertEquals(readFile("res/test.ppm"), bmp);
    assertEquals(readFile("res/test.png"), bmp);
    assertEquals(readFile("res/test.jpg"), bmp);
    assertEquals(readFile("res/test.bmp"), bmp);
  }

  @Test
  public void saveTestJPG() {
    Image test1 = readFile("res/4by4.jpg");
    Pixel black = new PixelRGB(0, 0, 0);
    Pixel[][] arrPPM = {{black, black, black, new PixelRGB(15, 0, 15)},
        {black, new PixelRGB(0, 15, 7), black, black},
        {black, black, new PixelRGB(0, 15, 7), black},
        {new PixelRGB(15, 0, 15), black, black, black}};
    Image jpg = new Image(arrPPM);
    assertEquals(jpg, test1);
    writeToFile(test1, "res/test.ppm");
    writeToFile(test1, "res/test.png");
    writeToFile(test1, "res/test.jpg");
    writeToFile(test1, "res/test.bmp");
    assertEquals(readFile("res/test.ppm"), jpg);
    assertEquals(readFile("res/test.png"), jpg);
    assertEquals(readFile("res/test.jpg"), jpg);
    assertEquals(readFile("res/test.bmp"), jpg);
  }
}
