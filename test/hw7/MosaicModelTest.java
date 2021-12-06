package hw7;

import static junit.framework.TestCase.assertEquals;

import java.util.Random;
import model.Image;
import model.ImageUtil;
import model.hw7.MosaicModel;
import model.hw7.MosaicModelImpl;
import org.junit.Test;

/**
 * Tests for the MosaicModel class.
 */
public class MosaicModelTest {

  private final Image testOG;
  private final Image mosaicEx;

  public MosaicModelTest() {
    this.testOG = ImageUtil.readFile("res/testOG.png");
    this.mosaicEx = ImageUtil.readFile("res/testOG-mosaic-1000.png");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullImage() {
    new MosaicModelImpl(null);
  }

  @Test
  public void testPreloadImageMosaic() {
    MosaicModel model = new MosaicModelImpl(testOG);
    model.mosaic(1000, 1);
    assertEquals(model.getImage(), mosaicEx);
  }

  @Test(expected = IllegalStateException.class)
  public void testNegativeMosaic() {
    new MosaicModelImpl(testOG).mosaic(-1, new Random().nextInt());
  }

  @Test(expected = IllegalStateException.class)
  public void testZeroMosaic() {
    new MosaicModelImpl(testOG).mosaic(0, new Random().nextInt());
  }

  @Test
  public void testNormalMosaic() {
    MosaicModel model = new MosaicModelImpl();
    model.overWriteImage(ImageUtil.readFile("res/testOG.png"));
    model.mosaic(1000, 1);
    assertEquals(model.getImage(), ImageUtil.readFile("res/testOG-mosaic-1000.png"));
  }
}
