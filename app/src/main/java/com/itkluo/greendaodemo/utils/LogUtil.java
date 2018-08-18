package com.itkluo.greendaodemo.utils;

import android.util.Log;

public class LogUtil {
    public static boolean isOutputLog = true;

    /**
     * 是否打印日志
     *
     * @param isOutputLog
     */
    public static void outputLog(boolean isOutputLog) {
        LogUtil.isOutputLog = isOutputLog;
    }

    /**
     * 打印info级别日志
     *
     * @param tag
     * @param message
     */
    public static void i(String tag, String message) {
        try {
            if (isOutputLog) {
                Log.i(tag, message);
            }
        } catch (Exception e) {
            Log.e(tag, e.getMessage() + "\nCauseby:" + e.getCause());
        }
    }

    /**
     * 打印Verbose级别日志
     *
     * @param tag
     * @param message
     */
    public static void v(String tag, String message) {
        try {
            if (isOutputLog) {
                Log.v(tag, message);
            }
        } catch (Exception e) {
            Log.e(tag, e.getMessage() + "\nCauseby:" + e.getCause());
        }
    }


    /**
     * 打印warn级别日志
     *
     * @param tag
     * @param message
     */
    public static void w(String tag, String message) {
        try {
            if (isOutputLog) {
                Log.w(tag, message);
            }
        } catch (Exception e) {
            Log.e(tag, e.getMessage() + "\nCauseby:" + e.getCause());
        }
    }

    /**
     * 打印dubug级别日志
     *
     * @param tag
     * @param message
     */
    public static void d(String tag, String message) {
        try {
            if (isOutputLog) {
                Log.d(tag, message);
            }
        } catch (Exception e) {
            Log.e(tag, e.getMessage() + "\nCauseby:" + e.getCause());
        }
    }

    /**
     * 打印Error级别日志
     *
     * @param tag
     * @param message
     */
    public static void e(String tag, String message) {
        try {
            if (isOutputLog) {
                Log.e(tag, message);
            }
        } catch (Exception e) {
            Log.e(tag, e.getMessage() + "\nCauseby:" + e.getCause());
        }
    }
}
