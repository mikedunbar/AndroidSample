package com.example.mdunbar.login.util;

import android.util.Log;

/**
 * @author Mike Dunbar
 */

public class AndroidLogger implements Logger {
    @Override
    public void log(String tag, String message) {
        log(tag, message, null);
    }

    @Override
    public void log(String tag, String message, Throwable throwable) {
        Log.i(tag, message, throwable);
    }


}
