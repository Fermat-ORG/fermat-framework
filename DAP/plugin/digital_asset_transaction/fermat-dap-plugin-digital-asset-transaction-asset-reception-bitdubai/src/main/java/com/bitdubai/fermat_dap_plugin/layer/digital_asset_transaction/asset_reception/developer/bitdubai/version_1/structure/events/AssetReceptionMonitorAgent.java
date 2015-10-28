package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_world.Agent;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.DealsWithLogger;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.ReceptionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.enums.DigitalAssetMetadataTransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendTransactionNewStatusNotificationException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingTransactionNotificationAgent;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantInitializeAssetMonitorAgentException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.AssetReceptionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.exceptions.CantCheckAssetReceptionProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.exceptions.CantReceiveDigitalAssetException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.DigitalAssetReceptionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.DigitalAssetReceptor;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.database.AssetReceptionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure.database.AssetReceptionDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/10/15.
 */
public class AssetReceptionMonitorAgent implements Agent,DealsWithLogger,DealsWithEvents,DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

    Database database;
    String userPublicKey;
    MonitorAgent monitorAgent;
    Thread agentThread;
    LogManager logManager;
    EventManager eventManager;
    ErrorManager errorManager;
    PluginDatabaseSystem pluginDatabaseSystem;
    UUID pluginId;
    DigitalAssetReceptionVault digitalAssetReceptionVault;
    DigitalAssetReceptor digitalAssetReceptor;
    AssetTransmissionNetworkServiceManager assetTransmissionManager;
    BitcoinNetworkManager bitcoinNetworkManager;
    ActorAssetUserManager actorAssetUserManager;
    ActorAssetIssuerManager actorAssetIssuerManager;

    public AssetReceptionMonitorAgent(EventManager eventManager,
                                         PluginDatabaseSystem pluginDatabaseSystem,
                                         ErrorManager errorManager,
                                         UUID pluginId,
                                         String userPublicKey){
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.userPublicKey = userPublicKey;
    }

    public void setBitcoinNetworkManager(BitcoinNetworkManager bitcoinNetworkManager) throws CantSetObjectException {
        if(bitcoinNetworkManager ==null){
            throw new CantSetObjectException("bitcoinNetworkManager is null");
        }
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    public void setDigitalAssetDistributionVault(DigitalAssetReceptionVault digitalAssetReceptionVault)throws CantSetObjectException{
        if(digitalAssetReceptionVault ==null){
            throw new CantSetObjectException("digitalAssetReceptionVault is null");
        }
        this.digitalAssetReceptionVault = digitalAssetReceptionVault;
    }

    public void setDigitalAssetReceptor(DigitalAssetReceptor digitalAssetReceptor)throws CantSetObjectException{
        if(digitalAssetReceptor ==null){
            throw new CantSetObjectException("digitalAssetReceptor is null");
        }
        this.digitalAssetReceptor = digitalAssetReceptor;
    }

    public void setAssetTransmissionManager(AssetTransmissionNetworkServiceManager assetTransmissionManager) throws CantSetObjectException {
        if(assetTransmissionManager ==null){
            throw new CantSetObjectException("assetTransmissionManager is null");
        }
        this.assetTransmissionManager = assetTransmissionManager;
    }

    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
        if(assetTransmissionManager ==null){
            throw new CantSetObjectException("actorAssetUserManager is null");
        }
        this.actorAssetUserManager = actorAssetUserManager;
    }

    public void setActorAssetUserManager(ActorAssetIssuerManager actorAssetIssuerManager) throws CantSetObjectException {
        if(actorAssetIssuerManager ==null){
            throw new CantSetObjectException("actorAssetIssuerManager is null");
        }
        this.actorAssetIssuerManager = actorAssetIssuerManager;
    }

    @Override
    public void start() throws CantStartAgentException {

        monitorAgent = new MonitorAgent();

        ((DealsWithPluginDatabaseSystem) this.monitorAgent).setPluginDatabaseSystem(this.pluginDatabaseSystem);
        ((DealsWithErrors) this.monitorAgent).setErrorManager(this.errorManager);

        try {
            ((MonitorAgent) this.monitorAgent).Initialize();
        } catch (CantInitializeAssetMonitorAgentException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
        }

        this.agentThread = new Thread(monitorAgent);
        this.agentThread.start();

    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager=errorManager;
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager=eventManager;
    }

    @Override
    public void setLogManager(LogManager logManager) {
        this.logManager=logManager;
    }

    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
        this.pluginDatabaseSystem=pluginDatabaseSystem;
    }

    @Override
    public void setPluginId(UUID pluginId) {
        this.pluginId=pluginId;
    }

    private class MonitorAgent implements AssetIssuingTransactionNotificationAgent, DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable{

        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = AssetIssuingTransactionNotificationAgent.AGENT_SLEEP_TIME;
        int iterationNumber = 0;
        AssetReceptionDao assetReceptionDao;
        @Override
        public void setErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        @Override
        public void run() {

            logManager.log(AssetReceptionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Reception Protocol Notification Agent: running...", null, null);
            while(true){
                /**
                 * Increase the iteration counter
                 */
                iterationNumber++;
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException interruptedException) {
                    return;
                }

                /**
                 * now I will check if there are pending transactions to raise the event
                 */
                try {

                    logManager.log(AssetReceptionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantCheckAssetReceptionProgressException | CantExecuteQueryException exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                }

            }

        }

        public void Initialize() throws CantInitializeAssetMonitorAgentException{
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId, userPublicKey);
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {
                AssetReceptionDatabaseFactory assetIssuingTransactionDatabaseFactory=new AssetReceptionDatabaseFactory(this.pluginDatabaseSystem);
                try {
                    database = assetIssuingTransactionDatabaseFactory.createDatabase(pluginId, userPublicKey);
                } catch (CantCreateDatabaseException cantCreateDatabaseException) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN,cantCreateDatabaseException);
                    throw new CantInitializeAssetMonitorAgentException(cantCreateDatabaseException,"Initialize Monitor Agent - trying to create the plugin database","Please, check the cause");
                }
            } catch (CantOpenDatabaseException exception) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
                throw new CantInitializeAssetMonitorAgentException(exception,"Initialize Monitor Agent - trying to open the plugin database","Please, check the cause");
            }
        }

        private void doTheMainTask() throws CantExecuteQueryException, CantCheckAssetReceptionProgressException {
            try{
                assetReceptionDao=new AssetReceptionDao(pluginDatabaseSystem,pluginId);

                if(assetReceptionDao.isPendingNetworkLayerEvents()){
                    System.out.println("ASSET RECEPTION is network layer pending events");
                    List<Transaction<DigitalAssetMetadataTransaction>> pendingEventsList=assetTransmissionManager.getPendingTransactions(Specialist.ASSET_ISSUER_SPECIALIST);
                    System.out.println("ASSET RECEPTION is "+pendingEventsList.size()+" events");
                    for(Transaction<DigitalAssetMetadataTransaction> transaction : pendingEventsList){
                        DigitalAssetMetadataTransaction digitalAssetMetadataTransaction=transaction.getInformation();
                        System.out.println("ASSET RECEPTION Digital Asset Metadata Transaction: "+digitalAssetMetadataTransaction);
                        DigitalAssetMetadataTransactionType digitalAssetMetadataTransactionType= digitalAssetMetadataTransaction.getType();
                        System.out.println("ASSET RECEPTION Digital Asset Metadata Transaction Type: "+digitalAssetMetadataTransactionType);
                        if(digitalAssetMetadataTransactionType.getCode().equals(DigitalAssetMetadataTransactionType.META_DATA_TRANSMIT.getCode())){
                            String senderId=digitalAssetMetadataTransaction.getSenderId();
                            System.out.println("ASSET RECEPTION Digital Asset Metadata Sender Id: "+senderId);
                            DigitalAssetMetadata digitalAssetMetadataReceived=digitalAssetMetadataTransaction.getDigitalAssetMetadata();
                            System.out.println("ASSET RECEPTION Digital Asset Metadata Received: " + digitalAssetMetadataReceived);
                            digitalAssetReceptor.receiveDigitalAssetMetadata(digitalAssetMetadataReceived, senderId);
                        }

                    }
                }
                ActorAssetIssuer actorAssetIssuer;
                List<String> genesisTransactionList;
                String senderId;
                ActorAssetUser actorAssetUser=actorAssetUserManager.getActorAssetUser();
                if(assetReceptionDao.isAcceptedAssets()){
                    System.out.println("ASSET RECEPTION there are accepted assets");
                    genesisTransactionList=assetReceptionDao.getGenesisTransactionByReceptionStatus(ReceptionStatus.ASSET_ACCEPTED);
                    for(String genesisTransaction : genesisTransactionList){
                        System.out.println("ASSET RECEPTION Genesis transaction accepted "+genesisTransaction);
                        senderId=assetReceptionDao.getSenderIdByGenesisTransaction(genesisTransaction);
                        System.out.println("ASSET RECEPTION sender id  "+senderId);
                        actorAssetIssuer=getActorAssetIssuer(senderId);
                        assetTransmissionManager.sendTransactionNewStatusNotification(
                                actorAssetUser,
                                actorAssetIssuer,
                                genesisTransaction,
                                DistributionStatus.ASSET_ACCEPTED);
                    }


                }

                if(assetReceptionDao.isRejectedByContract()){
                    System.out.println("ASSET RECEPTION there are rejected by contract assets");
                    //TODO: to handle
                }

                if(assetReceptionDao.isRejectedByHash()){
                    System.out.println("ASSET RECEPTION there are rejected by hash assets");
                    //TODO: to handle
                }

            } catch (CantExecuteDatabaseOperationException exception) {
                throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Exception in asset distribution monitor agent","Cannot execute database operation");
            } catch (CantDeliverPendingTransactionsException exception) {
                throw new CantCheckAssetReceptionProgressException(exception,"Exception in asset reception monitor agent","Cannot deliver pending transactions from network layer");
            } catch (CantReceiveDigitalAssetException e) {
                e.printStackTrace();
            } catch (CantAssetUserActorNotFoundException e) {
                e.printStackTrace();
            } catch (CantSendTransactionNewStatusNotificationException e) {
                e.printStackTrace();
            } catch (CantGetAssetUserActorsException e) {
                e.printStackTrace();
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                e.printStackTrace();
            } catch (CantGetAssetIssuerActorsException e) {
                e.printStackTrace();
            }
        }

        private ActorAssetIssuer getActorAssetIssuer(String senderId) throws CantGetAssetIssuerActorsException {
            List<ActorAssetIssuer> actorAssetIssuerList=actorAssetIssuerManager.getAllAssetIssuerActorRegistered();
            for(ActorAssetIssuer actorAssetIssuer : actorAssetIssuerList){
                if(actorAssetIssuer.getPublicKey().equals(senderId)){
                    return actorAssetIssuer;
                }
            }
            throw new CantGetAssetIssuerActorsException(CantGetAssetIssuerActorsException.DEFAULT_MESSAGE,null,
                    "Getting the ActorAssetUser","Cannot find sender id\n"+senderId+"\nfrom AssetIssuerActors registered");
        }

    }
}
