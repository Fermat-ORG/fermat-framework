package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletInstallationInformation;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface NewCatalogueItem {

    // Pensar si agregar una imagen y el formato

    public String getWalletName();

    public String getDeveloperPublicKey();

    public String getDeveloperName();

    public String getWalletVersion();

    public String getWalletDescription();

    public UUID getWalletCatalogId();

    public UUID getResourcesId();

    public UUID getNavigationStructureId();

}
