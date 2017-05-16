package com.apps.pyr.pyrtools.main;

import com.apps.pyr.pyrtools.core.android.ui.cards.Card;
import com.apps.pyr.pyrtools.core.execution.Dispatcher;
import com.apps.pyr.pyrtools.core.execution.Result;
import com.apps.pyr.pyrtools.main.cards.image.ImageCard;
import com.apps.pyr.pyrtools.main.usecase.LoadContentUseCase;
import java.util.ArrayList;
import java.util.List;

public class MainPresenter {
  private final MainView view;
  private final LoadContentUseCase loader;

  public MainPresenter(MainView view, LoadContentUseCase loader) {
    this.view = view;
    this.loader = loader;
  }

  public void start() {
    view.showProgress();
    Dispatcher<Result<List<ImageCard>>> dispatcher = createImagesLoadDispatcher();
    loader.execute(dispatcher);
  }

  private Dispatcher<Result<List<ImageCard>>> createImagesLoadDispatcher() {
    return result -> {
      view.hideProgress();
      if (result.isValid()) {
        List<Card> cards = new ArrayList<>();
        cards.addAll(result.getPayload());
        view.update(cards);
      }
    };
  }
}
