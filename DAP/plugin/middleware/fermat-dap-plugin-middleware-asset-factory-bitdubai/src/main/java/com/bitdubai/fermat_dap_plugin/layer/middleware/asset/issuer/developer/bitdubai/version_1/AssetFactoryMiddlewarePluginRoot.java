package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.developer.LogManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceDensity;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.enums.BlockchainNetworkType;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantSingMessageException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.enums.AssetBehavior;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantCreateEmptyAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantDeleteAsserFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantGetAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.exceptions.CantSaveAssetFactoryException;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactoryManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.DealsWithAssetIssuing;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.AssetFactoryMiddlewareManager;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssertFactoryMiddlewareDatabaseConstant;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssetFactoryMiddlewareDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.structure.database.AssetFactoryMiddlewareDeveloperFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.io.Console;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rodrigo on 9/7/15.
 */
public class AssetFactoryMiddlewarePluginRoot implements DealsWithAssetIssuing, AssetFactoryManager, LogManagerForDevelopers,  DealsWithErrors, DealsWithLogger, DealsWithEvents, Plugin, DatabaseManagerForDevelopers, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, Service {
    /**
     * DealsWithErrors interface member variables
     */
    com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager;

    /**
     * DealsWithDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithPluginFileSystem Interface member variables.
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    /**
     * DealsWithLogger interface member variable
     */
    LogManager logManager;

    /**
     * DealWithEvents Interface member variables.
     */
    EventManager eventManager;

    AssetIssuingManager assetIssuingManager;

    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    List<FermatEventListener> listenersAdded = new ArrayList<>();

    static Map<String, LogLevel> newLoggingLevel = new HashMap<>();

