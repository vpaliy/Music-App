package com.vpaliy.melophile.ui.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BackgroundScale extends Visibility {

  public BackgroundScale() {
  }

  public BackgroundScale(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public Animator onAppear(ViewGroup sceneRoot, View view,
                           TransitionValues startValues,
                           TransitionValues endValues) {
    view.setPivotX(view.getWidth());
    view.setPivotY(view.getHeight() / 2);
    return ObjectAnimator.ofFloat(view, View.SCALE_X, 0, 1).setDuration(400);
  }
}
