package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface WalletSearchParameters {

    public void setWalletName(String walletName);
    public String getWalletName();

    public void setDeveloperName(String developerName);
    public String getDeveloperName();

    public void setWalletVersion(String version);
    public String getWalletVersion();

    public void setWalletDescripción(String walletDescripción);
    public String getWalletDescripción();

}
