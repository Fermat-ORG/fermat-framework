package com.bitdubai.fermat_api.layer._16_module.wallet_manager;


import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer._16_module.wallet_manager.exceptions.OpenFailedException;

import java.util.UUID;

/**
 * Created by ciencias on 25.01.15.
 */
public interface Wallet {

    public void createWallet(WalletType walletType)  throws CantCreateWalletException;

    public void loadWallet  (UUID id) throws CantLoadWalletException;

    public UUID getId();

    public String getWalletName();

    public WalletType getWalletType();

    public WalletStatus getStatus();


    public void open() throws OpenFailedException;

}
