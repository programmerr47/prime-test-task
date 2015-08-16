package com.github.programmerr47.primetesttask;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class PApplication extends Application {

    private static Context context;
    private static Handler uiHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        uiHandler = new Handler();
    }

    public static Context getAppContext() {
        return context;
    }

    public static Handler getUiHandler() {
        return uiHandler;
    }
}
