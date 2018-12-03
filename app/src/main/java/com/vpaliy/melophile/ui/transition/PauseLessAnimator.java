package com.vpaliy.melophile.ui.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.util.ArrayMap;

import java.util.ArrayList;

public class PauseLessAnimator extends Animator {

  private final Animator mAnimator;
  private final ArrayMap<AnimatorListener, AnimatorListener> mListeners = new ArrayMap<>();

  public PauseLessAnimator(Animator animator) {
    mAnimator = animator;
  }

  @Override
  public void addListener(AnimatorListener listener) {
    AnimatorListener wrapper = new AnimatorListenerWrapper(this,
            listener);
    if (!mListeners.containsKey(listener)) {
      mListeners.put(listener, wrapper);
      mAnimator.addListener(wrapper);
    }
  }

  @Override
  public void cancel() {
    mAnimator.cancel();
  }

  @Override
  public void end() {
    mAnimator.end();
  }

  @Override
  public long getDuration() {
    return mAnimator.getDuration();
  }

  @Override
  public TimeInterpolator getInterpolator() {
    return mAnimator.getInterpolator();
  }

  @Override
  public ArrayList<AnimatorListener> getListeners() {
    return new ArrayList<>(mListeners.keySet());
  }

  @Override
  public long getStartDelay() {
    return mAnimator.getStartDelay();
  }

  @Override
  public boolean isPaused() {
    return mAnimator.isPaused();
  }

  @Override
  public boolean isRunning() {
    return mAnimator.isRunning();
  }

  @Override
  public boolean isStarted() {
    return mAnimator.isStarted();
  }

  @Override
  public void removeAllListeners() {
    super.removeAllListeners();
    mListeners.clear();
    mAnimator.removeAllListeners();
  }

  @Override
  public void removeListener(AnimatorListener listener) {
    AnimatorListener wrapper = mListeners.get(listener);
    if (wrapper != null) {
      mListeners.remove(listener);
      mAnimator.removeListener(wrapper);
    }
  }

    /* We don't want to override pause or resume methods
     * because we don't want them to affect mAnimator.
    public void pause();
    public void resume();
    public void addPauseListener(AnimatorPauseListener listener);
    public void removePauseListener(AnimatorPauseListener listener);
     */

  @Override
  public Animator setDuration(long durationMS) {
    mAnimator.setDuration(durationMS);
    return this;
  }

  @Override
  public void setInterpolator(TimeInterpolator timeInterpolator) {
    mAnimator.setInterpolator(timeInterpolator);
  }

  @Override
  public void setStartDelay(long delayMS) {
    mAnimator.setStartDelay(delayMS);
  }

  @Override
  public void setTarget(Object target) {
    mAnimator.setTarget(target);
  }

  @Override
  public void setupEndValues() {
    mAnimator.setupEndValues();
  }

  @Override
  public void setupStartValues() {
    mAnimator.setupStartValues();
  }

  @Override
  public void start() {
    mAnimator.start();
  }

  private static class AnimatorListenerWrapper
          implements Animator.AnimatorListener {

    private final Animator mAnimator;
    private final Animator.AnimatorListener mListener;

    public AnimatorListenerWrapper(Animator animator,
                                   Animator.AnimatorListener listener) {
      mAnimator = animator;
      mListener = listener;
    }

    @Override
    public void onAnimationStart(Animator animator) {
      mListener.onAnimationStart(mAnimator);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
      mListener.onAnimationEnd(mAnimator);
    }

    @Override
    public void onAnimationCancel(Animator animator) {
      mListener.onAnimationCancel(mAnimator);
    }

    @Override
    public void onAnimationRepeat(Animator animator) {
      mListener.onAnimationRepeat(mAnimator);
    }
  }
}
