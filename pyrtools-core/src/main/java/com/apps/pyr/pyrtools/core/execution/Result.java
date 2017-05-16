package com.apps.pyr.pyrtools.core.execution;

public class Result<Payload> {
  private Payload payload;
  private boolean valid = false;

  public Payload getPayload() {
    return payload;
  }

  public void setPayload(Payload payload) {
    this.payload = payload;
  }

  public boolean isValid() {
    return valid;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }
}
