package org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantPublishAssetFactoy;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import org.fermat.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseConstant;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.database.AssetFactoryMiddlewareDeveloperFactory;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.events.AssetFactoryMiddlewareMonitorAgent;
import org.fermat.fermat_dap_plugin.layer.middleware.asset.issuer.developer.version_1.structure.functional.AssetFactoryMiddlewareManager;

import java.util.List;

/**
 * Created by rodrigo on 9/7/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.MEDIUM,
        maintainerMail = "marsvicam@gmail.com",
        createdBy = "acostarodrigo",
        layer = Layers.MIDDLEWARE,
        platform = Platforms.DIGITAL_ASSET_PLATFORM,
        plugin = Plugins.ASSET_FACTORY)
public class AssetFactoryMiddlewarePluginRoot extends AbstractPlugin implements
        AssetFactoryManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.MIDDLEWARE, plugin = Plugins.WALLET_MANAGER)
    private WalletManagerManager walletManagerManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ASSET_ISSUING)
    private AssetIssuingManager assetIssuingManager;

    @NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.IDENTITY, plugin = Plugins.ASSET_ISSUER)
    private IdentityAssetIssuerManager identityAssetIssuerManager;

    AssetFactoryMiddlewareManager assetFactoryMiddlewareManager;

    public static final String PATH_DIRECTORY = "assetFactory/resources";

    public AssetFactoryMiddlewarePluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private AssetFactoryMiddlewareMonitorAgent assetFactoryMiddlewareMonitorAgent;

    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the asset issuing plugin
     *
     * @throws CantGetLoggedInDeviceUserException
     * @throws CantSetObjectException
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantGetLoggedInDeviceUserException, CantSetObjectException, CantStartAgentException {
        if (assetFactoryMiddlewareMonitorAgent == null) {
            assetFactoryMiddlewareMonitorAgent = new AssetFactoryMiddlewareMonitorAgent(
                    assetFactoryMiddlewareManager,
                    assetIssuingManager,
                    this);
        }
        assetFactoryMiddlewareMonitorAgent.start();
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetFactoryMiddlewareDeveloperFactory assetFactoryMiddlewareDeveloperFactory = new AssetFactoryMiddlewareDeveloperFactory(pluginDatabaseSystem, pluginId);
        return assetFactoryMiddlewareDeveloperFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        AssetFactoryMiddlewareDeveloperFactory assetFactoryMiddlewareDeveloperFactory = new AssetFactoryMiddlewareDeveloperFactory(pluginDatabaseSystem, pluginId);
        return assetFactoryMiddlewareDeveloperFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        AssetFactoryMiddlewareDeveloperFactory assetFactoryMiddlewareDeveloperFactory = new AssetFactoryMiddlewareDeveloperFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            assetFactoryMiddlewareDeveloperFactory.initializeDatabase();
            developerDatabaseTableRecordList = assetFactoryMiddlewareDeveloperFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Asset Factory ******");
        }
        return developerDatabaseTableRecordList;
    }

    @Override
    public void start() throws CantStartPluginException {
        assetFactoryMiddlewareManager = new AssetFactoryMiddlewareManager(assetIssuingManager, pluginDatabaseSystem, pluginFileSystem, pluginId, walletManagerManager, identityAssetIssuerManager);
        try {
            pluginDatabaseSystem.openDatabase(pluginId, AssetFactoryMiddlewareDatabaseConstant.DATABASE_NAME);
        } catch (CantOpenDatabaseException | DatabaseNotFoundException ex) {
            try {
                AssetFactoryMiddlewareDatabaseFactory assetFactoryMiddlewareDatabaseFactory = new AssetFactoryMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
                assetFactoryMiddlewareDatabaseFactory.createDatabase(this.pluginId, AssetFactoryMiddlewareDatabaseConstant.DATABASE_NAME);
            } catch (CantCreateDatabaseException e) {
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantStartPluginException();
            } catch (Exception e) {
                reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantStartPluginException("Cannot start AssetFactoryMiddleware plugin.", FermatException.wrapException(e), null, null);
            }
        }
        try {
            startMonitorAgent();
        } catch (CantGetLoggedInDeviceUserException | CantSetObjectException | CantStartAgentException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException("Cannot start AssetFactoryMiddleware plugin.", FermatException.wrapException(e), null, "Cannot start the agent.");
        }
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public AssetFactory getAssetFactoryByPublicKey(String assetPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryMiddlewareManager.getAssetFactoryByAssetPublicKey(assetPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException, CantCreateFileException {
        //TODO:Modifcar este metodo ya que tenemos que buscar en la tabla del Identity, leer todo los registros asociados a el buscarlo en la tabla asset factory y devolver un objeto lleno del asset factory con todas sus propiedades
        return assetFactoryMiddlewareManager.getAssetFactoryByIssuer(issuerIdentityPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByState(State state, BlockchainNetworkType networkType) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryMiddlewareManager.getAssetFactoryByState(state, networkType);
    }

    @Override
    public List<AssetFactory> getAssetFactoryAll(BlockchainNetworkType networkType) throws CantGetAssetFactoryException, CantCreateFileException {
        return assetFactoryMiddlewareManager.getAssetFactoryAll(networkType);
    }

    @Override
    public byte[] getAssetFactoryResource(Resource resource) throws FileNotFoundException, CantCreateFileException, CantGetResourcesException {
        return assetFactoryMiddlewareManager.getAssetFactoryResource(resource);
    }

    @Override
    public AssetFactory createEmptyAssetFactory() throws CantCreateEmptyAssetFactoryException, CantCreateAssetFactoryException {
        return assetFactoryMiddlewareManager.getNewAssetFactory();
    }

    @Override
    public void saveAssetFactory(AssetFactory assetFactory) throws CantSaveAssetFactoryException, CantCreateFileException, CantPersistFileException {
        assetFactoryMiddlewareManager.saveAssetFactory(assetFactory);
    }

    @Override
    public void markAssetFactoryState(State state, String assetPublicKey) throws CantSaveAssetFactoryException, CantGetAssetFactoryException, CantCreateFileException, CantPersistFileException {
        assetFactoryMiddlewareManager.markAssetFactoryState(state, assetPublicKey);
    }

    @Override
    public void removeAssetFactory(String publicKey) throws CantDeleteAsserFactoryException {
        assetFactoryMiddlewareManager.removeAssetFactory(publicKey);
    }

    @Override
    public long getAvailableBalance(long amount) {
        //TODO:Implementar de la Crypto Vault
        return 0;
    }

    @Override
    public void publishAsset(final AssetFactory assetFactory) throws CantSaveAssetFactoryException {
        assetFactoryMiddlewareManager.publishAsset(assetFactory);
    }

    @Override
    public List<com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet> getInstallWallets() throws CantListWalletsException {
        return assetFactoryMiddlewareManager.getInstallWallets();
    }

    @Override
    public boolean isReadyToPublish(String assetPublicKey) throws CantPublishAssetFactoy {
        try {
            return assetFactoryMiddlewareManager.isReadyToPublish(assetPublicKey);
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantPublishAssetFactoy(e, "Cant Publish Asset Factory", "Asset Factory incomplete");
        }
    }

    @Override
    public List<IdentityAssetIssuer> getActiveIdentities() {
        try {
            return identityAssetIssuerManager.getIdentityAssetIssuersFromCurrentDeviceUser();
        } catch (Exception e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            e.printStackTrace();
        }
        return null;
    }


}
