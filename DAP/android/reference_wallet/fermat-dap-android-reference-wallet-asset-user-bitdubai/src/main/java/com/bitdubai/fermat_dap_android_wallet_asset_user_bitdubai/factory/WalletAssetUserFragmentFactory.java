//package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.factory;
//
//import android.app.Fragment;
//
//import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
//import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
//import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
//import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;
//import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesProviderManager;
//import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments.MainFragment;
//import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
//import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.settings.AssetUserSettings;
//
///**
// * WalletAssetIssuerFragmentFactory
// *
// * @author Francisco Vasquez on 15/09/15.
// * @version 1.0
// */
//public class WalletAssetUserFragmentFactory extends FermatSubAppFragmentFactory<AssetUserSession, AssetUserSettings, WalletAssetUserFragmentsEnumType> implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory{
//
//
//    @Override
//    public FermatFragment getFermatFragment(WalletAssetUserFragmentsEnumType fragments) throws FragmentNotFoundException {
//        switch (fragments) {
//            case DAP_WALLET_ASSET_USER_MAIN_ACTIVITY:
//                return MainFragment.newInstance();
//            default:
//                throw new FragmentNotFoundException(String.format("Fragment: %s not found", fragments.getKey()),
//                        new Exception(), "fermat-dap-android-wallet-asset-issuer", "fragment not found");
//        }
//    }
//
//    @Override
//    public WalletAssetUserFragmentsEnumType getFermatFragmentEnumType(String key) {
//        return WalletAssetUserFragmentsEnumType.getValue(key);
//    }
//
//    /**
//     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
//     *
//     * @param code                           the reference used to identify the fragment
//     * @param walletSession
//     * @param walletResourcesProviderManager @return the fragment referenced
//     */
//    @Override
//    public Fragment getFragment(String code, WalletSession walletSession, WalletResourcesProviderManager walletResourcesProviderManager) throws FragmentNotFoundException {
//        switch (code) {
//            case "DWUIMA":
//                return MainFragment.newInstance();
//            default:
//                throw new FragmentNotFoundException(String.format("Fragment: %s not found", code),
//                        new Exception(), "fermat-dap-android-wallet-asset-issuer", "fragment not found");
//        }
//    }
//}