    AssetFactoryMiddlewareManager assetFactoryMiddlewareManager;

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
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
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void setAssetIssuingManager(AssetIssuingManager assetIssuingManager) throws CantSetObjectException {
        this.assetIssuingManager = assetIssuingManager;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    @Override
    public void setErrorManager(com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    @Override
    public void start() throws CantStartPluginException {
        assetFactoryMiddlewareManager = new AssetFactoryMiddlewareManager(errorManager, logManager, pluginDatabaseSystem, pluginFileSystem, pluginId);
        try {
            //System.out.println("******* Asset Factory Init, Open Database. ******");
            Database database = pluginDatabaseSystem.openDatabase(pluginId, AssertFactoryMiddlewareDatabaseConstant.DATABASE_NAME);
            //testAssetFactory();
            database.closeDatabase();
        }
        catch (CantOpenDatabaseException | DatabaseNotFoundException e)
        {
            try
            {
                AssetFactoryMiddlewareDatabaseFactory assetFactoryMiddlewareDatabaseFactory = new AssetFactoryMiddlewareDatabaseFactory(this.pluginDatabaseSystem);
                assetFactoryMiddlewareDatabaseFactory.createDatabase(this.pluginId, AssertFactoryMiddlewareDatabaseConstant.DATABASE_NAME);
            }
            catch(CantCreateDatabaseException cantCreateDatabaseException)
            {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_FACTORY, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            }catch (Exception exception) {
                throw new CantStartPluginException("Cannot start AssetFactoryMiddleware plugin.", FermatException.wrapException(exception), null, null);
            }
        }
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<>();
        returnedClasses.add("com/bitdubai/fermat_dap_plugin/layer/middleware/asset/issuer/developer/bitdubai/version_1/AssetFactoryMiddlewarePluginRoot");

        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * I will check the current values and update the LogLevel in those which is different
         */

        for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
            /**
             * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
             */
            if (AssetFactoryMiddlewarePluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                AssetFactoryMiddlewarePluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                AssetFactoryMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            } else {
                AssetFactoryMiddlewarePluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
            }
        }
    }

    public AssetFactory testAssetFactory()
    {
        try {
            java.util.Date date= new java.util.Date();
            System.out.println(new Timestamp(date.getTime()));
            AssetFactory assetFactory = assetFactoryMiddlewareManager.getNewAssetFactory();
/*            assetFactory.setPublicKey("ASD-125412541-BS-854");
            assetFactory.setDescription("Asset de Prueba");
            assetFactory.setAssetBehavior(AssetBehavior.RECUPERATION_BITCOINS);
            assetFactory.setAmount(1);
            assetFactory.setFee(1);
            assetFactory.setIsRedeemable(true);
            assetFactory.setName("Asset de Mcdonald - modificado");
            assetFactory.setCreationTimestamp(new Timestamp(date.getTime()));
            assetFactory.setExpirationDate(new Timestamp(date.getTime()));
            assetFactory.setLastModificationTimeststamp(new Timestamp(date.getTime()));
            assetFactory.setQuantity(100);
            assetFactory.setState(State.DRAFT);
            Resource resource = new Resource();
            resource.setId(UUID.randomUUID());
            resource.setName("Foto 1");
            resource.setFileName("imagen.png");
            resource.setResourceType(ResourceType.IMAGE);
            resource.setResourceDensity(ResourceDensity.HDPI);
            List<Resource> resources = new ArrayList<>();
            resources.add(resource);
            resource.setId(UUID.randomUUID());
            resource.setName("Foto 2");
            resource.setFileName("imagen2.png");
            resource.setResourceType(ResourceType.IMAGE);
            resource.setResourceDensity(ResourceDensity.HDPI);
            resources.add(resource);
            assetFactory.setResources(resources);
            IdentityAssetIssuer identityAssetIssuer = new IdentityAssetIssuer() {
                @Override
                public String getAlias() {
                    return "Franklin Marcano";
                }

                @Override
                public String getPublicKey() {
                    return "ASDS-10087982";
                }

                @Override
                public String createMessageSignature(String mensage) throws CantSingMessageException {
                    return "signature";
                }
            };
            assetFactory.setIdentityAssetIssuer(identityAssetIssuer);*/
            //assetFactoryMiddlewareManager.saveAssetFactory(assetFactory);
            assetFactory = assetFactoryMiddlewareManager.getAssetFactory("ASD-125412541-BS-854");
            //publishAsset(assetFactory);
            System.out.println("******* Metodo testAssetFactory. Franklin ******" + assetFactory + assetFactory.getName());
            return assetFactory;
        }catch (Exception e){
            System.out.println("******* Metodo testAssetFactory, Error. Franklin ******" );
            e.printStackTrace();
            return  null;
        }
    }

    @Override
    public AssetFactory getAssetFactoryByPublicKey(String assetPublicKey) throws CantGetAssetFactoryException {
        return assetFactoryMiddlewareManager.getAssetFactory(assetPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByIssuer(String issuerIdentityPublicKey) throws CantGetAssetFactoryException {
        //TODO:Modifcar este metodo ya que tenemos que buscar en la tabla del Identity, leer todo los registros asociados a el buscarlo en la tabla asset factory y devolver un objeto lleno del asset factory con todas sus propiedades
        return assetFactoryMiddlewareManager.getAssetFactoryByIssuer(issuerIdentityPublicKey);
    }

    @Override
    public List<AssetFactory> getAssetFactoryByState(State state) throws CantGetAssetFactoryException {
        return assetFactoryMiddlewareManager.getAssetFactoryByState(state);
    }

    @Override
    public List<AssetFactory> getAssetFactoryAll() throws CantGetAssetFactoryException {
        return assetFactoryMiddlewareManager.getAssetFactoryAll();
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
    public void removeAssetFactory(AssetFactory assetFactory) throws CantDeleteAsserFactoryException {

    }

    @Override
    public long getAvailableBalance(long amount) {
        return 0;
    }

    @Override
    public void publishAsset(final AssetFactory assetFactory, BlockchainNetworkType blockchainNetworkType) throws CantSaveAssetFactoryException{
        assetFactoryMiddlewareManager.publishAsset(assetFactory, blockchainNetworkType);
    }
}
