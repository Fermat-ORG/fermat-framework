package org.fermat.fermat_dap_android_wallet_asset_issuer.factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;

import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.AssetDeliveryFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.AssetDeliverySelectGroupsFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.AssetDeliverySelectUsersFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.AssetDetailTransactionsFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.MainActivityFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.MyAssetsActivityFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.SettingsAssetIssuerFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.SettingsAssetIssuerNetworkFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.SettingsAssetIssuerNotificationFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.UserAppropiateListFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.UserDeliveryListFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.fragments.UserRedeemedListFragment;
import org.fermat.fermat_dap_android_wallet_asset_issuer.sessions.AssetIssuerSession;


public class IssuerWalletFragmentFactory extends FermatFragmentFactory<AssetIssuerSession,WalletResourcesProviderManager, WalletAssetIssuerFragmentsEnumType> {//implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory {


    @Override
    public AbstractFermatFragment getFermatFragment(WalletAssetIssuerFragmentsEnumType fragment) throws FragmentNotFoundException {
        if (fragment == null) {
            throw createFragmentNotFoundException(null);
        }

        AbstractFermatFragment currentFragment = null;
        try {

            switch (fragment) {
                case DAP_WALLET_ASSET_ISSUER_MAIN_ACTIVITY:
                    currentFragment = new MyAssetsActivityFragment();
                    break;
                case DAP_WALLET_ASSET_ISSUER_MAIN_SETTINGS_ACTIVITY:
                    currentFragment = new SettingsAssetIssuerFragment();
                    break;
                case DAP_WALLET_ASSET_ISSUER_SETTINGS_MAIN_NETWORK_ACTIVITY:
                    currentFragment = new SettingsAssetIssuerNetworkFragment();
                    break;
                case DAP_WALLET_ASSET_ISSUER_SETTINGS_NOTIFICATIONS_ACTIVITY:
                    currentFragment = new SettingsAssetIssuerNotificationFragment();
                    break;
//                case DAP_WALLET_ASSET_ISSUER_HISTORY_ACTIVITY:
//                    currentFragment = new IssuerHistoryActivityFragment();
//                    break;
//                case DAP_WALLET_ASSET_ISSUER_STADISTICS_ACTIVITY:
//                    currentFragment = new IssuerStadisticsActivityFragment();
//                    break;
                case DAP_WALLET_ASSET_ISSUER_ASSET_DETAIL:
                    currentFragment = new AssetDetailTransactionsFragment();
                    break;
                case DAP_WALLET_ASSET_ISSUER_USER_DELIVERY_LIST:
                    currentFragment = new UserDeliveryListFragment();
                    break;
                case DAP_WALLET_ASSET_ISSUER_USER_REDEEMED_LIST:
                    currentFragment = new UserRedeemedListFragment();
                    break;
                case DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY:
                    currentFragment = new AssetDeliveryFragment();
                    break;
                case DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY_TAB_SELECT_USERS:
                    currentFragment = new AssetDeliverySelectUsersFragment();
                    break;
                case DAP_WALLET_ASSET_ISSUER_USER_APPROPIATE_LIST:
                    currentFragment = new UserAppropiateListFragment();
                    break;
                case DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY_TAB_SELECT_GROUPS:
                    currentFragment = new AssetDeliverySelectGroupsFragment();
                    break;
                case DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY_TABS:
                    currentFragment = new MainActivityFragment();
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
