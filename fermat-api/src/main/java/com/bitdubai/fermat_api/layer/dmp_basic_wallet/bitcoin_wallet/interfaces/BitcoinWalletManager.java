package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common_exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common_exceptions.CantLoadWalletException;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWalletManager {

    public BitcoinWalletWallet loadWallet (String walletPublicKey) throws CantLoadWalletException;

    public void createWallet (String walletPublicKey) throws CantCreateWalletException;
}
