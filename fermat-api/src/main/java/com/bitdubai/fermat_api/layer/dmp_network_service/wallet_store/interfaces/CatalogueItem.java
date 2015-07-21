package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletDetailsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface CatalogueItem {

    /**
     * This method gives us the size of the wallet if the user decides to install the default skin and language.
     *
     * @return the size that the resources and navigation structure would occupy
     */
    public int getDefaultSizeInBytes();

    /**
     *
     * @return the name of the developer of the wallet
     */
    public String getDeveloperName();

    /**
     *
     * @return the public key of the developer
     */
    public String getDeveloperPublicKey();

    /**
     * This method tells us the category of the wallet
     *
     * @return the wallet category
     */
    public WalletCategory getCategory();

    /**
     * This method downloads the icon of the wallet. <p>
     * The source of the icon is an specific file in the repository of the developer.
     *
     * @return the icon of the wallet.
     * @throws CantGetWalletIconException
     */
    public byte[] getWalletIcon() throws CantGetWalletIconException;

    /**
     *
     * @return the identifier of the wallet in the catalogue
     */
    public UUID getWalletCatalogId();

    /**
     *
     * @return the name of the wallet
     */
    public String getWalletName();

    /**
     *
     * @return more information about the wallet in the catalogue
     * @throws CantGetWalletDetailsException
     */
    public DetailedCatalogItem getDetailedCatalogItem() throws CantGetWalletDetailsException;

}
