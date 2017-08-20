package com.vpaliy.melophile.ui.base.bus;

import android.os.Handler;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class RxBus {

    private final Relay<Object> bus = PublishRelay.create().toSerialized();
    private volatile boolean isLocked;
    private Handler handler=new Handler();

    public void send(Object o) {
        bus.accept(o);
    }

    public void sendWithLock(Object event, long lockOutTime){
        if(!isLocked){
            isLocked=true;
            send(event);
            handler.postDelayed(()->isLocked=false,lockOutTime);
        }
    }

    public void sendWithLock(Object event){
        sendWithLock(event,500);
    }

    public Flowable<Object> asFlowable() {
        return bus.toFlowable(BackpressureStrategy.LATEST);
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}