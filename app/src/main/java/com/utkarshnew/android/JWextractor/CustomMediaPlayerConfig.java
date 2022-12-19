package com.utkarshnew.android.JWextractor;

public class CustomMediaPlayerConfig {
    /**
     * Sets the buffer duration parameters.
     *
     * @param minBufferMs The minimum duration of media that the player will attempt to ensure is
     * buffered at all times, in milliseconds.
     * @param maxBufferMs The maximum duration of media that the player will attempt to buffer, in
     * milliseconds.
     * @param bufferForPlaybackMs The duration of media that must be buffered for playback to start
     * or resume following a user action such as a seek, in milliseconds.
     * @param bufferForPlaybackAfterRebufferMs The default duration of media that must be buffered
     * for playback to resume after a rebuffer, in milliseconds. A rebuffer is defined to be
     * caused by buffer depletion rather than a user action.
     */
    //Minimum Video you want to buffer while Playing
    public static final int MIN_BUFFER_DURATION = 2000;
    //Max Video you want to buffer during PlayBack
    public static final int MAX_BUFFER_DURATION = 20000;
    //Min Video you want to buffer before start Playing it
    public static final int MIN_PLAYBACK_START_BUFFER = 1500;
    //Min video You want to buffer when user resumes video
    public static final int MIN_PLAYBACK_RESUME_BUFFER = 1500;

    public static final long CUSTOM_CACHE_BYTES = 10 * 1024 * 1024;
}
