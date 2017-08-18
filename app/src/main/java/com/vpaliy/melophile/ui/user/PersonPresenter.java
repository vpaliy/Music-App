package com.vpaliy.melophile.ui.user;

import com.vpaliy.domain.interactor.GetUserDetails;
import com.vpaliy.domain.interactor.GetUserFollowers;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.model.UserDetails;
import com.vpaliy.melophile.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;

import static com.vpaliy.melophile.ui.user.PersonContract.View;

@ViewScope
public class PersonPresenter implements PersonContract.Presenter{

    private View view;
    private GetUserDetails userDetailsUseCase;
    private GetUserFollowers userFollowersUseCase;

    @Inject
    public PersonPresenter(GetUserDetails userDetailsUseCase,
                           GetUserFollowers userFollowersUseCase){
        this.userDetailsUseCase=userDetailsUseCase;
        this.userFollowersUseCase=userFollowersUseCase;
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void start(String id) {
        userDetailsUseCase.execute(this::catchData,this::catchError,id);
    }

    @Override
    public void requestFollowers(String id) {
        userFollowersUseCase.execute(new DisposableSingleObserver<List<User>>() {
            @Override
            public void onSuccess(List<User> value) {
                if(value!=null){
                    view.showFollowers(value);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.showErrorMessage();
            }
        },id);
    }

    private void catchData(UserDetails details){
        if(details!=null){
            if(!isEmpty(details.getTracks())) {
                view.showTracks(details.getTracks());
            }
            if(!isEmpty(details.getPlaylists())) {
                view.showPlaylists(details.getPlaylists());
            }
            User user=details.getUser();
            if(user!=null){
                view.showFollowersCount(user.getFollowersCount());
                view.showTitle(user.getNickName());
                view.showDescription(user.getDescription());
                view.showLikedCount(user.getLikedTracksCount());
            }
        }else{
            view.showEmptyMessage();
        }
    }

    private <T> boolean isEmpty(List<T> items){
        return items==null||items.isEmpty();
    }

    private void catchError(Throwable ex){
        ex.printStackTrace();
        view.showErrorMessage();

    }

    @Override
    public void stop() {
        userDetailsUseCase.dispose();
    }
}
