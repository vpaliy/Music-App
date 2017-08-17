package com.vpaliy.melophile.di.component;

import android.content.Context;
import com.vpaliy.data.source.Source;
import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.interactor.GetPlaylist;
import com.vpaliy.domain.interactor.GetPlaylists;
import com.vpaliy.domain.interactor.GetTrack;
import com.vpaliy.domain.interactor.GetTracks;
import com.vpaliy.domain.interactor.GetUserDetails;
import com.vpaliy.domain.playback.Playback;
import com.vpaliy.domain.repository.Repository;
import com.vpaliy.melophile.di.module.ApplicationModule;
import com.vpaliy.melophile.di.module.DataModule;
import com.vpaliy.melophile.di.module.InteractorModule;
import com.vpaliy.melophile.di.module.MapperModule;
import com.vpaliy.melophile.di.module.NetworkModule;
import com.vpaliy.melophile.di.module.PlaybackModule;
import com.vpaliy.melophile.playback.PlaybackManager;
import com.vpaliy.melophile.ui.base.BaseActivity;
import com.vpaliy.melophile.ui.base.Navigator;
import com.vpaliy.melophile.ui.base.bus.RxBus;
import com.vpaliy.soundcloud.SoundCloudService;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {
        DataModule.class,
        MapperModule.class,
        NetworkModule.class,
        PlaybackModule.class,
        InteractorModule.class,
        ApplicationModule.class})
public interface ApplicationComponent {
    void inject(BaseActivity activity);
    Context context();
    Source remote();
    Repository repository();
    BaseSchedulerProvider scheduler();
    SoundCloudService soundCloud();
    Navigator navigator();
    RxBus rxBus();
    //use cases
    GetPlaylists playlistsInteractor();
    GetTracks tracksInteractor();
    GetPlaylist playlistInteractor();
    GetTrack trackInteractor();
    GetUserDetails userDetailsInteractor();

    //playback
    PlaybackManager playbackManager();
}
