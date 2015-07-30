package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.CatalogItem;

import java.util.List;

/**
 * Created by rodrigo on 7/27/15.
 */
public class WalletCatalog implements com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.WalletCatalog {
    List<CatalogItem> catalogItems;
    int catalogSize;

    @Override
    public List<CatalogItem> getWalletCatalog(int offset, int top) throws CantGetWalletsCatalogException {
        return catalogItems;
    }

    @Override
    public int getCatalogSize() {
        return catalogSize;
    }


    @Override
    public void setCatalogSize(int catalogSize) {
        this.catalogSize = catalogSize;
    }

    public void setCatalogItems(List<com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.CatalogItem> catalogItemList) {

    }
}
