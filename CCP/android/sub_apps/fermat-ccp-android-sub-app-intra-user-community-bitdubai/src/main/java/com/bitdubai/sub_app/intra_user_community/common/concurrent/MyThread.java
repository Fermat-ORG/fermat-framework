package com.bitdubai.sub_app.intra_user_community.common.concurrent;

import android.app.Activity;

import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;

import java.util.List;

/**
 * Created by mati on 2015.10.17..
 */
public abstract class MyThread extends Thread {


    private final String TAG = "UserListThread";

    /**
     * References Fields
     */
    private Activity context;
    private CallbackMati callBack;

    public MyThread() {
    }

    public MyThread(Activity context) {
        this.context = context;
    }

    public MyThread(Activity context, CallbackMati callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    public void run(){
        try{
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callBack.onPostExecute(mainTask());
                }
            });
        }catch (Exception e){
            callBack.onException(e);
        }


    }


    public abstract List<IntraUserInformation> mainTask();

}
