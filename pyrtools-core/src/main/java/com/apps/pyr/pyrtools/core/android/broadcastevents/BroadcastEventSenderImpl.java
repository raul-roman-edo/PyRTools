package com.apps.pyr.pyrtools.core.android.broadcastevents;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.apps.pyr.pyrtools.core.broadcastevents.BroadcastEventSender;

public abstract class BroadcastEventSenderImpl<T> implements BroadcastEventSender<T> {
  private static final String TAG = BroadcastEventSenderImpl.class.getName();
  private static final String EXTRA_PARAM = TAG + ".extra_param";
  private final Context context;
  private final String action;

  public BroadcastEventSenderImpl(Context context, String action) {
    this.context = context;
    this.action = action;
  }

  @Override public void send(T extra) {
    Intent intent = new Intent(action);
    putExtras(intent, extra);
    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
  }

  protected abstract void putExtras(Intent intent, T extra);
}
