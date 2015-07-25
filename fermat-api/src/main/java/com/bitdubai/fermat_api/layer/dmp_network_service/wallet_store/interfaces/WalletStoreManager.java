package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetCatalogItemException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishLanguageInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishSkinInCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishWalletInCatalogException;

import java.util.UUID;

/**
 * Created by loui on 18/02/15.
 */
public interface WalletStoreManager {
    public void publishWallet(CatalogItem catalogItem) throws CantPublishWalletInCatalogException;
    public void publishSkin(Skin skin) throws CantPublishSkinInCatalogException;
    public void publishLanguage(Language language) throws CantPublishLanguageInCatalogException;
    public void publishDesigner(Designer designer);
    public void publishTranslator (Translator translator);


    public WalletCatalog getWalletCatalogue() throws CantGetWalletsCatalogException;
    public CatalogItem getCatalogItem(UUID walletId) throws CantGetCatalogItemException;
    public DetailedCatalogItem getDetailedCatalogItem(UUID walletId) throws CantGetCatalogItemException;
    public Language getLanguage(UUID languageId);
    public Skin getSkin(UUID skinId);
}
