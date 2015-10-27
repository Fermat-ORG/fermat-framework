package com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.location_system.DeviceLocation;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.DealsWithAssetVault;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantCreateActorAssetIssuerException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantGetGenesisAddressException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.developerUtils.AssetIssuerActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantAddPendingAssetIssuerException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantGetAssetIssuersListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.exceptions.CantInitializeAssetIssuerActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.structure.AssetIssuerActorDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Nerio on 09/09/15.
 */
//TODO TERMINAR DE IMPLEMENTAR
public class AssetActorIssuerPluginRoot implements ActorAssetIssuerManager, DealsWithErrors, DatabaseManagerForDevelopers, DealsWithAssetVault, DealsWithEvents, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, LogManagerForDevelopers, Plugin, Service, Serializable {

    AssetIssuerActorDao assetIssuerActorDao;
    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PluginDatabaseSystem pluginDatabaseSystem;
    /**
     * FileSystem Interface member variables.
     */
    PluginFileSystem pluginFileSystem;
    /**
     * Plugin Interface member variables.
     */
    UUID pluginId;
    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;
    /**
     * DealsWithEvents Interface member variables.
     */
    EventManager eventManager;
    /**
     * DealsWithAssetVault interface member variable
     */
    AssetVaultManager assetVaultManager;

    BlockchainNetworkType blockchainNetworkType;

    List<FermatEventListener> listenersAdded = new ArrayList<>();
    /**
     * DealsWithLogger interface member variable
     */

    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    @Override
    public void setEventManager(EventManager DealsWithEvents) {
        this.eventManager = DealsWithEvents;
    }

    /**
     * DealsWithErrors Interface implementation.
     */

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithPluginDatabaseSystem interface implementation.
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    /**
     * DealWithPluginFileSystem Interface implementation.
     */
    @Override
    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    @Override
    public void setAssetVaultManager(AssetVaultManager assetVaultManager) {
        this.assetVaultManager = assetVaultManager;
    }

