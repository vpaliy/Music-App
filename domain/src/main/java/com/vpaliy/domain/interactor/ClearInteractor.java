package com.vpaliy.domain.interactor;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public interface ClearInteractor<Params> {
  void clear(Action onSuccess, Consumer<? super Throwable> onError);

  void remove(Params params, Action onSuccess, Consumer<? super Throwable> onError);
}
