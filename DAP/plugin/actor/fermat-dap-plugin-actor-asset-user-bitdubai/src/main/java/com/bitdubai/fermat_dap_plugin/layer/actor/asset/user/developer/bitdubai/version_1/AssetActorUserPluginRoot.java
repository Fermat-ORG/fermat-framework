package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.DealsWithAssetVault;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.DealsWithCryptoAddressBook;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorNetworkServiceAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRegisterActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.RequestedListNotReadyRecevivedException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.DealsWithAssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantGetGenesisAddressException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.developerUtils.AssetUserActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers.AssetUserActorCompleteRegistrationNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.event_handlers.AssetUserActorRequestListRegisteredNetworksNotificationEventHandler;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantAddPendingAssetUserException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantGetAssetUsersListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantInitializeAssetUserActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.monitorAgent.AssetUserActorMonitorAgent;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.structure.AssetUserActorDao;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.structure.AssetUserActorRecord;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DealsWithDeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Nerio on 09/09/15.
 */
//TODO TERMINAR DE IMPLEMENTAR
public class AssetActorUserPluginRoot implements ActorAssetUserManager, ActorNetworkServiceAssetUser, DatabaseManagerForDevelopers, DealsWithAssetVault, DealsWithCryptoAddressBook, DealsWithDeviceUser, DealsWithErrors, DealsWithEvents, DealsWithAssetUserActorNetworkServiceManager, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, LogManagerForDevelopers, Plugin, Service, Serializable {

    private AssetUserActorDao assetUserActorDao;
    private AssetUserActorMonitorAgent assetUserActorMonitorAgent;
    DeviceUserManager deviceUserManager;
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

    List<FermatEventListener> listenersAdded = new ArrayList<>();
    /**
     * DealsWithLogger interface member variable
     */
    static Map<String, LogLevel> newLoggingLevel = new HashMap<String, LogLevel>();

    BlockchainNetworkType blockchainNetworkType;

    /**
     * DealsWithIntraWalletUsersNetworkService interface member variable
     */
    AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager;

    /**
     * DealsWithAssetVault interface member variable
     */
    AssetVaultManager assetVaultManager;

    /**
     * DealsWithCryptoAddressBook interface member variable
     */
    CryptoAddressBookManager cryptoAddressBookManager;

    /**
     * DealsWithIntraWalletUsersNetworkService Interface implementation.
     */
    @Override
    public void setAssetUserActorNetworkServiceManager(AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager) {
        this.assetUserActorNetworkServiceManager = assetUserActorNetworkServiceManager;
    }

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
    public void setCryptoAddressBookManager(CryptoAddressBookManager cryptoAddressBookManager) {
        this.cryptoAddressBookManager = cryptoAddressBookManager;
    }

