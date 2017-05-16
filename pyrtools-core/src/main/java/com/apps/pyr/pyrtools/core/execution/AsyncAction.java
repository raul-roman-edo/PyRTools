package com.apps.pyr.pyrtools.core.execution;

public abstract class AsyncAction<Param, Result> {
  private final ContextSwitcher<Result> switcher;

  protected AsyncAction(ContextSwitcher<Result> switcher) {
    this.switcher = switcher;
  }

  public void execute(final Dispatcher<Result> dispatcher) {
    execute(null, dispatcher);
  }

  public void execute(final Param param, final Dispatcher<Result> dispatcher) {
    BackgroundAction action = createAction(param, dispatcher);
    switcher.startInBackground(action);
  }

  protected abstract Result executeInParallel(Param param);

  private BackgroundAction createAction(final Param param, final Dispatcher<Result> dispatcher) {
    return new BackgroundAction<Result>() {

      @Override public Result onBackground() {
        return executeInParallel(param);
      }

      @Override public void backOnMainThread(Result result) {
        dispatcher.dispatch(result);
      }
    };
  }
}
