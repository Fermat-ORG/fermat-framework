package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.factory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
import com.bitdubai.fermat_api.layer.ccp_network_service.wallet_resources.WalletResourcesProviderManager;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.fragments.MainFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.settings.AssetIssuerSettings;

/**
 * WalletAssetIssuerFragmentFactory
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public class WalletAssetIssuerFragmentFactory extends FermatSubAppFragmentFactory<AssetIssuerSession, AssetIssuerSettings, WalletAssetIssuerFragmentsEnumType> implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory {


    @Override
    public FermatFragment getFermatFragment(WalletAssetIssuerFragmentsEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case DAP_WALLET_ASSET_ISSUER_MAIN_ACTIVITY:
                return MainFragment.newInstance();
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-dap-android-wallet-asset-issuer", "fragment not found");
        }
    }

    @Override
    public WalletAssetIssuerFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletAssetIssuerFragmentsEnumType.getValue(key);
    }

    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code                           the reference used to identify the fragment
     * @param walletSession
     * @param walletResourcesProviderManager @return the fragment referenced
     */
    @Override
    public Fragment getFragment(String code, WalletSession walletSession, WalletResourcesProviderManager walletResourcesProviderManager) throws FragmentNotFoundException {
        switch (code) {
            case "DWAIMA":
                return MainFragment.newInstance();
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", code),
                        new Exception(), "fermat-dap-android-wallet-asset-issuer", "fragment not found");
        }
    }
}
