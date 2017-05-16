package com.apps.pyr.pyrtools.main.usecase;

import com.apps.pyr.pyrtools.core.execution.AsyncAction;
import com.apps.pyr.pyrtools.core.execution.ContextSwitcher;
import com.apps.pyr.pyrtools.core.execution.Result;
import com.apps.pyr.pyrtools.core.repository.Repository;
import com.apps.pyr.pyrtools.main.cards.image.ImageCard;
import java.util.List;

public class LoadContentUseCase extends AsyncAction<Void, Result<List<ImageCard>>> {
  private final Repository<Void, List<ImageCard>> repository;

  public LoadContentUseCase(ContextSwitcher<Result<List<ImageCard>>> switcher,
      Repository<Void, List<ImageCard>> repository) {
    super(switcher);
    this.repository = repository;
  }

  @Override protected Result<List<ImageCard>> executeInParallel(Void aVoid) {
    return repository.obtain();
  }
}
