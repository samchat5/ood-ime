package view.hw7;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.ImageModel;
import view.GuiViewImpl;

/**
 * Represents the GUI view for the program, with the added functionality for mosaicking. Builds a
 * new button for feature, and includes a popup menu for the user to select the number of seeds.
 */
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
      JOptionPane pane = new SpinnerOptionPane("Number of seeds", "Mosaic", 0, 15000, 7500, 100);
      Object val = pane.getInputValue();
      Object opt = pane.getValue();
      if (commandCallback != null && val != null && opt != null
          && (int) opt == JOptionPane.OK_OPTION) {
        commandCallback.accept("mosaic " + val);
      }
    });
    buttonPanel.add(mosaic);
  }
}
