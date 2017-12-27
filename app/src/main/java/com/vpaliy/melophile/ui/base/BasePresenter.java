package com.vpaliy.melophile.ui.base;

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);
}