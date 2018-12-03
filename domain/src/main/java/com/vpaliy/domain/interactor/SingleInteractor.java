package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;

public abstract class SingleInteractor<T, Params> {

  protected BaseSchedulerProvider schedulerProvider;
  protected CompositeDisposable disposables;

  public SingleInteractor(BaseSchedulerProvider schedulerProvider) {
    this.schedulerProvider = schedulerProvider;
    this.disposables = new CompositeDisposable();
  }

  public abstract Single<T> buildUseCase(Params params);

  public void execute(DisposableSingleObserver<T> singleObserver, Params params) {
    Single<T> single = buildUseCase(params)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui());
    disposables.add(single.subscribeWith(singleObserver));
  }

  public void execute(Consumer<? super T> onSuccess, Consumer<? super Throwable> onError, Params params) {
    Single<T> single = buildUseCase(params);
    if (single != null) {
      single = single.subscribeOn(schedulerProvider.io())
              .observeOn(schedulerProvider.ui());
      disposables.add(single.subscribe(onSuccess, onError));
    }
  }

  public void dispose() {
    if (!disposables.isDisposed()) {
      disposables.clear();
    }
  }
}
