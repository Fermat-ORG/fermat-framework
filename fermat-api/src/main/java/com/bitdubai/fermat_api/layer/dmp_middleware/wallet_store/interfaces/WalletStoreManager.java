package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.WalletInstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.exceptions.CantGetInstallationStatusException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.exceptions.CantSetInstallationStatusException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletCatalogue;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_factory.interfaces.WalletStoreManager</code>
 * indicates the functionality of a WalletStoreManager
 * <p/>
 *
 * This class will
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/07/15.
 * Modified by Ezequiel Postan - (ezequiel.postan@gmail.com)
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletStoreManager {

    /**
     *
     * @param walletCatalogId the wallet catalogue Id that we want to get information from
     * @return A <Code>WalletInformation</Code> interface that gives us the information we are looking for
     */
    public WalletInformation getWalletInformation(UUID walletCatalogId);

    /**
     * This method sets the wallet installation status to INSTALLING
     *
     * @param walletCatalogId
     * @throws CantSetInstallationStatusException
     */
    public void setStatusToInstalling(UUID walletCatalogId) throws CantSetInstallationStatusException;
}