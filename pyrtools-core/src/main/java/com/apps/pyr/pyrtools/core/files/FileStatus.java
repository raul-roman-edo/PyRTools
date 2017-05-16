package com.apps.pyr.pyrtools.core.files;

import java.util.ArrayList;
import java.util.List;

public class FileStatus {
  private String name = "";
  private boolean isDir = false;
  private boolean canRead = false;
  private boolean canWrite = false;
  private boolean canExecute = false;
  private long lastModified = 0L;
  private long size = 0L;
  private List<FileStatus> content;

  public FileStatus() {
    content = new ArrayList<>();
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDir(boolean dir) {
    isDir = dir;
  }

  public void setCanRead(boolean canRead) {
    this.canRead = canRead;
  }

  public void setCanWrite(boolean canWrite) {
    this.canWrite = canWrite;
  }

  public void setCanExecute(boolean canExecute) {
    this.canExecute = canExecute;
  }

  public void setLastModified(long lastModified) {
    this.lastModified = lastModified;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public void addContent(List<FileStatus> content) {
    this.content.addAll(content);
  }
}
