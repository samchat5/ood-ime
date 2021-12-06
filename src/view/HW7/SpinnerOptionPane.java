package view.HW7;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

public class SpinnerOptionPane extends JOptionPane {

  private final int stepSize;
  private final int maxVal;
  private final int minVal;
  private final int initVal;

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
