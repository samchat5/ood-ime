package cs3500.ime.view;

import cs3500.ime.controller.IGuiController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Concrete class for our GUI view. This is the main {@code JFrame} that everything in our program
 * is painted onto, and contains all the event listeners for the sub-components, as private,
 * anonymous classes.
 */
public class GuiView extends JFrame implements IGuiView {

  private final JFileChooser fileChooser;
  private final JFrame frame = this;
  private final JScrollPane imagePane;
  private final JScrollPane histogram;
  private final JDialog previewDialog;
  private final JScrollPane previewImagePane;
  private final Map<String, String> operationToCommand = Map.ofEntries(
      new SimpleEntry<>("Sepia", "sepia"),
      new SimpleEntry<>("Sharpen", "sharpen"),
      new SimpleEntry<>("Blur", "blur"),
      new SimpleEntry<>("Brighten", "brighten"),
      new SimpleEntry<>("Luma", "luma-component"),
      new SimpleEntry<>("Intensity", "intensity-component"),
      new SimpleEntry<>("Value Component", "value-component"),
      new SimpleEntry<>("Red Component", "red-component"),
      new SimpleEntry<>("Green Component", "green-component"),
      new SimpleEntry<>("Blue Component", "blue-component"),
      new SimpleEntry<>("Horizontal Flip", "horizontal-flip"),
      new SimpleEntry<>("Vertical Flip", "vertical-flip"),
      new SimpleEntry<>("Downscale", "downscale"));
  private final String[] unsupportedMaskOps = new String[]{"Downscale", "Horizontal Flip",
      "Vertical Flip", "Brighten"};
  private boolean isImageLoaded = false;
  private boolean isPreviewVisible;
  private IGuiController features;
  private final Map<String, Runnable> operationApplyFunctions = Map.ofEntries(
      new SimpleEntry<>("Sepia", () -> features.setScanner(new Scanner("sepia"))),
      new SimpleEntry<>("Sharpen", () -> features.setScanner(new Scanner("sharpen"))),
      new SimpleEntry<>("Blur", () -> features.setScanner(new Scanner("blur"))),
      new SimpleEntry<>("Brighten",
          () -> getSpinnerOptionPaneInput(new BrightenSpinnerOptionPane(),
              (Object val) -> features.setScanner(new Scanner("brighten " + val)))),
      new SimpleEntry<>("Luma", () -> features.setScanner(new Scanner("luma-component"))),
      new SimpleEntry<>("Intensity", () -> features.setScanner(new Scanner("intensity-component"))),
      new SimpleEntry<>("Value Component",
          () -> features.setScanner(new Scanner("value-component"))),
      new SimpleEntry<>("Red Component", () -> features.setScanner(new Scanner("red-component"))),
      new SimpleEntry<>("Green Component",
          () -> features.setScanner(new Scanner("green-component"))),
      new SimpleEntry<>("Blue Component", () -> features.setScanner(new Scanner("blue-component"))),
      new SimpleEntry<>("Horizontal Flip",
          () -> features.setScanner(new Scanner("horizontal-flip"))),
      new SimpleEntry<>("Vertical Flip", () -> features.setScanner(new Scanner("vertical-flip"))),
      new SimpleEntry<>("Downscale", () -> getSpinnerOptionPaneInput(
          new SpinnerOptionPane("What's the new width?", "Downscale", 0,
              Integer.MAX_VALUE, 1, 1), (Object val) -> getSpinnerOptionPaneInput(
              new SpinnerOptionPane("What's the new height?", "Downscale", 0, Integer.MAX_VALUE,
                  1, 1), (Object val2) -> features.setScanner(
                  new Scanner("downscale " + val + " " + val2))))));
  private String selectedOp = null;

  /**
   * Constructor for our GuiView. Takes no parameters, and builds the initial empty layout seen when
   * opening up the program. Called when any repaint is done.
   */
  public GuiView() {
    super("IME Program");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout(0, 0));
    this.setMinimumSize(new Dimension(755, 552));

