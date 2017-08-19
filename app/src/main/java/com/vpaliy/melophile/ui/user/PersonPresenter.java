package com.vpaliy.melophile.ui.user;

import com.vpaliy.domain.interactor.GetUserDetails;
import com.vpaliy.domain.interactor.GetUserFollowers;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.model.UserDetails;
import java.util.List;
import io.reactivex.observers.DisposableSingleObserver;
import static com.vpaliy.melophile.ui.user.PersonContract.View;
import static dagger.internal.Preconditions.checkNotNull;
import com.vpaliy.melophile.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;


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
        this.view=checkNotNull(view);
    }

    @Override
    public void start(String id) {
        view.showLoading();
        userDetailsUseCase.execute(this::catchData,this::catchError,id);
    }

    private void catchData(UserDetails details){
        view.hideLoading();
        if(details!=null){
            boolean isEmpty=isEmpty(details.getTracks());
            if(!isEmpty) {
                view.showTracks(details.getTracks());
            }
            //check the playlists
            if(!isEmpty(details.getPlaylists())) {
                view.showPlaylists(details.getPlaylists());
            }else if(isEmpty){
                view.showEmptyMediaMessage();
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
        view.hideLoading();
        ex.printStackTrace();
        view.showErrorMessage();

    }

    @Override
    public void stop() {
        userDetailsUseCase.dispose();
    }
}
