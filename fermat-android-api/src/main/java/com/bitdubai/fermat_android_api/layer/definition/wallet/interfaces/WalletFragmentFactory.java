package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

/**
 * This interface provides the fragments and objects needed by the wallet runtime
 *
 * @author Matias Furszyfer
 */
public interface WalletFragmentFactory<S extends WalletSession,J extends WalletSettings> {

    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code the reference used to identify the fragment
     * @return the fragment referenced
     */
    public Fragment getFragment(String code,S walletSession,J WalletSettings,WalletResourcesProviderManager walletResourcesProviderManager) throws FragmentNotFoundException;
}
