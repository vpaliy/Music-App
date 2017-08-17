package com.vpaliy.melophile.ui.user;

import com.vpaliy.domain.interactor.GetUserDetails;
import com.vpaliy.domain.model.UserDetails;
import com.vpaliy.melophile.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;

import static com.vpaliy.melophile.ui.user.PersonContract.View;

@ViewScope
public class PersonPresenter implements PersonContract.Presenter{

    private View view;
    private GetUserDetails userDetailsUseCase;

    @Inject
    public PersonPresenter(GetUserDetails userDetailsUseCase){
        this.userDetailsUseCase=userDetailsUseCase;
    }

    @Override
    public void attachView(@NonNull View view) {
        this.view=view;
    }

    @Override
    public void start(String id) {
        userDetailsUseCase.execute(this::catchData,this::catchError,id);
    }

    private void catchData(UserDetails details){
        if(details!=null){
            view.showPlaylists(details.getPlaylists());
            view.showTracks(details.getTracks());
        }else{
            view.showEmptyMessage();
        }
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
