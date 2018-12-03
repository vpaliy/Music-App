package com.vpaliy.melophile.playback.service;

import android.util.Log;

import com.vpaliy.melophile.playback.PlaybackManager;

public class MediaTasks {

  private static final String TAG = MediaTasks.class.getSimpleName();

  public static final String ACTION_PAUSE = "com.vpaliy.melophile.pause";
  public static final String ACTION_PLAY = "com.vpaliy.melophile.play";
  public static final String ACTION_PREV = "com.vpaliy.melophile.prev";
  public static final String ACTION_NEXT = "com.vpaliy.melophile.next";
  public static final String ACTION_STOP = "com.vpaliy.melophile.stop";

  public static final String ACTION_STOP_CASTING = "com.vpaliy.player.stop_casting";

  public static final String ACTION_CMD = "action:cmd";
  public static final String CMD_NAME = "cmd:name";
  public static final String CMD_PAUSE = "cmd:pause";


  public static void executeTask(PlaybackManager playbackManager, String action) {
    Log.d(TAG, "executeTask with action:" + action);
    if (playbackManager == null || action == null) return;
    if (action.equals(ACTION_PAUSE)) {
      playbackManager.handlePauseRequest();
    } else if (action.equals(ACTION_PLAY)) {
      playbackManager.handleResumeRequest();
    } else if (action.equals(ACTION_PREV)) {
      playbackManager.handlePrevRequest();
    } else if (action.equals(ACTION_NEXT)) {
      playbackManager.handleNextRequest();
    }
  }
}
