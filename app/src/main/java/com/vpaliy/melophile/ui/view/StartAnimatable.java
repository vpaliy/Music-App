package com.vpaliy.melophile.ui.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.vpaliy.melophile.R;

/**
 * A transition which sets a specified {@link Animatable} {@code drawable} on a target
 * {@link ImageView} and {@link Animatable#start() starts} it when the transition begins.
 */
public class StartAnimatable extends Transition {

  private final Animatable animatable;

  public StartAnimatable(Animatable animatable) {
    super();
    if (!(animatable instanceof Drawable)) {
      throw new IllegalArgumentException("Non-Drawable resource provided.");
    }
    this.animatable = animatable;
  }

  public StartAnimatable(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StartAnimatable);
    Drawable drawable = a.getDrawable(R.styleable.StartAnimatable_android_src);
    a.recycle();
    if (drawable instanceof Animatable) {
      animatable = (Animatable) drawable;
    } else {
      throw new IllegalArgumentException("Non-Animatable resource provided.");
    }
  }

  @Override
  public void captureStartValues(TransitionValues transitionValues) {
    // no-op
  }

  @Override
  public void captureEndValues(TransitionValues transitionValues) {
    // no-op
  }

  @Override
  public Animator createAnimator(ViewGroup sceneRoot,
                                 TransitionValues startValues,
                                 TransitionValues endValues) {
    if (animatable == null || endValues == null
            || !(endValues.view instanceof ImageView)) return null;

    ImageView iv = (ImageView) endValues.view;
    iv.setImageDrawable((Drawable) animatable);

    // need to return a non-null Animator even though we just want to listen for the start
    ValueAnimator transition = ValueAnimator.ofInt(0, 1);
    transition.addListener(new AnimatorListenerAdapter() {
      @Override
      public void onAnimationStart(Animator animation) {
        animatable.start();
      }
    });
    return transition;
  }
}