package com.vpaliy.melophile.di.component;

import android.content.Context;
import com.vpaliy.data.source.Source;
import com.vpaliy.domain.repository.Repository;
import com.vpaliy.melophile.di.module.ApplicationModule;
import com.vpaliy.melophile.di.module.DataModule;
import com.vpaliy.melophile.di.module.MapperModule;
import com.vpaliy.melophile.di.module.NetworkModule;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import com.vpaliy.soundcloud.SoundCloudService;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {
        DataModule.class,
        MapperModule.class,
        NetworkModule.class,
        ApplicationModule.class})
public interface ApplicationComponent {
    Context context();
    Source remote();
    Repository repository();
    SoundCloudService soundCloud();
    RxBus rxBus();
}
