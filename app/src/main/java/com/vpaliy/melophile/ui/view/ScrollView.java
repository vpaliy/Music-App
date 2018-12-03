package com.vpaliy.melophile.ui.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollView extends NestedScrollView {

  private float xDistance, yDistance, lastX, lastY;

  public ScrollView(Context context) {
    super(context);
  }

  public ScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);

  }

  public ScrollView(Context context, AttributeSet attrs,
                    int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @SuppressWarnings("unused")
  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    final float x = ev.getX();
    final float y = ev.getY();
    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        xDistance = yDistance = 0f;
        lastX = ev.getX();
        lastY = ev.getY();
        computeScroll();
        break;
      case MotionEvent.ACTION_MOVE:
        final float curX = ev.getX();
        final float curY = ev.getY();
        xDistance += Math.abs(curX - lastX);
        yDistance += Math.abs(curY - lastY);
        lastX = curX;
        lastY = curY;
        if (xDistance > yDistance) {
          return false;
        }
    }
    return super.onInterceptTouchEvent(ev);
  }
}