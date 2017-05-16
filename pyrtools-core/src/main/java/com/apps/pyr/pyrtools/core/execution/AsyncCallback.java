package com.apps.pyr.pyrtools.core.execution;

public interface AsyncCallback<Result> {
  void onFinished(Result result);
}
