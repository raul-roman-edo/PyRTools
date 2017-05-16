package com.apps.pyr.pyrtools.core.android.execution;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.apps.pyr.pyrtools.core.execution.BackgroundAction;
import com.apps.pyr.pyrtools.core.execution.ContextSwitcher;
import com.apps.pyr.pyrtools.core.execution.ThreadExecutor;

public class ContextSwitcherImpl<Result> implements ContextSwitcher<Result> {
  private final ThreadExecutor executor;

  public ContextSwitcherImpl(ThreadExecutor executor) {
    this.executor = executor;
  }

  @Override public void startInBackground(BackgroundAction<Result> action) {
    Runnable runnable = createRunnableForAction(action);
    executor.runTask(runnable);
  }

  private Runnable createRunnableForAction(final BackgroundAction<Result> action) {
    return new Runnable() {
      private Result result;

      @Override public void run() {
        result = action.onBackground();

        Handler mHandler = new Handler(Looper.getMainLooper()) {
          @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            action.backOnMainThread(result);
          }
        };
        mHandler.sendEmptyMessage(0);
      }
    };
  }
}
