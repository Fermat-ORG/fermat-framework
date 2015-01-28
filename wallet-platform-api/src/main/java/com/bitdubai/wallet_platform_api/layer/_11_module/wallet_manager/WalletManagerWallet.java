package com.bitdubai.wallet_platform_api.layer._11_module.wallet_manager;


import java.util.UUID;

/**
 * Created by ciencias on 25.01.15.
 */
public interface WalletManagerWallet {

    public void createWallet(WalletType walletType)  throws CantCreateWalletException;

    public void loadWallet  (UUID id) throws CantLoadWalletException;

    public UUID getId();

    public String getWalletName();

    public WalletType getWalletType();

    public WalletStatus getStatus();


    public void open() throws OpenFailedException;

}
