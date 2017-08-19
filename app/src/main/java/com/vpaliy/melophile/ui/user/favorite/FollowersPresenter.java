package com.vpaliy.melophile.ui.user.favorite;

import com.vpaliy.domain.interactor.GetUserFollowers;
import com.vpaliy.domain.model.User;
import com.vpaliy.melophile.di.scope.ViewScope;

import javax.inject.Inject;

@ViewScope
public class FollowersPresenter extends UserInfoPresenter<User> {

    private GetUserFollowers userFollowersUseCase;

    @Inject
    public FollowersPresenter(GetUserFollowers userFollowersUseCase){
        this.userFollowersUseCase=userFollowersUseCase;
    }

    @Override
    public void start(String id) {
        userFollowersUseCase.execute(this::catchData,this::catchError,id);
    }

    @Override
    public void stop() {
        userFollowersUseCase.dispose();
    }
}
