package com.apps.pyr.pyrtools.core.android.storage.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.apps.pyr.pyrtools.core.storage.base.StorageSystem;

public class PreferencesStorageSystem implements StorageSystem {
  private final SharedPreferences prefs;

  public PreferencesStorageSystem(Context applicationContext) {
    this.prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
  }

  @Override public String load(String key, String defaultObject) {
    return prefs.getString(key, defaultObject);
  }

  @Override public void save(String key, String object) {
    prefs.edit().putString(key, object).commit();
  }
}
