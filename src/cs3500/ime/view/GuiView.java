package cs3500.ime.view;

import cs3500.ime.controller.IGuiController;
import cs3500.ime.model.IIMEModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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
import javax.swing.filechooser.FileNameExtensionFilter;

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
  private String selectedOp = null;


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
    fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "tif", "ppm", "jpg", "bmp",
        "gif", "png", "wbmp", "jpeg"));

    String[] operations = new String[]{"Sepia", "Sharpen", "Blur", "Brighten",
        "Luma Component", "Intensity Component", "Value Component", "Red Component",
        "Green Component", "Blue Component", "Horizontal Flip", "Vertical Flip"};
    Map<String, String> operationToCommand = new HashMap<String, String>();
    for (String op : operations) {
      operationToCommand.put(op, String.join("-", op.toLowerCase().split(" ")));
    }
    operationDropdown = new JComboBox<>(operations);
    operationDropdown.setEditable(false);
    operationDropdown.addActionListener(
        e -> selectedOp = (String) ((JComboBox<?>) e.getSource()).getSelectedItem());
    toolbar.add(operationDropdown);

    separator1 = new Separator();
    toolbar.add(separator1);

    applyButton = new JButton();
    applyButton.setText("Apply");
    applyButton.addActionListener(new ActionListener() {
      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        if (selectedOp != null) {
          features.setScanner(new Scanner(operationToCommand.get(selectedOp)));
          features.run();
        }
      }
    });
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
        features.saveImage(fileChooser.getSelectedFile().getAbsolutePath());
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
