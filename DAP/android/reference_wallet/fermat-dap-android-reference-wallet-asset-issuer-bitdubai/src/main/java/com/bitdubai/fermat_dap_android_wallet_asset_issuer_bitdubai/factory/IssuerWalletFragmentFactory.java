package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatWalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.fragments.MainFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;



public class IssuerWalletFragmentFactory extends FermatWalletFragmentFactory<AssetIssuerSession, WalletSettings, WalletAssetIssuerFragmentsEnumType> {//implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory {


    @Override
    public FermatWalletFragment getFermatFragment(WalletAssetIssuerFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatWalletFragment currentFragment = null;
        try {

            switch (fragments) {
                case DAP_WALLET_ASSET_ISSUER_MAIN_ACTIVITY:
                    currentFragment = new MainFragment();
                    break;
                default:
                    throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.getKey(), "Swith failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return currentFragment;
    }

    @Override
    public WalletAssetIssuerFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletAssetIssuerFragmentsEnumType.getValue(key);
    }
}
