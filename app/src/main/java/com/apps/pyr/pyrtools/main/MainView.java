package com.apps.pyr.pyrtools.main;

import com.apps.pyr.pyrtools.core.android.ui.cards.Card;
import java.util.List;

public interface MainView {
  void update(List<Card> cards);

  void showProgress();

  void hideProgress();
}
