package com.vpaliy.data.mapper;

import android.content.Context;
import android.icu.text.NumberFormat;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;

import com.vpaliy.data.Config;
import com.vpaliy.data.R;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.http.Path;

@SuppressWarnings("WeakerAccess")
public class MapperUtils {

  public static List<String> splitString(String string) {
    if (string == null || string.isEmpty()) return null;
    string = string.replace(" ", ",");
    return Arrays.asList(string.split("\\s*,\\s*"));
  }

  public static String toString(List<String> strings) {
    if (strings == null) return null;
    return strings.toString().replaceAll("[\\[.\\].\\s+]", "");
  }

  public static int convertToInt(String number) {
    if (number == null) return 0;
    return Integer.parseInt(number);
  }

  public static String convertToStream(String streamUrl) {
    if (streamUrl == null) return null;
    return streamUrl + "?client_id=" + Config.CLIENT_ID;
  }

  public static String convertFromStream(String streamUrl) {
    if (streamUrl == null || !streamUrl.contains("?client_id=")) return streamUrl;
    return streamUrl.substring(0, streamUrl.indexOf("?client_id=") - 1);
  }

  public static String convertDuration(Context context, long millis) {
    long time = TimeUnit.MILLISECONDS.toHours(millis);
    String result = "";
    if (time != 0) {
      result = context.getResources().getQuantityString(R.plurals.hours, (int) (time), time);
    }
    time = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
    if (time != 0) {
      result += " " + context.getResources().getQuantityString(R.plurals.minutes, (int) (time), time);
    }
    return result;
  }

  public static String convertToRuntime(Context context, String duration) {
    if (duration == null) return null;
    String hr = context.getString(R.string.hour_label);
    String hrs = context.getString(R.string.hours_label);
    Pattern pattern = Pattern.compile("-?\\d+");
    Matcher matcher = pattern.matcher(duration);
    int runtime = 0;
    int count = 0;
    while (matcher.find()) count++;
    matcher = matcher.reset();
    if (count == 2) {
      matcher.find();
      runtime = Integer.parseInt(matcher.group()) * 60;
      if (matcher.find()) runtime += Integer.parseInt(matcher.group());
    } else if (matcher.find()) {
      runtime = Integer.parseInt(matcher.group());
      duration = duration.trim();
      if (duration.contains(hr) || duration.contains(hrs)) runtime *= 60;
    }

    return Integer.toString(runtime);
  }

  public static String convertDuration(Context context, String millis) {
    if (!TextUtils.isEmpty(millis)) {
      return convertDuration(context, Long.parseLong(millis));
    }
    return null;
  }
}
