package com.apps.pyr.pyrtools.core.android;

import android.content.Context;

public class RawResourceObtainerCommand {
  private static final String RAW_TYPE = "raw";
  private static final int INVALID_ID = 0;
  private static final String EMPTY = "";
  private final Context applicationContext;

  public RawResourceObtainerCommand(Context applicationContext) {
    this.applicationContext = applicationContext;
  }

  public String obtainByName(String resourceName) {
    int id = applicationContext.getResources()
        .getIdentifier(resourceName, RAW_TYPE, applicationContext.getPackageName());
    if (id == INVALID_ID) return EMPTY;

    String resource = ResourceUtils.extractStringFromRawResource(applicationContext, id);
    return resource;
  }
}
