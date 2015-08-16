package com.github.programmerr47.primetesttask.background.tasks;

import android.os.AsyncTask;

/**
 * @author Michael Spitsin
 * @since 2014-08-30
 */
public abstract class AsyncTaskWithListener<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private OnTaskFinishedListener listener;

    @Override
    protected final void onPostExecute(Result result) {
        if (listener != null) {
            listener.onTaskFinished(this.getClass().getName(), result);
        }
    }

    public void setOnTaskFinishedListener(OnTaskFinishedListener listener) {
        this.listener = listener;
    }

    public interface OnTaskFinishedListener {
        void onTaskFinished(String taskName, Object extraObject);
    }
}
