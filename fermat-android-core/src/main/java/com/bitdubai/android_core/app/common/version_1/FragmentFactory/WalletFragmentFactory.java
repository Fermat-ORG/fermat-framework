package com.bitdubai.android_core.app.common.version_1.FragmentFactory;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.factory.IssuerWalletFragmentFactory;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.factory.WalletAssetUserFragmentFactory;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.factory.WalletRedeemPointFragmentFactory;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragment_factory.ReferenceWalletFragmentFactory;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragmentFactory.CryptoBrokerWalletFragmentFactory;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragmentFactory.CryptoCustomerWalletFragmentFactory;

//import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.factory.WalletAssetIssuerFragmentFactory;
//import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.factory.WalletAssetUserFragmentFactory;


/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public class WalletFragmentFactory {


    public static com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory getFragmentFactoryByWalletType(String walletCategory, String walletType, String walletPublicKey) {

        try {

            WalletCategory category = WalletCategory.getByCode(walletCategory);
            WalletType type = WalletType.getByCode(walletType);


            switch (category) {
                case REFERENCE_WALLET:
                    switch (type) {
                        case REFERENCE:
                            switch (walletPublicKey) {
                                case "reference_wallet":
                                    return new ReferenceWalletFragmentFactory();
                                case "test_wallet":
                                    return new com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.fragmentFactory.ReferenceWalletFragmentFactory();
                                case "crypto_broker_wallet":
                                    return new CryptoBrokerWalletFragmentFactory();
                                case "crypto_customer_wallet":
                                    return new CryptoCustomerWalletFragmentFactory();
                                case "redeem_point":
                                    return new WalletRedeemPointFragmentFactory();
                                case "asset_issuer":
                                    return new IssuerWalletFragmentFactory();
                                case "asset_user":
                                    return new WalletAssetUserFragmentFactory();
                                default:
                                    return new ReferenceWalletFragmentFactory();
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
