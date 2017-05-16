package com.apps.pyr.pyrtools.main.usecase;

import com.apps.pyr.pyrtools.core.execution.Result;
import com.apps.pyr.pyrtools.main.cards.image.ImageCard;
import java.util.List;

public class ImagesCacheEntity {
  private long date = 0;
  private Result<List<ImageCard>> result = new Result<>();

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public Result<List<ImageCard>> getResult() {
    return result;
  }

  public void setResult(Result<List<ImageCard>> result) {
    this.result = result;
  }
}
