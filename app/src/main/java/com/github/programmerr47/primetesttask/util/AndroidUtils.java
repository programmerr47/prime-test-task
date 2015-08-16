package com.github.programmerr47.primetesttask.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.github.programmerr47.primetesttask.PApplication;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class AndroidUtils {

    public static boolean isNetworkConnected(@NonNull Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isNetworkConnected() {
        return isNetworkConnected(PApplication.getAppContext());
    }
}
