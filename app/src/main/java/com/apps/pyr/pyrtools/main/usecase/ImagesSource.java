package com.apps.pyr.pyrtools.main.usecase;

import com.apps.pyr.pyrtools.core.android.RawResourceObtainerCommand;
import com.apps.pyr.pyrtools.core.execution.Result;
import com.apps.pyr.pyrtools.core.repository.Source;
import com.apps.pyr.pyrtools.main.cards.image.ImageCard;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class ImagesSource implements Source<Void, Result<List<ImageCard>>> {
  private static final String SOURCE_NAME = "images";
  private final RawResourceObtainerCommand obtainer;

  public ImagesSource(RawResourceObtainerCommand obtainer) {
    this.obtainer = obtainer;
  }

  @Override public Result<List<ImageCard>> request(Void aVoid) {
    String raw = obtainer.obtainByName(SOURCE_NAME);
    if (raw.isEmpty()) {
      return new Result<>();
    }
    Result<List<ImageCard>> result = createResult(raw);
    return result;
  }

  private Result<List<ImageCard>> createResult(String raw) {
    Gson gson = new Gson();
    Type type = new TypeToken<List<ImageCard>>() {
    }.getType();
    List<ImageCard> cards = gson.fromJson(raw, type);
    Result<List<ImageCard>> result = new Result<>();
    result.setPayload(cards);
    result.setValid(true);
    return result;
  }
}
