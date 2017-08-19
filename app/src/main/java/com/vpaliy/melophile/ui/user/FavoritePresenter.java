package com.vpaliy.melophile.ui.user;

import static com.vpaliy.melophile.ui.user.FavoriteContract.View;
import static dagger.internal.Preconditions.checkNotNull;
import com.vpaliy.domain.interactor.GetUserFavorites;
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

    }

    @Override
    public void stop() {
        favoritesUseCase.dispose();
    }
}
