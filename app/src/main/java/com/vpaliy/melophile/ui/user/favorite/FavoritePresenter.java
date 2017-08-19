package com.vpaliy.melophile.ui.user.favorite;

import com.vpaliy.domain.interactor.GetUserFavorites;
import com.vpaliy.domain.model.Track;
import com.vpaliy.melophile.di.scope.ViewScope;
import javax.inject.Inject;

@ViewScope
public class FavoritePresenter extends UserInfoPresenter<Track> {

    private GetUserFavorites favoritesUseCase;

    @Inject
    public FavoritePresenter(GetUserFavorites favoritesUseCase){
        this.favoritesUseCase=favoritesUseCase;
    }

    @Override
    public void start(String id) {
        favoritesUseCase.execute(this::catchData,this::catchError,id);
    }

    @Override
    public void stop() {
        favoritesUseCase.dispose();
    }
}
