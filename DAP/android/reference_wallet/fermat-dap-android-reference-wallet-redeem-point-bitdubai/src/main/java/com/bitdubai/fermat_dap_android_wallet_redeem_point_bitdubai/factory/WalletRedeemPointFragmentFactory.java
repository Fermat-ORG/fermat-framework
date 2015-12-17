package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.fragments.MyAssetsActivityFragment;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions.RedeemPointSession;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.settings.RedeemPointSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

/**
 * WalletRedeemPointFragmentFactory
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public class WalletRedeemPointFragmentFactory extends FermatFragmentFactory<RedeemPointSession, RedeemPointSettings,WalletResourcesProviderManager, WalletRedeemPointFragmentsEnumType> {


    @Override
    public FermatWalletFragment getFermatFragment(WalletRedeemPointFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == null) {
            throw createFragmentNotFoundException(null);
        }

        FermatWalletFragment currentFragment = null;
        try {

            switch (fragment) {
                case DAP_WALLET_REDEEM_POINT_MAIN_ACTIVITY:
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
    public WalletRedeemPointFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletRedeemPointFragmentsEnumType.getValue(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason = (fragments == null) ? "The parameter 'fragments' is NULL" : "Not found in switch block";
        String context = (fragments == null) ? "Null Value" : fragments.toString();

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }
}
