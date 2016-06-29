package org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.agent;

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

import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantCreateActorRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.redeem_point.interfaces.AssetRedeemPointActorNetworkServiceManager;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.RedeemPointActorPluginRoot;
import org.fermat.fermat_dap_plugin.layer.actor.redeem.point.developer.version_1.structure.RedeemPointActorDao;

import java.util.UUID;

/**
 * Created by Nerio on 02/11/15.
 */
public class ActorAssetRedeemPointMonitorAgent implements Agent, DealsWithLogger, DealsWithEvents, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {


    private Thread agentThread;
    private MonitorAgent monitorAgent;
    LogManager logManager;
    EventManager eventManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    RedeemPointActorDao redeemPointActorDao;
    UUID pluginId;
    String userPublicKey;
    AssetRedeemPointActorNetworkServiceManager assetRedeemPointActorNetworkServiceManager;
    RedeemPointActorPluginRoot redeemPointPluginRoot;

    public ActorAssetRedeemPointMonitorAgent(EventManager eventManager,
                                             PluginDatabaseSystem pluginDatabaseSystem,
                                             UUID pluginId,
                                             AssetRedeemPointActorNetworkServiceManager assetRedeemPointActorNetworkServiceManager,
                                             RedeemPointActorDao redeemPointActorDao,
                                             RedeemPointActorPluginRoot redeemPointPluginRoot) {

        this.pluginId = pluginId;
        this.eventManager = eventManager;
        this.assetRedeemPointActorNetworkServiceManager = assetRedeemPointActorNetworkServiceManager;
        this.redeemPointActorDao = redeemPointActorDao;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.redeemPointPluginRoot = redeemPointPluginRoot;
    }

    @Override
    public void start() throws CantStartAgentException {
        monitorAgent = new MonitorAgent(this.redeemPointPluginRoot, this.pluginDatabaseSystem);
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

        RedeemPointActorPluginRoot redeemPointPluginRoot;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 60000;/*  / 1000 = TIME in SECONDS = 60 seconds */
        boolean threadWorking;

        public MonitorAgent(RedeemPointActorPluginRoot redeemPointPluginRoot, PluginDatabaseSystem pluginDatabaseSystem) {
            this.redeemPointPluginRoot = redeemPointPluginRoot;
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
                    redeemPointPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        private void doTheMainTask() throws CantCreateActorRedeemPointException {
//            try {
//                listByActorAssetRedeemPointNetworkService();
//
//            } catch (CantCreateActorRedeemPointException e) {
//                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_REDEEM_POINT_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                throw new CantCreateActorRedeemPointException("CAN'T START AGENT FOR SEARCH NEW ACTOR ASSET REDEEM POINT IN ACTOR NETWORK SERVICE", e, "", "");
//            }
        }
        //EN DESHUSO
//        private void listByActorAssetRedeemPointNetworkService() throws CantCreateActorRedeemPointException {
//            try {
//                if (assetRedeemPointActorNetworkServiceManager != null && redeemPointActorDao.getActorAssetRedeemPoint() != null) {
//                    List<ActorAssetRedeemPoint> list = assetRedeemPointActorNetworkServiceManager.getListActorAssetRedeemPointRegistered();
//                    if (list.isEmpty()) {
//                        System.out.println("Actor Asset Redeem Point - Lista de Actor Asset Network Service: RECIBIDA VACIA - Nuevo intento en: " + SLEEP_TIME / 1000 / 60 + " minute (s)");
//                        System.out.println("Actor Asset Redeem Point - Se procede actualizar Lista en TABLA (si) Existiera algun Registro");
//                        redeemPointActorDao.createNewAssetRedeemPointRegisterInNetworkServiceByList(list);
//                    } else {
//                        System.out.println("Actor Asset Redeem Point - Se Recibio Lista de: " + list.size() + " Actors desde Actor Network Service - SE PROCEDE A SU REGISTRO");
//                        int recordInsert = redeemPointActorDao.createNewAssetRedeemPointRegisterInNetworkServiceByList(list);
//                        System.out.println("Actor Asset Redeem Point - Se Registro en tabla REGISTER Lista de: " + recordInsert + " Actors desde Actor Network Service");
//                    }
//                }
//            } catch (CantRequestListActorAssetRedeemPointRegisteredException e) {
//                redeemPointPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                throw new CantCreateActorRedeemPointException("CAN'T REQUEST LIST ACTOR ASSET REDEEM POINT NETWORK SERVICE, POSSIBLE NULL", e, "", "POSSIBLE REASON: " + assetRedeemPointActorNetworkServiceManager);
//            } catch (CantAddPendingRedeemPointException e) {
//                redeemPointPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                throw new CantCreateActorRedeemPointException("CAN'T ADD LIST ACTOR ASSET REDEEM POINT IN BD ACTORS ", e, "", "");
//            } catch (CantGetAssetRedeemPointActorsException e) {
//                redeemPointPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                throw new CantCreateActorRedeemPointException("CAN'T GET ASSET ACTOR ASSET REDEEM POINT", e, "", "");
//            }
//        }
    }
}
