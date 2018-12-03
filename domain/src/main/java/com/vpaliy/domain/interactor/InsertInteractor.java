package com.vpaliy.domain.interactor;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public interface InsertInteractor<Params> {
  void add(Params params, Action onComplete, Consumer<? super Throwable> onError);
}
