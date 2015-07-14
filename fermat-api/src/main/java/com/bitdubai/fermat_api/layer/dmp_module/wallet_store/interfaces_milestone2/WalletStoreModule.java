package com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces_milestone2;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantGetRefinedCatalogException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantStartInstallationException;

import java.util.UUID;

/**
 * This interface manage the presentation logic of the wallet store
 */
public interface WalletStoreModule {
    public void installWallet(UUID walletCatalogueId) throws CantStartInstallationException;
    public RefinedCatalogue getCatalogue() throws CantGetRefinedCatalogException;
}
