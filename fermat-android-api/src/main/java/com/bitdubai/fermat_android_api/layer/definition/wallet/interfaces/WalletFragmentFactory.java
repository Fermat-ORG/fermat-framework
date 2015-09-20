package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.WalletSettingsManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesProviderManager;

/**
 * This interface provides the fragments and objects needed by the wallet runtime
 *
 * @author Matias Furszyfer
 */
public interface WalletFragmentFactory {

    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code the reference used to identify the fragment
     * @return the fragment referenced
     */
    public Fragment getFragment(String code,WalletSession walletSession,WalletResourcesProviderManager walletResourcesProviderManager) throws FragmentNotFoundException;
}
