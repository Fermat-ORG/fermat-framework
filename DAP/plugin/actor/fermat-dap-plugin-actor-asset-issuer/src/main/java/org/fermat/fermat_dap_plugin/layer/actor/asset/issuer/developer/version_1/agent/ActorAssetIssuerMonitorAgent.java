package org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.agent;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantCreateActorAssetIssuerException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantRequestListActorAssetIssuerRegisteredException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
import org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.AssetIssuerActorPluginRoot;
import org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.exceptions.CantAddPendingAssetIssuerException;
import org.fermat.fermat_dap_plugin.layer.actor.asset.issuer.developer.version_1.structure.AssetIssuerActorDao;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 02/11/15.
 */
public class ActorAssetIssuerMonitorAgent implements Agent, DealsWithLogger, DealsWithEvents, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    private Thread agentThread;
    private MonitorAgent monitorAgent;
    LogManager logManager;
    EventManager eventManager;

    PluginDatabaseSystem pluginDatabaseSystem;
    AssetIssuerActorDao assetIssuerActorDao;
    UUID pluginId;
    String userPublicKey;
    AssetIssuerActorNetworkServiceManager assetIssuerActorNetworkServiceManager;
    AssetIssuerActorPluginRoot assetActorIssuerPluginRoot;

    public ActorAssetIssuerMonitorAgent(EventManager eventManager,
                                        PluginDatabaseSystem pluginDatabaseSystem,
                                        UUID pluginId,
                                        AssetIssuerActorNetworkServiceManager assetIssuerActorNetworkServiceManager,
                                        AssetIssuerActorDao assetIssuerActorDao,
                                        AssetIssuerActorPluginRoot assetActorIssuerPluginRoot) {

        this.pluginId = pluginId;
        this.eventManager = eventManager;
        this.assetIssuerActorNetworkServiceManager = assetIssuerActorNetworkServiceManager;
        this.assetIssuerActorDao = assetIssuerActorDao;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.assetActorIssuerPluginRoot = assetActorIssuerPluginRoot;
    }

    @Override
    public void start() throws CantStartAgentException {
        monitorAgent = new MonitorAgent(this.assetActorIssuerPluginRoot, this.pluginDatabaseSystem);
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

        AssetIssuerActorPluginRoot assetActorIssuerPluginRoot;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 60000;/*  / 1000 = TIME in SECONDS = 60 seconds */
        boolean threadWorking;

        public MonitorAgent(AssetIssuerActorPluginRoot assetActorIssuerPluginRoot, PluginDatabaseSystem pluginDatabaseSystem) {
            this.assetActorIssuerPluginRoot = assetActorIssuerPluginRoot;
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
                    assetActorIssuerPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                }
            }
        }

        private void doTheMainTask() throws CantCreateActorAssetIssuerException {
//            try {
//                listByActorAssetIssuerNetworkService();
//
//            } catch (CantCreateActorAssetIssuerException e) {
//                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_ISSUER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
//                throw new CantCreateActorAssetIssuerException("CAN'T START AGENT FOR SEARCH NEW ACTOR ASSET ISSUER IN ACTOR NETWORK SERVICE", e, "", "");
//            }
        }

        private void listByActorAssetIssuerNetworkService() throws CantCreateActorAssetIssuerException {
            try {
                if (assetIssuerActorNetworkServiceManager != null && assetIssuerActorDao.getActorAssetIssuer() != null) {
                    List<ActorAssetIssuer> list = assetIssuerActorNetworkServiceManager.getListActorAssetIssuerRegistered();
                    if (list.isEmpty()) {
                        System.out.println("Actor Asset Issuer - Lista de Actor Asset Network Service: RECIBIDA VACIA - Nuevo intento en: " + SLEEP_TIME / 1000 / 60 + " minute (s)");
                        System.out.println("Actor Asset Issuer - Se procede actualizar Lista en TABLA (si) Existiera algun Registro");
                        assetIssuerActorDao.createNewAssetIssuerRegisterInNetworkServiceByList(list);
                    } else {
                        System.out.println("Actor Asset Issuer - Se Recibio Lista de: " + list.size() + " Actors desde Actor Network Service - SE PROCEDE A SU REGISTRO");
                        int recordInsert = assetIssuerActorDao.createNewAssetIssuerRegisterInNetworkServiceByList(list);
                        System.out.println("Actor Asset Issuer - Se Registro en tabla REGISTER Lista de: " + recordInsert + " Actors desde Actor Network Service");
                    }
                }
            } catch (CantRequestListActorAssetIssuerRegisteredException e) {
                assetActorIssuerPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCreateActorAssetIssuerException("CAN'T REQUEST LIST ACTOR ASSET ISSUER NETWORK SERVICE, POSSIBLE NULL", e, "", "POSSIBLE REASON: " + assetIssuerActorNetworkServiceManager);
            } catch (CantAddPendingAssetIssuerException e) {
                assetActorIssuerPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCreateActorAssetIssuerException("CAN'T ADD LIST ACTOR ASSET ISSUER IN BD ACTORS ", e, "", "");
            } catch (CantGetAssetIssuerActorsException e) {
                assetActorIssuerPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                throw new CantCreateActorAssetIssuerException("CAN'T GET ASSET ACTOR ASSET ISSUER", e, "", "");
            }
        }
    }
}
