package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.factory;

import com.bitdubai.fermat_android_api.engine.FermatWalletFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.fragments.MainFragment;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions.RedeemPointSession;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.settings.RedeemPointSettings;

/**
 * WalletRedeemPointFragmentFactory
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public class WalletRedeemPointFragmentFactory extends FermatWalletFragmentFactory<RedeemPointSession, RedeemPointSettings, WalletRedeemPointFragmentsEnumType> {


    @Override
    public FermatWalletFragment getFermatFragment(WalletRedeemPointFragmentsEnumType fragments) throws FragmentNotFoundException {
        switch (fragments) {
            case DAP_WALLET_REDEEM_POINT_MAIN_ACTIVITY:
                return MainFragment.newInstance();
            default:
                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
                        new Exception(), "fermat-dap-android-wallet-asset-issuer", "fragment not found");
        }
    }

    @Override
    public WalletRedeemPointFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletRedeemPointFragmentsEnumType.getValue(key);
    }
}
