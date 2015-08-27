package com.bitdubai.fermat_android_api.layer.definition.wallet;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.WizardTypes;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Common Android Fragment Base
 *
 * @author Matias Furszy
 * @author Francisco Vasquez
 * @version 1.1
 */
public abstract class FermatFragment extends Fragment {

    /**
     * FLAGS
     */
    protected boolean isAttached;

    /**
     * Platform
     */
    protected SubAppsSession subAppsSession;
    protected SubAppSettings subAppSettings;
    protected SubAppResourcesProviderManager subAppResourcesProviderManager;

    /**
     * REFERENCES
     */
    protected WizardConfiguration context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = (WizardConfiguration) getActivity();
        } catch (Exception ex) {
            throw new ClassCastException("cannot convert the current context to FermatActivity");
        }
    }

    /**
     * Start a configuration Wizard
     *
     * @param key Enum Wizard registered type
     */
    protected void startWizard(WizardTypes key) {
        if (context != null && isAttached) {
            context.showWizard(key);
        }
    }

    /**
     * Dismiss active wizard configuration
     */
    protected void dismissWizard() {
        if (context != null && isAttached) {
            context.dismissWizard();
        }
    }

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

