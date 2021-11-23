package cs3500.ime.view;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

/**
 * Option pane used for getting the amount a user wants to brighten the image by in our GUI. Shown
 * as a dialog box with a spinner, clamped to the range [-255, 255]. Closing or cancelling the
 * dialog does nothing, no matter what value the spinner was at that point.
 */
public class BrightenSpinnerOptionPane extends JOptionPane {

  /**
   * Constructor for this brighten option pane.
   */
  public BrightenSpinnerOptionPane() {
    super(null);
    JSpinner spinner = this.getSpinner();
    this.setMessage(new Object[]{"How much do you want to brighten by?", spinner});
    this.setMessageType(JOptionPane.QUESTION_MESSAGE);
    this.setOptionType(JOptionPane.OK_CANCEL_OPTION);
    JDialog d = this.createDialog("Brighten");
    d.setResizable(true);
    d.setVisible(true);
  }

  private JSpinner getSpinner() {
    JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, -255, 255, 1));
    ChangeListener changeListener = changeEvent -> {
      JSpinner s = (JSpinner) changeEvent.getSource();
      this.setInputValue(s.getValue());
    };
    spinner.addChangeListener(changeListener);
    return spinner;
  }
}