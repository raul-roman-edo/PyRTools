package com.apps.pyr.pyrtools.core.android.ui.cards;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewEndProcessor extends RecyclerView.OnScrollListener {
  private final int preloadThreshold;
  private boolean processingBottom = false;
  private OnNearBottomEventListener onNearBottomEventListener = null;

  public RecyclerViewEndProcessor(int preloadThreshold) {
    this.preloadThreshold = preloadThreshold;
  }

  public void setOnNearBottomEventListener(OnNearBottomEventListener onNearBottomEventListener) {
    this.onNearBottomEventListener = onNearBottomEventListener;
  }

  public void setBottomProcessed() {
    processingBottom = false;
  }

  @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    if (processingBottom || onNearBottomEventListener == null) {
      return;
    }

    boolean isList = recyclerView.getLayoutManager() instanceof LinearLayoutManager;
    boolean isGrid = recyclerView.getLayoutManager() instanceof GridLayoutManager;
    if (isList) {
      checkListBottom(recyclerView);
    } else if (isGrid) {
      checkGridBottom(recyclerView);
    }
  }

  private void checkListBottom(RecyclerView recyclerView) {
    int last = recyclerView.getAdapter().getItemCount() - 1;
    int lastVisible =
        ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
    checkBottom(last, lastVisible, preloadThreshold);
  }

  private void checkGridBottom(RecyclerView recyclerView) {
    int last = recyclerView.getAdapter().getItemCount() - 1;
    int lastVisible =
        ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
    int numColumns = ((GridLayoutManager) recyclerView.getLayoutManager()).getSpanCount();
    checkBottom(last, lastVisible, preloadThreshold * numColumns);
  }

  private void checkBottom(int lastElement, int lastElementVisible, int elementsThreshold) {
    if (lastElement < 0) {
      return;
    }
    if (lastElement - lastElementVisible < elementsThreshold) {
      processingBottom = true;
      onNearBottomEventListener.onNearBottom();
    }
  }

  public interface OnNearBottomEventListener {
    void onNearBottom();
  }
}
