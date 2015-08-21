package com.bitdubai.android_core.app.common.version_1.FragmentFactory;

import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragmentFactory.ReferenceWalletFragmentFactory;


/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public class WalletFragmentFactory {


    public static com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletFragmentFactory getFragmentFactoryByWalletType(String walletType) {
        switch (walletType) {
            case "reference_wallet":
                return new ReferenceWalletFragmentFactory();
            default:
                return null;

        }
    }
}
