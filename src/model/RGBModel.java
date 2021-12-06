package model;

import java.util.Objects;

/**
 * This class is an implementation of the image model. Represents all methods that can modify
 * model.
 */
public class RGBModel implements ImageModel {

  protected Image image;

  public RGBModel(Image image) throws IllegalArgumentException {
    try {
      this.image = Objects.requireNonNull(image);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Image cannot be null");
    }
  }

  public RGBModel() {
    this.image = new Image(new Pixel[3][3]);
  }

  /**
   * Produces the image represented by the red values of the current image. The red values are
   * defined by the color representation.
   */
  public void makeRed() {
    for (int r = 0; r < image.getImageHeight(); r++) {
      for (int c = 0; c < image.getImageWidth(); c++) {
        this.image.getImage()[r][c].makePixelRed();
      }
    }
  }

  /**
   * Produces the image represented by the green values of the current image. The red values are
   * defined by the color representation.
   */
  public void makeGreen() {
    for (int r = 0; r < image.getImageHeight(); r++) {
      for (int c = 0; c < image.getImageWidth(); c++) {
        this.image.getImage()[r][c].makePixelGreen();
      }
    }
  }

  /**
   * Produces the image represented by the blue values of the current image. The red values are
   * defined by the color representation.
   */
  public void makeBlue() {
    for (int r = 0; r < image.getImageHeight(); r++) {
      for (int c = 0; c < image.getImageWidth(); c++) {
        this.image.getImage()[r][c].makePixelBlue();
      }
    }
  }

  /**
   * Produces the image represented by the value of the current image. The value is the maximum
   * color component of each pixel.
   */
  public void imageValue() {
    for (int r = 0; r < image.getImageHeight(); r++) {
      for (int c = 0; c < image.getImageWidth(); c++) {
        this.image.getImage()[r][c].makePixelValue();
      }
    }
  }

  /**
   * Produces the image represented by the intensity of the current image. The intensity is the
   * average of all color components.
   */
  public void imageIntensity() {
    for (int r = 0; r < image.getImageHeight(); r++) {
      for (int c = 0; c < image.getImageWidth(); c++) {
        this.image.getImage()[r][c].makePixelIntensity();
      }
    }
  }

  /**
   * Produces the image represented by the luma of the current image. The luma is the weighted sum
   * of the color components.
   */
  public void imageLuma() {
    for (int r = 0; r < image.getImageHeight(); r++) {
      for (int c = 0; c < image.getImageWidth(); c++) {
        this.image.getImage()[r][c].makePixelLuma();
      }
    }
  }

  /**
   * Flips the image so that the left and right sides of the image are reversed. The vertical
   * component of each part of the image stays constant.
   */
  public void flipHorizontal() {
    Image newImage = new Image(new Pixel[image.getImageHeight()][image.getImageWidth()]);
    for (int r = 0; r < image.getImageHeight(); r++) {
      for (int c = 0; c < image.getImageWidth(); c++) {
        newImage.getImage()[r][image.getImageWidth() - c - 1] = this.image.getImage()[r][c];
      }
    }
    this.image = newImage;
  }

  /**
   * Flips each image so that the top and bottom of the image are reversed. The horizontal component
   * of each part should stay constant.
   */
  public void flipVertical() {
    Image newImage = new Image(new Pixel[image.getImageHeight()][image.getImageWidth()]);
    for (int r = 0; r < image.getImageHeight(); r++) {
      for (int c = 0; c < image.getImageWidth(); c++) {
        newImage.getImage()[image.getImageHeight() - r - 1][c] = this.image.getImage()[r][c];
      }
    }
    this.image = newImage;
  }

  /**
   * Brightens the image by a specified amount. Brightens the image by increasing the color values.
   *
   * @param amount the amount to brighten the image by.
   */
  public void brighten(int amount) {
    for (int r = 0; r < image.getImageHeight(); r++) {
      for (int c = 0; c < image.getImageWidth(); c++) {
        this.image.getImage()[r][c].makePixelBrighter(amount);
      }
    }
  }

