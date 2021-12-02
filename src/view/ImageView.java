package view;

import java.io.IOException;

/**
 * This interface represents the view of this Image Processor. This interface holds all the
 * methods that modify the view of this Image Processor.
 */
public interface ImageView {

  /**
   * Renders the given message.
   * Sends the string to the provided output.
   */
  public void renderMessage(String message) throws IOException;


}
