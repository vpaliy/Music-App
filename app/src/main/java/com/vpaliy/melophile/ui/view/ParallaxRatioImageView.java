package com.vpaliy.melophile.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.FloatRange;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Property;

import com.vpaliy.melophile.R;
import com.vpaliy.melophile.ui.utils.PresentationUtils;

public class ParallaxRatioImageView extends AppCompatImageView {

  private float imageRatio = .5625f;
  private static final int[] STATE_PINNED = {R.attr.state_pinned};
  private final Paint scrimPaint;
  private int imageOffset;
  private int minOffset;
  private Rect clipBounds = new Rect();
  private float scrimAlpha = 0f;
  private float maxScrimAlpha = 1f;
  private int scrimColor = Color.TRANSPARENT;
  private float parallaxFactor = -0.5f;
  private boolean isPinned = false;
  private boolean immediatePin = false;

  public static final Property<ParallaxRatioImageView, Integer> OFFSET =
          PresentationUtils.createIntProperty(new PresentationUtils.IntProp<ParallaxRatioImageView>("offset") {
            @Override
            public void set(ParallaxRatioImageView parallaxRatioImageView, int offset) {
              parallaxRatioImageView.setOffset(offset);
            }

            @Override
            public int get(ParallaxRatioImageView parallaxRatioImageView) {
              return parallaxRatioImageView.getOffset();
            }
          });

  public ParallaxRatioImageView(Context context) {
    this(context, null, 0);
  }

  public ParallaxRatioImageView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ParallaxRatioImageView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    if (attrs != null) {
      TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.ParallaxRatioImageView);
      imageRatio = array.getFloat(R.styleable.ParallaxRatioImageView_image_ratio, imageRatio);
      scrimAlpha = array.getFloat(R.styleable.ParallaxRatioImageView_scrimAlpha, scrimAlpha);
      maxScrimAlpha = array.getFloat(R.styleable.ParallaxRatioImageView_maxScrimAlpha, maxScrimAlpha);
      scrimColor = array.getColor(R.styleable.ParallaxRatioImageView_scrimColor, scrimColor);
      parallaxFactor = array.getFloat(R.styleable.ParallaxRatioImageView_parallaxFactor, parallaxFactor);
      array.recycle();
    }
    scrimPaint = new Paint();
    scrimPaint.setColor((scrimColor & 0x00ffffff) | ((int) (255f * scrimAlpha) << 24));
  }

  public int getOffset() {
    return (int) getTranslationY();
  }

  public void setOffset(int offset) {
    offset = Math.max(minOffset, offset);
    if (offset != getTranslationY()) {
      setTranslationY(offset);
      imageOffset = (int) (offset * parallaxFactor);
      clipBounds.set(0, -offset, getWidth(), getHeight());
      setClipBounds(clipBounds);
      setScrimAlpha(Math.min(
              ((float) -offset / getMinimumHeight()) * maxScrimAlpha, maxScrimAlpha));
      postInvalidateOnAnimation();
    }
    setPinned(offset == minOffset);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    if (h > getMinimumHeight()) {
      minOffset = getMinimumHeight() - h;
    }
  }


  public void setScrimAlpha(@FloatRange(from = 0f, to = 1f) float alpha) {
    if (scrimAlpha != alpha) {
      scrimAlpha = alpha;
      scrimPaint.setColor(PresentationUtils.modifyAlpha(scrimColor, scrimAlpha));
      postInvalidateOnAnimation();
    }
  }


  @Override
  protected void onDraw(Canvas canvas) {
    if (imageOffset != 0) {
      final int saveCount = canvas.save();
      canvas.translate(0f, imageOffset);
      super.onDraw(canvas);
      canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), scrimPaint);
      canvas.restoreToCount(saveCount);
    } else {
      super.onDraw(canvas);
      canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), scrimPaint);
    }
  }

  @Override
  public int[] onCreateDrawableState(int extraSpace) {
    final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
    if (isPinned) {
      mergeDrawableStates(drawableState, STATE_PINNED);
    }
    return drawableState;
  }

  public boolean isPinned() {
    return isPinned;
  }

  public void setPinned(boolean isPinned) {
    if (this.isPinned != isPinned) {
      this.isPinned = isPinned;
      refreshDrawableState();
      if (isPinned && immediatePin) {
        jumpDrawablesToCurrentState();
      }
    }
  }

  public boolean isImmediatePin() {
    return immediatePin;
  }

  public void setImmediatePin(boolean immediatePin) {
    this.immediatePin = immediatePin;
  }

  public void setImageRatio(float imageRatio) {
    this.imageRatio = imageRatio;
    requestLayout();
  }
}