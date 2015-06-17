package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantLoadWalletException;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWalletManager {
    public void loadWallet (UUID walletId) throws CantLoadWalletException;

    public void createWallet (UUID walletId) throws CantCreateWalletException;

    public BitcoinWallet getCurrentDiscountWallet();
}
