package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.FermatSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.SubAppSettings;

/**
 * Created by mati on 2015.11.21..
 */
public interface AppFragmentFactory<S extends AbstractFermatSession,J extends FermatSettings,R extends ResourceProviderManager> {

    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code the reference used to identify the fragment
     * @return the fragment referenced
     */
    public android.app.Fragment getFragment(String code, S AppsSession, J settingsManager,R resourceProviderManager) throws FragmentNotFoundException;

}
