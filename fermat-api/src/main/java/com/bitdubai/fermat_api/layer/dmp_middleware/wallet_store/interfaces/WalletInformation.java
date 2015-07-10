package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface WalletInformation {

    public UUID getWalletId();

    public String getWalletName();

    public String getDeveloperPublicKey();

    public String getDeveloperName();

    public String getWalletVersion();

    public String getWalletDescription();

    public UUID getResourcesId();

    public UUID getNavigationStructureId();

    public long getNumberOfDownloads();

    public boolean isInstalled();

}
