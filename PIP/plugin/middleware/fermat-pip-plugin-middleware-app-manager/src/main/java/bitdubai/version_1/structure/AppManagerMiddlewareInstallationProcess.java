package bitdubai.version_1.structure;


import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantInstallWalletException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletInstallationProcess;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesInstalationManager;

import java.util.UUID;
import java.util.logging.Logger;

import bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import bitdubai.version_1.exceptions.CantGetInstalledWalletsException;
import bitdubai.version_1.exceptions.CantPersistWalletException;
import bitdubai.version_1.exceptions.CantPersistWalletLanguageException;
import bitdubai.version_1.exceptions.CantPersistWalletSkinException;
import bitdubai.version_1.structure.database.AppManagerMiddlewareDao;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_manager.developer.bitdubai.version_1.structure.AppManagerMiddlewareInstallationProcess</code>
 * is the implementation of WalletInstallationProcess.
 * <p/>
 * <p/>
 * Created by Natalia on 21/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AppManagerMiddlewareInstallationProcess implements WalletInstallationProcess {

    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;

    WalletResourcesInstalationManager walletResources;
    WalletCategory walletCategory;
    String walletPlatformIdentifier;
    InstallationStatus installationProgress;

    /**
     * Constructor
     */
    public AppManagerMiddlewareInstallationProcess(WalletResourcesInstalationManager walletResources, WalletCategory walletCategory, String walletPlatformIdentifier, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId) {
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

    private boolean isWalletInstalled(UUID walletCatalogueId) throws CantExecuteDatabaseOperationException {
        //Logger LOG = Logger.getGlobal();
        try {
            AppManagerMiddlewareDao AppManagerDao = new AppManagerMiddlewareDao(this.pluginDatabaseSystem, pluginId);
            InstalledWallet installedWallet = AppManagerDao.getInstalledWalletByCatalogueId(walletCatalogueId);
            return installedWallet != null;
        } catch (CantExecuteDatabaseOperationException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Error checking if the wallet is installed", "Please, check the cause");
        } catch (CantGetInstalledWalletsException exception) {
            return false;
        }

    }

    /**
     * This method starts the wallet installation process
     */
    @Override
    public void startInstallation(WalletType walletType,
                                  String walletName,
                                  String walletPublicKey,
                                  String walletPrivateKey,
                                  String deviceUserPublicKey,
                                  String walletIconName,
                                  UUID walletCatalogueId,
                                  Version walletVersion,
                                  String screenSize,
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
        Logger LOG = Logger.getGlobal();
        try {
            /**
             * Start the installation process
             */
            installationProgress = InstallationStatus.INSTALLING;
            LOG.info("INSTALANDO:" + installationProgress);

            if (!isWalletInstalled(walletCatalogueId)) {
                /**
                 * Send wallet info to Wallet Resource
                 */
                //TODO: se necesita pasarle la public key de la wallet  instalar al resources
                LOG.info("MAP_WCAT:" + walletCategory.getCode());
                LOG.info("MAP_WTYP:" + walletType.getCode());
                LOG.info("MAP_DNAM:" + developerName);
                LOG.info("MAP_SSIZ:" + screenSize);
                LOG.info("MAP_SNAM:" + skinName);
                LOG.info("MAP_LVAL:" + language.getCode());
                LOG.info("MAP_NSV:" + navigationStructureVersion);
                LOG.info("MAP_WPK" + walletPublicKey);
                walletResources.installCompleteWallet(walletCategory.getCode(), walletType.getCode(), developerName, screenSize, skinName, language.getCode(), navigationStructureVersion, walletPublicKey);
                //TODO: erase this test line.
                //walletResources.installCompleteWallet("reference_wallet", "bitcoin_wallet", "bitDubai", "medium", "default", "en", "1.0.0","TestPublicKey");
                /**
                 * Persist wallet info in database
                 */
                AppManagerMiddlewareDao AppManagerDao = new AppManagerMiddlewareDao(this.pluginDatabaseSystem, pluginId);

                AppManagerDao.persistWallet(walletPublicKey, walletPrivateKey, deviceUserPublicKey, walletCategory, walletName, walletIconName, walletPlatformIdentifier, walletCatalogueId, walletVersion, developerName, screenSize, navigationStructureVersion, BlockchainNetworkType.getDefaultBlockchainNetworkType());

                AppManagerDao.persistWalletSkin(walletCatalogueId, skinId, skinName, skinPreview, skinVersion);

                AppManagerDao.persistWalletLanguage(walletCatalogueId, languageId, language, languageLabel, languageVersion);
                /**
                 * Set status installed
                 */
                installationProgress = InstallationStatus.INSTALLED;
                LOG.info("INSTALADO:" + installationProgress);

            }

            // TODO: Le tendria que pasar la wallet public key

        } catch (CantExecuteDatabaseOperationException ex) {
            installationProgress = InstallationStatus.NOT_INSTALLED;
            throw new CantInstallWalletException("ERROR INSTALLING WALLET", ex, "Wallet to install " + walletPublicKey, "");
        }
//        catch (WalletResourcesInstalationException ex)
//        {
//            installationProgress = InstallationStatus.NOT_INSTALLED;
//            throw new CantInstallWalletException("ERROR INSTALLING WALLET",ex, "Error Save Skin on DB ", "");
//        }
        catch (CantPersistWalletSkinException ex) {

            throw new CantInstallWalletException("ERROR INSTALLING WALLET", ex, "Error Save Skin on DB ", "");
        } catch (CantPersistWalletLanguageException ex) {

            throw new CantInstallWalletException("ERROR INSTALLING WALLET", ex, " Error Save language on DB ", "");
        } catch (CantPersistWalletException ex) {

            throw new CantInstallWalletException("ERROR INSTALLING WALLET", ex, "Error Save wallet on DB", "");
        } catch (Exception e) {
            LOG.info("MAP_EXCEPCION_GENERICA:" + e.toString() + "-" + e.getMessage());
            throw new CantInstallWalletException("ERROR INSTALLING WALLET", e, "Wallet to install " + walletPublicKey, "");
        }
    }
}
