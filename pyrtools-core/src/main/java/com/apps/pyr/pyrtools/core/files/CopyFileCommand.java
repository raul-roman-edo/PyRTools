package com.apps.pyr.pyrtools.core.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class CopyFileCommand {

  public boolean duplicate(File sourceFile, File dstFile) {
    FileChannel src = null;
    FileChannel dst = null;
    try {
      if (sourceFile.exists()) {
        src = new FileInputStream(sourceFile).getChannel();
        dst = new FileOutputStream(dstFile).getChannel();
        dst.transferFrom(src, 0, src.size());
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (src != null) {
          src.close();
        }
        if (dst != null) {
          dst.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return true;
  }
}
