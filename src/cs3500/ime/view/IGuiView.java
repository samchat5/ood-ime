package cs3500.ime.view;

import cs3500.ime.controller.IGuiController;
import java.awt.image.BufferedImage;

public interface IGuiView extends IIMEView {

  void loadImage(BufferedImage image);

  void acceptFeatures(IGuiController features);
}
