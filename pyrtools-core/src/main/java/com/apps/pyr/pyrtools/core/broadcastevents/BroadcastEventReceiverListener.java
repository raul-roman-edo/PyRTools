package com.apps.pyr.pyrtools.core.broadcastevents;

public interface BroadcastEventReceiverListener<T> {
  void onReceived(T extra);
}
