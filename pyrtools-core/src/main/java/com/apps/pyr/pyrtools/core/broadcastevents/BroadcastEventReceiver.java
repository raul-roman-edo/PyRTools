package com.apps.pyr.pyrtools.core.broadcastevents;

public interface BroadcastEventReceiver<T> {
  void register(BroadcastEventReceiverListener<T> listener);

  void unregister();
}
