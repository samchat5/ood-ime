package cs3500.ime.view;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

/**
 * General class represents an option pane with a spinner input. Used for command such as brighten
 * and downscale, but can be used for any purpose where an alert with a spinner is needed.
 */
public class SpinnerOptionPane extends JOptionPane {

  private final int stepSize;
  private final int maxVal;
  private final int minVal;
  private final int initVal;

  /**
   * Constructs an option pane with a spinner input.
   *
   * @param message  the message to display
   * @param title    the title of the option pane
   * @param minVal   the minimum value of the spinner
   * @param maxVal   the maximum value of the spinner
   * @param initVal  the initial value of the spinner
   * @param stepSize the step size of the spinner
   */
  public SpinnerOptionPane(String message, String title, int minVal, int maxVal, int initVal,
      int stepSize) {
    super(null);
    this.minVal = minVal;
    this.maxVal = maxVal;
    this.initVal = initVal;
    this.stepSize = stepSize;

    this.setInputValue(initVal);
    this.setMessage(new Object[]{message == null ? "Message" : message, this.getSpinner()});
    this.setMessageType(JOptionPane.QUESTION_MESSAGE);
    this.setOptionType(JOptionPane.OK_CANCEL_OPTION);

    JDialog d = this.createDialog(title == null ? "Message" : title);
    d.setResizable(true);
    d.setVisible(true);
  }

  private JSpinner getSpinner() {
    JSpinner spinner = new JSpinner(new SpinnerNumberModel(initVal, minVal, maxVal, stepSize));
    ChangeListener changeListener = changeEvent -> {
      JSpinner s = (JSpinner) changeEvent.getSource();
      this.setInputValue(s.getValue());
    };
    spinner.addChangeListener(changeListener);
    return spinner;
  }
}
