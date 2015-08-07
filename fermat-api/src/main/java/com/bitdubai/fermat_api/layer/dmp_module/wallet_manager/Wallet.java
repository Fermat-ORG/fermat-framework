package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager;


import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.CantGetUserWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.exceptions.OpenFailedException;

import java.util.UUID;

/**
 * Created by ciencias on 25.01.15.
 */
public interface Wallet {

    public void createWallet(NicheWalletType nicheWalletType)  throws CantCreateWalletException;

    public void loadWallet  (UUID id) throws CantGetUserWalletException;

    public UUID getId();

    public String getWalletName();

    public NicheWalletType getNicheWalletType();

    public WalletStatus getStatus();

    public void open() throws OpenFailedException;

}