    @Override
    public void setDeviceUserManager(DeviceUserManager deviceUserManager) {
        this.deviceUserManager = deviceUserManager;
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
                if (AssetActorUserPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    AssetActorUserPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    AssetActorUserPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    AssetActorUserPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                }
            }

        } catch (Exception exception) {
            //FermatException e = new CantGetLogTool(CantGetLogTool.DEFAULT_MESSAGE, FermatException.wrapException(exception), "setLoggingLevelPerClass: " + ActorIssuerPluginRoot.newLoggingLevel, "Check the cause");
            // this.errorManager.reportUnexpectedAddonsException(Addons.EXTRA_USER, UnexpectedAddonsExceptionSeverity.DISABLES_THIS_ADDONS, e);
        }
    }

    @Override
    public void start() throws CantStartPluginException {
        try {
            /**
             * I created instance of AssetUserActorDao
             * and initialize Database
             */
            this.assetUserActorDao = new AssetUserActorDao(this.pluginDatabaseSystem, this.pluginFileSystem, this.pluginId);

            this.assetUserActorDao.initializeDatabase();
            /**
             * I will initialize the handling of com.bitdubai.platform events.
             */
            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;

            /**
             * Listener Accepted connection event
             */
            fermatEventListener = eventManager.getNewListener(EventType.COMPLETE_ASSET_USER_REGISTRATION_NOTIFICATION);
            fermatEventListener.setEventHandler(new AssetUserActorCompleteRegistrationNotificationEventHandler(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

            fermatEventListener = eventManager.getNewListener(EventType.COMPLETE_REQUEST_LIST_ASSET_USER_REGISTERED_NOTIFICATION);
            fermatEventListener.setEventHandler(new AssetUserActorRequestListRegisteredNetworksNotificationEventHandler(this));
            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

//            fermatEventListener = eventManager.getNewListener(EventType.ASSET_USER_CONNECTION_ACCEPTED);
//            fermatEventHandler = new AssetUserActorConnectionAcceptedEventHandlers();
//            ((AssetUserActorConnectionAcceptedEventHandlers) fermatEventHandler).setActorAssetUserManager(this);
//            ((AssetUserActorConnectionAcceptedEventHandlers) fermatEventHandler).setEventManager(eventManager);
//            ((AssetUserActorConnectionAcceptedEventHandlers) fermatEventHandler).setAssetUserActorNetworkServiceManager(this.assetUserActorNetworkServiceManager);
//            fermatEventListener.setEventHandler(fermatEventHandler);
//            eventManager.addListener(fermatEventListener);
//            listenersAdded.add(fermatEventListener);

            this.serviceStatus = ServiceStatus.STARTED;

            blockchainNetworkType = BlockchainNetworkType.REG_TEST;

            //test();

            /**
             * Agent for Search Actor Asset User REGISTERED in Actor Network Service User
             */
            startMonitorAgent();

//            registerActorInANS();
//            getAllAssetUserActorConnected();

//            CryptoAddress genesisAddress = obtenerGenesisAddress();
//            registerGenesisAddressInCryptoAddressBook(genesisAddress);

//            testRaiseEvent();

        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR);
        }
    }

    private void testRaiseEvent() {
        System.out.println("Start event test");
        FermatEvent eventToRaise = eventManager.getNewEvent(EventType.COMPLETE_ASSET_USER_REGISTRATION_NOTIFICATION);
        eventToRaise.setSource(EventSource.NETWORK_SERVICE_ACTOR_ASSET_USER);
        eventManager.raiseEvent(eventToRaise);
        System.out.println("End event test");
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

    /**
     * This method register the genesis address in crypto address book.
     *
     * @param genesisAddress
     * @throws CantRegisterCryptoAddressBookRecordException
     */
    public void registerGenesisAddressInCryptoAddressBook(CryptoAddress genesisAddress) throws CantRegisterCryptoAddressBookRecordException {
        //TODO: solicitar la publickey del Issuer, la publicKey de la wallet
        try {
            this.cryptoAddressBookManager.registerCryptoAddress(genesisAddress,
                    this.assetUserActorDao.getActorAssetUser().getPublicKey(),//"testDeliveredByActorPublicKey",
                    Actors.DAP_ASSET_USER,
                    "testDeliveredToActorIssuerPublicKey",
                    Actors.DAP_ASSET_ISSUER,
                    Platforms.DIGITAL_ASSET_PLATFORM,
                    VaultType.ASSET_VAULT,
                    CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                    UUID.randomUUID().toString(),//this.walletPublicKey,
                    ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);

//            System.out.println("========================================================================");
//            System.out.println("Genesis Address: " + genesisAddress.getAddress());
//            System.out.println("Delivered By: " + this.assetUserActorDao.getActorPublicKey().getPublicKey());
//            System.out.println("Actor: " + Actors.DAP_ASSET_USER);
//            System.out.println("Delivered To: " + "testDeliveredToActorIssuerPublicKey");
//            System.out.println("Actor: " + Actors.DAP_ASSET_ISSUER);
//            System.out.println("Platform: " + Platforms.DIGITAL_ASSET_PLATFORM);
//            System.out.println("Vault Type: " + VaultType.ASSET_VAULT);
//            System.out.println("CryptoCurrency Vault: " + CryptoCurrencyVault.BITCOIN_VAULT.getCode());
//            System.out.println("Wallet PublicKey: " + UUID.randomUUID().toString());
//            System.out.println("Reference Wallet: " + ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);
//            System.out.println("========================================================================");
        } catch (CantGetAssetUsersListException e) {
            throw new CantRegisterCryptoAddressBookRecordException(e.getMessage(), e, "Asset User Actor", "Can't Register CryptoAddress Book: Review Actor PublicKey");
        }
    }

//    private void setBlockchainNetworkType(BlockchainNetworkType blockchainNetworkType)throws ObjectNotSetException {
//        if(blockchainNetworkType==null){
//            throw new ObjectNotSetException("The BlockchainNetworkType is null");
//        }
//        this.blockchainNetworkType=blockchainNetworkType;
//    }

    @Override
    public void handleCompleteRequestListRegisteredAssetUserActorNetworksNotificationEvent(List<ActorAssetUser> actorAssetUserList) {
        System.out.println("Satisfactoriamente llego la lista remota de asset user actor");
    }

    @Override
    public void handleCompleteClientAssetUserActorRegistrationNotificationEvent(ActorAssetUser actorAssetUser) {
        System.out.println("==========================================================");
        System.out.println("Satisfactoriamente se Registro " + actorAssetUser.getName());
        System.out.println("==========================================================");
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
        this.assetUserActorMonitorAgent.stop();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetUserActorDeveloperDatabaseFactory dbFactory = new AssetUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        AssetUserActorDeveloperDatabaseFactory dbFactory = new AssetUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            AssetUserActorDeveloperDatabaseFactory dbFactory = new AssetUserActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeAssetUserActorDatabaseException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return new ArrayList<>();
    }

    /**
     * The method <code>getActorByPublicKey</code> shows the information associated with the actorPublicKey
     *
     * @param actorPublicKey the public key of the Asset Actor User
     * @return THe information associated with the actorPublicKey.
     * @throws CantGetAssetUserActorsException
     * @throws CantAssetUserActorNotFoundException
     */
    @Override
    public List<ActorAssetUser> getActorByPublicKey(String actorPublicKey) throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
        List<ActorAssetUser> list = new ArrayList<ActorAssetUser>(); // Asset User Actor list.

        try {
//            return this.assetUserActorDao.getAssetUserActor(actorPublicKey);

//            not found actor
//             if(actor == null)
//             throw new CantAssetUserActorNotFoundException("", null, ".","Intra User not found");

//             return new IntraUserActorRecord(actorPublicKey, "",actor.getName(),actor.getProfileImage());

//            list.add(new AssetUserActorRecord("Thunders Asset Wallet User", UUID.randomUUID().toString(), new byte[0], 987654321, ConnectionState.CONNECTED));

//             } catch (CantGetAssetUserActorsException  e) {
//              throw new CantGetAssetUserActorsException("", e, ".","Cant Get Intra USer from Data Base");
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException("", FermatException.wrapException(e), "There is a problem I can't identify.", null);
        }
        return list;
    }

    @Override
    public ActorAssetUser getActorAssetUser() throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {

        ActorAssetUser actorAssetUser;
        try {
            actorAssetUser = this.assetUserActorDao.getActorAssetUser();
        } catch (Exception e) {
            throw new CantGetAssetUserActorsException("", FermatException.wrapException(e), "There is a problem I can't identify.", null);
        }
        return actorAssetUser;
    }

    @Override
    public List<ActorAssetUser> getAllAssetUserActorRegistered() throws CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
        List<ActorAssetUser> list; // Asset User Actor list.
        try {
            list = this.assetUserActorDao.getAllAssetUserActorRegistered();
        } catch (CantGetAssetUsersListException e) {
            throw new CantGetAssetUserActorsException("CAN'T GET ASSET USER REGISTERED ACTOR", e, "", "");
        }
        return list;
    }

    /**
     * Method getAllAssetUserActorConnected usado para obtener la lista de ActorAssetUser
     * que tienen CryptoAddress en table REGISTERED
     * y ser usados en Wallet Issuer para poder enviarles BTC del Asset
     *
     * @return List<ActorAssetUser> with CryptoAddress
     * @see #getAllAssetUserActorConnected();
     */
    @Override
    public List<ActorAssetUser> getAllAssetUserActorConnected() throws CantGetAssetUserActorsException {
        List<ActorAssetUser> list; // Asset User Actor list.
        try {
            list = this.assetUserActorDao.getAllAssetUserActorConnected();
        } catch (CantGetAssetUsersListException e) {
            throw new CantGetAssetUserActorsException("CAN'T GET ASSET USER ACTORS CONNECTED WITH CRYPTOADDRESS ", e, "", "");
        }
        return list;
    }

    /*
     * Metodo para ser usado por el Actor Network Service para Instanciar los ActorAssetUser
     */
    @Override
    public ActorAssetUser createActorAssetUserFactory(String assetUserActorPublicKey, String assetUserActorName, byte[] assetUserActorprofileImage, Location assetUserActorlocation) throws CantCreateAssetUserActorException {
        return new AssetUserActorRecord(assetUserActorPublicKey, assetUserActorName, assetUserActorprofileImage, assetUserActorlocation);
    }

    private void registerActorInANS() {

        try {//TODO Escuchar EVENTO para confirmar que se Registro Actor Correctamente en el A.N.S
            /*
             * Envio del ActorAssetUser para registar en el Actor Network Service
             */
            assetUserActorNetworkServiceManager.registerActorAssetUser(this.assetUserActorDao.getActorAssetUser());
        } catch (CantRegisterActorAssetUserException | CantGetAssetUsersListException e) {
            e.printStackTrace();
        }
            /*
             * Envio de Solicitud al Actor Network Service para obtener List<ActorAseetUser>
             *     Registrados en el Actor Network Service y registrarlos en table REGISTERED
             */
        List<ActorAssetUser> list = new LinkedList<>();
//            assetUserActorNetworkServiceManager.requestListActorAssetUserRegistered();
        /*
        TODO: COMENTADO POR CAMBÏOS EN LA INTERFACE
        try {

            list = assetUserActorNetworkServiceManager.getListActorAssetUserRegistered();
        } catch (CantRequestListActorAssetUserRegisteredException e) {
            e.printStackTrace();
        }*/
    }


    @Override
    public void createAndRegisterActorAssetUserTest() throws CantCreateAssetUserActorException {

        try {
            for (int i = 9; i < 10; i++) {
//                String assetUserActorIdentityToLinkPublicKey = UUID.randomUUID().toString();
                String assetUserActorPublicKey = UUID.randomUUID().toString();
//                CryptoAddress cryptoAddress = new CryptoAddress(UUID.randomUUID().toString(), CryptoCurrency.BITCOIN);
                CryptoAddress genesisAddress = getGenesisAddress();
                Genders genders = Genders.INDEFINITE;
                String age = "25";
                ConnectionState connectionState = ConnectionState.CONNECTED;
                Double locationLatitude = new Random().nextDouble();
                Double locationLongitude = new Random().nextDouble();
                AssetUserActorRecord record = new AssetUserActorRecord(assetUserActorPublicKey, "Thunder User_" + new Random().nextInt(10), age, genders,
                        connectionState, locationLatitude, locationLongitude,
                        genesisAddress, System.currentTimeMillis(),
                        System.currentTimeMillis(), new byte[0]);

                /**
                 * Register User in Table Actor Asset User, Simulating user in Local Device
                 */
                if (i == 9) {
                    this.assetUserActorDao.createNewAssetUser(record);
                    ActorAssetUser actorAssetUser = this.assetUserActorDao.getActorAssetUser();
                    System.out.println("*****************************************Actor Asset User***********************************************");
                    System.out.println("PublicKey: " + actorAssetUser.getPublicKey());
                    System.out.println("Name: " + actorAssetUser.getName());
                    System.out.println("GenesisAddress in Crypto Address Book: " + actorAssetUser.getCryptoAddress().getAddress());
                    System.out.println("********************************************************************************************************");
                }

                registerActorInANS();
//                else {
                /**
                 * Register User in Table Actor Asset User Registered,
                 * Simulating other users on their devices, Registered in (Actor Network Service User)
                 */
                  // this.assetUserActorDao.createNewAssetUserRegisterInNetworkService(record);
//                }
//                registerGenesisAddressInCryptoAddressBook(genesisAddress);


            }
        } catch (CantAddPendingAssetUserException e) {
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR", e, "", "");
        } catch (Exception e) {
            throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR", FermatException.wrapException(e), "", "");
        }
    }

    /**
     * Private methods
     */
    private void startMonitorAgent() throws CantGetLoggedInDeviceUserException, CantStartAgentException {
        if (this.assetUserActorMonitorAgent == null) {
            String userPublicKey = this.deviceUserManager.getLoggedInDeviceUser().getPublicKey();
            this.assetUserActorMonitorAgent = new AssetUserActorMonitorAgent(this.eventManager,
                    this.pluginDatabaseSystem,
                    this.errorManager,
                    this.pluginId,
                    this.assetUserActorNetworkServiceManager,
                    this.assetUserActorDao,
                    this);
//            this.assetUserActorMonitorAgent.setLogManager(this.logManager);
            this.assetUserActorMonitorAgent.start();
        } else {
            this.assetUserActorMonitorAgent.start();
        }
    }

