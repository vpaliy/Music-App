package com.vpaliy.melophile.playback;

public class MediaTasks {

    public static final String ACTION_PAUSE = "com.vpaliy.player.pause";
    public static final String ACTION_PLAY = "com.vpaliy.player.play";
    public static final String ACTION_PREV = "com.vpaliy.player.prev";
    public static final String ACTION_NEXT = "com.vpaliy.player.next";
    public static final String ACTION_STOP_CASTING = "com.vpaliy.player.stop_casting";

    public static final String ACTION_CMD="action:cmd";
    public static final String CMD_NAME="cmd:name";
    public static final String CMD_PAUSE="cmd:pause";


    public static void executeTask(PlaybackManager playbackManager, String action){
        if(playbackManager==null || action==null) return ;
        if(action.equals(ACTION_PAUSE)){
            playbackManager.handlePauseRequest();
        }else if(action.equals(ACTION_PLAY)){
            playbackManager.handleResumeRequest();
        }else if(action.equals(ACTION_PREV)){
        }
    }
}
