package com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWalletManager {

    public BitcoinWalletWallet loadWallet (String walletPublicKey) throws CantLoadWalletException;

    public void createWallet (String walletPublicKey) throws CantCreateWalletException;
}
