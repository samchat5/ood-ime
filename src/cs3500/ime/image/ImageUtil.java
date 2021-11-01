package cs3500.ime.image;

import cs3500.ime.pixel.IPixel;
import cs3500.ime.pixel.Pixel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   * @throws IllegalArgumentException if the file is not found or the PPM file is invalid
   */
  public static IImage readPPM(String filename) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
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
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
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
    return new PPMImage(height, width, image);
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

