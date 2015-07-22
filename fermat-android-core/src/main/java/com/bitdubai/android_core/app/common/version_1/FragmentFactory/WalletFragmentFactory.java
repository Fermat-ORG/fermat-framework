package com.bitdubai.android_core.app.common.version_1.FragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FragmentFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletFragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Fragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_engine.wallet_runtime.WalletRuntimeManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragmentFactory.Fragments;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public class WalletFragmentFactory {

    public WalletFragmentFactory(){}

    public FragmentFactory getFragmentFactoryByWalletType(String walletType) {
        switch (Wallets.getByCode(walletType)) {
            case CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI:
                return new com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragmentFactory.WalletFragmentFactory();
            default:
                return null;

        }
    }
}
