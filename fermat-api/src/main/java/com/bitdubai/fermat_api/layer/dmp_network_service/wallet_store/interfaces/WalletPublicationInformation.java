package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface WalletPublicationInformation {

    // Pensar si agregar una imagen y el formato

    public void setWalletId(UUID walletId);
    public UUID getWalletId();

    public void setWalletName(String walletName);
    public String getWalletName();

    public void setDeveloperPublicKey(String developerPublicKey);
    public String getDeveloperPublicKey();

    public void setDeveloperName(String developerName);
    public String getDeveloperName();

    public void setWalletVersion(String version);
    public String getWalletVersion();

    public void setWalletDescription(String walletDescription);
    public String getWalletDescription();

    public void setResourcesId(UUID resourcesId);
    public UUID getResourcesId();

    public void setNavigationStructureId(UUID navigationStructureId);
    public UUID getNavigationStructureId();
}
