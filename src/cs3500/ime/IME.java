package cs3500.ime;

import cs3500.ime.controller.IIMEController;
import cs3500.ime.controller.IMEController;
import cs3500.ime.model.IMEModel;
import cs3500.ime.view.IMETextView;
import java.io.InputStreamReader;

public class IME {

  public static void main(String[] args) {
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(System.out),
        new InputStreamReader(System.in));
    cont.run();
  }
}
