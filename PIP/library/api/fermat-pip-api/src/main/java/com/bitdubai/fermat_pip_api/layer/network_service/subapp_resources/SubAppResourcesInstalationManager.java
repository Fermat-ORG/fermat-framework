package com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources;


import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.InstalationProgress;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantInstallCompleteSubAppResourcesException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantInstallSubAppLanguageException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantInstallSubAppSkinException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantUninstallCompleteSubAppException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantUninstallSubAppLanguageException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.exceptions.CantUninstallSubAppSkinException;

import java.util.UUID;

/**
 * Created by natalia on 2015.07.28..
 */
public interface SubAppResourcesInstalationManager extends FermatManager {


    void installCompleteSubApp(String subAppType, String developer, String screenSize, String skinName, String languageName, String navigationStructureVersion, String subAppPublickey) throws CantInstallCompleteSubAppResourcesException;


    /**
     * @param developer
     * @param screenSize
     * @param skinName
     * @param navigationStructureVersion
     * @throws CantInstallSubAppSkinException
     */
    void installSkinForSubApp(String subAppType, String developer, String screenSize, String skinName, String navigationStructureVersion, String subAppPublicKey) throws CantInstallSubAppSkinException;


    void installLanguageForSubApp(String subAppType, String developer, String screenSize, UUID skinId, String languageName, String subAppPublicKey) throws CantInstallSubAppLanguageException;


    //TODO: la wallet puede tener mas de un lenguage y skin, este metodo va a recibir el array de skins y language?

    void uninstallCompleteSubApp(String subAppType, String developer, String subAppName, UUID skinId, String screenSize, String navigationStructureVersion, boolean isLastWallet, String subAppPublicKey) throws CantUninstallCompleteSubAppException;


    void uninstallSkinForSubApp(UUID skinId, String subAppPublicKey) throws CantUninstallSubAppSkinException;


    /**
     * @param skinId
     * @param languageName
     * @param subAppPublicKey
     * @throws CantUninstallSubAppLanguageException
     */

    void uninstallLanguageForSubApp(UUID skinId, String languageName, String subAppPublicKey) throws CantUninstallSubAppLanguageException;


    /**
     * Get enum type of wallet instalation progress
     *
     * @return
     */
    InstalationProgress getInstallationProgress();
}
