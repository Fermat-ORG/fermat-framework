package com.bitdubai.android_core.app.common.version_1.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.android_core.app.common.version_1.adapters.TabsPagerAdapter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 2016.05.03..
 */
public class NotificationReceiver extends BroadcastReceiver {

    public static final String INTENT_NAME = "NotificationReceiver";

    private final WeakReference<FermatActivity> weakReference;

    public NotificationReceiver(FermatActivity fermatActivity) {
        this.weakReference = new WeakReference<FermatActivity>(fermatActivity);
    }

    @Override
    public void onReceive(Context context, Intent intent) {


    }

    private void updateView(FermatBundle bundle) {
        if(weakReference.get() instanceof FermatActivity) {
            TabsPagerAdapter adapter = ((FermatActivity)weakReference.get()).getAdapter();
            if (adapter != null) {
                for (AbstractFermatFragment fragment : adapter.getLstCurrentFragments()) {
                    fragment.onUpdateView(bundle);
                    fragment.onUpdateViewUIThred(bundle);
                }
            }
        }
    }

    private void updateView(String code){
        if(weakReference.get() instanceof FermatActivity) {
            TabsPagerAdapter adapter = ((FermatActivity) weakReference.get()).getAdapter();
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
        if(weakReference.get() instanceof FermatActivity) {
            TabsPagerAdapter adapter = ((FermatActivity) weakReference.get()).getAdapter();
            if (adapter != null) {
                for (AbstractFermatFragment fragment : adapter.getLstCurrentFragments()) {
                    fragment.onUpdateViewHandler(appCode, code);
                    fragment.onUpdateView(code);
                    fragment.onUpdateViewUIThred(code);
                }
            }
        }
    }

    public void clear() {
        weakReference.clear();
    }
}
