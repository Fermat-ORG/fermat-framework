package com.bitdubai.android_core.app.common.version_1.classes;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bitdubai.android_core.app.ApplicationSession;
import com.bitdubai.fermat_android_api.constants.ApplicationConstants;
import com.bitdubai.android_core.app.common.version_1.receivers.NotificationReceiver;
import com.bitdubai.android_core.app.common.version_1.receivers.UpdateViewReceiver;
import com.bitdubai.android_core.app.common.version_1.util.interfaces.BroadcasterInterface;
import com.bitdubai.fermat_api.layer.all_definition.enums.FermatApps;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

import java.util.UUID;

/**
 * Created by Matias Furszyfer on 2016.02.04..
 */
public class BroadcastManager implements BroadcasterInterface {

    private static final String TAG = "broadcaster-manager";
    private final UUID id;

    public BroadcastManager() {
        id = UUID.randomUUID();
    }

    @Override
    public void publish(BroadcasterType broadcasterType, String code) {
        try {
            switch (broadcasterType){
                case UPDATE_VIEW:
//                    updateView(code);
                    sendUpdateIntent(null,code,null);
                    break;
                case NOTIFICATION_SERVICE:
                    sendNotificationIntent(null,code,null);
                    //ApplicationSession.getInstance().getNotificationService().notificate(code, null);
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
//                    updateView(appCode,code);
                    sendUpdateIntent(appCode,code,null);
                    break;
                case NOTIFICATION_SERVICE:
                    sendNotificationIntent(appCode, code,null);
                    //ApplicationSession.getInstance().getNotificationService().notificate(code, appCode);
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
//                    updateView(code);
                    sendUpdateIntent(null,code,null);
                    break;
                case NOTIFICATION_SERVICE:
                    sendNotificationIntent(null,code,null);
//                    ApplicationSession.getInstance().getNotificationService().notificate(code, null);
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
//                    updateView(code);
                    sendUpdateIntent(null,code,null);
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
    public void publish(BroadcasterType broadcasterType, String appCode, FermatBundle fermatBundle) {
        try {
            switch (broadcasterType){
                case UPDATE_VIEW:
//                    updateView(bundle);
                    sendUpdateIntent(appCode,null,fermatBundle);
                    break;
                case NOTIFICATION_SERVICE:
                    sendNotificationIntent(appCode,null,fermatBundle);
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
//                    updateView(bundle);
                    sendUpdateIntent(null,null,bundle);
                    break;
                case NOTIFICATION_SERVICE:
//                    fermatActivity.get().notificateBroadcast(null,bundle);
                    break;
                case NOTIFICATION_PROGRESS_SERVICE:
//                    id = (fermatActivity.get()!=null)?fermatActivity.get().notificateProgressBroadcast(bundle):0;
                    break;
            }
        }catch (Exception e){
            Log.e(TAG,"Cant broadcast excepcion");
            e.printStackTrace();
        }
        return id;
    }


    private void sendUpdateIntent(String appPublicKey,@Nullable String code,@Nullable FermatBundle data){
        Intent intnet = new Intent(UpdateViewReceiver.INTENT_NAME);
        intnet.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY,appPublicKey);
        intnet.putExtra(ApplicationConstants.INTENT_EXTRA_DATA,code);
        intnet.putExtra(ApplicationConstants.INTENT_EXTRA_DATA_BUNDLE, data);
        ApplicationSession.getInstance().sendBroadcast(intnet);
    }

    private void sendNotificationIntent(String appPublicKey,@Nullable String code,@Nullable FermatBundle data) {
        Intent intnet = new Intent(NotificationReceiver.INTENT_NAME);
        intnet.putExtra(ApplicationConstants.INTENT_DESKTOP_APP_PUBLIC_KEY,appPublicKey);
        intnet.putExtra(ApplicationConstants.INTENT_EXTRA_DATA,code);
        intnet.putExtra(ApplicationConstants.INTENT_EXTRA_DATA_BUNDLE, data);
        ApplicationSession.getInstance().sendBroadcast(intnet);
    }


    @Override
    public UUID getId() {
        return id;

    }

//    private void updateView(FermatBundle bundle) {
//        if(fermatActivity.get() instanceof FermatActivity) {
//            TabsPagerAdapter adapter = ((FermatActivity)fermatActivity.get()).getAdapter();
//            if (adapter != null) {
//                for (AbstractFermatFragment fragment : adapter.getLstCurrentFragments()) {
//                    fragment.onUpdateView(bundle);
//                    fragment.onUpdateViewUIThred(bundle);
//                }
//            }
//        }
//    }
//
//    private void updateView(String code){
//        if(fermatActivity.get() instanceof FermatActivity) {
//            TabsPagerAdapter adapter = ((FermatActivity) fermatActivity.get()).getAdapter();
//            if (adapter != null) {
//                for (AbstractFermatFragment fragment : adapter.getLstCurrentFragments()) {
//                    fragment.onUpdateView(code);
//                    fragment.onUpdateViewUIThred(code);
//                }
//            }
//        }
//    }
//
//    //TODO: esto va a ser del codigo de la app, el paquete del intent
//    private void updateView(String appCode,String code){
//        if(fermatActivity.get() instanceof FermatActivity) {
//            TabsPagerAdapter adapter = ((FermatActivity) fermatActivity.get()).getAdapter();
//            if (adapter != null) {
//                for (AbstractFermatFragment fragment : adapter.getLstCurrentFragments()) {
//                    fragment.onUpdateViewHandler(appCode, code);
//                    fragment.onUpdateView(code);
//                    fragment.onUpdateViewUIThred(code);
//                }
//            }
//        }
//    }




}
