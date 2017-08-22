package com.vpaliy.data.source.remote;
import com.vpaliy.data.source.SearchSource;
import com.vpaliy.soundcloud.SoundCloudService;
import com.vpaliy.soundcloud.model.PlaylistEntity;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;
import java.util.List;
import io.reactivex.Single;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class RemoteSearchSource implements SearchSource {

    private SoundCloudService service;
    private Filter filter;

    @Inject
    public RemoteSearchSource(@NonNull SoundCloudService soundCloudService,
                              @NonNull Filter filter){
        this.service=soundCloudService;
        this.filter=filter;
    }

    @Override
    public Single<List<PlaylistEntity>> searchPlaylists(@NonNull String query) {
        return service.searchPlaylists(PlaylistEntity.Filter.start()
                                        .byName(query).createOptions())
                                    .map(filter::filterPlaylists);
    }

    @Override
    public Single<List<TrackEntity>> searchTracks(@NonNull String query) {
        return service.searchTracks(TrackEntity.Filter.start()
                                    .byName(query).createOptions())
                                .map(filter::filterTracks);
    }

    @Override
    public Single<List<UserEntity>> searchUsers(@NonNull String query) {
        return service.searchUsers(UserEntity.Filter.start()
                            .byName(query).createOptions());
    }
}
