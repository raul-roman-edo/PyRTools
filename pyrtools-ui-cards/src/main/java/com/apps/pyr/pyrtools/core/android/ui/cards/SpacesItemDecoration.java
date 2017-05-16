package com.apps.pyr.pyrtools.core.android.ui.cards;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
  private final boolean applyToSides;
  private final boolean applyToFirstAndLast;
  private final int space;

  public SpacesItemDecoration(int space) {
    this(space, true);
  }

  public SpacesItemDecoration(int space, boolean applyToSides) {
    this(space, applyToSides, true);
  }

  public SpacesItemDecoration(int space, boolean applyToSides, boolean applyToFirstAndLast) {
    this.space = space;
    this.applyToSides = applyToSides;
    this.applyToFirstAndLast = applyToFirstAndLast;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    int pos = parent.getChildLayoutPosition(view);
    boolean first = pos == 0;
    boolean last = pos == parent.getAdapter().getItemCount() - 1;

    if (first) {
      if (applyToFirstAndLast) {
        outRect.top = space;
      }
    } else {
      outRect.top = space / 2;
    }

    if (last) {
      if (applyToFirstAndLast) {
        outRect.bottom = space;
      }
    } else {
      outRect.bottom = space / 2;
    }

    if (applyToSides) {
      outRect.left = space;
      outRect.right = space;
    }
  }
}