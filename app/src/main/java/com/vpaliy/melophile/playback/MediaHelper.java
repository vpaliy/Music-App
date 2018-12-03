package com.vpaliy.melophile.playback;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaDescriptionCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MediaHelper {

  // Media IDs used on browseable items of MediaBrowser

  public static final String MEDIA_ID_ROOT = "root";
  public static final String MEDIA_ID_EMPTY_ROOT = "empty_root";

  public static final String MEDIA_ID_MUSICS_BY_GENRE = "__BY_GENRE__";
  public static final String MEDIA_ID_MUSICS_BY_SEARCH = "__BY_SEARCH__";

  private static final char CATEGORY_SEPARATOR = '/';
  private static final char LEAF_SEPARATOR = '|';

  public static String createMediaID(String musicID, String... categories) {
    StringBuilder sb = new StringBuilder();
    if (categories != null) {
      for (int i = 0; i < categories.length; i++) {
        if (!isValidCategory(categories[i])) {
          throw new IllegalArgumentException("Invalid category: " + categories[i]);
        }
        sb.append(categories[i]);
        if (i < categories.length - 1) {
          sb.append(CATEGORY_SEPARATOR);
        }
      }
    }
    if (musicID != null) {
      sb.append(LEAF_SEPARATOR).append(musicID);
    }
    return sb.toString();
  }


  private static boolean isValidCategory(String category) {
    return category == null || (category.indexOf(CATEGORY_SEPARATOR) < 0 &&
            category.indexOf(LEAF_SEPARATOR) < 0);
  }

  public static String extractMusicId(@NonNull String mediaID) {
    int pos = mediaID.indexOf(LEAF_SEPARATOR);
    if (pos >= 0) {
      return mediaID.substring(pos + 1);
    }
    return null;
  }

  public static @NonNull
  String[] getHierarchy(@NonNull String mediaID) {
    int pos = mediaID.indexOf(LEAF_SEPARATOR);
    if (pos >= 0) {
      mediaID = mediaID.substring(0, pos);
    }
    return mediaID.split(String.valueOf(CATEGORY_SEPARATOR));
  }


  public static String extractBrowseCategoryValueFromMediaID(@NonNull String mediaID) {
    String[] hierarchy = getHierarchy(mediaID);
    if (hierarchy.length == 2) {
      return hierarchy[1];
    }
    return null;
  }

  public static boolean isBrowseable(@NonNull String mediaID) {
    return mediaID.indexOf(LEAF_SEPARATOR) < 0;
  }

  public static String getParentMediaID(@NonNull String mediaID) {
    String[] hierarchy = getHierarchy(mediaID);
    if (!isBrowseable(mediaID)) {
      return createMediaID(null, hierarchy);
    }
    if (hierarchy.length <= 1) {
      return MEDIA_ID_ROOT;
    }
    String[] parentHierarchy = Arrays.copyOf(hierarchy, hierarchy.length - 1);
    return createMediaID(null, parentHierarchy);
  }

  private static MediaBrowserCompat.MediaItem createBrowseableMediaItemForRoot(Resources resources) {
    MediaDescriptionCompat description = new MediaDescriptionCompat.Builder()
            .setMediaId(MEDIA_ID_MUSICS_BY_GENRE)
            //  .setTitle(resources.getString(R.string.browse_genres))
            //  .setSubtitle(resources.getString(R.string.browse_genre_subtitle))
            .build();
    return new MediaBrowserCompat.MediaItem(description,
            MediaBrowserCompat.MediaItem.FLAG_BROWSABLE);
  }

  public static List<MediaBrowserCompat.MediaItem> getChildren(String mediaId, Resources resources) {
    List<MediaBrowserCompat.MediaItem> mediaItems = new ArrayList<>();
    if (!isBrowseable(mediaId)) {
      return mediaItems;
    }
    if (MEDIA_ID_ROOT.equals(mediaId)) {
      mediaItems.add(createBrowseableMediaItemForRoot(resources));
    }
    return mediaItems;
  }


}