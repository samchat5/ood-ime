package model.HW7;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import model.Image;
import model.Pixel;
import model.PixelRGB;
import model.RGBModel;

public class MosaicModelImpl extends RGBModel implements MosaicModel {

  private final Map<Point, Set<Point>> seedsToPoints = new HashMap<>();
  private final Map<Point, Integer[]> seedsToRGB = new HashMap<>();
  private List<Point> seeds;

  public MosaicModelImpl(Image img) throws IllegalArgumentException {
    super(img);
  }

  public MosaicModelImpl() {
    super();
  }

  private Point closestSeed(Point p) {
    double minDist = Double.MAX_VALUE;
    Point minSeed = null;
    for (Point seed : seeds) {
      if (minDist > p.distance(seed)) {
        minDist = p.distance(seed);
        minSeed = seed;
      }
    }
    return minSeed;
  }

  private void generateSeeds(int seedCount, int randSeed) {
    Random r = new Random(randSeed);
    this.seeds = new ArrayList<>();
    for (int i = 0; i < seedCount; i++) {
      Point p = new Point(r.nextInt(image.getImageWidth()), r.nextInt(image.getImageHeight()));
      // if we randomly generate two of the same seeds, decrement i since we need to redo the
      // operations to get a new seed
      if (!seeds.contains(p)) {
        seeds.add(p);
        seedsToPoints.put(p, new HashSet<>());
        seedsToRGB.put(p, null);
      } else {
        i--;
      }
    }
  }

  private void generateMeanSeeds() {
    Pixel[][] img = image.getImage();
    for (Point seed : seeds) {
      Set<Point> points = seedsToPoints.get(seed);
      if (points.size() != 0) {
        int rSum = 0;
        int gSum = 0;
        int bSum = 0;
        for (Point p : points) {
          rSum += img[p.y][p.x].getRed();
          gSum += img[p.y][p.x].getGreen();
          bSum += img[p.y][p.x].getBlue();
        }
        int rAvg = rSum / points.size();
        int gAvg = gSum / points.size();
        int bAvg = bSum / points.size();
        seedsToRGB.put(seed, new Integer[]{rAvg, gAvg, bAvg});
      }
    }
  }

  private Pixel[][] changePoints() {
    Pixel[][] img = new Pixel[image.getImageHeight()][image.getImageWidth()];
    for (Point seed : seeds) {
      Integer[] rgb = seedsToRGB.get(seed);
      Set<Point> points = seedsToPoints.get(seed);
      points.forEach(p -> img[p.y][p.x] = new PixelRGB(rgb[0], rgb[1], rgb[2]));
    }
    return img;
  }

  @Override
  public void mosaic(int seedCount, int randSeed) throws IllegalStateException {
    if (seedCount < 1) {
      throw new IllegalStateException("Seed count must be greater than 0.");
    }
    // Make sure seed count is not greater than the number of pixels in the image
    try {
      seedCount = Math.min(seedCount, image.getImageHeight() * image.getImageWidth());
      this.generateSeeds(seedCount, randSeed);
      for (int y = 0; y < image.getImageHeight(); y++) {
        for (int x = 0; x < image.getImageWidth(); x++) {
          Point p = new Point(x, y);
          Point closestSeed = this.closestSeed(p);
          seedsToPoints.get(closestSeed).add(p);
        }
      }
      this.generateMeanSeeds();
      this.overWriteImage(new Image(this.changePoints()));
    } catch (NullPointerException e) {
      throw new IllegalStateException("Invalid image (either not loaded or corrupted).");
    }
  }
}