    // File Chooser
    this.fileChooser = new JFileChooser();
    fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "tif", "ppm", "jpg", "bmp",
        "gif", "png", "wbmp", "jpeg"));

    // Main pane(s)
    imagePane = new JScrollPane();
    histogram = new JScrollPane();
    JPanel mainSplitPane = new JPanel(new GridLayout(1, 2));
    mainSplitPane.add(imagePane);
    mainSplitPane.add(histogram);
    this.add(mainSplitPane, BorderLayout.CENTER);

    // Preview Dialog
    this.previewImagePane = new JScrollPane();
    this.previewDialog = new PreviewPanel(this, this.previewImagePane);
    this.previewDialog.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        isPreviewVisible = false;
        previewDialog.setVisible(false);
      }
    });
    addPreviewPaneListeners();

    // Toolbar
    this.add(new IMEToolbar(
        new JComponent[]{new OperationDropdown(), new ApplyButton(), new LoadButton(),
            new SaveButton(), new EnablePreviewButton()}), BorderLayout.NORTH);

    // Pack and show
    pack();
    setVisible(true);
  }

  private void getSpinnerOptionPaneInput(JOptionPane pane, Consumer<Object> callback) {
    Object val = pane.getInputValue();
    Object opt = pane.getValue();
    if (val != null && opt != null && (int) opt == JOptionPane.OK_OPTION) {
      callback.accept(val);
    }
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
  public void acceptFeatures(IGuiController features) {
    this.features = features;
  }

  @Override
  public boolean isPreviewEnabled() {
    return this.isPreviewVisible;
  }

  private void addPreviewPaneListeners() {
    this.previewImagePane.getHorizontalScrollBar().addAdjustmentListener(e -> {
      if (!e.getValueIsAdjusting()) {
        features.setMask(new Point(e.getAdjustable().getValue(),
            this.previewImagePane.getVerticalScrollBar().getValue()));
        if (Stream.of(unsupportedMaskOps).noneMatch(s -> s.equals(selectedOp))) {
          operationApplyFunctions.get(selectedOp).run();
          features.run();
        }
      }
    });
    this.previewImagePane.getVerticalScrollBar().addAdjustmentListener(e -> {
      if (!e.getValueIsAdjusting()) {
        features.setMask(new Point(this.previewImagePane.getHorizontalScrollBar().getValue(),
            e.getAdjustable().getValue()));
        if (Stream.of(unsupportedMaskOps).noneMatch(s -> s.equals(selectedOp))) {
          operationApplyFunctions.get(selectedOp).run();
          features.run();
        }
      }
    });
  }

  private void removePreviewPaneListeners() {
    this.previewImagePane.getVerticalScrollBar().removeAdjustmentListener(
        this.previewImagePane.getVerticalScrollBar().getAdjustmentListeners()[0]);
    this.previewImagePane.getHorizontalScrollBar().removeAdjustmentListener(
        this.previewImagePane.getHorizontalScrollBar().getAdjustmentListeners()[0]);
  }

  @Override
  public void loadImage(BufferedImage image) {
    isImageLoaded = true;
    Point p = this.previewImagePane.getViewport().getViewPosition();
    removePreviewPaneListeners();
    this.previewImagePane.setViewportView(new JLabel(new ImageIcon(image)));
    this.previewImagePane.getViewport().setViewPosition(p);
    addPreviewPaneListeners();
    if (!isPreviewEnabled()) {
      this.histogram.setViewportView(new Histogram(image.getRaster()));
      this.imagePane.setViewportView(new JLabel(new ImageIcon(image)));
      this.repaint();
    }
  }

  private class SaveButton extends JButton {

    private SaveButton() {
      super();
      this.setText("Save");
      this.addActionListener(e -> {
        fileChooser.showSaveDialog(frame);
        if (fileChooser.getSelectedFile() != null) {
          features.setScanner(
              new Scanner("save " + fileChooser.getSelectedFile().getAbsolutePath()));
        }
        features.run();
      });
    }
  }

  private class OperationDropdown extends JComboBox<String> {

    private OperationDropdown() {
      super(operationToCommand.keySet().toArray(new String[0]));
      this.setEditable(false);
      this.addActionListener(
          e -> selectedOp = (String) ((JComboBox<?>) e.getSource()).getSelectedItem());
      this.setSelectedIndex(0);
    }
  }

  private class EnablePreviewButton extends JButton {

    private EnablePreviewButton() {
      super();
      this.setText("Enable Preview");
      this.addActionListener(e -> {
        if (isImageLoaded && Stream.of(unsupportedMaskOps).noneMatch(s -> s.equals(selectedOp))) {
          isPreviewVisible = true;
          previewDialog.setVisible(true);
        }
      });
    }
  }

  private class LoadButton extends JButton {

    private LoadButton() {
      super();
      this.setText("Load");
      this.addActionListener(e -> {
        fileChooser.showOpenDialog(frame);
        if (fileChooser.getSelectedFile() != null) {
          features.setScanner(
              new Scanner("load " + fileChooser.getSelectedFile().getAbsolutePath()));
        }
        features.run();
      });
    }
  }

  private class ApplyButton extends JButton {

    private ApplyButton() {
      super();
      this.setText("Apply");
      this.addActionListener(e -> {
        if (selectedOp != null) {
          operationApplyFunctions.get(selectedOp).run();
          features.run();
        }
      });
    }
  }
}