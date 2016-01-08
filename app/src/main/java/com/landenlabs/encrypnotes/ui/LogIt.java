package com.landenlabs.encrypnotes.ui;

import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.landenlabs.encrypnotes.BuildConfig;

/**
 * Created by dlang_local on 7/3/2015.
 *
 * Code lifted from:
 * https://developer.android.com/samples/MediaBrowserService/src/com.example.android.mediabrowserservice/utils/LogHelper.html
 *
 * Notes:
 *   Use adb shell setprop log.tag.<YOUR_LOG_TAG> <LEVEL>
 *   to enable specific logging where LEVEL is
 *      VERBOSE, DEBUG, INFO, WARN, ERROR
 *   default is INFO for all TAGs.
 *
 */
public class LogIt {

    public static final int VERBOSE = Log.VERBOSE;
    public static final int DEBUG = Log.DEBUG;
    public static final int INFO = Log.INFO;
    public static final int WARN = Log.WARN;
    public static final int ERROR = Log.ERROR;


    static boolean s_debugMode = BuildConfig.DEBUG;

    public static void setDebugMode(ApplicationInfo appInfo) {
        s_debugMode = ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String getTag(Class cls) {
        return cls.getSimpleName();
    }

    public static void v(String tag, String message) {
        log(tag, Log.VERBOSE, message);
    }

    public static void d(String tag, String message) {
        log(tag, Log.DEBUG, message);
    }

    public static void i(String tag, String message) {
        log(tag, Log.INFO, message);
    }

    public static void w(String tag, String message) {
        log(tag, Log.WARN, message);
    }

    public static void w(String tag, String message, Throwable t) {
        log(tag, Log.WARN, message, t);
    }

    public static void e(String tag, String message) {
        log(tag, Log.ERROR, message);
    }

    public static void e(String tag, String message, Throwable t) {
        log(tag, Log.ERROR, message, t);
    }

    public static void log(String tag, int level, String message) {
        log(tag, level, message, null);
    }

    public static void log(Class cls, int level, String message, Throwable t) {
        log(getTag(cls), level, message, t);
    }

    public static void log(String tag, int level, String message, Throwable t) {
        // Only log if build type is DEBUG
        if (s_debugMode && Log.isLoggable(tag, level)) {
            if (t != null) {
                StringBuilder sb = new StringBuilder(message);
                if (t != null) {
                    sb.append("\n").append(Log.getStackTraceString(t));
                }
                message = sb.toString();
            }

            Log.println(level, tag, message);
        }
    }
}
