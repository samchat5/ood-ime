package cs3500.ime.controller;

import java.io.File;
import java.util.Scanner;

public interface IGuiController extends IIMEController {

  void setScanner(Scanner s);

  void loadImage(File imageFile);

  void saveImage(String absolutePath);
}
