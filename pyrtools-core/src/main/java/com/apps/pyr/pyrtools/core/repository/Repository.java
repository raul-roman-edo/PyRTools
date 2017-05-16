package com.apps.pyr.pyrtools.core.repository;

import com.apps.pyr.pyrtools.core.execution.Result;
import java.util.List;

public class Repository<Params, Data> {
  private final Cache<Params, Result<Data>> cache;
  private final List<Source<Params, Result<Data>>> sources;

  public Repository(Cache<Params, Result<Data>> cache, List<Source<Params, Result<Data>>> sources) {
    this.cache = cache;
    this.sources = sources;
  }

  public Result<Data> obtain() {
    return obtain(null);
  }

  public Result<Data> obtain(Params params) {
    if (sources == null || cache == null) return new Result<>();

    Result<Data> sourceResult = cache.request(params);
    if (!sourceResult.isValid()) {
      sourceResult = obtainFromSource(params);
      cache.update(sourceResult);
    }

    return sourceResult;
  }

  private Result<Data> obtainFromSource(Params params) {
    Result<Data> sourceResult = null;
    for (Source<Params, Result<Data>> source : sources) {
      sourceResult = source.request(params);
      if (sourceResult.isValid()) break;
    }
    return sourceResult;
  }
}
