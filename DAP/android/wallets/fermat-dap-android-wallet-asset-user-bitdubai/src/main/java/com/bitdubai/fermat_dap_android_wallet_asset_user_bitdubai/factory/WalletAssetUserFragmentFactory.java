package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments.MainFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.settings.AssetUserSettings;

/**
 * WalletAssetIssuerFragmentFactory
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public class WalletAssetUserFragmentFactory extends FermatSubAppFragmentFactory<AssetUserSession, AssetUserSettings, WalletAssetUserFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(WalletAssetUserFragmentsEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case DAP_WALLET_ASSET_USER_MAIN_ACTIVITY:
                return MainFragment.newInstance();
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-dap-android-wallet-asset-issuer", "fragment not found");
        }
    }

    @Override
    public WalletAssetUserFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletAssetUserFragmentsEnumType.getValue(key);
    }
}
