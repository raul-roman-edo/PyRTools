package com.apps.pyr.pyrtools.core.android.ui.cards;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public abstract class CardViewHolder<T extends Card> extends RecyclerView.ViewHolder {
  public CardViewHolder(ViewGroup parent, int layoutId) {
    super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
  }

  public abstract void fillWith(T card);

  public void onAttached() {

  }

  public void onDetached() {

  }
}
