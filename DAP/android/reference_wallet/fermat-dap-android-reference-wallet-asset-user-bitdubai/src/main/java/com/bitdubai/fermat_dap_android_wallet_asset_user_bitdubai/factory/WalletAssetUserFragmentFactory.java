package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatWalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments.MainFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.settings.AssetUserSettings;

/**
 * Wallet Asset User Fragment Factory
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public class WalletAssetUserFragmentFactory extends FermatWalletFragmentFactory<AssetUserSession, AssetUserSettings, WalletAssetUserFragmentsEnumType> {


    @Override
    public FermatWalletFragment getFermatFragment(WalletAssetUserFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatWalletFragment fragment = null;
        switch (fragments) {
            case DAP_WALLET_ASSET_USER_MAIN_ACTIVITY:
                fragment = MainFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.getKey(), "Swith failed");
        }
        return fragment;
    }

    @Override
    public WalletAssetUserFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletAssetUserFragmentsEnumType.getValue(key);
    }
}
