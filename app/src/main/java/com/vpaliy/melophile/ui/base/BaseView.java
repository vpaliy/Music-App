package com.vpaliy.melophile.ui.base;

public interface BaseView<P extends BasePresenter<? extends BaseView>> {
    void attachPresenter(P presenter);
}