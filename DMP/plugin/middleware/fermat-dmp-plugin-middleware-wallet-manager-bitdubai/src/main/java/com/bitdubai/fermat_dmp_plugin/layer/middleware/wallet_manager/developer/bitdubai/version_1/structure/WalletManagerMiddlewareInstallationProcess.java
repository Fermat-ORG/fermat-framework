package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.exceptions.CantInstallWalletException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces.WalletInstallationProcess;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesInstalationManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantPersistWalletException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantPersistWalletLanguageException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.exceptions.CantPersistWalletSkinException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.database.WalletManagerMiddlewareDao;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.WalletManagerMiddlewareInstallationProcess</code>
 * is the implementation of WalletInstallationProcess.
 * <p>
 * <p>
 * Created by Natalia on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletManagerMiddlewareInstallationProcess implements WalletInstallationProcess {

    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;

    WalletResourcesInstalationManager walletResources;
    WalletCategory walletCategory;
    String walletPlatformIdentifier;
    InstallationStatus installationProgress;

    /**
     * Constructor
     */

    public WalletManagerMiddlewareInstallationProcess(WalletResourcesInstalationManager walletResources, WalletCategory walletCategory, String walletPlatformIdentifier, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
        this.walletResources = walletResources;
        this.walletCategory = walletCategory;
        this.walletPlatformIdentifier = walletPlatformIdentifier;
        this.pluginId = pluginId;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }
    /**
     * WalletInstallationProcess Interface implementation.
     */

    /**
     * This method gives us the progress of the current installation
     */
    @Override
    public InstallationStatus getInstallationProgress() { //enum
        return this.installationProgress;
    }


    /**
     * This method starts the wallet installation process
     */
    @Override
    public void startInstallation(WalletType walletType,
                                  String walletName,
                                  String walletPublicKey,
                                  String walletPrivateKey,
                                  String walletIconName,
                                  UUID walletCatalogueId,
                                  Version walletVersion,
                                  String screenSize,
                                  String screenDensity,
                                  UUID skinId,
                                  Version skinVersion,
                                  String skinName,
                                  String skinPreview,
                                  UUID languageId,
                                  Version languageVersion,
                                  Languages language,
                                  String languageLabel,
                                  String developerName,
                                  String navigationStructureVersion) throws CantInstallWalletException {
        try {
            /**
             * Start the installation process
             */
            installationProgress = InstallationStatus.INSTALLING;

            /**
             * Send wallet info to Wallet Resource
             */

            walletResources.installCompleteWallet(walletCategory.getCode(), walletType.getCode(), developerName, screenSize, screenDensity, skinName, language.value(), navigationStructureVersion);


            /**
             * Persist wallet info in database
             */
            WalletManagerMiddlewareDao walletManagerDao = new WalletManagerMiddlewareDao(this.pluginDatabaseSystem, pluginId);

            walletManagerDao.persistWallet(walletPublicKey, walletPrivateKey, walletCategory, walletName, walletIconName, walletPlatformIdentifier, walletCatalogueId, walletVersion,developerName);

            walletManagerDao.persistWalletSkin(walletCatalogueId,skinId,skinName,skinPreview, skinVersion);

            walletManagerDao.persistWalletLanguage( walletCatalogueId,languageId, language, languageLabel, languageVersion);
            /**
             * Set status installed
             */
            installationProgress = InstallationStatus.INSTALLED;
        }
        catch (CantExecuteDatabaseOperationException ex)
        {

            throw new CantInstallWalletException("ERROR INSTALLING WALLET",ex, "Wallet to install "+ walletPublicKey, "");
        }
        catch (CantPersistWalletSkinException ex)
        {

            throw new CantInstallWalletException("ERROR INSTALLING WALLET",ex, "Error Save Skin on DB ", "");
        }
        catch (CantPersistWalletLanguageException ex)
        {

            throw new CantInstallWalletException("ERROR INSTALLING WALLET",ex," Error Save language on DB ", "");
        }
        catch (CantPersistWalletException ex)
        {

            throw new CantInstallWalletException("ERROR INSTALLING WALLET",ex, "Error Save wallet on DB", "");
        }
        catch (Exception e)
        {
            throw new CantInstallWalletException("ERROR INSTALLING WALLET", e, "Wallet to install " + walletPublicKey, "");
        }
    }



}
