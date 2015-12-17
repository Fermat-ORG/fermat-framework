package com.bitdubai.android_core.app.common.version_1.connection_manager;


import android.app.Activity;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatAppConnection;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection.BitcoinWalletFermatAppConnection;
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
            case "redeem_point":
//                fermatAppConnection = WalletRedeemPointFragmentFactory();
                break;
            case "asset_issuer":
//                fermatAppConnection = IssuerWalletFragmentFactory();
                break;
            case "asset_user":
//                fermatAppConnection = WalletAssetUserFragmentFactory();
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
