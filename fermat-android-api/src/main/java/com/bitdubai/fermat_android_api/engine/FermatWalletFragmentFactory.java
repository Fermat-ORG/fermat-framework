package com.bitdubai.fermat_android_api.engine;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

/**
 * Created by Matias Furszyfer on 2015.09.23..
 */
public abstract class FermatWalletFragmentFactory <S extends WalletSession,J extends WalletSettings,F extends FermatFragmentsEnumType> implements WalletFragmentFactory<S,J> {

    protected FermatWalletFragment fermatFragment;

    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code                           the reference used to identify the fragment
     * @param walletSession
     * @param walletSettings
     * @param walletResourcesProviderManager @return the fragment referenced
     */
    @Override
    public Fragment getFragment(String code, S walletSession, J walletSettings, WalletResourcesProviderManager walletResourcesProviderManager) throws FragmentNotFoundException {
        F fragments = getFermatFragmentEnumType(code);

        fermatFragment = getFermatFragment(fragments);

        fermatFragment.setWalletSession(walletSession);
        fermatFragment.setWalletSettings(walletSettings);
        fermatFragment.setWalletResourcesProviderManager(walletResourcesProviderManager);
        return fermatFragment;
    }

    public abstract FermatWalletFragment getFermatFragment(F fragments) throws FragmentNotFoundException;

    public abstract F getFermatFragmentEnumType(String key);

}
