package com.apps.pyr.pyrtools.core.broadcastevents;

public interface BroadcastEventSender<T> {
  void send(T extra);
}
