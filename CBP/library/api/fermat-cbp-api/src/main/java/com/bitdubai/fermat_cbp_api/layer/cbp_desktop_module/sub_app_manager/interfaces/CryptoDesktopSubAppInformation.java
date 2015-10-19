package com.bitdubai.fermat_cbp_api.layer.cbp_desktop_module.sub_app_manager.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.SubAppCategory;
import com.bitdubai.fermat_cbp_api.all_definition.enums.SubAppType;

import com.bitdubai.fermat_cbp_api.all_definition.util.Version;

import java.util.List;

/**
 * Created by natalia on 16/09/15.
 */
public interface CryptoDesktopSubAppInformation {

    /**
     * This method gives us the public key of the SubApp in this device. It is used as identifier of
     * the wallet
     *
     * @return the public key represented as a string
     */
    public String getSubAppCatalogueId();

    /**
     * This method gives us the SubApp name
     *
     * @return the name of the wallet
     */
    public String getSubAppName();

    /**
     * This method gives us the version of the wallet
     *
     * @return the version of the wallet
     */
    public Version getWalletVersion();

    /**
     * This method gives us the list of all the languages installed for this SubApp
     *
     * @return the saud list of languages
     */
    public List<CryptoSubAppInstalledLanguage> getLanguagesId();

    /**
     * This method gives us the list of all the skins installed for this wallet
     *
     * @return the saud list of skins
     */
    public List<CryptoSubAppInstalledSkin> getSkinsId();

    /**
     * This method tell us the category of the wallet
     *
     * @return the category of the wallet
     */
    public SubAppCategory getSubAppCategory();

    /**
     * This method gives us a codification of the SubApp identifier (the identifier is an enum that
     * registers the SubApp)
     *
     * @return an string that is result of the method getCode of an enum that can be inferred by the
     * SubAppCategory of the wallet.
     */
    public String getSubAppPlatformIdentifier();

    /**
     * This method gives us the name of the wallet icon used to identify the image in the SubApp resources plug-in
     *
     * @return the name of the said icon
     */
    public String getSubAppIcon();


    public SubAppType getSubAppType();
}



