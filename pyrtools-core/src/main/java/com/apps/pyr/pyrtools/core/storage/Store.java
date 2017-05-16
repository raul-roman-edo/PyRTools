package com.apps.pyr.pyrtools.core.storage;

public interface Store<Params, Result> {
  Result load(Params params);

  void save(Result data);
}