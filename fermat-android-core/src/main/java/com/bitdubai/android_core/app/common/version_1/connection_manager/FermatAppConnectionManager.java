package com.bitdubai.android_core.app.common.version_1.connection_manager;


import android.app.Activity;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppConnections;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection.BitcoinWalletFermatAppConnection;
import com.bitdubai.sub_app.crypto_broker_community.app_connection.CryptoBrokerCommunityFermatAppConnection;
import com.bitdubai.sub_app.intra_user_community.app_connection.CryptoWalletUserCommunityFermatAppConnection;
import com.bitdubai.sub_app.intra_user_identity.app_connection.CryptoWalletUserFermatAppConnection;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class FermatAppConnectionManager {


    public static AppConnections getFermatAppConnection(String publicKey,Activity activity, IntraUserLoginIdentity intraUserLoginIdentity){

        switch (publicKey){
            case "reference_wallet":
                return new BitcoinWalletFermatAppConnection(activity,intraUserLoginIdentity);
            case "test_wallet":
                return null;//return new com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.fragmentFactory.ReferenceWalletFragmentFactory();
            case "crypto_broker_wallet":
                //fermatAppConnection = new CryptoBrokerWalletFragmentFactory();
                return null;
            case "crypto_customer_wallet":
//                fermatAppConnection = CryptoCustomerWalletFragmentFactory();
                return null;
            case "redeem_point":
//                fermatAppConnection = WalletRedeemPointFragmentFactory();
                return null;
            case "asset_issuer":
//                fermatAppConnection = IssuerWalletFragmentFactory();
                return null;
            case "asset_user":
//                fermatAppConnection = WalletAssetUserFragmentFactory();
                return null;
            case "public_key_ccp_intra_user_identity":
                return new CryptoWalletUserFermatAppConnection(activity);
            case "public_key_intra_user_commmunity":
                return new CryptoWalletUserCommunityFermatAppConnection(activity);
            case "sub_app_crypto_broker_community":
                return new CryptoBrokerCommunityFermatAppConnection(activity);
        }
        return null;
    }


}
