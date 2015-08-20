package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

/**
 * Created by Matias Furszyfer on 2015.08.19..
 */

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.SubAppSettingsManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * This interface provides the fragments and objects needed by the wallet runtime
 *
 * @author Matias Furszyfer
 */

public interface SubAppFragmentFactory {

    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code the reference used to identify the fragment
     * @return the fragment referenced
     */

    public android.app.Fragment getFragment(String code, SubAppsSession subAppsSession, SubAppSettingsManager subAppSettingsManager, SubAppResourcesProviderManager subAppResourcesProviderManager) throws FragmentNotFoundException;

}
