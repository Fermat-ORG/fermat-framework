package com.bitdubai.android_core.app.common.version_1.connection_manager;


import android.app.Activity;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatAppConnection;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.app_connection.AssetFactoryFermatAppConnection;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.app_connection.CommunityAssetIssuerFermatAppConnection;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.app_connection.AssetIssuerFermatAppConnection;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.app_connection.CommunityAssetUserFermatAppConnection;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_identity_bitdubai.app_connection.AssetUserFermatAppConnection;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.app_connection.CommunityRedeemPointFermatAppConnection;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_identity_bitdubai.app_connection.RedeemPointFermatAppConnection;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection.BitcoinWalletFermatAppConnection;
import com.bitdubai.sub_app.developer.app_connection.DeveloperFermatAppConnection;
import com.bitdubai.sub_app.intra_user_community.app_connection.CryptoWalletUserCommunityFermatAppConnection;
import com.bitdubai.sub_app.intra_user_identity.app_connection.CryptoWalletUserFermatAppConnection;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class FermatAppConnectionManager {


    public static AppConnections getFermatAppConnection(String publicKey,Activity activity, IntraUserLoginIdentity intraUserLoginIdentity){
        AppConnections fermatAppConnection = null;
        switch (publicKey){
            case "reference_wallet":
                fermatAppConnection = new BitcoinWalletFermatAppConnection(activity,intraUserLoginIdentity);
                break;
            case "test_wallet":
                fermatAppConnection = null;//return new com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.fragmentFactory.ReferenceWalletFragmentFactory();
            case "crypto_broker_wallet":
                //fermatAppConnection = new CryptoBrokerWalletFragmentFactory();
                break;
            case "crypto_customer_wallet":
//                fermatAppConnection = CryptoCustomerWalletFragmentFactory();
                break;
            case "public_key_pip_developer_sub_app":
                fermatAppConnection = new DeveloperFermatAppConnection(activity);
                break;
            case "public_key_dap_asset_issuer_identity":
                fermatAppConnection = new AssetIssuerFermatAppConnection(activity);
                break;
            case "public_key_dap_asset_user_identity":
                fermatAppConnection = new AssetUserFermatAppConnection(activity);
                break;
            case "public_key_dap_redeem_point_identity":
                fermatAppConnection = new RedeemPointFermatAppConnection(activity);
                break;
            case "public_key_dap_factory":
                fermatAppConnection = new AssetFactoryFermatAppConnection(activity);
                break;
            case "public_key_dap_issuer_community":
                fermatAppConnection = new CommunityAssetIssuerFermatAppConnection(activity);
                break;
            case "public_key_dap_user_community":
                fermatAppConnection = new CommunityAssetUserFermatAppConnection(activity);
                break;
            case "public_key_dap_reedem_point_community":
                fermatAppConnection = new CommunityRedeemPointFermatAppConnection(activity);
                break;
            case "public_key_ccp_intra_user_identity":
                fermatAppConnection = new CryptoWalletUserFermatAppConnection(activity);
                break;
            case "public_key_intra_user_commmunity":
                fermatAppConnection = new CryptoWalletUserCommunityFermatAppConnection(activity);
                break;
        }
        return fermatAppConnection;
    }


}
