package com.vpaliy.melophile.ui.base;

import android.content.Context;

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);
}