package com.bitdubai.android_core.app.common.version_1.classes;

import android.util.Log;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapter;
import com.bitdubai.android_core.app.common.version_1.util.interfaces.BroadcasterInterface;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.FermatApps;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

import java.lang.ref.WeakReference;
import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2016.02.04..
 */
public class BroadcastManager implements BroadcasterInterface {

    private static final String TAG = "broadcaster-manager";
    WeakReference<FermatActivity> fermatActivity;
    private final UUID id;

    public BroadcastManager(FermatActivity fermatActivity) {
        this.fermatActivity = new WeakReference<FermatActivity>(fermatActivity);
        id = UUID.randomUUID();
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

    @Override
    public void publish(BroadcasterType broadcasterType, String appCode, FermatBundle bundle) {
        try {
            switch (broadcasterType){
                case UPDATE_VIEW:
                    updateView(bundle);
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

    @Override
    public int publish(BroadcasterType broadcasterType, FermatBundle bundle) {
        int id = 0;
        try {
            switch (broadcasterType){
                case UPDATE_VIEW:
                    updateView(bundle);
                    break;
                case NOTIFICATION_SERVICE:
                    fermatActivity.get().notificateBroadcast(null,bundle);
                    break;
                case NOTIFICATION_PROGRESS_SERVICE:
                    id = (fermatActivity.get()!=null)?fermatActivity.get().notificateProgressBroadcast(bundle):0;
                    break;
            }
        }catch (Exception e){
            Log.e(TAG,"Cant broadcast excepcion");
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    private void updateView(FermatBundle bundle) {
        if (fermatActivity.get() != null) {
            TabsPagerAdapter adapter = fermatActivity.get().getAdapter();
            if (adapter != null) {
                for (AbstractFermatFragment fragment : adapter.getLstCurrentFragments()) {
                    fragment.onUpdateView(bundle);
                    fragment.onUpdateViewUIThred(bundle);
                }
            }
        }
    }

    private void updateView(String code){
        if (fermatActivity.get() != null) {
            TabsPagerAdapter adapter = fermatActivity.get().getAdapter();
            if (adapter != null) {
                for (AbstractFermatFragment fragment : adapter.getLstCurrentFragments()) {
                    fragment.onUpdateView(code);
                    fragment.onUpdateViewUIThred(code);
                }
            }
        }
    }

    //TODO: esto va a ser del codigo de la app, el paquete del intent
    private void updateView(String appCode,String code){
        if (fermatActivity.get() != null) {
            TabsPagerAdapter adapter = fermatActivity.get().getAdapter();
            if (adapter != null) {
                for (AbstractFermatFragment fragment : adapter.getLstCurrentFragments()) {
                    fragment.onUpdateViewHandler(appCode, code);
                    fragment.onUpdateView(code);
                    fragment.onUpdateViewUIThred(code);
                }
            }
        }
    }




}
