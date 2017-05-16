package com.apps.pyr.pyrtools.core.execution.network;

import com.apps.pyr.pyrtools.core.execution.Result;

public class ApiResponse<Payload> extends Result<Payload> {
  public static final int INVALID_CODE = 0;
  private int code = INVALID_CODE;
  private int errorCode = INVALID_CODE;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
