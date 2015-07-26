package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.exceptions.CantSetInstallationStatusException;

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

    public CatalogItemInformation getItemInformation(CatalogItems catalogItemType, UUID itemId);


    public void setCatalogItemInformation(CatalogItems catalogItemType, UUID itemId, CatalogItemInformation catalogItemInformation) throws CantSetInstallationStatusException;
}