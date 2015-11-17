package com.bitdubai.android_core.app.common.version_1.header_factory;

import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.factory.IssuerWalletFragmentFactory;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.factory.WalletAssetUserFragmentFactory;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.factory.WalletRedeemPointFragmentFactory;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.header.BitcoinWalletHeaderFactory;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragment_factory.ReferenceWalletFragmentFactory;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentFactory;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory.CryptoCustomerWalletFragmentFactory;

//import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.factory.WalletAssetIssuerFragmentFactory;
//import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.factory.WalletAssetUserFragmentFactory;


/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public class HeaderFactory {


    public static View getHeaderByWalletType(ViewGroup header,String walletCategory, String walletType, String walletPublicKey) {

        try {

            WalletCategory category = WalletCategory.getByCode(walletCategory);
            WalletType type = WalletType.getByCode(walletType);


            switch (category) {
                case REFERENCE_WALLET:
                    switch (type) {
                        case REFERENCE:
                            switch (walletPublicKey) {
                                case "reference_wallet":
                                     BitcoinWalletHeaderFactory bitcoinWalletHeaderFactory = new BitcoinWalletHeaderFactory(header);
                                    return bitcoinWalletHeaderFactory.obtainView();
                                default:
                                    return null;
                            }

                        default:
                            throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + type, "This Code Is Not Valid for the Plugins enum");
                    }
                default:
                    throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + category, "This Code Is Not Valid for the Plugins enum");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
