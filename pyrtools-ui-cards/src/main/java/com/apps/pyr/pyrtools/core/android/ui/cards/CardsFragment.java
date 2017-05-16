package com.apps.pyr.pyrtools.core.android.ui.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class CardsFragment extends Fragment {
  protected RecyclerView recyclerView;
  private View mainView = null;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (mainView == null) {
      mainView = inflater.inflate(getLayout(), container, false);
    }
    if (recyclerView == null) {
      recyclerView = (RecyclerView) mainView.findViewById(obtainListId());
    }

    return mainView;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initPresenter();
    boolean isNewInstance = recyclerView.getAdapter() == null;
    if (isNewInstance) {
      initRecyclerView();
    }
  }

  @Override public void onResume() {
    super.onResume();
    launchEntriesLoading();
  }

  public void updateEntries(final List<Card> newCards) {
    recyclerView.post(new Runnable() {
      @Override public void run() {
        CardsAdapter adapter = (CardsAdapter) recyclerView.getAdapter();
        boolean isNotEmpty = adapter.getItemCount() > 0;
        boolean haveSameElements = adapter.getItemCount() == newCards.size();
        adapter.update(newCards);
        if (isNotEmpty && haveSameElements) {
          adapter.notifyDataSetChanged();
        }
      }
    });
  }

  public void refresh(List<Card> newCards) {
    CardsAdapter adapter = (CardsAdapter) recyclerView.getAdapter();
    adapter.refresh(newCards);
  }

  protected abstract int getLayout();

  protected abstract int obtainListId();

  protected abstract void initPresenter();

  protected abstract RecyclerView.LayoutManager obtainLayoutManager();

  protected abstract RecyclerView.ItemDecoration obtainItemDecoration();

  protected abstract CardsAdapter createCardsAdapter(RecyclerView recyclerView);

  protected abstract void launchEntriesLoading();

  private void initRecyclerView() {
    recyclerView.setLayoutManager(obtainLayoutManager());
    RecyclerView.ItemDecoration decoration = obtainItemDecoration();
    if (decoration != null) {
      recyclerView.addItemDecoration(decoration);
    }
    assignNewAdapter();
  }

  private void assignNewAdapter() {
    recyclerView.setAdapter(createCardsAdapter(recyclerView));
  }

  private void refreshAdapter() {
    recyclerView.getAdapter().notifyDataSetChanged();
  }
}
