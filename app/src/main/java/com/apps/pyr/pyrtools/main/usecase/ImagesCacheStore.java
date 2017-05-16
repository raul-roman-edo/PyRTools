package com.apps.pyr.pyrtools.main.usecase;

import com.apps.pyr.pyrtools.core.storage.base.BaseStorage;
import com.apps.pyr.pyrtools.core.storage.base.StorageSystem;

public class ImagesCacheStore extends BaseStorage<ImagesCacheEntity> {
  private static final String CACHED_IMAGES_KEY = "cached_images";

  public ImagesCacheStore(StorageSystem store) {
    super(store);
  }

  @Override protected Class<? extends ImagesCacheEntity> obtainClass() {
    return ImagesCacheEntity.class;
  }

  @Override protected String obtainKey() {
    return CACHED_IMAGES_KEY;
  }

  @Override protected ImagesCacheEntity obtainDefault() {
    return new ImagesCacheEntity();
  }
}
