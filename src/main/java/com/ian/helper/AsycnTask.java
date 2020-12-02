/*
 * Helper JAVAFX application
 */

package com.ian.helper;

import javafx.application.Platform;

public abstract class AsycnTask<T1, T2, T3> {
    private boolean daemon = true;
    private T1[] params;

    public abstract void onPreExecute();

    public abstract T3 doInBackground(T1... params);

    public abstract void onPostExecute(T3 params);

    public abstract void progressCallback(T2... params);

    public void publishProgress(final T2... progressParams) {
        Platform.runLater(() -> progressCallback(progressParams));
    }

    public final Thread backgroundThread = new Thread(() -> {
        final T3 param = doInBackground(params);
        Platform.runLater(() -> onPostExecute(param));
    });

    public void execute(final T1... params) {
        this.params = params;
        Platform.runLater(() -> {
            onPreExecute();

            backgroundThread.setDaemon(daemon);
            backgroundThread.start();
        });
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    public final boolean isInterrupted() {
        return this.backgroundThread.isInterrupted();
    }

    public final boolean isAlive() {
        return this.backgroundThread.isAlive();
    }
}
