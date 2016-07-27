package com.bitdubai.android_core.app.common.version_1.top_settings;

import android.widget.ImageButton;

import com.bitdubai.android_core.app.FermatActivity;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.callback.AppStatusCallbackChanges;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by mati on 2016.02.10..
 */
public class AppStatusListener implements AppStatusCallbackChanges {

    private WeakReference<ImageButton> btn_fermat_apps_status;
    private WeakReference<FermatActivity> activityWeakReference;
    private WeakReference<FermatTextView> fermatTextViewWeakReference;

    public AppStatusListener(FermatActivity activityWeakReference, ImageButton btn_fermat_apps_status, FermatTextView subTextView) {
        this.activityWeakReference = new WeakReference<FermatActivity>(activityWeakReference);
        this.btn_fermat_apps_status = new WeakReference<ImageButton>(btn_fermat_apps_status);
        fermatTextViewWeakReference = new WeakReference<FermatTextView>(subTextView);
    }

    @Override
    public void appSoftwareStatusChanges(AppsStatus appsStatus) {
        activityWeakReference.get().setAppStatus(appsStatus);
        List<AbstractFermatFragmentInterface> list = activityWeakReference.get().getAdapter().getLstCurrentFragments();
        for (AbstractFermatFragmentInterface fragment : list) {
            try {
                fragment.onUpdateViewOnUIThread(appsStatus.getCode());
            } catch (Exception e) {

            }
        }
        switch (appsStatus) {
            case RELEASE:
                btn_fermat_apps_status.get().setBackgroundResource(R.drawable.app_filter_hdpi);
                break;
            case BETA:
                btn_fermat_apps_status.get().setBackgroundResource(R.drawable.beta_hdpi);
                break;
            case ALPHA:
                btn_fermat_apps_status.get().setBackgroundResource(R.drawable.icon_alpha_hdpi);
                break;
            case DEV:
                btn_fermat_apps_status.get().setBackgroundResource(R.drawable.icon_develop_hdpi);
                break;
            default:
                btn_fermat_apps_status.get().setBackgroundResource(R.drawable.icon_alpha_hdpi);
                break;
        }

        fermatTextViewWeakReference.get().setText(appsStatus.getCode());

    }

    public void clear() {
        activityWeakReference.clear();
        btn_fermat_apps_status.clear();
    }
}
