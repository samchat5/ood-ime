package cs3500.ime.view;

import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JToolBar;

/**
 * Toolbar component used in the main GUI for out IME program. Takes in a list of components (i.e.
 * buttons) and adds them to the toolbar.
 */
public class IMEToolbar extends JToolBar {

  /**
   * Constructor for IMEToolbar. Takes in a list of components to add to the toolbar.
   *
   * @param components list of components to add to the toolbar
   */
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
