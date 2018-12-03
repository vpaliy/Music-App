package com.vpaliy.melophile.ui.view;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.widget.Checkable;

public class FabToggle extends FloatingActionButton
        implements Checkable {

  private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

  private boolean isChecked = false;
  private int minOffset;
  private int staticOffset;

  public FabToggle(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public void setOffset(int offset) {
    if (offset != getTranslationY()) {
      offset = Math.max(minOffset, offset);
      setTranslationY(offset);
    }
  }

  public void setStaticOffset(int staticOffset) {
    this.staticOffset = staticOffset;
  }

  public void setMinOffset(int minOffset) {
    this.minOffset = minOffset;
  }

  public int getStaticOffset() {
    return staticOffset;
  }

  public boolean isChecked() {
    return isChecked;
  }

  public void setChecked(boolean isChecked) {
    if (this.isChecked != isChecked) {
      this.isChecked = isChecked;
      refreshDrawableState();
    }
  }

  public void toggle() {
    setChecked(!isChecked);
  }

  @Override
  public int[] onCreateDrawableState(int extraSpace) {
    final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
    if (isChecked()) {
      mergeDrawableStates(drawableState, CHECKED_STATE_SET);
    }
    return drawableState;
  }
}