package com.vpaliy.domain.executor;

import android.support.annotation.NonNull;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SchedulerProvider implements BaseSchedulerProvider{

    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    public Scheduler multi(){
        return Schedulers.newThread();
    }

    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}