package com.apps.pyr.pyrtools.core.android.ui.cards;

import android.view.ViewGroup;

public interface CardViewHolderCreator<T extends CardViewHolder> {
  T create(ViewGroup parent);
}
