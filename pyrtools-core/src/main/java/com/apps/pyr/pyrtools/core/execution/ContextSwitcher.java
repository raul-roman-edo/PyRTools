package com.apps.pyr.pyrtools.core.execution;

public interface ContextSwitcher<Result> {
  void startInBackground(BackgroundAction<Result> action);
}
