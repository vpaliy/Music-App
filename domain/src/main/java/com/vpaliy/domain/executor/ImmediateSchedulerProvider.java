package com.vpaliy.domain.executor;

import android.support.annotation.NonNull;
import rx.Scheduler;
import rx.schedulers.Schedulers;

public class ImmediateSchedulerProvider implements BaseSchedulerProvider {

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler multi() {
        return Schedulers.immediate();
    }
}