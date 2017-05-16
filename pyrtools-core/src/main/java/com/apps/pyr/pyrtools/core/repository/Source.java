package com.apps.pyr.pyrtools.core.repository;

public interface Source<Params, Data> {
  Data request(Params params);
}
