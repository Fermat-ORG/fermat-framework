package com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.monitorAgent;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantCreateAssetUserActorException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantRequestListActorAssetUserRegisteredException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.interfaces.AssetUserActorNetworkServiceManager;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.AssetActorUserPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.exceptions.CantAddPendingAssetUserException;
import com.bitdubai.fermat_dap_plugin.layer.actor.asset.user.developer.bitdubai.version_1.structure.AssetUserActorDao;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.AssetUserActorRecord;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Nerio on 24/10/15.
 */
public class AssetUserActorMonitorAgent implements Agent, DealsWithLogger, DealsWithEvents, DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    private Thread agentThread;
    private MonitorAgent monitorAgent;
    LogManager logManager;
    EventManager eventManager;
    ErrorManager errorManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    AssetUserActorDao assetUserActorDao;
    UUID pluginId;
    String userPublicKey;
    AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager;
    AssetActorUserPluginRoot assetActorUserPluginRoot;

    public AssetUserActorMonitorAgent(EventManager eventManager, PluginDatabaseSystem pluginDatabaseSystem,
                                      ErrorManager errorManager, UUID pluginId,
                                      AssetUserActorNetworkServiceManager assetUserActorNetworkServiceManager,
                                      AssetUserActorDao assetUserActorDao, AssetActorUserPluginRoot assetActorUserPluginRoot) {
        this.pluginId = pluginId;
        this.eventManager = eventManager;
        this.errorManager = errorManager;
        this.assetUserActorNetworkServiceManager = assetUserActorNetworkServiceManager;
        this.assetUserActorDao = assetUserActorDao;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.assetActorUserPluginRoot = assetActorUserPluginRoot;

    }

    @Override
    public void start() throws CantStartAgentException {

        monitorAgent = new MonitorAgent(this.errorManager, this.pluginDatabaseSystem);
        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
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

        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = 60000;/*  / 1000 = TIME in SECONDS = 60 seconds */
        boolean threadWorking;

        public MonitorAgent(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem) {
            this.errorManager = errorManager;
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
                   errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_ACTOR, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        private void doTheMainTask() throws CantCreateAssetUserActorException {
            try {
//                test_RegisterActorNetworkService();

                listByActorNetworkServiceUser();

            } catch (CantCreateAssetUserActorException e) {
                throw new CantCreateAssetUserActorException("CAN'T ADD NEW ASSET USER ACTOR NETWORK SERVICE", e, "", "");
            }
        }

        private void listByActorNetworkServiceUser() throws CantCreateAssetUserActorException {
//                Procedimiento para Obtener lista de Actores del Actor Network Service User
            try {
                List<ActorAssetUser> list = assetUserActorNetworkServiceManager.getListActorAssetUserRegistered();

                if (list.isEmpty()) {
                    System.out.println("Lista de Actores de Actor Network Service: RECIBIDA VACIA - Nuevo intento en: " + SLEEP_TIME / 1000 / 60 + " minute (s)");
                } else {
                    System.out.println("Se Recibio Lista de: " + list.size() + " Actors desde Actor Network Service - SE PROCEDE A SU REGISTRO");
                    int recordInsert = assetUserActorDao.createNewAssetUserRegisterInNetworkServiceByList(list);
                    System.out.println("Se Registro en tabla ASSET_USER_REGISTER_ACTOR Lista de: " + recordInsert + " Actors desde Actor Network Service");
                }

            } catch (CantRequestListActorAssetUserRegisteredException e) {
                //throw new CantCreateAssetUserActorException("CAN'T ADD TEST NEW ASSET USER ACTOR NETWORK SERVICE", e, "", "");
            } catch (CantAddPendingAssetUserException e) {
                e.printStackTrace();
            }
        }

        private void test_RegisterActorNetworkService() throws CantCreateAssetUserActorException {
            try {
                //Comentar CICLO FOR para realizar prueba directa con Actor Network Service
                for (int i = 0; i < 10; i++) {
//                String assetUserActorIdentityToLinkPublicKey = UUID.randomUUID().toString();
                    String assetUserActorPublicKey = UUID.randomUUID().toString();
//                CryptoAddress cryptoAddress = new CryptoAddress(UUID.randomUUID().toString(), CryptoCurrency.BITCOIN);
                    CryptoAddress genesisAddress = assetActorUserPluginRoot.getGenesisAddress();
//                    CryptoAddress genesisAddress = new CryptoAddress(UUID.randomUUID().toString(), CryptoCurrency.BITCOIN);
                    ;
                    Genders genders = Genders.INDEFINITE;
                    String age = "25";
                    ConnectionState connectionState = ConnectionState.CONNECTED;
                    Double locationLatitude = new Random().nextDouble();
                    Double locationLongitude = new Random().nextDouble();
                    AssetUserActorRecord record = new AssetUserActorRecord(assetUserActorPublicKey, "ANS User_" + new Random().nextInt(10), age, genders,
                            connectionState, locationLatitude, locationLongitude,
                            genesisAddress, System.currentTimeMillis(),
                            System.currentTimeMillis(), new byte[0]);

                    assetUserActorDao.createNewAssetUserRegisterInNetworkService(record);

                    assetActorUserPluginRoot.registerGenesisAddressInCryptoAddressBook(genesisAddress);
                }
                System.out.println("Actores SIMULANDO Actor Network Service: GUARDADOS - Nuevo intento en: " + SLEEP_TIME / 1000 / 60 + " minute (s)");
            } catch (CantAddPendingAssetUserException e) {
                throw new CantCreateAssetUserActorException("CAN'T ADD (TEST) NEW ASSET USER ACTOR NETWORK SERVICE", e, "", "");
            } catch (Exception e) {
                throw new CantCreateAssetUserActorException("CAN'T ADD (TEST) NEW ASSET USER ACTOR NETWORK SERVICE", FermatException.wrapException(e), "", "");
            }
        }
    }
}
