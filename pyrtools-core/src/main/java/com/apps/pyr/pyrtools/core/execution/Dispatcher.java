package com.apps.pyr.pyrtools.core.execution;

public interface Dispatcher<Result> {
  void dispatch(Result result);
}
