package com.vpaliy.melophile.ui.base.bus.event;

public class OnClick<T> {

    public final T clicked;
    public final ExposeEvent exposeEvent;

    public OnClick(T clicked, ExposeEvent exposeEvent){
        this.clicked=clicked;
        this.exposeEvent=exposeEvent;
    }

    public static <T> OnClick<T> click(T object,ExposeEvent exposeEvent){
        return new OnClick<>(object,exposeEvent);
    }
}
