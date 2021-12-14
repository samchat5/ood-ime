package cs3500.ime.view;

import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * Class representing the preview panel for the IME. This panel is used to display an operation
 * being applied to a portion of an image.
 */
public class PreviewPanel extends JDialog {

  /**
   * Constructor for the preview panel. Takes in the image pane that contains the image to be
   * displayed, and the parent frame.
   *
   * @param frame            the parent frame
   * @param previewImagePane the image pane that contains the image to be displayed
   */
  public PreviewPanel(JFrame frame, JScrollPane previewImagePane) {
    super(frame, "Preview");
    this.add(previewImagePane);
    this.setMinimumSize(new Dimension(200, 200));
    previewImagePane.setMinimumSize(new Dimension(200, 200));
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}
