package com.bitdubai.android_core.app.common.version_1.top_settings;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.callback.AppStatusCallbackChanges;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 2016.02.10..
 */
public class AppStatusListener implements AppStatusCallbackChanges {

    private WeakReference<FermatActivity> activityWeakReference;

    public AppStatusListener(FermatActivity activityWeakReference) {
        this.activityWeakReference = new WeakReference<FermatActivity>(activityWeakReference);
    }

    @Override
    public void appSoftwareStatusChanges(AppsStatus appsStatus) {
        for (AbstractFermatFragment fragment : activityWeakReference.get().getScreenAdapter().getLstCurrentFragments()) {
            fragment.onUpdateViewUIThred(appsStatus.getCode());
        }
    }

    public void clear() {
        activityWeakReference.clear();
    }
}
