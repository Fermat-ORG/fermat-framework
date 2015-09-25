package com.bitdubai.fermat_api.layer.ccp_module.wallet_factory.interfaces;

/**
 * Created by rodrigo on 8/23/15.
 */
public interface DealsWithModuleWalletFactory {

    WalletFactoryManager getWalletFactoryManager();

    void setWalletFactoryManager(WalletFactoryManager walletFactoryManager);
}
