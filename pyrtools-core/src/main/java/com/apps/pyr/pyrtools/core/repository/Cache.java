package com.apps.pyr.pyrtools.core.repository;

public interface Cache<Params, Data> {
  Data request(Params params);

  void update(Data data);
}
