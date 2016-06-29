package org.fermat.fermat_dap_android_wallet_redeem_point.factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import org.fermat.fermat_dap_android_wallet_redeem_point.fragments.RedeemPointHistoryActivityFragment;
import org.fermat.fermat_dap_android_wallet_redeem_point.fragments.RedeemPointMainActivityFragment;
import org.fermat.fermat_dap_android_wallet_redeem_point.fragments.RedeemPointStadisticsActivityFragment;
import org.fermat.fermat_dap_android_wallet_redeem_point.fragments.SettingsFragment;
import org.fermat.fermat_dap_android_wallet_redeem_point.fragments.SettingsMainNetworkFragment;
import org.fermat.fermat_dap_android_wallet_redeem_point.fragments.SettingsNotificationsFragment;
import org.fermat.fermat_dap_android_wallet_redeem_point.sessions.RedeemPointSessionReferenceApp;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.fragments.RedeemHomeCardFragment;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.fragments.RedeemHomeHistoryFragment;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.fragments.RedeemPointDetailFragment;

/**
 * WalletRedeemPointFragmentFactory
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public class WalletRedeemPointFragmentFactory extends FermatFragmentFactory<RedeemPointSessionReferenceApp, WalletResourcesProviderManager, WalletRedeemPointFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(WalletRedeemPointFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == null) {
            throw createFragmentNotFoundException(null);
        }

        AbstractFermatFragment currentFragment = null;
        try {

            switch (fragment) {
                case DAP_WALLET_REDEEM_POINT_MAIN_ACTIVITY:
                    currentFragment = new RedeemPointMainActivityFragment();
//                    currentFragment = new RedeemHomeCardFragment();
                    //currentFragment = new RedeemHomeHistoryFragment();
                    break;
                case DAP_WALLET_REDEEM_POINT_HISTORY_ACTIVITY:
                    currentFragment = new RedeemPointHistoryActivityFragment();
                    //currentFragment = new RedeemHomeHistoryFragment();
                    break;
                case DAP_WALLET_REDEEM_POINT_STADISTICS_ACTIVITY:
                    currentFragment = new RedeemPointStadisticsActivityFragment();
                    break;
                case DAP_WALLET_REDEEM_POINT_DETAILS_USERS_TAB:
                    //currentFragment = new RedeemPointDetailFragment();
                    currentFragment = new RedeemPointDetailFragment();
                    break;
                case DAP_WALLET_REDEEM_POINT_DETAILS_TRANSACTIONS_TAB:
                    //currentFragment = new RedeemPointDetailTransactionsFragment();
                    currentFragment = new RedeemPointDetailFragment();
                    break;
                case DAP_WALLET_REDEEM_POINT_SETTINGS_MAIN_NETWORK:
                    currentFragment = new SettingsMainNetworkFragment();
                    break;
                case DAP_WALLET_REDEEM_POINT_ASSET_SETTINGS_ACTIVITY:
                    currentFragment = new SettingsFragment();
                    break;
                case DAP_WALLET_REDEEM_POINT_ASSET_SETTINGS_NOTIFICATIONS:
                    currentFragment = new SettingsNotificationsFragment();
                    break;
                case DAP_WALLET_REDEEM_POINT_MAIN_PENDING_TAB_FRAGMENT:
                    currentFragment = new RedeemHomeCardFragment();
                    break;
                case DAP_WALLET_REDEEM_POINT_MAIN_HISTORY_TAB_FRAGMENT:
                    currentFragment = new RedeemHomeHistoryFragment();
                    break;
                case DAP_WALLET_REDEEM_POINT_USER_DETAIL_FRAGMENT:
                    currentFragment = new RedeemPointDetailFragment();
                    break;
                default:
                    throw new FragmentNotFoundException("Fragment not found", new Exception(), fragment.getKey(), "Swith failed");
            }
        } catch (Exception e) {
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
