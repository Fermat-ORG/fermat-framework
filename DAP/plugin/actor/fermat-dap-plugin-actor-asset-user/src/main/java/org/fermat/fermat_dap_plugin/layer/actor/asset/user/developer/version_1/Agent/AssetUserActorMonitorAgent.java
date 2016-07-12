package org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.Agent;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.AssetUserActorPluginRoot;
import org.fermat.fermat_dap_plugin.layer.actor.asset.user.developer.version_1.structure.AssetUserActorDao;

import java.util.UUID;

/**
 * Created by Nerio on 24/10/15.
 */
public class AssetUserActorMonitorAgent implements Agent, DealsWithLogger, DealsWithEvents, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    private Thread agentThread;
    LogManager logManager;
    EventManager eventManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    AssetUserActorDao assetUserActorDao;
    UUID pluginId;
    String userPublicKey;
    AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager;
    AssetUserActorPluginRoot assetActorUserPluginRoot;

    public AssetUserActorMonitorAgent(EventManager eventManager,
                                      PluginDatabaseSystem pluginDatabaseSystem,
                                      UUID pluginId,
                                      AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager,
                                      AssetUserActorDao assetUserActorDao,
                                      AssetUserActorPluginRoot assetActorUserPluginRoot) {
        this.pluginId = pluginId;
        this.eventManager = eventManager;
        this.assetUserActorNetworkServiceManager = assetUserActorNetworkServiceManager;
        this.assetUserActorDao = assetUserActorDao;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.assetActorUserPluginRoot = assetActorUserPluginRoot;

    }

    @Override
    public void start() throws CantStartAgentException {

        MonitorAgent monitorAgent = new MonitorAgent(this.assetActorUserPluginRoot, this.pluginDatabaseSystem);
        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
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
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem = pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    private class MonitorAgent implements Runnable {

        AssetUserActorPluginRoot assetActorUserPluginRoot;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 60000;/*  / 1000 = TIME in SECONDS = 60 seconds */
        boolean threadWorking;

        public MonitorAgent(AssetUserActorPluginRoot assetActorUserPluginRoot, PluginDatabaseSystem pluginDatabaseSystem) {
            this.assetActorUserPluginRoot = assetActorUserPluginRoot;
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        @Override
        public void run() {
            threadWorking = true;

            while (threadWorking) {

                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                try {

                    doTheMainTask();
                } catch (Exception e) {
                    assetActorUserPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        private void doTheMainTask() throws CantCreateAssetUserActorException {
//            try {
//                listByActorAssetUserNetworkService();
//
//            } catch (CantCreateAssetUserActorException e) {
//                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                throw new CantCreateAssetUserActorException("CAN'T START AGENT FOR SEARCH NEW ACTOR ASSET USER IN ACTOR NETWORK SERVICE", e, "", "");
//            }
        }
        //EN DESHUSO
//        private void listByActorAssetUserNetworkService() throws CantCreateAssetUserActorException {
//            try {
//                if (assetUserActorNetworkServiceManager != null && assetUserActorDao.getActorAssetUser() != null) {
//                    List<ActorAssetUser> list = assetUserActorNetworkServiceManager.getListActorAssetUserRegistered();
//                    if (list.isEmpty()) {
//                        System.out.println("Actor Asset User - Lista de Actor Asset Network Service: RECIBIDA VACIA - Nuevo intento en: " + SLEEP_TIME / 1000 / 60 + " minute (s)");
//                        System.out.println("Actor Asset User - Se procede actualizar Lista en TABLA (si) Existiera algun Registro");
//                        assetUserActorDao.createNewAssetUserRegisterInNetworkServiceByList(list);
//                    } else {
//                        System.out.println("Actor Asset User - Se Recibio Lista de: " + list.size() + " Actors desde Actor Network Service - SE PROCEDE A SU REGISTRO");
//                        int recordInsert = assetUserActorDao.createNewAssetUserRegisterInNetworkServiceByList(list);
//                        System.out.println("Actor Asset User - Se Registro en tabla REGISTER Lista de: " + recordInsert + " Actors desde Actor Network Service");
//                    }
//                }
//            } catch (CantRequestListActorAssetUserRegisteredException e) {
//                assetActorUserPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                throw new CantCreateAssetUserActorException("CAN'T REQUEST LIST ACTOR ASSET USER NETWORK SERVICE, POSSIBLE NULL", e, "", "POSSIBLE REASON: " + assetUserActorNetworkServiceManager);
//            } catch (CantAddPendingActorAssetException e) {
//                assetActorUserPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                throw new CantCreateAssetUserActorException("CAN'T ADD LIST ACTOR ASSET USER IN BD ACTORS ", e, "", "");
//            } catch (CantGetAssetUserActorsException e) {
//                assetActorUserPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                throw new CantCreateAssetUserActorException("CAN'T GET ASSET ACTOR ASSET USER", e, "", "");
//            }
//        }
    }
}
