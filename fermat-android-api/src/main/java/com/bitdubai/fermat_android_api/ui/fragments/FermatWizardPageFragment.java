package com.bitdubai.fermat_android_api.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardPageListener;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWizardActivity;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Wizard Page Fragment
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public abstract class FermatWizardPageFragment extends Fragment
        implements WizardPageListener {

    private final String TAG = "FermatWizardPage";

    /**
     * FLAGS
     */
    protected boolean isAttached;

    /*
    * PARENT REFERENCE
    */
    protected FermatWizardActivity mParent;

    /**
     * Platform
     */
    protected SubAppsSession subAppsSession;
    protected SubAppSettings subAppSettings;
    protected SubAppResourcesProviderManager subAppResourcesProviderManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mParent = (FermatWizardActivity) getActivity();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    /**
     * Setting up SubApp Session
     *
     * @param subAppsSession SubAppSession object
     */
    public void setSubAppsSession(SubAppsSession subAppsSession) {
        this.subAppsSession = subAppsSession;
    }

    /**
     * Setting up SubAppSettings
     *
     * @param subAppSettings SubAppSettings object
     */
    public void setSubAppSettings(SubAppSettings subAppSettings) {
        this.subAppSettings = subAppSettings;
    }

    /**
     * Setting up SubAppResourcesProviderManager
     *
     * @param subAppResourcesProviderManager SubAppResourcesProviderManager object
     */
    public void setSubAppResourcesProviderManager(SubAppResourcesProviderManager subAppResourcesProviderManager) {
        this.subAppResourcesProviderManager = subAppResourcesProviderManager;
    }
}
