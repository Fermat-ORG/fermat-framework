package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.exceptions.CantGetItemInformationException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.exceptions.CantSetInstallationStatusException;

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
public interface WalletStoreManager extends FermatManager {

    public InstallationStatus getInstallationStatus(CatalogItems catalogItemType, UUID itemId) throws CantGetItemInformationException;


    public void setInstallationStatus(CatalogItems catalogItemType, UUID itemId, InstallationStatus installationStatus) throws CantSetInstallationStatusException;
}