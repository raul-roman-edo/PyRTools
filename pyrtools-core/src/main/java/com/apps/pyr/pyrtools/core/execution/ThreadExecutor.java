package com.apps.pyr.pyrtools.core.execution;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadExecutor {
  private static final int MAX_SECONDS = 10;
  private static final int CAPACITY = 5;
  public static ThreadExecutor singleton = null;
  private ThreadPoolExecutor threadPool = null;

  private ThreadExecutor() {
    int poolSize = Runtime.getRuntime().availableProcessors();
    int maxPoolSize = Runtime.getRuntime().availableProcessors();
    long keepAliveTime = MAX_SECONDS;
    ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(CAPACITY);
    threadPool =
        new ThreadPoolExecutor(poolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, queue);
  }

  public static ThreadExecutor getInstance() {
    if (singleton == null) {
      singleton = new ThreadExecutor();
    }

    return singleton;
  }

  public void runTask(Runnable task) {
    threadPool.execute(task);
  }

  public void shutDown() {
    threadPool.shutdown();
    singleton = null;
  }
}