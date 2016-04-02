package com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions.CantGetExchangeProviderIdException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions.CantSaveExchangeProviderIdException;

import java.util.UUID;

/**
 * Created by eze on 2015.06.17..
 */
public interface BitcoinLossProtectedWalletManager extends FermatManager {

    BitcoinLossProtectedWallet loadWallet(String walletPublicKey) throws CantLoadWalletException;

    void createWallet(String walletPublicKey) throws CantCreateWalletException;

    UUID getExchangeProviderId() throws CantGetExchangeProviderIdException;

    void saveExchangeProviderIdFile(UUID providerId) throws CantSaveExchangeProviderIdException;
}
