package com.vpaliy.melophile.ui.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.IntProperty;
import android.util.Property;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class PresentationUtils {

  private PresentationUtils() {
    throw new UnsupportedOperationException();
  }

  public static int getStatusBarHeight(Resources resources) {
    int result = 0;
    int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = resources.getDimensionPixelSize(resourceId);
    }
    return result;
  }

  public static @CheckResult
  @ColorInt
  int modifyAlpha(@ColorInt int color,
                  @IntRange(from = 0, to = 255) int alpha) {
    return (color & 0x00ffffff) | (alpha << 24);
  }

  public static @CheckResult
  @ColorInt
  int modifyAlpha(@ColorInt int color,
                  @FloatRange(from = 0f, to = 1f) float alpha) {
    return modifyAlpha(color, (int) (255f * alpha));
  }

  public static void setDrawableColor(TextView view, int color) {
    Drawable[] drawables = view.getCompoundDrawables();
    for (Drawable drawable : drawables) {
      if (drawable != null) {
        drawable.mutate();
        DrawableCompat.setTint(drawable, color);
      }
    }
  }

  public static void setDrawableColor(ImageView view, int color) {
    Drawable drawable = view.getDrawable();
    if (drawable != null) {
      drawable.mutate();
      DrawableCompat.setTint(drawable, color);
    }
  }

  public static abstract class IntProp<T> {

    public final String name;

    public IntProp(String name) {
      this.name = name;
    }

    public abstract void set(T object, int value);

    public abstract int get(T object);
  }


  public static <T> Property<T, Integer> createIntProperty(final IntProp<T> impl) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      return new IntProperty<T>(impl.name) {
        @Override
        public Integer get(T object) {
          return impl.get(object);
        }

        @Override
        public void setValue(T object, int value) {
          impl.set(object, value);
        }
      };
    } else {
      return new Property<T, Integer>(Integer.class, impl.name) {
        @Override
        public Integer get(T object) {
          return impl.get(object);
        }

        @Override
        public void set(T object, Integer value) {
          impl.set(object, value);
        }
      };
    }
  }

  public static <T> T convertFromJsonString(String jsonString, Type type) {
    if (jsonString == null) return null;
    Gson gson = new Gson();
    return gson.fromJson(jsonString, type);
  }

  public static String convertToJsonString(Object object, Type type) {
    if (object == null) return null;
    Gson gson = new Gson();
    return gson.toJson(object, type);
  }

}
