package com.bitdubai.android_core.app.common.version_1.FragmentFactory;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
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
                            return null;
                    }
                default:
                    return null;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
