package com.apps.pyr.pyrtools.core.android.broadcastevents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.apps.pyr.pyrtools.core.broadcastevents.BroadcastEventReceiver;
import com.apps.pyr.pyrtools.core.broadcastevents.BroadcastEventReceiverListener;

public abstract class BroadcastEventReceiverImpl<T> extends BroadcastReceiver
    implements BroadcastEventReceiver<T> {
  private final Context context;
  private final IntentFilter intentFilter;

  private BroadcastEventReceiverListener<T> listener;

  protected BroadcastEventReceiverImpl(Context context, String action) {
    this.context = context;
    intentFilter = new IntentFilter(action);
  }

  @Override public void register(BroadcastEventReceiverListener<T> listener) {
    this.listener = listener;
    LocalBroadcastManager.getInstance(context).registerReceiver(this, intentFilter);
  }

  @Override public void unregister() {
    this.listener = null;
    LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
  }

  protected void notifyListener(T extra) {
    if (listener == null) return;

    listener.onReceived(extra);
  }
}
