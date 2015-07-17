package com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces_milestone2;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantExpandWalletInformationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantGetRefinedCatalogException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantStartInstallationException;

import java.util.UUID;

/**
 * This interface manage the presentation logic of the wallet store
 */
public interface WalletStoreModule {

    /**
     * This method initialize the installation presses of the wallet by giving the control to the
     * wallet manager.
     *
     * @param walletCatalogueId the wallet id in the catalogue
     * @throws CantStartInstallationException
     */
    public void installWallet(UUID walletCatalogueId) throws CantStartInstallationException;
    public WalletStoreCatalogue getCatalogue() throws CantGetRefinedCatalogException;
}
