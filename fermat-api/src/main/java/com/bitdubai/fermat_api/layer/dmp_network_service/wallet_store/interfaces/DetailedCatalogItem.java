package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.NicheWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetLanguagesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetSkinsException;

import java.util.List;

/**
 * Created by ciencias on 7/17/15.
 */
public interface DetailedCatalogItem {

    public Version getVersion();

    /**
     * This method tells us the category of the wallet
     *
     * @return the wallet category
     */
    public WalletCategory getCategory();

    /**
     * This method finds the languages supported in the said wallet.
     *
     * @return the list of languages supported
     */
    public List<Language> getLanguages() throws CantGetLanguagesException;


    public List<Skin> getSkins() throws CantGetSkinsException;


    /**
     * This method gives us the niche wallet type
     *
     * @return the wallet type
     */
    public NicheWallet getWalletType();

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
