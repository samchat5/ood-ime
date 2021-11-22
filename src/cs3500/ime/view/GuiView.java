package cs3500.ime.view;

import cs3500.ime.controller.IGuiController;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JToolBar.Separator;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GuiView extends JFrame implements IGuiView {

  private final JScrollPane histogram;
  private final JFileChooser fileChooser;
  private final JFrame frame = this;
  private final JScrollPane imagePane;
  private IGuiController features;
  private String selectedOp = null;


  public GuiView() {
    super("IME Program");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout(0, 0));
    this.setMinimumSize(new Dimension(500, 552));

    JToolBar toolbar = new JToolBar();
    toolbar.setFloatable(false);
    toolbar.setMargin(new Insets(10, 50, 10, 50));
    this.add(toolbar, BorderLayout.NORTH);

    this.fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "tif", "ppm", "jpg", "bmp",
        "gif", "png", "wbmp", "jpeg"));

    String[] operations = new String[]{"Sepia", "Sharpen", "Blur", "Brighten",
        "Luma Component", "Intensity Component", "Value Component", "Red Component",
        "Green Component", "Blue Component", "Horizontal Flip", "Vertical Flip"};
    Map<String, String> operationToCommand = new HashMap<>();
    for (String op : operations) {
      operationToCommand.put(op, String.join("-", op.toLowerCase().split(" ")));
    }
    JComboBox<String> operationDropdown = new JComboBox<>(operations);
    operationDropdown.setEditable(false);
    operationDropdown.addActionListener(
        e -> selectedOp = (String) ((JComboBox<?>) e.getSource()).getSelectedItem());
    toolbar.add(operationDropdown);

    toolbar.add(new Separator());
    JButton applyButton = new JButton();
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
          if (selectedOp.equals("Brighten")) {
            JOptionPane pane = new BrightenSpinnerOptionPane();
            Object val = pane.getInputValue();
            if (val != null) {
              features.setScanner(new Scanner("brighten " + val));
              features.run();
            }
          } else {
            features.setScanner(new Scanner(operationToCommand.get(selectedOp)));
            features.run();
          }
        }
      }
    });
    toolbar.add(applyButton);
    toolbar.add(new Separator());

    JButton loadButton = new JButton();
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
        features.setScanner(new Scanner("load " + fileChooser.getSelectedFile().getAbsolutePath()));
        features.run();
      }
    });
    toolbar.add(loadButton);
    toolbar.add(new Separator());

    JButton saveButton = new JButton();
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
        features.setScanner(new Scanner("save " + fileChooser.getSelectedFile().getAbsolutePath()));
      }
    });
    toolbar.add(saveButton);

    imagePane = new JScrollPane();
    this.add(imagePane, BorderLayout.CENTER);

    histogram = new JScrollPane();
    this.add(histogram, BorderLayout.EAST);

    pack();
    setVisible(true);
  }

  /**
   * Display the given message to the user. With a GUI, this means showing a dialog to the user.
   * This method is only used to display error messages for our GUI.
   *
   * @param message message to display
   */
  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void loadImage(BufferedImage image) {
    this.imagePane.setViewportView(new JLabel(new ImageIcon(image)));
    this.histogram.setViewportView(new Histogram(image.getRaster()));
    this.repaint();
  }

  @Override
  public void acceptFeatures(IGuiController features) {
    this.features = features;
  }
}