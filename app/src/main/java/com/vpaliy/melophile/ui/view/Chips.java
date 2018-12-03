package com.vpaliy.melophile.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vpaliy.melophile.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chips extends ViewGroup {

  private List<TextView> chips;
  private int lineHeight;
  private int horizontalSpacing;
  private int verticalSpacing;
  private int textAppearance;

  public Chips(Context context) {
    this(context, null, 0);
  }

  public Chips(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public Chips(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initAttrs(attrs);
  }

  private void initAttrs(AttributeSet attrs) {
    if (attrs != null) {
      TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.Chips);
      horizontalSpacing = (int) (array.getDimension(R.styleable.Chips_horizontal_spacing, 1));
      verticalSpacing = (int) (array.getDimension(R.styleable.Chips_vertical_spacing, 1));
      textAppearance = array.getResourceId(R.styleable.Chips_text_style, -1);
      int arrayRes = array.getResourceId(R.styleable.Chips_array, -1);
      if (arrayRes != -1) {
        String[] textArray = getResources().getStringArray(arrayRes);
        setTags(Arrays.asList(textArray));
      }
      array.recycle();
      return;
    }
  }

  private static class LayoutParams extends ViewGroup.LayoutParams {

    final int horizontalSpacing;
    final int verticalSpacing;

    LayoutParams(int horizontalSpacing, int verticalSpacing) {
      super(WRAP_CONTENT, WRAP_CONTENT);
      this.horizontalSpacing = horizontalSpacing;
      this.verticalSpacing = verticalSpacing;
    }
  }

  @Override
  protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
    return new Chips.LayoutParams(horizontalSpacing, verticalSpacing);
  }

  @Override
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
    return new Chips.LayoutParams(horizontalSpacing, verticalSpacing);
  }

  @Override
  protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
    return p instanceof Chips.LayoutParams;
  }


  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    final int count = getChildCount();
    final int width = r - l;
    int xPos = getPaddingLeft();
    int yPos = getPaddingTop();

    for (int i = 0; i < count; i++) {
      final View child = getChildAt(i);
      if (child.getVisibility() != GONE) {
        final int childWidth = child.getMeasuredWidth();
        final int childHeight = child.getMeasuredHeight();
        final Chips.LayoutParams lp = (Chips.LayoutParams) child.getLayoutParams();
        if (xPos + childWidth > width) {
          xPos = getPaddingLeft();
          yPos += lineHeight;
        }
        child.layout(xPos, yPos, xPos + childWidth, yPos + childHeight);
        xPos += childWidth + lp.horizontalSpacing;
      }
    }
  }

  public void setHorizontalSpacing(int horizontalSpacing) {
    this.horizontalSpacing = horizontalSpacing;
  }

  public void setVerticalSpacing(int verticalSpacing) {
    this.verticalSpacing = verticalSpacing;
  }

  public int getVerticalSpacing() {
    return verticalSpacing;
  }

  public int getHorizontalSpacing() {
    return horizontalSpacing;
  }

  public List<TextView> getChips() {
    return chips;
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int width = View.MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
    int height = View.MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
    int count = getChildCount();
    int lineHeight = 0;

    int xPos = getPaddingLeft();
    int yPos = getPaddingTop();

    int childHeightMeasureSpec;
    if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
      childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
    } else {
      childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    }

    for (int i = 0; i < count; i++) {
      final View child = getChildAt(i);
      if (child.getVisibility() != GONE) {
        final Chips.LayoutParams lp = (Chips.LayoutParams) child.getLayoutParams();
        child.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST), childHeightMeasureSpec);
        final int childWidth = child.getMeasuredWidth();
        lineHeight = Math.max(lineHeight, child.getMeasuredHeight() + lp.verticalSpacing);

        if (xPos + childWidth > width) {
          xPos = getPaddingLeft();
          yPos += lineHeight;
        }
        xPos += childWidth + lp.horizontalSpacing;
      }
    }
    this.lineHeight = lineHeight;
    if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.UNSPECIFIED) {
      height = yPos + lineHeight;

    } else if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
      if (yPos + lineHeight < height) {
        height = yPos + lineHeight;
      }
    }
    setMeasuredDimension(width, height);
  }

  public void setTags(List<String> tags) {
    if (tags == null || tags.isEmpty()) return;
    if (chips == null) {
      chips = new ArrayList<>(tags.size());
    }
    if (tags.size() > chips.size()) {
      int diff = tags.size() - chips.size();
      for (int index = 0; index < diff; index++) {
        TextView chip = new TextView(getContext());
        chip.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ring));
        if (textAppearance != -1) {
          chip.setTextAppearance(getContext(), textAppearance);
        }
        chip.setOnTouchListener((view, event) -> {
          return false;
        });
        chips.add(chip);
        addView(chip);
      }
    }
    int index = 0;
    for (; index < tags.size(); index++) {
      TextView chip = chips.get(index);
      chip.setText(tags.get(index));
      if (chip.getVisibility() != View.VISIBLE) {
        chip.setVisibility(View.VISIBLE);
      }
    }

    if (index < chips.size()) {
      for (; index < chips.size(); index++) {
        chips.get(index).setVisibility(View.GONE);
      }
    }
    requestLayout();
  }

  public TextView assignListenerByName(String chipTitle, OnClickListener listener) {
    if (chips != null) {
      for (TextView chip : chips) {
        if (TextUtils.equals(chip.getText(), chipTitle)) {
          chip.setOnClickListener(listener);
          return chip;
        }
      }
    }
    return null;
  }

  public void setClickListenerToAll(OnClickListener listener) {
    if (chips != null) {
      for (TextView chip : chips) {
        chip.setOnClickListener(listener);
      }
    }
  }
}
