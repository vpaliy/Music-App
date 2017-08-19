package com.vpaliy.melophile.ui.user.favorite;

import static com.vpaliy.melophile.ui.user.favorite.FavoriteContract.View;
import static dagger.internal.Preconditions.checkNotNull;
import com.vpaliy.domain.interactor.GetUserFavorites;
import com.vpaliy.domain.model.Track;
import java.util.List;
import com.vpaliy.melophile.di.scope.ViewScope;
import android.support.annotation.NonNull;
import javax.inject.Inject;

@ViewScope
public class FavoritePresenter implements FavoriteContract.Presenter {

    private GetUserFavorites favoritesUseCase;
    private View view;

    @Inject
    public FavoritePresenter(GetUserFavorites favoritesUseCase){
        this.favoritesUseCase=favoritesUseCase;
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=checkNotNull(view);
    }

    @Override
    public void start(String id) {
        favoritesUseCase.execute(this::catchData,this::catchError,id);
    }

    private void catchData(List<Track> tracks){
        if(tracks==null||tracks.isEmpty()){
            view.showEmpty();
        }else{
            view.showTracks(tracks);
        }
    }

    private void catchError(Throwable throwable){
        throwable.printStackTrace();
        view.showError();
    }

    @Override
    public void stop() {
        favoritesUseCase.dispose();
    }
}
