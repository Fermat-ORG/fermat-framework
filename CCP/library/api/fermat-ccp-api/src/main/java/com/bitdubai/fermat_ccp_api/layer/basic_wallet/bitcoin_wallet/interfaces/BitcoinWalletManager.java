package com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCreateWalletException;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinWalletManager extends FermatManager {

    BitcoinWalletWallet loadWallet(String walletPublicKey) throws CantLoadWalletsException;

    void createWallet(String walletPublicKey) throws CantCreateWalletException;
}