    @Override
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.actor.asset.issuer.developer.bitdubai.version_1.ActorIssuerPluginRoot");
        /**
         * I return the values.
         */
        return returnedClasses;
    }

    @Override
    public void setLoggingLevelPerClass(Map<String, LogLevel> newLoggingLevel) {
        /**
         * Modify by Manuel on 25/07/2015
         * I will wrap all this method within a try, I need to catch any generic java Exception
         */
        try {

            /**
             * I will check the current values and update the LogLevel in those which is different
             */
            for (Map.Entry<String, LogLevel> pluginPair : newLoggingLevel.entrySet()) {
                /**
                 * if this path already exists in the Root.bewLoggingLevel I'll update the value, else, I will put as new
                 */
                if (AssetActorIssuerPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    AssetActorIssuerPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    AssetActorIssuerPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    AssetActorIssuerPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
//            FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + ActorIssuerPluginRoot.newLoggingLevel, "Check the cause");
//             this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            assetIssuerActorDao = new AssetIssuerActorDao(pluginDatabaseSystem, pluginFileSystem, pluginId);
            this.serviceStatus = ServiceStatus.STARTED;

            blockchainNetworkType = BlockchainNetworkType.REG_TEST;

            test();

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR);
        }
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
        return serviceStatus;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetIssuerActorDeveloperDatabaseFactory dbFactory = new AssetIssuerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        AssetIssuerActorDeveloperDatabaseFactory dbFactory = new AssetIssuerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            AssetIssuerActorDeveloperDatabaseFactory dbFactory = new AssetIssuerActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeAssetIssuerActorDatabaseException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return Collections.EMPTY_LIST;
    }

    public CryptoAddress getGenesisAddress() throws CantGetGenesisAddressException {
        try {
//            System.out.println("La BlockChain es: " + blockchainNetworkType);
            CryptoAddress genesisAddress = this.assetVaultManager.getNewAssetVaultCryptoAddress(this.blockchainNetworkType);
//            System.out.println("========================================================================");
//            System.out.println("Genesis Address Actor Asset User: " + genesisAddress.getAddress() + " Currency: " + genesisAddress.getCryptoCurrency());
            return genesisAddress;
        } catch (GetNewCryptoAddressException exception) {
            throw new CantGetGenesisAddressException(exception, "Requesting a genesis address", "Cannot get a new crypto address from asset vault");
        }
    }

    private void test() throws CantCreateActorAssetIssuerException {

        try {
            for (int i = 0; i < 10; i++) {
//                String assetIssuerActorIdentityToLinkPublicKey = i + UUID.randomUUID().toString();
                String assetIssuerActorPublicKey = i + UUID.randomUUID().toString();
                CryptoAddress genesisAddress = getGenesisAddress();

//                CryptoAddress cryptoAddress = new CryptoAddress(UUID.randomUUID().toString(), CryptoCurrency.BITCOIN);
//                DeviceLocation location = new DeviceLocation();
//                location.setLongitude(new Random().nextDouble());
//                location.setLatitude(new Random().nextDouble());
                Double locationLatitude = new Random().nextDouble();
                Double locationLongitude = new Random().nextDouble();
                String description = "Asset Issuer de Prueba";
                ConnectionState connectionState = ConnectionState.CONNECTED;

//                AssetIssuerActorRecord record = new AssetIssuerActorRecord("Issuer_" + i, assetIssuerActorPublicKey);
//                record.setDescription("Asset Issuer de Prueba");
//                record.setContactState(ConnectionState.CONNECTED);
//                record.setProfileImage(new byte[0]);
//                record.setCryptoAddress(cryptoAddress);
//                record.setLocation(location);
                AssetIssuerActorRecord record = new AssetIssuerActorRecord(assetIssuerActorPublicKey, "Thunder Issuer_" + new Random().nextInt(10),
                        connectionState, locationLatitude, locationLongitude, System.currentTimeMillis(),
                        new byte[0], description);
                if (i == 0) {
//                        assetIssuerActorDao.createNewAssetIssuer(assetIssuerActorIdentityToLinkPublicKey, record);
                    assetIssuerActorDao.createNewAssetIssuer(record);
                    ActorAssetIssuer actorAssetIssuer = this.assetIssuerActorDao.getActorAssetIssuer();

                    System.out.println("****************************************Actor Asset Issuer**********************************************");
                    System.out.println("Actor Asset PublicKey: " + actorAssetIssuer.getPublicKey());
                    System.out.println("Actor Asset Name: " + actorAssetIssuer.getName());
                    System.out.println("Actor Asset Description: " + actorAssetIssuer.getDescription());
                    System.out.println("********************************************************************************************************");
//                        record.setDescription("Asset Issuer de Prueba cuya información fue modificada.");
//                        record.setProfileImage(new byte[0]);
//                        record.setContactState(ConnectionState.DISCONNECTED_LOCALLY);
//                        record.setName("Modificación hecha por Víctor!");
//                        try {
//                            assetIssuerActorDao.updateAssetIssuer(record);
//                        } catch (CantUpdateAssetIssuerException | AssetIssuerNotFoundException e) {
//                            System.out.println("*******************************************************");
//                            System.out.println("ASSET ISSUER: Falló actualizando el record número: " + i);
//                            e.printStackTrace();
//                            System.out.println("*******************************************************");
//                        }
                }
                assetIssuerActorDao.createNewAssetIssuerRegistered(record);
            }
        } catch (CantAddPendingAssetIssuerException e) {
//                System.out.println("*******************************************************");
//                System.out.println("ASSET ISSUER: Falló creando el record número: " + i);
//                e.printStackTrace();
//                System.out.println("*******************************************************");
        } catch (Exception e) {
            throw new CantCreateActorAssetIssuerException("CAN'T ADD NEW ASSET ISSUER ACTOR", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * The method <code>getActorPublicKey</code> get All Information about Actor
     *
     * @throws CantGetAssetIssuerActorsException
     */
    @Override
    public ActorAssetIssuer getActorAssetIssuer() throws CantGetAssetIssuerActorsException {

        ActorAssetIssuer actorAssetIssuer;
        try {
            actorAssetIssuer = this.assetIssuerActorDao.getActorAssetIssuer();
        } catch (Exception e) {
            throw new CantGetAssetIssuerActorsException("", FermatException.wrapException(e), "There is a problem I can't identify.", null);
        }
        return actorAssetIssuer;
    }

    /**
     * The method <code>getAllAssetUserActorRegistered</code> get All Actors Registered in Actor Network Service
     * and used in Sub App Community
     *
     * @throws CantGetAssetIssuerActorsException
     */
    @Override
    public List<ActorAssetIssuer> getAllAssetIssuerActorRegistered() throws CantGetAssetIssuerActorsException {
        List<ActorAssetIssuer> list;
        try {
            list = this.assetIssuerActorDao.getAllAssetIssuerActorRegistered();
        } catch (CantGetAssetIssuersListException e) {
            throw new CantGetAssetIssuerActorsException("CAN'T GET ASSET ISSUER REGISTERED ACTOR", e, "", "");
        }
        return list;
    }

    /**
     * The method <code>getAllAssetIssuerActorConnected</code> receives All Actors with have CryptoAddress in BD
     *
     * @throws CantGetAssetIssuerActorsException
     */
    @Override
    public List<ActorAssetIssuer> getAllAssetIssuerActorConnected() throws CantGetAssetIssuerActorsException {
        List<ActorAssetIssuer> list; // Asset User Actor list.
        try {
            list = this.assetIssuerActorDao.getAllAssetIssuerActorConnected();
        } catch (CantGetAssetIssuersListException e) {
            throw new CantGetAssetIssuerActorsException("CAN'T GET ASSET USER ACTORS CONNECTED WITH CRYPTOADDRESS ", e, "", "");
        }
        return list;
    }

    /**
     * The method <code>createActorAssetUserFactory</code> create Actor in Actor Network Service
     *
     * @param assetIssuerActorPublicKey
     * @param assetIssuerActorName
     * @param assetIssuerActorprofileImage
     * @param assetIssuerActorlocation
     * @throws CantCreateActorAssetIssuerException
     */
    @Override
    public ActorAssetIssuer createActorAssetIssuerFactory(String assetIssuerActorPublicKey, String assetIssuerActorName, byte[] assetIssuerActorprofileImage, Location assetIssuerActorlocation) throws CantCreateActorAssetIssuerException {

        return null;
    }
}
