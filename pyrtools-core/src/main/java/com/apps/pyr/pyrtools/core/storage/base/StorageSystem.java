package com.apps.pyr.pyrtools.core.storage.base;

public interface StorageSystem {
  String load(String key, String defaultObject);

  void save(String key, String object);
}
