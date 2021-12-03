package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * It transmits this data into an array of pixels which have the color values described.
 */
public class ImageUtil {

  /**
   * Reads the given file and returns and Image of that file. Supports JPG, JPEG, PPM, BMP, and PNG
   * file types.
   *
   * @param filepath The filepath of the image.
   * @return an image of the given file.
   */
  public static Image readFile(String filepath) {
    if (getFileExtension(filepath).equals("ppm")) {
      return readPPM(filepath);
    } else {
      return readImage(filepath);
    }
  }

  /**
   * Writes the given image to the given file returns and Image of that file. Supports JPG, JPEG,
   * PPM, and PNG file types.
   *
   * @param image    The image that will be written in the file.
   * @param filepath The filepath of the image
   * @throws IllegalArgumentException if the file is not a ppm or if it cannot be found.
   */
  public static void writeToFile(Image image, String filepath) {
    if (getFileExtension(filepath).equals("ppm")) {
      saveToPPM(image, filepath);
    } else {
      writeToImage(image, image.getImageWidth(), image.getImageHeight(), filepath);
    }
  }

  /**
   * Read an image file in the PPM format and return the contents in an array.
   *
   * @param filename the filepath fo the given file.
   * @return An image that represents the rgb values in the given ppm file
   * @throws IllegalArgumentException If the ppm file is not type P3 (ASCII).
   */
  private static Image readPPM(String filename) {
    Scanner sc;

    if (!(getFileExtension(filename).equals("ppm"))) {
      throw new IllegalArgumentException("File must be an ASCII ppm file.");
    }

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File " + filename + " not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();

    //Create empty image with the correct width and height

    Image image = new Image(new Pixel[height][width]);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = sc.nextInt(); //get the red value of the pixel
        int g = sc.nextInt(); //get the green value of the pixel
        int b = sc.nextInt(); //get the blue value of the pixel
        Pixel pixel = new PixelRGB(r, g, b); // pass the rgb value to create a new pixel
        image.getImage()[row][col] = pixel; //put pixel in array
      }
    }
    return image;
  }

  //returns the extension of the file
  private static String getFileExtension(String filename) {
    int index = filename.lastIndexOf('.');
    String extension = "";
    if (index > 0) {
      extension = filename.substring(index + 1);
    }
    return extension;
  }

  /**
   * Saves a PPM file to a PPM file format.
   *
   * @param filename String representing the filepath.
   * @throws IllegalArgumentException If  the file is not a PPM.
   * @throws IllegalStateException    If the file cannot be written in.
   */
  private static void saveToPPM(ImageState givenimage, String filename) throws
      IllegalStateException {
    FileOutputStream out;

    if (!(getFileExtension(filename).equals("ppm"))) {
      throw new IllegalArgumentException("File must be an ASCII ppm file.");
    }

    try {
      out = new FileOutputStream(filename);
    } catch (FileNotFoundException e) {
      File file = new File(filename);
      try {
        out = new FileOutputStream(file, true);
      } catch (FileNotFoundException ex) {
        throw new IllegalStateException("cannot create output stream");
      }
    }

    int width = givenimage.getImageWidth();
    int height = givenimage.getImageHeight();
    int maxValue = givenimage.getMaxRgb();

    String header = "P3\n" + width + " " + height + "\n" + maxValue + "\n";

    StringBuilder imagestr = new StringBuilder();
    imagestr.append(header);

    for (int row = 0; row < height; row++) {
      StringBuilder line = new StringBuilder();
      for (int col = 0; col < width; col++) {
        int r = givenimage.getImage()[row][col].getRed();
        int g = givenimage.getImage()[row][col].getGreen();
        int b = givenimage.getImage()[row][col].getBlue();

        String pixel = "" + r + " " + g + " " + b + " ";
        line.append(pixel);
      }

      imagestr.append(line);
      if (row != height - 1) {
        imagestr.append("\n");
      }

    }

    byte[] strToBytes = imagestr.toString().getBytes();
    try {
      out.write(strToBytes);
      out.close();
    } catch (IOException e) {
      throw new IllegalStateException("Cannot write to file");
    }

  }

  /**
   * This method reads an image of the following formats: jpeg, jpg, png, and gif.
   *
   * @param filepath String representing the filepath of an image.
   * @return an image from the given file.
   * @throws IllegalStateException If the file cannot be read
   */
  private static Image readImage(String filepath) {
    BufferedImage givenImage;

    //use read method to read an image
    try {
      givenImage = ImageIO.read(new FileInputStream(filepath));
    } catch (IOException e) {
      throw new IllegalStateException("cannot read filepath");
    }

    int width = givenImage.getWidth();
    int height = givenImage.getHeight();

    //Create empty image with the correct width and height
    Image result = new Image(new Pixel[height][width]);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int rgb = givenImage.getRGB(col, row); //get the RGB value of one pixel
        Color c = new Color(rgb); //turn it into a color to avoid dealing with binary
        int red = c.getRed();  //get the red value of the pixel
        int green = c.getGreen(); //get the green value of the pixel
        int blue = c.getBlue();  //get the blue value of the pixel
        Pixel pixel = new PixelRGB(red, green, blue); // pass the rgb value to create a new pixel
        result.getImage()[row][col] = pixel; //put pixel in array
      }
    }
    return result;
  }

  //wries to a file that is not a ppm. Throws an exception if the file cannot be written into
  private static void writeToImage(Image image, int width, int height, String filepath) {
    BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        Pixel p = image.getImage()[row][col];
        int red = p.getRed(); //get red value of pixel
        int green = p.getGreen(); //get green value of pixel
        int blue = p.getBlue(); //get blue value of pixel
        Color c = new Color(red, green, blue);  //create a Color using red green and blue
        out.setRGB(col, row, c.getRGB()); //pass the rgb value to the pixel
        // at the position (row,col)
      }
    }
    String ext = getFileExtension(filepath);
    try {
      ImageIO.write(out, ext, new File(filepath));
    } catch (IOException e) {
      throw new IllegalStateException("Cannot write to file" + filepath);
    }
  }
}



