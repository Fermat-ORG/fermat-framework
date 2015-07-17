package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletsCatalogueException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishWalletInCatalogueException;

import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 18/02/15.
 */
public interface WalletStoreManager {
    public void publishWallet(NewCatalogueItem newCatalogueItem) throws CantPublishWalletInCatalogueException;
    public WalletCatalogue getWalletCatalogue() throws CantGetWalletsCatalogueException;
    public List<SkinPreview> getSkinsPreview(UUID walletCatalogueId);

    /**
     * Given a wallet id this method find the languages supported in the said wallet.
     *
     * @param walletCatalogueId the id of a wallet published in the catalogue
     * @return the list of languages supported
     */
    public List<String> getLanguages(UUID walletCatalogueId);

}
