package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseConstants;
import com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.database.WalletStoreCatalogDatabaseDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Created by rodrigo on 7/24/15.
 */
public class WalletStoreNetworkServiceMonitoringAgent implements Agent, DealsWithEvents, DealsWithErrors, DealsWithLogger, DealsWithPluginDatabaseSystem {
    /**
     * WalletStoreNetworkServiceMonitoringAgent
     */
    boolean runner;
    UUID pluginId;
    WalletStoreCatalogDatabaseDao databaseDao;

    /**
     * DealsWithEvents interface member variable
     */
    EventManager eventManager;

    /**
     * DealsWithErrors interface member variable
     */
    ErrorManager errorManager;

    /**
     * DealsWithLogger interface member varible
     */
    LogManager logManager;


    /**
     * DealsWithPlugindatabaseSystem interface member variable
     */
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * DealsWithCommunicationLayerManager interface variables and implementation
     */

    /**
     * constructor
     * @param eventManager
     * @param errorManager
     * @param logManager
     * @param pluginDatabaseSystem
     */
    public WalletStoreNetworkServiceMonitoringAgent(EventManager eventManager, ErrorManager errorManager, LogManager logManager, PluginDatabaseSystem pluginDatabaseSystem, UUID pluginId){
        this.eventManager = eventManager;
        this.errorManager = errorManager;
        this.logManager = logManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginId = pluginId;
    }

    /**
     * Agent interface implementation
     */

    @Override
    public void start() throws CantStartAgentException {
        // I register this networkservice.
       // ECCKeyPair keyPair = new ECCKeyPair();
       // try {
        //    communicationLayerManager.registerNetworkService(NetworkServices.WALLET_STORE, keyPair.getPublicKey());
        //    communicationLayerManager.acceptIncomingNetworkServiceConnectionRequest(CommunicationChannels.CLOUD, NetworkServices.WALLET_STORE, null);
         //   System.out.println("Remote networkservices: " + communicationLayerManager.getNetworkServiceChannelPublicKey(NetworkServices.WALLET_STORE));
       // } catch (CommunicationException e) {
        //    e.printStackTrace();
       // }


        Thread thread = new Thread(new Monitoring());
        runner = true;
        thread.start();
    }

    @Override
    public void stop() {
        runner = false;
    }

    /**
     * DealsWithEvents interface implementation
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * DealsWithError interface implementation
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealsWithLogger interface implementation
     */
    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }


    /**
     * DealsWithPluginDatabaseSystem interface implementation
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    private class Monitoring implements Runnable {
        @Override
        public void run() {
            while (runner){
                try {
                    doTheMainTask();
                    Thread.sleep(10000);
                } catch (Exception e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_WALLET_STORE_NETWORK_SERVICE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }

        }

        private void doTheMainTask() throws InvalidParameterException, CantExecuteDatabaseOperationException {
            //TODO: comentado porqué está molestando
//            if (areMissingIds(CatalogItems.LANGUAGE)){
//                insertValidLanguagesFromPeer();
//                raiseEvent();
//            }
//
//
//            if (areMissingIds(CatalogItems.SKIN))
//                raiseEvent();
//
//            if (areMissingIds(CatalogItems.WALLET))
//                raiseEvent();
        }

        private void insertValidLanguagesFromPeer() throws InvalidParameterException, CantExecuteDatabaseOperationException {
            for (UUID idToInsert : getMissingIds(CatalogItems.LANGUAGE)){
                //todo get the object from peer
                //validate if the version is for our platform
                //persist in the catalog
            }

        }

        private boolean isValidVersion(Version version){
            return true;
        }

        private void raiseEvent(){
            //todo add events depending on the event raised I need find the way to pass the missing Ids.
        }

        private boolean areMissingIds(CatalogItems catalogItems) throws CantExecuteDatabaseOperationException, InvalidParameterException {
            return getDatabaseDao().getCatalogIdsForNetworkService(catalogItems).size() != 0;
        }

        private List<UUID> getIdsFromMyCatalog(CatalogItems catalogItems) throws CantExecuteDatabaseOperationException, InvalidParameterException {
            return getDatabaseDao().getCatalogIdsForNetworkService(catalogItems);
        }

        private List<UUID> getIdsFromPeer(CatalogItems catalogItems) throws CantExecuteDatabaseOperationException, InvalidParameterException {
            return getDatabaseDao().getCatalogIdsForNetworkService(catalogItems);
        }

        private WalletStoreCatalogDatabaseDao getDatabaseDao() throws CantExecuteDatabaseOperationException {
            if (databaseDao == null)
                databaseDao = new WalletStoreCatalogDatabaseDao(errorManager, logManager, pluginDatabaseSystem, pluginId, WalletStoreCatalogDatabaseConstants.WALLET_STORE_DATABASE);

            return databaseDao;
        }

        private List<UUID> getMissingIds(CatalogItems catalogItems) throws InvalidParameterException, CantExecuteDatabaseOperationException {
            Collection<UUID> localIds = getIdsFromMyCatalog(catalogItems);
            Collection<UUID> remoteIds = getIdsFromPeer(catalogItems);

            remoteIds.removeAll(localIds);
            List<UUID> idsToReturn;
            if (remoteIds instanceof List)
                idsToReturn = (List) remoteIds;
            else
                idsToReturn = new ArrayList<>(remoteIds);

            return idsToReturn;
        }
    }
}
