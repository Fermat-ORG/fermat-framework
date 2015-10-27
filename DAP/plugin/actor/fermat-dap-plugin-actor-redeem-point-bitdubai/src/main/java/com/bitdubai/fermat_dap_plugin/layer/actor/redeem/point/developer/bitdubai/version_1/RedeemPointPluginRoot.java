package com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
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
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantCreateActorRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantGetAssetRedeemPointActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.developerUtils.RedeemPointActorDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantAddPendingRedeemPointException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantGetRedeemPointsListException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.exceptions.CantInitializeRedeemPointActorDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.structure.RedeemPointActorAddress;
import com.bitdubai.fermat_dap_plugin.layer.actor.redeem.point.developer.bitdubai.version_1.structure.RedeemPointActorDao;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.RedeemPointActorRecord;
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

public class RedeemPointPluginRoot implements ActorAssetRedeemPointManager, DealsWithErrors, DatabaseManagerForDevelopers, DealsWithEvents, DealsWithPluginDatabaseSystem, DealsWithPluginFileSystem, LogManagerForDevelopers, Plugin, Service, Serializable {

    RedeemPointActorDao redeemPointActorDao;

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
    public List<String> getClassesFullPath() {
        List<String> returnedClasses = new ArrayList<String>();
        returnedClasses.add("com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.ActorUserPluginRoot");
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
                if (RedeemPointPluginRoot.newLoggingLevel.containsKey(pluginPair.getKey())) {
                    RedeemPointPluginRoot.newLoggingLevel.remove(pluginPair.getKey());
                    RedeemPointPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
                } else {
                    RedeemPointPluginRoot.newLoggingLevel.put(pluginPair.getKey(), pluginPair.getValue());
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
            redeemPointActorDao = new RedeemPointActorDao(pluginDatabaseSystem, pluginFileSystem, pluginId);

            test();
        } catch (CantInitializeRedeemPointActorDatabaseException e) {
            throw new CantStartPluginException();
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
        return serviceStatus;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        RedeemPointActorDeveloperDatabaseFactory dbFactory = new RedeemPointActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        RedeemPointActorDeveloperDatabaseFactory dbFactory = new RedeemPointActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            RedeemPointActorDeveloperDatabaseFactory dbFactory = new RedeemPointActorDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeRedeemPointActorDatabaseException e) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception e) {
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empty list
        return Collections.EMPTY_LIST;
    }

    private void test() throws CantInitializeRedeemPointActorDatabaseException {
//        try {
            for (int i = 0; i < 10; i++) {

//                System.out.println("*******************************************************");
//                System.out.println("REDEEM POINT: iniciando a crear el record: " + i);
//                System.out.println("*******************************************************");
                String identityPublicKey = i + UUID.randomUUID().toString();
                String publicKey = i + UUID.randomUUID().toString();
                CryptoAddress cryptoAddress = new CryptoAddress(UUID.randomUUID().toString(), CryptoCurrency.BITCOIN);
                DeviceLocation location = new DeviceLocation();
                location.setLongitude(new Random().nextDouble());
                location.setLatitude(new Random().nextDouble());
                RedeemPointActorRecord record = new RedeemPointActorRecord("RedeemP_" + i, publicKey);
                record.setConnectionState(ConnectionState.CONNECTED);
                record.setProfileImage(new byte[0]);
                record.setLocation(location);
                RedeemPointActorAddress address = new RedeemPointActorAddress();
                address.setCountryName("Venezuela");
                address.setProvinceName("Zulia");
                address.setPostalCode("4019");
                address.setCityName("Ciudad Ojeda");
                address.setStreetName("Avenida 8");
                address.setHouseNumber("#712");
                record.setAddress(address);
                record.setCryptoAddress(cryptoAddress);
                record.setHoursOfOperation("08:00 am a 05:30pm");
                record.setContactInformation("marsvicam@gmail.com");
                try {
                    if (i == 0) {
                        redeemPointActorDao.createNewRedeemPoint(identityPublicKey, record);
                    }
                    redeemPointActorDao.createNewRedeemPointRegisterInNetworkService(record);
                } catch (CantAddPendingRedeemPointException e) {
                    System.out.println("*******************************************************");
                    System.out.println("REDEEM POINT: Falló creando el record número: " + i);
                    e.printStackTrace();
                    System.out.println("*******************************************************");
                }
            }


//        } catch (CantInitializeRedeemPointActorDatabaseException e) {
//            System.out.println("*******************************************************");
//            System.out.println("REDEEM POINT: Falló iniciando la base de datos.: ");
//            e.printStackTrace();
//            System.out.println("*******************************************************");
//            throw e;
//        }
    }

    /**
     * The method <code>getActorPublicKey</code> get All Information about Actor
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    @Override
    public ActorAssetRedeemPoint getActorAssetRedeemPoint() throws CantGetAssetRedeemPointActorsException {

        ActorAssetRedeemPoint actorAssetRedeemPoint;
        try {
            actorAssetRedeemPoint = this.redeemPointActorDao.getActorAssetRedeemPoint();
        } catch (Exception e) {
            throw new CantGetAssetRedeemPointActorsException("", FermatException.wrapException(e), "There is a problem I can't identify.", null);
        }
        return actorAssetRedeemPoint;
    }

    /**
     * The method <code>getAllAssetUserActorRegistered</code> get All Actors Registered in Actor Network Service
     * and used in Sub App Community
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    @Override
    public List<ActorAssetRedeemPoint> getAllAssetRedeemPointActorRegistered() throws CantGetAssetRedeemPointActorsException {
        List<ActorAssetRedeemPoint> list; // Asset User Actor list.
        try {
            list = this.redeemPointActorDao.getAllAssetRedeemPointActorRegistered();
        } catch (CantGetRedeemPointsListException e) {
            throw new CantGetAssetRedeemPointActorsException("CAN'T GET REDEEM POINT ACTOR REGISTERED", e, "", "");
        }
        return list;
    }

    /**
     * The method <code>getAllAssetIssuerActorConnected</code> receives All Actors with have CryptoAddress in BD
     *
     * @throws CantGetAssetRedeemPointActorsException
     */
    @Override
    public List<ActorAssetRedeemPoint> getAllRedeemPointActorConnected() throws CantGetAssetRedeemPointActorsException {
        List<ActorAssetRedeemPoint> list; // Asset User Actor list.
        try {
            list = this.redeemPointActorDao.getAllAssetRedeemPointActorConnected();
        } catch (CantGetRedeemPointsListException e) {
            throw new CantGetAssetRedeemPointActorsException("CAN'T GET REDEEM POINT ACTOR CONNECTED ", e, "", "");
        }
        return list;
    }

    /**
     * The method <code>createActorAssetUserFactory</code> create Actor in Actor Network Service
     *
     * @param assetRedeemPointActorPublicKey
     * @param assetRedeemPointActorName
     * @param assetRedeemPointActorprofileImage
     * @param assetRedeemPointActorlocation
     * @throws CantCreateActorRedeemPointException
     */
    @Override
    public ActorAssetRedeemPoint createActorAssetRedeemPointFactory(String assetRedeemPointActorPublicKey, String assetRedeemPointActorName, byte[] assetRedeemPointActorprofileImage, Location assetRedeemPointActorlocation) throws CantCreateActorRedeemPointException {

        return null;
    }
}
