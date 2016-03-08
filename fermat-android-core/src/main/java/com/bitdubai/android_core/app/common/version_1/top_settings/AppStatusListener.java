package com.bitdubai.android_core.app.common.version_1.top_settings;

import android.widget.ImageButton;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.callback.AppStatusCallbackChanges;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 2016.02.10..
 */
public class AppStatusListener implements AppStatusCallbackChanges {

    private WeakReference<ImageButton> btn_fermat_apps_status;
    private WeakReference<FermatActivity> activityWeakReference;

    public AppStatusListener(FermatActivity activityWeakReference, ImageButton btn_fermat_apps_status) {
        this.activityWeakReference = new WeakReference<FermatActivity>(activityWeakReference);
        this.btn_fermat_apps_status = new WeakReference<ImageButton>(btn_fermat_apps_status);
    }

    @Override
    public void appSoftwareStatusChanges(AppsStatus appsStatus) {
        for (AbstractFermatFragment fragment : activityWeakReference.get().getScreenAdapter().getLstCurrentFragments()) {
            fragment.onUpdateViewUIThred(appsStatus.getCode());
        }
        switch (appsStatus){
            case RELEASE:
                btn_fermat_apps_status.get().setBackgroundResource(R.drawable.icon_relese);
                break;
            case BETA:
                btn_fermat_apps_status.get().setBackgroundResource(R.drawable.icons_beta);
                break;
            case ALPHA:
                btn_fermat_apps_status.get().setBackgroundResource(R.drawable.icons_alfa);
                break;
            case DEV:
                btn_fermat_apps_status.get().setBackgroundResource(R.drawable.icons_developer);
                break;
            default:
                btn_fermat_apps_status.get().setBackgroundResource(R.drawable.icon_relese);
                break;
        }
    }

    public void clear() {
        activityWeakReference.clear();
        btn_fermat_apps_status.clear();
    }
}
