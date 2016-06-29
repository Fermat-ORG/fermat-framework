package com.bitdubai.fermat_ccp_api.layer.basic_wallet.fermat_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions.CantGetExchangeProviderIdException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions.CantSaveExchangeProviderIdException;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface FermatWalletManager extends FermatManager {

    FermatWalletWallet loadWallet(String walletPublicKey) throws CantLoadWalletsException;

    void createWallet(String walletPublicKey) throws CantCreateWalletException;

    UUID getExchangeProviderId() throws CantGetExchangeProviderIdException;

    void saveExchangeProviderIdFile(UUID providerId) throws CantSaveExchangeProviderIdException;
}