//
//    /**
//     * Procces the list o f notifications from Intra User Network Services
//     * And update intra user actor contact state
//     *
//     * @throws CantProcessNotificationsExceptions
//     */
//    private void processNotifications() throws CantProcessNotificationsExceptions {
//
//        try {
//
//            List<IntraUserNotification> intraUserNotificationes = intraUserNetworkServiceManager.getPendingNotifications();
//
//
//            for (IntraUserNotification notification : intraUserNotificationes) {
//
//                String intraUserSendingPublicKey = notification.getPublicKeyOfTheSender();
//
//                String intraUserToConnectPublicKey = notification.getPublicKeyOfTheIntraUserToConnect();
//
//                switch (notification.getNotificationDescriptor()) {
//                    case ASKFORACCEPTANCE:
//
//                        this.askIntraWalletUserForAcceptance(intraUserSendingPublicKey, notification.getActorSenderAlias(), intraUserToConnectPublicKey, notification.getActorSenderProfileImage());
//
//                    case CANCEL:
//                        this.cancelIntraWalletUser(intraUserSendingPublicKey, intraUserToConnectPublicKey);
//
//                    case ACCEPTED:
//                        this.acceptIntraWalletUser(intraUserSendingPublicKey, intraUserToConnectPublicKey);
//                        /**
//                         * fire event "INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION"
//                         */
//                        eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_ACCEPTED_NOTIFICATION));
//                        break;
//                    case DISCONNECTED:
//                        this.disconnectIntraWalletUser("", intraUserSendingPublicKey);
//                        break;
//                    case RECEIVED:
//                        this.receivingIntraWalletUserRequestConnection(intraUserSendingPublicKey, notification.getActorSenderAlias(), intraUserToConnectPublicKey, notification.getActorSenderProfileImage());
//                        /**
//                         * fire event "INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION"
//                         */
//                        eventManager.raiseEvent(eventManager.getNewEvent(EventType.INTRA_USER_CONNECTION_REQUEST_RECEIVED_NOTIFICATION));
//                        break;
//                    case DENIED:
//                        this.denyConnection(intraUserSendingPublicKey, intraUserToConnectPublicKey);
//                        break;
//                    default:
//                        break;
//
//                }
//
//                /**
//                 * I confirm the application in the Network Service
//                 */
//                intraUserNetworkServiceManager.confirmNotification(intraUserSendingPublicKey, intraUserToConnectPublicKey);
//            }
//
//
//        } catch (CantAcceptIntraWalletUserException e) {
//            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Accepted");
//
//        } catch (CantDisconnectIntraWalletUserException e) {
//            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Disconnected");
//
//        } catch (CantDenyConnectionException e) {
//            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", e, "", "Error Update Contact State to Denied");
//
//        } catch (Exception e) {
//            throw new CantProcessNotificationsExceptions("CAN'T PROCESS NETWORK SERVICE NOTIFICATIONS", FermatException.wrapException(e), "", "");
//
//        }
//    }
}
