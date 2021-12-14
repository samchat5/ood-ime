package cs3500.ime.view;

import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JToolBar;

public class IMEToolbar extends JToolBar {

  public IMEToolbar(JComponent[] components) {
    super();
    this.setFloatable(false);
    this.setMargin(new Insets(10, 50, 10, 50));
    for (int i = 0; i < components.length; i++) {
      this.add(components[i]);
      if (i != components.length - 1) {
        this.add(new Separator());
      }
    }
  }
}
