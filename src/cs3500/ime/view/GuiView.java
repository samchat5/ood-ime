package cs3500.ime.view;

import cs3500.ime.controller.IGuiController;
import cs3500.ime.model.IIMEModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JToolBar.Separator;

public class GuiView extends JFrame implements IGuiView {

  private final JComboBox<String> operationDropdown;
  private final JButton applyButton;
  private final JButton loadButton;
  private final JButton saveButton;
  private final Separator separator1;
  private final Separator separator2;
  private final Separator separator3;
  private final JToolBar toolbar;
  private final JPanel histogram;
  private final JFileChooser fileChooser;
  private final JFrame frame = this;
  private final JLabel messageLabel;
  private final JScrollPane imagePane;
  private IGuiController features;


  public GuiView(IIMEModel model) {
    super("IME Program");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout(0, 0));
    this.setMinimumSize(new Dimension(500, 552));

    toolbar = new JToolBar();
    toolbar.setFloatable(false);
    toolbar.setMargin(new Insets(10, 50, 10, 50));
    this.add(toolbar, BorderLayout.NORTH);

    this.fileChooser = new JFileChooser();

    operationDropdown = new JComboBox<>(new String[]{"Sepia", "Sharpen", "Blur", "Brighten",
        "Greyscale - Luma", "Greyscale - Intensity", "Greyscale - Value", "Greyscale - Red",
        "Greyscale - Green", "Greyscale - Blue", "Flip - Horizontal", "Flip - Vertical"});
    operationDropdown.setEditable(false);
    toolbar.add(operationDropdown);

    separator1 = new Separator();
    toolbar.add(separator1);
    applyButton = new JButton();
    applyButton.setText("Apply");
    toolbar.add(applyButton);
    separator2 = new Separator();
    toolbar.add(separator2);

    loadButton = new JButton();
    loadButton.setText("Load");
    loadButton.addActionListener(new ActionListener() {
      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        fileChooser.showOpenDialog(frame);
        features.loadImage(fileChooser.getSelectedFile());
      }
    });

    toolbar.add(loadButton);
    separator3 = new Separator();
    toolbar.add(separator3);
    messageLabel = new JLabel("IME Program");
    this.add(messageLabel, BorderLayout.PAGE_END);

    saveButton = new JButton();
    saveButton.setText("Save");
    saveButton.addActionListener(new ActionListener() {
      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        fileChooser.showSaveDialog(frame);
      }
    });
    toolbar.add(saveButton);

    imagePane = new JScrollPane();
    this.add(imagePane, BorderLayout.CENTER);

    histogram = new Histogram();
    this.add(histogram, BorderLayout.EAST);

    pack();
    setVisible(true);
  }

  /**
   * Display the given message to the user.
   *
   * @param message message to display
   */
  @Override
  public void displayMessage(String message) {
    this.messageLabel.setText(message);
  }

  @Override
  public void loadImage(BufferedImage image) {
    this.imagePane.setViewportView(new JLabel(new ImageIcon(image)));
    this.repaint();
  }

  @Override
  public void acceptFeatures(IGuiController features) {
    this.features = features;
  }
}
