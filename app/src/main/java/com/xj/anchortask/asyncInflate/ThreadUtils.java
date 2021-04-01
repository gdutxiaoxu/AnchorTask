package com.xj.anchortask.asyncInflate;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by jun xu on 4/1/21.
 */
public class ThreadUtils {

    public static void runOnUiThread(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            LazyHolder.sUiThreadHandler.post(r);
        }
    }

    public static void runOnUiThreadAtFront(Runnable r) {
        if (isMainThread()) {
            r.run();
        } else {
            LazyHolder.sUiThreadHandler.postAtFrontOfQueue(r);
        }
    }

    public static void runOnUiThread(Runnable r, long delay) {
        LazyHolder.sUiThreadHandler.postDelayed(r, delay);
    }

    public static void postOnUiThread(Runnable r) {
        LazyHolder.sUiThreadHandler.post(r);
    }

    public static void removeCallbacks(Runnable r) {
        LazyHolder.sUiThreadHandler.removeCallbacks(r);
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private static class LazyHolder {
        private static Handler sUiThreadHandler = new Handler(Looper.getMainLooper());
    }
}
