package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
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
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.exceptions.CantSendAssetBitcoinsToUserException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantInitializeAssetMonitorAgentException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingTransactionNotificationAgent;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.AssetDistributionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.exceptions.CantCheckAssetDistributionProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.DigitalAssetDistributionVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/10/15.
 */
public class AssetDistributionMonitorAgent  implements Agent,DealsWithLogger,DealsWithEvents,DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

        Database database;
        String userPublicKey;
        MonitorAgent monitorAgent;
        Thread agentThread;
        LogManager logManager;
        EventManager eventManager;
        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        UUID pluginId;
        //OutgoingIntraActorManager outgoingIntraActorManager;
        AssetVaultManager assetVaultManager;
        DigitalAssetDistributionVault digitalAssetDistributionVault;

        public AssetDistributionMonitorAgent(EventManager eventManager,
                PluginDatabaseSystem pluginDatabaseSystem,
                ErrorManager errorManager,
                UUID pluginId,
                String userPublicKey,
                AssetVaultManager assetVaultManager) throws CantSetObjectException {
            this.eventManager = eventManager;
            this.pluginDatabaseSystem = pluginDatabaseSystem;
            this.errorManager = errorManager;
            this.pluginId = pluginId;
            this.userPublicKey = userPublicKey;
            setAssetVaultManager(assetVaultManager);
        }


    private void setAssetVaultManager(AssetVaultManager assetVaultManager) throws CantSetObjectException{
        if(assetVaultManager==null){
            throw new CantSetObjectException("AssetVaultManager is null");
        }
        this.assetVaultManager=assetVaultManager;
    }

    public void setDigitalAssetDistributionVault(DigitalAssetDistributionVault digitalAssetDistributionVault)throws CantSetObjectException{
        if(digitalAssetDistributionVault ==null){
            throw new CantSetObjectException("DigitalAssetDistributionVault is null");
        }
        this.digitalAssetDistributionVault = digitalAssetDistributionVault;
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

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private class MonitorAgent implements AssetIssuingTransactionNotificationAgent, DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable{

        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = AssetIssuingTransactionNotificationAgent.AGENT_SLEEP_TIME;
        int iterationNumber = 0;
        AssetDistributionDao assetDistributionDao;
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

            logManager.log(AssetDistributionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Distribution Protocol Notification Agent: running...", null, null);
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

                    logManager.log(AssetDistributionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantCheckAssetDistributionProgressException | CantExecuteQueryException exception) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
                }

            }

        }
        public void Initialize() throws CantInitializeAssetMonitorAgentException {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId, userPublicKey);
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {
                AssetDistributionDatabaseFactory assetIssuingTransactionDatabaseFactory=new AssetDistributionDatabaseFactory(this.pluginDatabaseSystem);
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

        private void doTheMainTask() throws CantExecuteQueryException, CantCheckAssetDistributionProgressException {

            try {
                assetDistributionDao=new AssetDistributionDao(pluginDatabaseSystem,pluginId);
//TODO implement this
                List<String> transactionHashList;
                CryptoStatus transactionCryptoStatus;
                if(isPendingNetworkLayerEvents()){
                    List<String> assetAcceptedGenesisTransactionList=assetDistributionDao.getGenesisTransactionByAssetAcceptedStatus();
                    for(String assetAcceptedGenesisTransaction : assetAcceptedGenesisTransactionList){
                        String actorUserCryptoAddress=assetDistributionDao.getActorUserCryptoAddressByGenesisTransaction(assetAcceptedGenesisTransaction);
                        //For now, I set the cryptoAddress for Bitcoins
                        CryptoAddress cryptoAddressTo=new CryptoAddress(actorUserCryptoAddress, CryptoCurrency.BITCOIN);
                        sendCryptoAmountToRemoteActor(assetAcceptedGenesisTransaction, cryptoAddressTo);
                        updateDistributionStatus(DistributionStatus.SENDING_CRYPTO, assetAcceptedGenesisTransaction);
                    }
                }

                if(isTransactionToBeNotified(CryptoStatus.PENDING_SUBMIT)){
                    if(isPendingAssetVaultEvents()){
                        List<String> eventIdList=getPendingAssetVaultEvents();
                        for(String eventId : eventIdList){
                            transactionHashList=assetDistributionDao.getTransactionsHashByCryptoStatus(CryptoStatus.PENDING_SUBMIT);
                            for(String transactionHash: transactionHashList){
                                transactionCryptoStatus= getGenesisTransactionFromAssetVault(transactionHash).getCryptoStatus();
                                assetDistributionDao.updateDigitalAssetCryptoStatusByTransactionHash(transactionHash, transactionCryptoStatus);
                                assetDistributionDao.updateEventStatus(eventId);
                            }
                        }
                    }
                }

            } catch (CantExecuteDatabaseOperationException exception) {
                throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Exception in asset distribution monitor agent","Cannot execute database operation");
            }  catch (CantSendAssetBitcoinsToUserException exception) {
                throw new CantCheckAssetDistributionProgressException(exception,"Exception in asset distribution monitor agent","Cannot send crypto currency to asset user");
            } catch (UnexpectedResultReturnedFromDatabaseException exception) {
                throw new CantCheckAssetDistributionProgressException(exception,"Exception in asset distribution monitor agent","Unexpected result in database query");
            } catch (CantGetGenesisTransactionException exception) {
                throw new CantCheckAssetDistributionProgressException(exception,"Exception in asset distribution monitor agent","Cannot get genesis transaction from asset vault");
            }

        }

        private boolean isTransactionToBeNotified(CryptoStatus cryptoStatus) throws CantExecuteQueryException {
            boolean isPending =assetDistributionDao.isPendingTransactions(cryptoStatus);
            return isPending;
        }

        private void updateDistributionStatus(DistributionStatus distributionStatus, String genesisTransaction) throws CantExecuteQueryException, UnexpectedResultReturnedFromDatabaseException {
            assetDistributionDao.updateDistributionStatusByGenesisTransaction(distributionStatus, genesisTransaction);
        }

        private boolean isPendingNetworkLayerEvents() throws CantExecuteQueryException {
            return assetDistributionDao.isPendingNetworkLayerEvents();
        }

        private boolean isPendingAssetVaultEvents() throws CantExecuteQueryException {
            return assetDistributionDao.isPendingAssetVaultEvents();
        }

        private void sendCryptoAmountToRemoteActor(String genesisTransaction, CryptoAddress cryptoAddressTo) throws CantSendAssetBitcoinsToUserException {
            assetVaultManager.sendBitcoinAssetToUser(genesisTransaction, cryptoAddressTo);
        }

        private List<String> getPendingAssetVaultEvents() throws CantCheckAssetDistributionProgressException, UnexpectedResultReturnedFromDatabaseException {
            return assetDistributionDao.getPendingAssetVaultEvents();
        }

        private List<String> getPendingNetworkLayerEvents() throws CantCheckAssetDistributionProgressException, UnexpectedResultReturnedFromDatabaseException {
            return assetDistributionDao.getPendingNetworkLayerEvents();
        }

        private CryptoTransaction getGenesisTransactionFromAssetVault(String transactionHash) throws CantGetGenesisTransactionException {
            //CryptoTransaction cryptoTransaction=assetVaultManager.getGenesisTransaction(transactionHash);
            return null;
        }

    }
}
