package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatWalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.fragments.MainFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.fragments.MyAssetsActivityFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.WalletSettings;



public class IssuerWalletFragmentFactory extends FermatWalletFragmentFactory<AssetIssuerSession, WalletSettings, WalletAssetIssuerFragmentsEnumType> {//implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory {


    @Override
    public FermatWalletFragment getFermatFragment(WalletAssetIssuerFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == null) {
            throw createFragmentNotFoundException(null);
        }

        FermatWalletFragment currentFragment = null;
        try {

            switch (fragment) {
                case DAP_WALLET_ASSET_ISSUER_MAIN_ACTIVITY:
                    currentFragment = new MyAssetsActivityFragment();
                    break;
                default:
                    throw new FragmentNotFoundException("Fragment not found", new Exception(), fragment.getKey(), "Swith failed");
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

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason = (fragments == null) ? "The parameter 'fragments' is NULL" : "Not found in switch block";
        String context = (fragments == null) ? "Null Value" : fragments.toString();

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }
}
