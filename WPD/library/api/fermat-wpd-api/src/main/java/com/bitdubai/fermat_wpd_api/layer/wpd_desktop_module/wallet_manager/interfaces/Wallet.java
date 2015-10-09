package com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.enums.WalletModule;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantGetUserWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_desktop_module.wallet_manager.exceptions.OpenFailedException;

import java.util.UUID;

/**
 * Created by ciencias on 25.01.15.
 */
public interface Wallet {

    public void createWallet(WalletModule walletModule)  throws CantCreateWalletException;

    public void loadWallet  (UUID id) throws CantGetUserWalletException;

    public UUID getId();

    public String getWalletName();

    public WalletModule getWalletModule();

    public WalletStatus getStatus();

    public void open() throws OpenFailedException;

}
