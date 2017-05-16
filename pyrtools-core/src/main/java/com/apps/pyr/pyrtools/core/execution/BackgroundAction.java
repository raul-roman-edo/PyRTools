package com.apps.pyr.pyrtools.core.execution;

public interface BackgroundAction<Result> {
  Result onBackground();

  void backOnMainThread(Result result);
}
