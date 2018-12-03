package com.vpaliy.melophile.ui.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * A transition that animates the alpha, scale X & Y of a view simultaneously.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Pop extends Visibility {

  public Pop(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues,
                           TransitionValues endValues) {
    view.setAlpha(0f);
    view.setScaleX(0f);
    view.setScaleY(0f);
    return ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat(View.ALPHA, 1f),
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f));
  }

  @Override
  public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues,
                              TransitionValues endValues) {
    return ObjectAnimator.ofPropertyValuesHolder(
            view,
            PropertyValuesHolder.ofFloat(View.ALPHA, 0f),
            PropertyValuesHolder.ofFloat(View.SCALE_X, 0f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f));
  }

}