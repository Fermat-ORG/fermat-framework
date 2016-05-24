package org.fermat.fermat_dap_android_wallet_asset_user.factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import org.fermat.fermat_dap_android_wallet_asset_user.fragments.AssetNegotiationDetailFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.fragments.AssetRedeemRedeemFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.fragments.AssetRedeemSelectRedeemPointsFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.fragments.AssetSellSelectUserFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.fragments.AssetTransferSelectUserFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.fragments.AssetTransferUserFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.fragments.SettingsFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.fragments.SettingsMainNetworkFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.fragments.SettingsNotificationsFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.fragments.UserHistoryActivityFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.fragments.UserSellAssetFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.AssetUserSession;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.fragments.DetailFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.fragments.HomeFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.fragments.RedeemPointsFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.v3.fragments.HomeCardFragment;
import org.fermat.fermat_dap_android_wallet_asset_user.v3.fragments.TransactionsFragment;

/**
 * Wallet Asset User Fragment Factory
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public class WalletAssetUserFragmentFactory extends FermatFragmentFactory<AssetUserSession,WalletResourcesProviderManager, WalletAssetUserFragmentsEnumType> {


    @Override
    public AbstractFermatFragment getFermatFragment(WalletAssetUserFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == null) {
            throw createFragmentNotFoundException(null);
        }

        AbstractFermatFragment currentFragment = null;
        try {

            switch (fragment) {
                case DAP_WALLET_ASSET_USER_MAIN_ACTIVITY:
//                    currentFragment = new UserMainActivityFragment();
                    break;
                case DAP_WALLET_ASSET_USER_HISTORY_ACTIVITY:
                    currentFragment = new UserHistoryActivityFragment();
                    break;
                case DAP_WALLET_ASSET_USER_ASSET_DETAIL_TRANSACTIONS:
                    currentFragment = TransactionsFragment.newInstance();
                    break;
                case DAP_WALLET_ASSET_USER_ASSET_REDEEM:
//                    currentFragment = new AssetRedeemFragment();
                    currentFragment = new AssetRedeemRedeemFragment();
                    break;
                case DAP_WALLET_ASSET_USER_ASSET_REDEEM_SELECT_REDEEMPOINTS:
                    currentFragment = new AssetRedeemSelectRedeemPointsFragment();
                    break;
                case DAP_WALLET_ASSET_USER_SETTINGS_ACTIVITY:
                    currentFragment = new SettingsFragment();
                    break;
                case DAP_WALLET_ASSET_USER_SETTINGS_MAIN_NETWORK:
                    currentFragment = new SettingsMainNetworkFragment();
                    break;
                case DAP_WALLET_ASSET_USER_SETTINGS_NOTIFICATIONS:
                    currentFragment = new SettingsNotificationsFragment();
                    break;
                case DAP_WALLET_ASSET_USER_ASSET_SELL_FRAGMENT:
//                    currentFragment = new AssetSellFragment();
                    currentFragment = UserSellAssetFragment.newInstance();
                    break;
                case DAP_WALLET_ASSET_USER_ASSET_SELL_SELECT_USERS_FRAGMENT:
                    currentFragment = new AssetSellSelectUserFragment();
                    break;
                case DAP_WALLET_ASSET_USER_ASSET_TRANSFER_FRAGMENT:
//                    currentFragment = new AssetTransferFragment();
                    currentFragment = new AssetTransferUserFragment();
                    break;
                case DAP_WALLET_ASSET_USER_ASSET_TRANSFER_SELECT_USERS_FRAGMENT:
                    currentFragment = new AssetTransferSelectUserFragment();
                    break;
                case DAP_WALLET_ASSET_USER_ASSET_NEGOTIATION_DETAIL_FRAGMENT:
                    currentFragment = new AssetNegotiationDetailFragment();
                    break;
                case DAP_WALLET_ASSET_USER_V2_HOME:
                    currentFragment = new HomeFragment();
                    break;
                case DAP_WALLET_ASSET_USER_V2_DETAIL:
                    currentFragment = new DetailFragment();
                    break;
                case DAP_WALLET_ASSET_USER_V2_REDEEM_POINTS:
                    currentFragment = new RedeemPointsFragment();
                    break;
                case DAP_WALLET_ASSET_USER_V3_HOME:
                    currentFragment = new HomeCardFragment();
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
    public WalletAssetUserFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletAssetUserFragmentsEnumType.getValue(key);
    }

    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason = (fragments == null) ? "The parameter 'fragments' is NULL" : "Not found in switch block";
        String context = (fragments == null) ? "Null Value" : fragments.toString();

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }
}
