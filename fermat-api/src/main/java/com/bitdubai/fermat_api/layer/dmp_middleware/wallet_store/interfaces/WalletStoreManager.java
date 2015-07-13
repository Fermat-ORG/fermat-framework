package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.WalletInstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.exceptions.CantGetInstallationStatusException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletCatalogue;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletStoreManager</code>
 * indicates the functionality of a WalletStoreManager
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletStoreManager {
    public WalletInformation getWalletInformation(UUID walletCatalogId);

    public WalletInstallationStatus getInstallationStatus(UUID walletCatalogId) throws CantGetInstallationStatusException;

    // Marca a la wallet en estado INTALLING
    public void setWalletToInstalling(UUID walletCatalogId);

    // Lo llama el Wallet manager cuando culmina el proceso de instalación
    public void setWalletToInstalled(UUID walletCatalogId);
}