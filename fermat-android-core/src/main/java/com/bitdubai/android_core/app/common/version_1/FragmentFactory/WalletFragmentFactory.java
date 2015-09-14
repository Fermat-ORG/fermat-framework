package com.bitdubai.android_core.app.common.version_1.FragmentFactory;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragmentFactory.ReferenceWalletFragmentFactory;


/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public class WalletFragmentFactory {


    public static com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory getFragmentFactoryByWalletType(String walletCategory,String walletType,String walletPublicKey) {

        try {

            WalletCategory category = WalletCategory.getByCode(walletCategory);
            WalletType type = WalletType.getByCode(walletType);

            switch (category) {
                case REFERENCE_WALLET:
                    switch (type) {
                        case REFERENCE:
                            return new ReferenceWalletFragmentFactory();
                        default:
                            throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + type, "This Code Is Not Valid for the Plugins enum");
                    }
                default:
                    throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + category, "This Code Is Not Valid for the Plugins enum");

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.err.println("Method: getFragmentFactoryByWalletType - TENGO RETURN NULL");
        return null;
    }
}
