package cs3500.ime.model.image;

import cs3500.ime.model.image.pixel.IPixel;
import cs3500.ime.model.image.pixel.Pixel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.imageio.ImageIO;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Creates an 8 bit IImage based off the file provided.
   *
   * @param filename String reference to the image file
   * @return Java's ImageIO's attempt to read the image
   * @throws IllegalArgumentException if filename is null or if the referenced file cannot be found
   */
  public static IImage readImage(String filename) throws IllegalArgumentException {
    if (filename == null) {
      throw new IllegalArgumentException();
    }
    File fis;
    BufferedImage img;
    try {
      fis = new File(filename);
      img = ImageIO.read(fis);
    } catch (IOException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }
    // get image width and height
    int width = img.getWidth();
    int height = img.getHeight();

    IPixel[][] pixelArray = new IPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // get pixel value (the arguments in the getRGB method denotes the  coordinates of the
        // image from which the pixel values need to be extracted)
        int p = img.getRGB(j, i);

        // get red
        int r = (p >> 16) & 0xff;

        // get green
        int g = (p >> 8) & 0xff;

        // get blue
        int b = p & 0xff;

        IPixel foo = new Pixel(r, g, b);
        pixelArray[i][j] = foo;
      }
    }
    return new Image(height, width, pixelArray);
  }

  /**
   * Writes a non-PPM image to the specified filepath.
   *
   * @param filePath filepath to write image to
   * @param img      image to write
   * @throws IllegalArgumentException if the parameters are null, the image is empty, the file is
   *                                  write-protected, or an IO error has occurred.
   */
  public static void writeImage(String filePath, IImage img)
      throws IllegalArgumentException {
    if (filePath == null || img == null) {
      throw new IllegalArgumentException("Null values");
    }
    if (img.equals(new Image(0, 0, new IPixel[][]{}))) {
      throw new IllegalArgumentException("Can't write null image");
    }
    if (new File(filePath).exists() && !new File(filePath).canWrite()) {
      throw new IllegalArgumentException("Write protected file");
    }
    BufferedImage renderedImage = getBufferedImage(img);

    try {
      ImageIO.write(renderedImage, filePath.substring(filePath.lastIndexOf(".") + 1),
          new File(filePath));
    } catch (Exception e) {
      throw new IllegalArgumentException("IO error occurred");
    }
  }

  public static BufferedImage getBufferedImage(IImage img) {
    BufferedImage image = new BufferedImage(img.getWidth(), img.getHeight(),
        BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < img.getHeight(); i++) {
      for (int j = 0; j < img.getWidth(); j++) {
        int[] pixelVals = img.getPixelAt(i, j).getValues();
        int redVal = pixelVals[0];
        int greenVal = pixelVals[1];
        int blueVal = pixelVals[2];
        int pixelVal = (redVal << 16) | (greenVal << 8) | blueVal;
        image.setRGB(j, i, pixelVal);
      }
    }
    return image;
  }

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   * @throws IllegalArgumentException if the file is not found or the PPM file is invalid
   */
  public static IImage readPPM(String filename) throws IllegalArgumentException {
    try {
      if (filename == null) {
        throw new IllegalArgumentException();
      }
      Scanner sc;
      FileInputStream fis;
      try {
        fis = new FileInputStream(filename);
        sc = new Scanner(fis);
      } catch (FileNotFoundException e) {
        throw new IllegalArgumentException("File " + filename + " not found!");
      }
      StringBuilder builder = new StringBuilder();
      // read the file line by line, and populate a string. This will throw away any comment lines
      while (sc.hasNextLine()) {
        String s = sc.nextLine();
        if (s.charAt(0) != '#') {
          builder.append(s).append(System.lineSeparator());
        }
      }

      // now set up the scanner to read from the string we just built
      sc = new Scanner(builder.toString());

      String token;

      token = sc.next();
      if (!token.equals("P3")) {
        fis.close();
        throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
      }

      int width;
      int height;
      try {
        width = sc.nextInt();
        height = sc.nextInt();
        sc.nextInt();

        IPixel[][] image = new IPixel[height][width];

        for (int i = 0; i < height; i++) {
          for (int j = 0; j < width; j++) {
            int r = sc.nextInt();
            int g = sc.nextInt();
            int b = sc.nextInt();

            IPixel foo = new Pixel(r, g, b);
            image[i][j] = foo;
          }
        }
        fis.close();
        return new Image(height, width, image);
      } catch (NoSuchElementException e) {
        fis.close();
        throw new IllegalArgumentException("Invalid file");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("File IO exception.");
    }
  }

  /**
   * Write the given image to the given file path.
   *
   * @param filePath file path to write to
   * @param image    image to write
   * @throws IllegalArgumentException if the path cannot be written to (e.g. write-protected or
   *                                  nonexistent directory) or an IO exception occurs
   */
  public static void writePPM(String filePath, IImage image) throws IllegalArgumentException {
    if (filePath == null || image == null) {
      throw new IllegalArgumentException("Null values");
    }
    if (new File(filePath).exists() && !new File(filePath).canWrite()) {
      throw new IllegalArgumentException("Write protected file");
    }
    String out = "P3\n";
    out += image.toString();
    FileOutputStream fos;
    try {
      fos = new FileOutputStream(filePath);
      fos.write(out.getBytes(StandardCharsets.UTF_8));
      fos.close();
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid file found and/or IO Exception occurred.");
    }
  }
}

