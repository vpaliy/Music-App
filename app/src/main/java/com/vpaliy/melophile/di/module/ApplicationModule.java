package com.vpaliy.melophile.di.module;

import android.content.Context;

import com.vpaliy.melophile.ui.base.bus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Context context;

    public ApplicationModule(Context context){
        this.context=context;
    }

    @Singleton @Provides Context context(){
        return context;
    }

    @Singleton @Provides
    RxBus rxBus(){
        return new RxBus();
    }

}
