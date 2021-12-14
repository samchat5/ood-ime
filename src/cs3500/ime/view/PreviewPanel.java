package cs3500.ime.view;

import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class PreviewPanel extends JDialog {


  public PreviewPanel(JFrame frame, JScrollPane previewImagePane) {
    super(frame, "Preview");
    this.add(previewImagePane);
    this.setMinimumSize(new Dimension(200, 200));
    previewImagePane.setMinimumSize(new Dimension(200, 200));
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}
