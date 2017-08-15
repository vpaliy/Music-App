package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public abstract class ObservableUseCase<T,Params>{

    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable disposables;

    public ObservableUseCase(BaseSchedulerProvider schedulerProvider){
        this.schedulerProvider=schedulerProvider;
        this.disposables=new CompositeDisposable();
    }

    public abstract Observable<T> buildUseCase(Params params);

    public Observable<T> execute(Params params){
        return buildUseCase(params)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui());
    }

    public void dispose(){
        if(!disposables.isDisposed()){
            disposables.dispose();
        }
    }
}
