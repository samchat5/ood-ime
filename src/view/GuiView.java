package view;

import java.util.function.Consumer;


/**
 * This interface represents a GUI View. It holds methods that will allow implementation of the
 * GUI within the view.
 */
public interface GuiView extends ImageView {


  /**
   * Redraws the view whenever there is a change to an image.
   */
  void refresh();

  /**
   * Sets the command callback to the given consumer.
   * @param c the given consumer.
   */
  public void setCommandCallback(Consumer<String> c);

  /**
   * This method makes objects visible in the view. It uses a built in JFrame method called
   * setVisible to make things like panels visible on screen.
   */
  void makeVisible(boolean b);


}
