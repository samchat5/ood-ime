package view;

import static model.ImageUtil.writeToFile;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import model.ImageModel;

/**
 * This represents the implementation of a GuiView. It shows a graphical interface that the user can
 * interact with.
 */
public class GuiViewImpl extends JFrame implements GuiView {

  private final ImageModel model;
  protected Consumer<String> commandCallback;
  protected JPanel buttonPanel;
  private JPanel mainPanel;
  private JPanel imagePanel;
  private BufferedImage currentImage;
  private JPanel dialogBoxesPanel;
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JLabel imagelabel;


  /**
   * This constructor creates and instance of this class and draws the initial version of the view
   * before the image is loaded.
   *
   * @param model the given model.
   * @throws IllegalArgumentException if the model is null.
   */
  public GuiViewImpl(ImageModel model) throws IllegalArgumentException {
    super();
    try {
      setTitle("Image Processor");
      setSize(1200, 700);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.model = Objects.requireNonNull(model);
      setUpMainPanel();
      buildButtons();
      buildImageDisplay();
      dialogueBoxes();
      saveImage();
      openImage();
      commandCallback = null;
      this.makeVisible();
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Model cannot be null");
    }
  }

  public void setCommandCallback(Consumer<String> c) {
    this.commandCallback = c;
  }

  /**
   * This method makes objects visible in the view. It uses a built-in JFrame method called
   * setVisible to make things like panels visible on screen.
   */
  private void makeVisible() {
    this.setVisible(true);
  }


  /**
   * Updates the view based on the recent changes. Ensures that all displays are up-to-date.
   */
  public void refresh() {
    showImage();
    if (imagelabel != null) {
      imagePanel.removeAll();
      imagePanel.revalidate();
      imagelabel.setIcon(new ImageIcon(currentImage));
      imagePanel.add(imagelabel);
    }
    repaint();
  }

