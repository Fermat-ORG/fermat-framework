package com.bitdubai.android_core.app.common.version_1.classes;

import android.util.Log;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.util.interfaces.BroadcasterInterface;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.FermatApps;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;

import java.lang.ref.WeakReference;

/**
 * Created by Matias Furszyfer on 2016.02.04..
 */
public class BroadcastManager implements BroadcasterInterface {

    private static final String TAG = "broadcaster-manager";
    WeakReference<FermatActivity> fermatActivity;

    public BroadcastManager(FermatActivity fermatActivity) {
        this.fermatActivity = new WeakReference<FermatActivity>(fermatActivity);
    }

    public void setFermatActivity(FermatActivity fermatActivity) {
        this.fermatActivity = new WeakReference<FermatActivity>(fermatActivity);
    }

    public void stop(){
        fermatActivity.clear();
    }

    public void resume(FermatActivity fermatActivity) {
        this.fermatActivity = new WeakReference<FermatActivity>(fermatActivity);
    }


    @Override
    public void publish(BroadcasterType broadcasterType, String code) {
        try {
            switch (broadcasterType){
                case UPDATE_VIEW:
                    updateView(code);
                    break;
                case NOTIFICATION_SERVICE:
                    fermatActivity.get().notificateBroadcast(null,code);
                    break;
            }
        }catch (Exception e){
            Log.e(TAG, "Cant broadcast excepcion");
            e.printStackTrace();
        }
    }

    @Override
    public void publish(BroadcasterType broadcasterType, String appCode, String code) {
        try {
            switch (broadcasterType){
                case UPDATE_VIEW:
                    updateView(appCode,code);
                    break;
                case NOTIFICATION_SERVICE:
                    fermatActivity.get().notificateBroadcast(appCode,code);
                    break;
            }
        }catch (Exception e){
            Log.e(TAG, "Cant broadcast excepcion");
            e.printStackTrace();
        }
    }

    @Override
    public void publish(BroadcasterType broadcasterType, String code,Platforms platform) {
        try {
            switch (broadcasterType){
                case UPDATE_VIEW:
                    updateView(code);
                    break;
                case NOTIFICATION_SERVICE:
                    fermatActivity.get().notificateBroadcast(null,code);
                    break;
            }
        }catch (Exception e){
            Log.e(TAG,"Cant broadcast excepcion");
            e.printStackTrace();
        }
    }

    @Override
    public void publish(BroadcasterType broadcasterType, String code,FermatApps fermatApps) {
        try {
            switch (broadcasterType){
                case UPDATE_VIEW:
                    updateView(code);
                    break;
                case NOTIFICATION_SERVICE:
//                    String publicKey = fermatActivity.get().searchAppFromPlatformIdentifier(fermatApps);
//                    fermatActivity.get().notificateBroadcast(publicKey,code);
                    break;
            }
        }catch (Exception e){
            Log.e(TAG,"Cant broadcast excepcion");
            e.printStackTrace();
        }
    }

    private void updateView(String code){
        TabsPagerAdapter adapter = fermatActivity.get().getAdapter();
        if(adapter!=null) {
            for (AbstractFermatFragment fragment :adapter.getLstCurrentFragments()){
                fragment.onUpdateView(code);
                fragment.onUpdateViewUIThred(code);
            }
        }
    }

    //TODO: esto va a ser del codigo de la app, el paquete del intent
    private void updateView(String appCode,String code){
        TabsPagerAdapter adapter = fermatActivity.get().getAdapter();
        if(adapter!=null) {
            for (AbstractFermatFragment fragment :adapter.getLstCurrentFragments()){
                fragment.onUpdateViewHandler(appCode,code);
                fragment.onUpdateViewUIThred(code);
            }
        }
    }




}
