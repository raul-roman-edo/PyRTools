package com.apps.pyr.pyrtools.main.cards.image;

import com.apps.pyr.pyrtools.core.android.ui.cards.Card;

public class ImageCard extends Card {
  public static final int TYPE = 1;
  private static final String EMPTY = "";
  private String name = EMPTY;
  private String url = EMPTY;

  public String getName() {
    return name;
  }

  public String getUrl() {
    return url;
  }

  @Override public String obtainId() {
    return url;
  }

  @Override public int getType() {
    return TYPE;
  }
}
