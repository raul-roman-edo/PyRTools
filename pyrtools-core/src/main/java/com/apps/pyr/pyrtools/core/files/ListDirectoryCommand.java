package com.apps.pyr.pyrtools.core.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListDirectoryCommand {
  public List<FileStatus> ls(String dirPath) {
    File dir = new File(dirPath);
    if (!dir.exists() || !dir.isDirectory()) return new ArrayList<>();

    return listDir(dir);
  }

  private List<FileStatus> listDir(File dir) {
    List<FileStatus> filesList = new ArrayList<>();
    FileStatus status;
    for (File file : dir.listFiles()) {
      status = new FileStatus();
      status.setDir(file.isDirectory());
      status.setCanRead(file.canRead());
      status.setCanWrite(file.canWrite());
      status.setCanExecute(file.canExecute());
      status.setLastModified(file.lastModified());
      status.setName(file.getAbsolutePath());
      status.setSize(file.length());

      if (file.isDirectory()) {
        status.addContent(listDir(file));
      }
    }
    return filesList;
  }
}
