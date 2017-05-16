package com.apps.pyr.pyrtools.core.android.ui.cards;

public abstract class Card {
  public abstract String obtainId();

  public abstract int getType();

  public boolean isSwipeable() {
    return false;
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Card other = (Card) obj;
    return obtainId().equals(other.obtainId());
  }

  @Override public int hashCode() {
    return obtainId().hashCode();
  }
}
