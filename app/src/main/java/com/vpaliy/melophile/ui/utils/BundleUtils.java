package com.vpaliy.melophile.ui.utils;

import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class BundleUtils {

  public static byte[] compress(String data) {
    if (data == null) return null;
    ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
    try {
      GZIPOutputStream gzip = new GZIPOutputStream(bos);
      gzip.write(data.getBytes());
      gzip.close();
      byte[] compressed = bos.toByteArray();
      bos.close();
      return compressed;
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public static String decompress(byte[] compressed) {
    if (compressed == null) return null;
    ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
    try {
      GZIPInputStream gis = new GZIPInputStream(bis);
      BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      br.close();
      gis.close();
      bis.close();
      return sb.toString();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return null;
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

  public static void packHeavyObject(Bundle bundle, String key, Object object, Type type) {
    String jsonString = convertToJsonString(object, type);
    byte[] compressedStuff = compress(jsonString);
    bundle.putByteArray(key, compressedStuff);
  }

  public static <T> T fetchHeavyObject(Type type, Bundle bundle, String key) {
    if (bundle != null && !TextUtils.isEmpty(key) && type != null) {
      byte[] compressedStuff = bundle.getByteArray(key);
      String jsonString = decompress(compressedStuff);
      if (!TextUtils.isEmpty(jsonString)) {
        return convertFromJsonString(jsonString, type);
      }
    }
    return null;
  }
}
