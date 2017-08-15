package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public abstract class SingleUseCase<T,Params>{

    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable disposables;

    public SingleUseCase(BaseSchedulerProvider schedulerProvider){
        this.schedulerProvider=schedulerProvider;
        this.disposables=new CompositeDisposable();
    }

    public abstract Single<T> buildUseCase(Params params);

    public void execute(DisposableSingleObserver<T> singleObserver, Params params){
        Single<T> single= buildUseCase(params)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui());
        disposables.add(single.subscribeWith(singleObserver));
    }

    public void dispose(){
        if(!disposables.isDisposed()){
            disposables.dispose();
        }
    }
}
