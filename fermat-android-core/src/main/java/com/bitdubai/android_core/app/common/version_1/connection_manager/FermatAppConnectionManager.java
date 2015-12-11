package com.bitdubai.android_core.app.common.version_1.connection_manager;


import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatAppConnection;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatFragmentFactory;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.factory.IssuerWalletFragmentFactory;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.factory.WalletAssetUserFragmentFactory;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.factory.WalletRedeemPointFragmentFactory;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.diagram.Platform;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection.BitcoinWalletFermatAppConnection;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragment_factory.ReferenceWalletFragmentFactory;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentFactory;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory.CryptoCustomerWalletFragmentFactory;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public class FermatAppConnectionManager {


    public FermatAppConnection getFermatAppConnection(String publicKey){
        FermatAppConnection fermatAppConnection = null;
        switch (publicKey){
            case "reference_wallet":
                fermatAppConnection = new BitcoinWalletFermatAppConnection();
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
        }
        return null;
    }


}
