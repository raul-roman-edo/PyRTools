package com.apps.pyr.pyrtools.main.usecase;

import com.apps.pyr.pyrtools.core.execution.Result;
import com.apps.pyr.pyrtools.core.repository.Cache;
import com.apps.pyr.pyrtools.core.storage.Store;
import com.apps.pyr.pyrtools.main.cards.image.ImageCard;
import java.util.List;

public class ImagesCache implements Cache<Void, Result<List<ImageCard>>> {
  private static final int CACHE_VALIDITY = 24 * 3600000;
  private final Store<Void, ImagesCacheEntity> store;

  public ImagesCache(Store<Void, ImagesCacheEntity> store) {
    this.store = store;
  }

  @Override public Result<List<ImageCard>> request(Void aVoid) {
    ImagesCacheEntity cached = store.load(aVoid);
    Result<List<ImageCard>> result = obtainAdjustedResult(cached);
    return result;
  }

  @Override public void update(Result<List<ImageCard>> result) {
    if (!result.isValid()) return;
    ImagesCacheEntity cached = new ImagesCacheEntity();
    cached.setResult(result);
    cached.setDate(System.currentTimeMillis());
    store.save(cached);
  }

  private Result<List<ImageCard>> obtainAdjustedResult(ImagesCacheEntity cached) {
    Result<List<ImageCard>> result = cached.getResult();
    if (!result.isValid()) return result;
    result = adjustByValidity(result, cached.getDate());

    return result;
  }

  private Result<List<ImageCard>> adjustByValidity(Result<List<ImageCard>> result, long date) {
    if (System.currentTimeMillis() - date > CACHE_VALIDITY) {
      result.setValid(false);
    }
    return result;
  }
}
