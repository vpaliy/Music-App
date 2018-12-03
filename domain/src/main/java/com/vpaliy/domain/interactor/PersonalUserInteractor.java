package com.vpaliy.domain.interactor;

import com.vpaliy.domain.executor.BaseSchedulerProvider;
import com.vpaliy.domain.model.User;
import com.vpaliy.domain.repository.PersonalRepository;

import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class PersonalUserInteractor extends SingleInteractor<User, Void>
        implements ModifyInteractor<User> {

  private PersonalRepository personalRepository;

  public PersonalUserInteractor(PersonalRepository personalRepository,
                                BaseSchedulerProvider schedulerProvider) {
    super(schedulerProvider);
    this.personalRepository = personalRepository;
  }

  @Override
  public Single<User> buildUseCase(Void aVoid) {
    return personalRepository.fetchMyself();
  }

  @Override
  public void add(User user, Action onComplete, Consumer<? super Throwable> onError) {
    personalRepository.follow(user)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onComplete, onError);
  }

  @Override
  public void remove(User user, Action onSuccess, Consumer<? super Throwable> onError) {
    personalRepository.unfollow(user)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onSuccess, onError);
  }

  @Override
  public void clear(Action onSuccess, Consumer<? super Throwable> onError) {
    personalRepository.unfollowAll()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(onSuccess, onError);
  }
}
