package com.apps.pyr.pyrtools.main;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.apps.pyr.pyrtools.R;
import com.apps.pyr.pyrtools.core.android.RawResourceObtainerCommand;
import com.apps.pyr.pyrtools.core.android.execution.ContextSwitcherImpl;
import com.apps.pyr.pyrtools.core.android.storage.base.PreferencesStorageSystem;
import com.apps.pyr.pyrtools.core.android.ui.cards.CardViewHolderCreator;
import com.apps.pyr.pyrtools.core.android.ui.cards.CardsActivity;
import com.apps.pyr.pyrtools.core.android.ui.cards.CardsAdapter;
import com.apps.pyr.pyrtools.core.execution.ContextSwitcher;
import com.apps.pyr.pyrtools.core.execution.Result;
import com.apps.pyr.pyrtools.core.execution.ThreadExecutor;
import com.apps.pyr.pyrtools.core.repository.Cache;
import com.apps.pyr.pyrtools.core.repository.Repository;
import com.apps.pyr.pyrtools.core.repository.Source;
import com.apps.pyr.pyrtools.core.storage.Store;
import com.apps.pyr.pyrtools.main.cards.image.ImageCard;
import com.apps.pyr.pyrtools.main.cards.image.ImageHolder;
import com.apps.pyr.pyrtools.main.usecase.ImagesCache;
import com.apps.pyr.pyrtools.main.usecase.ImagesCacheEntity;
import com.apps.pyr.pyrtools.main.usecase.ImagesCacheStore;
import com.apps.pyr.pyrtools.main.usecase.ImagesSource;
import com.apps.pyr.pyrtools.main.usecase.LoadContentUseCase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends CardsActivity implements MainView {
  private static final int GRID_COLS = 2;
  private View progress;
  private MainPresenter presenter;

  @Override public void showProgress() {
    progress.setVisibility(View.VISIBLE);
  }

  @Override public void hideProgress() {
    progress.setVisibility(View.GONE);
  }

  @Override protected void loadViews() {
    super.loadViews();
    progress = findViewById(R.id.progress);
    configureToolbar();
  }

  @Override protected int obtainLayoutResource() {
    return R.layout.activity_main;
  }

  @Override protected int obtainListId() {
    return R.id.recyclerView;
  }

  @Override protected void initPresenter() {
    ContextSwitcher<Result<List<ImageCard>>> switcher =
        new ContextSwitcherImpl<>(ThreadExecutor.getInstance());
    Repository<Void, List<ImageCard>> repository = createRepository();
    LoadContentUseCase loader = new LoadContentUseCase(switcher, repository);
    presenter = new MainPresenter(this, loader);
  }

  @Override protected RecyclerView.LayoutManager obtainLayoutManager() {
    return new GridLayoutManager(this, GRID_COLS);
  }

  @Override protected RecyclerView.ItemDecoration obtainItemDecoration() {
    return null;
  }

  @Override protected CardsAdapter createCardsAdapter(RecyclerView recyclerView) {
    CardsAdapter adapter = new CardsAdapter(recyclerView);
    Map<Integer, CardViewHolderCreator> holdersMap = new HashMap<>();
    holdersMap.put(ImageCard.TYPE, ImageHolder::new);
    adapter.setHolderCreatorsMap(holdersMap);
    return adapter;
  }

  @Override protected void launchEntriesLoading() {
    presenter.start();
  }

  private void configureToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
    toolbar.setTitle(R.string.app_name);
    setSupportActionBar(toolbar);
  }

  private Repository<Void, List<ImageCard>> createRepository() {
    Store<Void, ImagesCacheEntity> imagesStore =
        new ImagesCacheStore(new PreferencesStorageSystem(getApplicationContext()));
    Cache<Void, Result<List<ImageCard>>> imagesCache = new ImagesCache(imagesStore);
    RawResourceObtainerCommand obtainer = new RawResourceObtainerCommand(getApplicationContext());
    List<Source<Void, Result<List<ImageCard>>>> imagesSources = new ArrayList<>();
    imagesSources.add(new ImagesSource(obtainer));
    return new Repository<>(imagesCache, imagesSources);
  }
}
