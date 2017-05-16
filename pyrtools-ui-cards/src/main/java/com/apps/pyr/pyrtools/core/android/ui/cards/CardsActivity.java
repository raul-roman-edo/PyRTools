package com.apps.pyr.pyrtools.core.android.ui.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import java.util.List;

public abstract class CardsActivity extends AppCompatActivity {
  protected RecyclerView recyclerView;

  public void update(final List<Card> newCards) {
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

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(obtainLayoutResource());
    loadViews();
    initPresenter();
    initRecyclerView();
  }

  @Override protected void onResume() {
    super.onResume();
    launchEntriesLoading();
  }

  protected void loadViews() {
    recyclerView = (RecyclerView) findViewById(obtainListId());
  }

  protected abstract int obtainLayoutResource();

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
}