  /**
   * Darkens the image by a specified amount. Darkens the image by decreasing the color values.
   *
   * @param amount the amount to darken the image by.
   */
  public void darken(int amount) {
    for (int r = 0; r < image.getImageHeight(); r++) {
      for (int c = 0; c < image.getImageWidth(); c++) {
        this.image.getImage()[r][c].makePixelDarker(amount);
      }
    }
  }


  /**
   * Gets the image of this RGBModel. This image is a copy of the image instead of the image itself.
   * This prevents a user from changing values in this image's array through access.
   *
   * @return the array representing this image.
   */
  public Image getImage() {
    Image copy = new Image(new Pixel[image.getImageHeight()][image.getImageWidth()]);
    for (int row = 0; row < image.getImageHeight(); row++) {
      for (int col = 0; col < image.getImageWidth(); col++) {
        copy.getImage()[row][col] = this.image.getImage()[row][col];
      }
    }
    return copy;
  }


  public int redValuePixel(int row, int col) {
    return image.getImage()[row][col].getRed();
  }

  public int greenValuePixel(int row, int col) {
    return image.getImage()[row][col].getGreen();
  }

  public int blueValuePixel(int row, int col) {
    return image.getImage()[row][col].getBlue();
  }

  public void overWriteImage(Image image) {
    this.image = image;
  }

  /**
   * Applies a blur effect to an image. Blur effect is based on a 3x3 kernel.
   */
  @Override
  public void gaussianBlur() {
    double[][] blur = new double[3][3];
    blur[0][0] = 0.0625;
    blur[0][1] = 0.125;
    blur[0][2] = 0.0625;
    blur[1][0] = 0.125;
    blur[1][1] = 0.25;
    blur[1][2] = 0.125;
    blur[2][0] = 0.0625;
    blur[2][1] = 0.125;
    blur[2][2] = 0.0625;
    Filter gaussian = new FilterImpl(blur);
    this.image = gaussian.apply(this.image);
  }

  /**
   * Adjusts the color values of each pixel in the image. Makes color values represent a greyscale
   * image based on luma.
   */
  public void greyscale() {
    double[][] grey = new double[3][3];
    grey[0][0] = .2126;
    grey[0][1] = .7152;
    grey[0][2] = .2126;
    grey[1][0] = .2126;
    grey[1][1] = .7152;
    grey[1][2] = .2126;
    grey[2][0] = .2126;
    grey[2][1] = .7152;
    grey[2][2] = .2126;
    Transformation greyscale = new TransformationImpl(grey);
    this.image = greyscale.applyLinear(this.image);
  }

  /**
   * Adjusts the color values of each pixel in the image. Makes color values represent a sepia
   * image.
   */
  public void sepia() {
    double[][] sep = new double[3][3];
    sep[0][0] = .393;
    sep[0][1] = .769;
    sep[0][2] = .189;
    sep[1][0] = .349;
    sep[1][1] = .686;
    sep[1][2] = .168;
    sep[2][0] = .272;
    sep[2][1] = .534;
    sep[2][2] = .131;
    Transformation sepia = new TransformationImpl(sep);
    this.image = sepia.applyLinear(this.image);
  }

  /**
   * Applies a sharpening effect to an image. Sharpening effect is based on 5x5 kernel.
   */
  @Override
  public void sharpen() {
    double[][] sharpen = {
        {-0.125, -0.125, -0.125, -0.125, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, 0.25, 1, 0.25, -0.125},
        {-0.125, 0.25, 0.25, 0.25, -0.125},
        {-0.125, -0.125, -0.125, -0.125, -0.125}};
    Filter sharp = new FilterImpl(sharpen);
    this.image = sharp.apply(this.image);
  }

  /**
   * Checks if two models are equal to each other. DOes this based on their images.
   *
   * @param m is the object being compared to.
   * @return whether the object is equal to the model.
   */
  public boolean equals(Object m) {
    if (!(m instanceof RGBModel)) {
      return false;
    }

    RGBModel model = (RGBModel) m;
    return this.image.equals(model.getImage());
  }

  /**
   * Returns an identifying key for the model. Creates this key based on the image.
   *
   * @return the identifying key.
   */
  public int hashCode() {
    return this.image.hashCode();
  }

}
