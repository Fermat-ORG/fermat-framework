package com.bitdubai.fermat_android_api.ui.util;

import android.app.Activity;
import android.util.Log;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Fermat Background Worker
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public abstract class FermatWorker extends Thread {

    private final String TAG = "FermatWorker";
    /**
     * References Fields
     */
    private Activity context;
    private FermatWorkerCallBack callBack;

    /**
     * Simple constructor
     */
    protected FermatWorker() {
        //do nothing...
    }

    /**
     * Overload constructor
     *
     * @param context Activity Context used for init the context field and be able to call delegates on the UI Thread.
     */
    protected FermatWorker(Activity context) {
        setContext(context);
    }

    /**
     * Overload constructor
     *
     * @param context  Activity Context used for init the context field and be able to call delegates on the UI Thread.
     * @param callBack Instance of FermatWorkerCallBack to call functions over UI Thread and handle the result
     */
    public FermatWorker(Activity context, FermatWorkerCallBack callBack) {
        setContext(context);
        setCallBack(callBack);
    }

    @Override
    public void run() {
        try {
            //todo: check connection availability
            final Object result = doInBackground();
            if (context != null && callBack != null) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callBack.onPostExecute(result);
                        } catch (Exception ex) {
                            Log.i(TAG, "Cannot call onPostExecute method...", ex);
                        }
                    }
                });
            }
        } catch (final Exception ex) {
            if (callBack != null && context != null) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            callBack.onErrorOccurred(ex);
                        } catch (Exception ex) {
                            Log.i(TAG, "Cannot call onErrorOccurred...", ex);
                        }
                    }
                });
            }
        }
        super.run();
    }


    /**
     * Execute this thread with single thread executor service
     *
     * @return ExecutorService Reference to handled this thread. <b>Use executor.shutDown for stop the current thread instance</b>
     */
    public ExecutorService execute() {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.execute(this);
        return exec;
    }

    /**
     * Execute this thread with single thread executor service
     *
     * @param executor ExecutorService to run this thread instance
     * @return ExecutorService Reference to handled this thread. <b>Use executor.shutDown for stop the current thread instance</b>
     */
    public ExecutorService execute(ExecutorService executor) {
        executor.execute(this);
        return executor;
    }

    /**
     * Set Context
     *
     * @param context Activity Context used for init the context field and be able to call delegates on the UI Thread.
     */
    public void setContext(Activity context) {
        this.context = context;
    }

    /**
     * Get Worker CallBack
     *
     * @return FermatWorkerCallBack
     */
    public FermatWorkerCallBack getCallBack() {
        return this.callBack;
    }

    /**
     * Set CallBack
     *
     * @param callBack Instance of FermatWorkerCallBack to call functions over UI Thread and handle the result
     */
    public void setCallBack(FermatWorkerCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * This function is used for the run method of the fermat background worker
     *
     * @throws Exception any type of exception
     */
    protected abstract Object doInBackground() throws Exception;
}
