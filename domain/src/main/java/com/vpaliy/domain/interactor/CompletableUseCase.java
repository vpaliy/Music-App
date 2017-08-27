package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import io.reactivex.Completable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import android.support.annotation.NonNull;

public abstract class CompletableUseCase<Params> {

    private BaseSchedulerProvider schedulerProvider;

    public CompletableUseCase(@NonNull BaseSchedulerProvider schedulerProvider){
        this.schedulerProvider=schedulerProvider;
    }

    public void execute(@NonNull Action onComplete,
                        @NonNull Consumer<? super Throwable> onError,
                        Params params){
        buildCompletable(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(onComplete,onError);
    }

    public void execute2(@NonNull Action onComplete,
                         @NonNull Consumer<? super Throwable> onError,
                         Params params){
        buildCompletable(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(onComplete,onError);
    }

    public abstract Completable buildCompletable(Params params);
    public abstract Completable buildCompletable2(Params params);
}
