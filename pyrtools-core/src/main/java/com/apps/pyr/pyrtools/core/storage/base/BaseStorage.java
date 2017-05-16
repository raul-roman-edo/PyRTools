package com.apps.pyr.pyrtools.core.storage.base;

import com.apps.pyr.pyrtools.core.storage.Store;
import com.google.gson.Gson;
import java.lang.reflect.Type;

public abstract class BaseStorage<Result> implements Store<Void, Result> {
  private final StorageSystem store;

  public BaseStorage(StorageSystem store) {
    this.store = store;
  }

  @Override public Result load(Void ignore) {
    Result defaultValue = obtainDefault();
    String key = obtainKey();
    String raw = store.load(key, null);
    if (raw == null) return defaultValue;
    Result loaded = createLoadedObject(raw);

    return loaded;
  }

  @Override public void save(Result data) {
    String raw = obtainRawContent(data);
    String key = obtainKey();
    store.save(key, raw);
  }

  protected Gson createGson() {
    return new Gson();
  }

  protected Type createTypeToken() {
    return null;
  }

  protected abstract Class<? extends Result> obtainClass();

  protected abstract String obtainKey();

  protected abstract Result obtainDefault();

  private Result createLoadedObject(String raw) {
    Gson gson = createGson();
    Type type = createTypeToken();
    Result loaded;
    if (type != null) {
      loaded = gson.fromJson(raw, type);
    } else {
      Class<? extends Result> clazz = obtainClass();
      loaded = gson.fromJson(raw, clazz);
    }
    return loaded;
  }

  private String obtainRawContent(Result data) {
    Gson gson = createGson();
    Type type = createTypeToken();
    String raw;
    if (type != null) {
      raw = gson.toJson(data, type);
    } else {
      Class<? extends Result> clazz = obtainClass();
      raw = gson.toJson(data, clazz);
    }
    return raw;
  }
}
