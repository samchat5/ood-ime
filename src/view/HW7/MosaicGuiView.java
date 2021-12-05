package view.HW7;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import model.ImageModel;
import view.GuiViewImpl;

public class MosaicGuiView extends GuiViewImpl {

  /**
   * This constructor creates and instance of this class and draws the initial version of the view
   * before the image is loaded.
   *
   * @param model the given model.
   */
  public MosaicGuiView(ImageModel model) {
    super(model);
  }

  @Override
  protected void buildButtons() {
    super.buildButtons();
    this.buildMosaicButton();
  }

  private void buildMosaicButton() {
    JButton mosaic = new JButton("Mosaic");
    mosaic.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("mosaic 1000");
      }
    });
    buttonPanel.add(mosaic);
  }
}