  //Sets up the main panel with scroll bars on both axis
  private void setUpMainPanel() {
    this.mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainScrollPane);
  }


  private void buildImageDisplay() {
    //show an image with a scrollbar
    imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    mainPanel.add(imagePanel);
  }

  private void showImage() {
    writeToFile(model.getImage(), "res/temp.png");
    try {
      currentImage = ImageIO.read(new FileInputStream("res/temp.png"));
    } catch (IOException e) {
      throw new IllegalArgumentException(" File does not exist");
    }
    imagelabel = new JLabel(new ImageIcon(currentImage));
  }


  // This method builds the button panel and its content.
  protected void buildButtons() {
    this.buildButtonPanel();
    this.buildGreyscaleButton();
    this.buildSepiaButton();
    this.buildBlurButton();
    this.buildMakeRedButton();
    this.buildMakeGreenButton();
    this.buildMakeBlueButton();
    this.buildLumaButton();
    this.buildIntensityButton();
    this.buildFlipHorizontalButton();
    this.buildFlipVerticalButton();
    this.buildBrighten();
    this.buildDarken();
  }


  // Creates the panel that houses all the buttons for this program.
  private void buildButtonPanel() {
    buttonPanel = new JPanel();
    mainPanel.add(buttonPanel); //Add the button panel to the main panel
  }

  // creates the greyscale button
  private void buildGreyscaleButton() {
    JButton greyscale = new JButton("Greyscale");
    greyscale.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("greyscale");
      }
    });
    buttonPanel.add(greyscale);
  }

  private void buildSepiaButton() {
    JButton sepia = new JButton("Sepia");
    sepia.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("sepia");
      }
    });
    buttonPanel.add(sepia);
  }

  private void buildBlurButton() {
    JButton blur = new JButton("Blur");
    blur.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("blur");
      }
    });
    buttonPanel.add(blur);
  }

  private void buildMakeRedButton() {
    JButton red = new JButton("Make Red");
    red.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("make-red");
      }
    });
    buttonPanel.add(red);
  }

  private void buildMakeGreenButton() {
    JButton green = new JButton("Make Green");
    green.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("make-green");
      }
    });
    buttonPanel.add(green);
  }

  private void buildMakeBlueButton() {
    JButton blue = new JButton("Make Blue");
    blue.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("make-blue");
      }
    });
    buttonPanel.add(blue);
  }

  private void buildLumaButton() {
    JButton luma = new JButton("Luma");
    luma.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("luma-component");
      }
    });
    buttonPanel.add(luma);
  }

  private void buildIntensityButton() {
    JButton intensity = new JButton("Intensity");
    intensity.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("change-intensity");
      }
    });
    buttonPanel.add(intensity);
  }

  private void buildFlipVerticalButton() {
    JButton flipv = new JButton("Flip Vertical");
    flipv.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("vertical-flip");
      }
    });
    buttonPanel.add(flipv);
  }

  private void buildFlipHorizontalButton() {
    JButton fliph = new JButton("Flip Horizontal");
    fliph.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("horizontal-flip");
      }
    });
    buttonPanel.add(fliph);
  }

  private void buildBrighten() {
    JButton brighten = new JButton("Brighten");
    brighten.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("brighten");
      }
    });

    buttonPanel.add(brighten);
  }

  private void buildDarken() {
    JButton darken = new JButton("Darken");
    darken.addActionListener((ActionEvent e) -> {
      if (commandCallback != null) {
        commandCallback.accept("darken");
      }
    });
    buttonPanel.add(darken);
  }

  @Override
  public void renderMessage(String message) {
    JOptionPane.showMessageDialog(GuiViewImpl.this, message,
        "Message", JOptionPane.PLAIN_MESSAGE);
  }

  private void dialogueBoxes() {
    //dialog boxes
    dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Dialog boxes"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);
  }

  private void openImage() {
    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("load");
    final JFileChooser fchooser = new JFileChooser(FileSystemView.getFileSystemView());
    fileOpenButton.addActionListener((ActionEvent e) -> {
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
          "Image Files", "jpg", "gif", "png", "jpeg", "ppm", "bpm");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(null);
      if (retvalue == JFileChooser.APPROVE_OPTION && commandCallback != null) {
        File f = fchooser.getSelectedFile();
        fileOpenDisplay.setText(f.getAbsolutePath());
        commandCallback.accept("load " + f);
      }
    });
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);
  }

  private void saveImage() {
    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filesavePanel);
    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("save");
    fileSaveButton.addActionListener((ActionEvent e) -> {
      final JFileChooser fchooser = new JFileChooser(".");
      int retvalue = fchooser.showSaveDialog(GuiViewImpl.this);
      if (retvalue == JFileChooser.APPROVE_OPTION && commandCallback != null) {
        File imageSave = fchooser.getSelectedFile();
        fileSaveDisplay.setText(imageSave.getAbsolutePath());
        commandCallback.accept("save " + imageSave.getAbsolutePath());
      }
    });
    filesavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);
  }

  // --Commented out by Inspection START (12/6/2021 1:05 AM):
  //  private void histograms() {
  //    if (model.getImage() != null) {
  //      HistogramPanel hist = new HistogramPanel();
  //      hist.paintComponent(getGraphics());
  //      mainPanel.add(hist);
  //    }
  //  }
  // --Commented out by Inspection STOP (12/6/2021 1:05 AM)

  // --Commented out by Inspection START (12/6/2021 1:08 AM):
  //  /**
  //   * This class represents a panel in which to draw the histogram. It contains method to a draw
  //   * a histogram.
  //   */
  //  private class HistogramPanel extends JPanel {
  //
  //    /**
  //     * This method overrides the original painComponent method to draw a histogram.
  //     *
  //     * @param g the graphics that are being painted.
  //     */
  //    @Override
  //    public void paintComponent(Graphics g) {
  //      super.paintComponent(g);
  //      HistogramImpl h = new HistogramImpl();
  //      int[] red = h.redHistogram(model.getImage());
  //      int[] green = h.greenHistogram(model.getImage());
  //      int[] blue = h.blueHistogram(model.getImage());
  //      for (int i = 0; i < 255; i++) {
  //        int xTwo = i + 1;
  //        g.setColor(Color.RED);
  //        g.drawLine(i * 20, red[i] * 20, xTwo * 20, red[xTwo] * 20);
  //        g.setColor(Color.GREEN);
  //        g.drawLine(i * 20, green[i] * 20 + 100, xTwo * 20, green[xTwo] * 20 + 100);
  //        g.setColor(Color.BLUE);
  //        g.drawLine(i * 20, blue[i] * 20 + 150, xTwo * 20, blue[xTwo] * 20 + 150);
  //      }
  //    }
  //  }
  // --Commented out by Inspection STOP (12/6/2021 1:08 AM)
}
