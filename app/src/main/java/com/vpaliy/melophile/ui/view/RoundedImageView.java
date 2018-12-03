package com.vpaliy.melophile.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.vpaliy.melophile.R;

public class RoundedImageView extends AppCompatImageView {

  private float radius = 18.0f;
  private Path path;
  private RectF rect;

  public RoundedImageView(Context context) {
    this(context, null, 0);
  }

  public RoundedImageView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    if (attrs != null) {
      TypedArray array = getContext().obtainStyledAttributes(attrs,
              R.styleable.RoundedImageView);
      final int N = array.getIndexCount();
      for (int i = 0; i < N; ++i) {
        int attr = array.getIndex(i);
        if (attr == R.styleable.RoundedImageView_radius) {
          radius = array.getFloat(R.styleable.RoundedImageView_radius, 18.f);
        }
      }
      array.recycle();
    }
    init();
  }

  private void init() {
    path = new Path();
  }

  public void setRadius(float radius) {
    this.radius = radius;
  }

  public float getRadius() {
    return radius;
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    if (w != oldw || h != oldh) {
      rect = new RectF(0, 0, this.getWidth(), this.getHeight());
    }
    super.onSizeChanged(w, h, oldw, oldh);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    path.addRoundRect(rect, radius, radius, Path.Direction.CW);
    canvas.clipPath(path);
    super.onDraw(canvas);
  }
}