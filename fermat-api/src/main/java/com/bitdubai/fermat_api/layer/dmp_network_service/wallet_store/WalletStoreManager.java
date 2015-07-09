package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletsCatalogueException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishWalletException;

import java.util.List;

/**
 * Created by loui on 18/02/15.
 */
public interface WalletStoreManager {
    public void publishWallet(WalletPublicationInformation walletPublicationInformation) throws CantPublishWalletException;
    public List<WalletPublicationInformation> searchWallet(WalletSearchParameters walletSearchParameters);
    public List<WalletPublicationInformation> getWalletCatalogue() throws CantGetWalletsCatalogueException;
}
