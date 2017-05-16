package com.apps.pyr.pyrtools.core.execution.network;

import com.apps.pyr.pyrtools.core.execution.AsyncCallback;
import com.apps.pyr.pyrtools.core.execution.Dispatcher;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class NetworkDispatcher<Result extends ApiResponse> implements Dispatcher<Result> {
  private static final int STATUS_CONNECTIVITY_ERROR = 0;
  private static final int STATUS_OK = 100;
  private static final int STATUS_ERROR = 400;
  private final Map<Integer, AsyncCallback<Result>> statusCallbacks;
  private final Map<String, AsyncCallback<Result>> errorCallbacks;
  private final NavigableMap<Integer, AsyncCallback<Result>> globalCallbacks;

  public NetworkDispatcher(AsyncCallback<Result> success, AsyncCallback<Result> error,
      AsyncCallback<Result> connectivityError) {
    statusCallbacks = new HashMap<>();
    errorCallbacks = new HashMap<>();
    globalCallbacks = new TreeMap<>();
    globalCallbacks.put(STATUS_OK, success);
    globalCallbacks.put(STATUS_ERROR, error);
    globalCallbacks.put(STATUS_CONNECTIVITY_ERROR, connectivityError);
  }

  public NetworkDispatcher addStatusCallback(int status, AsyncCallback<Result> callback) {
    statusCallbacks.put(status, callback);
    return this;
  }

  public NetworkDispatcher addErrorCodeCallback(String errorCode, AsyncCallback<Result> callback) {
    errorCallbacks.put(errorCode, callback);
    return this;
  }

  @Override public void dispatch(Result result) {
    int status = result.getCode();
    boolean isError = status >= STATUS_ERROR;
    AsyncCallback<Result> callback = null;
    if (isError) {
      callback = errorCallbacks.get(result.getErrorCode());
    }
    if (callback == null) {
      callback = statusCallbacks.get(status);
    }
    if (callback == null) {
      callback = globalCallbacks.floorEntry(status).getValue();
    }
    callback.onFinished(result);
  }
}
