package com.bitdubai.fermat_android_api.ui.interfaces;

/**
 * Fermat Worker CallBack functions
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public interface FermatWorkerCallBack {

    /**
     * implement this function to handle the result object through dynamic array
     *
     * @param result array of native object (handle result field with result[0], result[1],... result[n]
     */
    void onPostExecute(Object... result);

    /**
     * Implement this function to handle errors during the execution of any fermat worker instance
     *
     * @param ex Throwable object
     */
    void onErrorOccurred(Exception ex);
}
