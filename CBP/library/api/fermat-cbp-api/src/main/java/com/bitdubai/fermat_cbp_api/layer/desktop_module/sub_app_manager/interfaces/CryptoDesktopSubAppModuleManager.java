package com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.interfaces;


import com.bitdubai.fermat_api.layer.modules.interfaces.ModuleManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.SubAppCategory;
import com.bitdubai.fermat_cbp_api.all_definition.util.Version;
import com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.exceptions.CantListSubAppsException;
import com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.exceptions.CantStartSubAppInstallationException;
import com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.exceptions.CantStartSubAppLanguageInstallationException;
import com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.exceptions.CantStartSubAppSkinInstallationException;
import com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.exceptions.CantStartUninstallSubAppException;
import com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.exceptions.CantStartUninstallSubAppLanguageException;
import com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.exceptions.CantStartUninstallSubAppSkinException;

import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 16/09/15.
 */

/**
 * The interface <code>CryptoDesktopSubAppModuleManager</code>
 * provides the methods for the Crypto Broker SubApps Installed sub app.
 */
public interface CryptoDesktopSubAppModuleManager extends ModuleManager {

    /**
     * The method <code>getAllSubAppInstalled</code> returns the list of all crypto SubApp installed
     *
     * @return the list of crypto SubApp installed
     * @throws com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.exceptions.CantGetCryptoSubAppListException
     */
    List<CryptoDesktopSubAppInformation> getAllSubAppInstalled(int max, int offset) throws com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.exceptions.CantGetCryptoSubAppListException;


    /**
     * This method returns the list of installed SubApps in the device
     *
     * @return A list with the installed SubApps information
     */
    List<com.bitdubai.fermat_cbp_api.layer.desktop_module.sub_app_manager.interfaces.CryptoDesktopInstalledSubApp> getInstalledSubApps() throws CantListSubAppsException;

    /**
     * This method initialize the installation presses of the SubApp by giving the control to the
     * SubApp manager.
     *
     * @param subAppCategory    the category of the SubApp to install
     * @param skinId            the skin to install with the SubApp
     * @param languageId        the language to install in the SubApp
     * @param SubAppCatalogueId the SubApp identifier in the catalogue
     * @param version           the version of the SubApp to install
     * @throws CantStartSubAppInstallationException
     */
    void installSubApp(SubAppCategory subAppCategory,
                       UUID skinId,
                       UUID languageId,
                       UUID SubAppCatalogueId,
                       Version version) throws CantStartSubAppInstallationException;


    /**
     * This method initialize the installation presses of a skin by giving the control to the
     * SubApp manager.
     *
     * @param subAppCatalogueId the SubApp identifier where to install the skin in.
     * @param languageId        the language identifier
     * @throws CantStartSubAppLanguageInstallationException
     */
    void installLanguage(UUID subAppCatalogueId, UUID languageId) throws CantStartSubAppLanguageInstallationException;

    /**
     * This method initialize the installation presses of a skin by giving the control to the
     * SubApp manager.
     *
     * @param subAppCatalogueId the SubApp identifier where to install the skin in.
     * @param skinId            the skin identifier
     * @throws CantStartSubAppSkinInstallationException
     */
    void installSkin(UUID subAppCatalogueId, UUID skinId) throws CantStartSubAppSkinInstallationException;


    /**
     * This method initialize the uninstall presses of a skin by giving the control to the
     * SubApp manager.
     *
     * @param subAppCatalogueId the SubApp identifier where to uninstall the skin from.
     * @param languageId        the language identifier
     * @throws CantStartUninstallSubAppLanguageException
     */
    void uninstallLanguage(UUID subAppCatalogueId, UUID languageId) throws CantStartUninstallSubAppLanguageException;

    /**
     * This method initialize the uninstall presses of a skin by giving the control to the
     * SubApp manager.
     *
     * @param subAppCatalogueId the SubApp identifier where to uninstall the skin from.
     * @param skinId            the skin identifier
     * @throws CantStartUninstallSubAppSkinException
     */
    void uninstallSkin(UUID subAppCatalogueId, UUID skinId) throws CantStartUninstallSubAppSkinException;

    /**
     * This method initialize the uninstall presses of the SubApp by giving the control to the
     * SubApp manager.
     *
     * @param subAppCatalogueId the SubApp id in the catalogue
     * @throws CantStartUninstallSubAppException
     */
    void uninstallSubApp(UUID subAppCatalogueId) throws CantStartUninstallSubAppException;
}
