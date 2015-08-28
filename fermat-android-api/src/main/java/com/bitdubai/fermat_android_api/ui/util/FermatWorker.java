package com.bitdubai.fermat_android_api.ui.util;

import android.app.Activity;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Fermat Background Worker
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public abstract class FermatWorker extends Thread {

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
                        callBack.onPostExecute(result);
                    }
                });
            }
        } catch (final Exception ex) {
            if (callBack != null && context != null) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onErrorOccurred(ex);
                    }
                });
            }
        }
        super.run();
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
     * Set CallBack
     *
     * @param callBack Instance of FermatWorkerCallBack to call functions over UI Thread and handle the result
     */
    public void setCallBack(FermatWorkerCallBack callBack) {
        this.callBack = callBack;
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
     * This function is used for the run method of the fermat background worker
     *
     * @throws Exception any type of exception
     */
    protected abstract Object doInBackground() throws Exception;
}
