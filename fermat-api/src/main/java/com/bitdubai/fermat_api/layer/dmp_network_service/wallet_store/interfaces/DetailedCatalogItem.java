package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 7/17/15.
 */
public interface DetailedCatalogItem {

    /**
     * This method finds the languages supported in the said wallet.
     *
     * @return the list of languages supported
     */
    public List<String> getLanguages();

    /**
     * Given an Skin id this method returns the skin information
     *
     * @param skinId the id of the skin
     * @return The skin
     * @throws CantGetSkinException
     */
    public SkinPreview getSkin(UUID skinId) throws CantGetSkinException;

    /**
     * This method finds the lost of identifiers of the skins associated to the wallet
     *
     * @return the list of identifiers
     */
    public List<UUID> getSkinsList();

    /**
     * This method gives us a description of the wallet in the catalogue
     *
     * @return a description represented as a String
     */
    public String getWalletDescription();

    /**
     * This method gives us the version of the wallet
     *
     * @return the version of the wallet represented as a String
     */
    public String getWalletVersion();
}
