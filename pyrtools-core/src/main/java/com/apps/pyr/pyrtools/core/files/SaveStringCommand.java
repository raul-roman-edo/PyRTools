package com.apps.pyr.pyrtools.core.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveStringCommand {
  private static final String DESTINATION_TEMPLATE = "%s/%s";

  public boolean save(String toSave, String dstDir, String dstFilename) {
    boolean result = true;
    File file = new File(String.format(DESTINATION_TEMPLATE, dstDir, dstFilename));
    try {
      FileWriter fw = new FileWriter(file, false);
      fw.write(toSave);
      fw.close();
    } catch (IOException ioe) {
      result = false;
    }
    return result;
  }
}
