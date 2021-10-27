package pixel;

/**
 * Abstract class to represent all possible pixels (greyscale and RGB)
 */
public abstract class Pixel {
  protected int numBits;

  public Pixel(int numBits) {
    this.numBits = numBits;
  }

  public int getNumBits() {
    return this.numBits;
  }

  /**
   * Brightens/Darkens an individual pixel based on the given scalar.
   * @param scalar factor by which to change a pixels value.
   *               Positive means brighten, while negative means darden.
   */
  public abstract void brighten(int scalar);

}
