package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.DealsWithPluginIdentity;
import com.bitdubai.fermat_api.layer.DAPException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetGenesisTransactionException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetBalanceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.AssetIssuingTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeleteDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantInitializeAssetMonitorAgentException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.DigitalAssetIssuingVault;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/09/15.
 */
public class AssetIssuingTransactionMonitorAgent implements Agent,DealsWithLogger,DealsWithEvents,DealsWithErrors, DealsWithPluginDatabaseSystem, DealsWithPluginIdentity {

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
    DigitalAssetIssuingVault digitalAssetIssuingVault;


    public AssetIssuingTransactionMonitorAgent(EventManager eventManager,
                                               PluginDatabaseSystem pluginDatabaseSystem,
                                               ErrorManager errorManager,
                                               UUID pluginId,
                                               String userPublicKey,
                                               AssetVaultManager assetVaultManager
                                               /*OutgoingIntraActorManager outgoingIntraActorManager*/) throws CantSetObjectException {
        this.eventManager = eventManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.errorManager = errorManager;
        this.pluginId = pluginId;
        this.userPublicKey = userPublicKey;
        setAssetVaultManager(assetVaultManager);
        //setOutgoingIntraActorManager(outgoingIntraActorManager);
    }

    /*private void setOutgoingIntraActorManager(OutgoingIntraActorManager outgoingIntraActorManager)throws CantSetObjectException{
        if(outgoingIntraActorManager==null){
            throw new CantSetObjectException("outgoingIntraActorManager is null");
        }
        this.outgoingIntraActorManager=outgoingIntraActorManager;
    }*/

    private void setAssetVaultManager(AssetVaultManager assetVaultManager) throws CantSetObjectException{
        if(assetVaultManager==null){
            throw new CantSetObjectException("AssetVaultManager is null");
        }
        this.assetVaultManager=assetVaultManager;
    }

    public void setDigitalAssetIssuingVault(DigitalAssetIssuingVault digitalAssetIssuingVault)throws CantSetObjectException{
        if(digitalAssetIssuingVault ==null){
            throw new CantSetObjectException("DigitalAssetIssuingVault is null");
        }
        this.digitalAssetIssuingVault = digitalAssetIssuingVault;
    }

    @Override
    public void start() throws CantStartAgentException {

        Logger LOG = Logger.getGlobal();
        LOG.info("Asset Issuing monitor agent starting");
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
    private class MonitorAgent implements /*AssetIssuingTransactionNotificationAgent,*/ DealsWithPluginDatabaseSystem, DealsWithErrors, Runnable{

        ErrorManager errorManager;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = /*AssetIssuingTransactionNotificationAgent.AGENT_SLEEP_TIME*/5000;
        int iterationNumber = 0;
        AssetIssuingTransactionDao assetIssuingTransactionDao;
        boolean threadWorking;
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

            threadWorking=true;
            logManager.log(AssetIssuingTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Issuing Transaction Protocol Notification Agent: running...", null, null);
            while(threadWorking){
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

                    logManager.log(AssetIssuingTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantDeliverDigitalAssetToAssetWalletException | CantCheckAssetIssuingProgressException | CantExecuteQueryException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }

            }

        }
        public void Initialize() throws CantInitializeAssetMonitorAgentException {
            try {

                database = this.pluginDatabaseSystem.openDatabase(pluginId,  AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE);
            }
            catch (DatabaseNotFoundException databaseNotFoundException) {

                Logger LOG = Logger.getGlobal();
                LOG.info("Database in issuing monitor agent doesn't exists");
                AssetIssuingTransactionDatabaseFactory assetIssuingTransactionDatabaseFactory=new AssetIssuingTransactionDatabaseFactory(this.pluginDatabaseSystem);
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

        private void doTheMainTask() throws CantCheckAssetIssuingProgressException, CantExecuteQueryException, CantDeliverDigitalAssetToAssetWalletException {

            Logger LOG = Logger.getGlobal();
            LOG.info("Asset Issuing monitor agent DoTheMainTask");
            try {
                assetIssuingTransactionDao=new AssetIssuingTransactionDao(pluginDatabaseSystem,pluginId);

                List<String> transactionHashList;
                CryptoStatus transactionCryptoStatus;
                if (isTransactionToBeNotified(CryptoStatus.PENDING_SUBMIT)){
                    if(isPendingEvents()){
                        System.out.println("AID: is pending event");
                        List<String> eventIdList=getPendingEvents();
                        for(String eventId : eventIdList){
                            System.out.println("Id event:"+eventId);
                            transactionHashList=assetIssuingTransactionDao.getTransactionsHashByCryptoStatus(CryptoStatus.PENDING_SUBMIT);
                            for(String transactionHash: transactionHashList){
                                System.out.println("Transaction Hash: "+transactionHash);
                                /**
                                 * I will hardcode the CryptoStatus update in this plugin database for testing porpoises.
                                 * The following line will return a mocked CryptoTransaction
                                 */
                                transactionCryptoStatus= getGenesisTransactionFromAssetVault(transactionHash).getCryptoStatus();
                                //transactionCryptoStatus=CryptoStatus.ON_CRYPTO_NETWORK;
                                //TODO: implement the correct way to get the genesisTransaction from CryptoNetwork.
                                assetIssuingTransactionDao.updateDigitalAssetCryptoStatusByTransactionHash(transactionHash, transactionCryptoStatus);
                                assetIssuingTransactionDao.updateEventStatus(eventId);
                            }
                        }
                    }
                }

                if (isTransactionToBeNotified(CryptoStatus.ON_CRYPTO_NETWORK)){
                    transactionHashList=assetIssuingTransactionDao.getTransactionsHashByCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
                    for(String transactionHash: transactionHashList){
                        //transactionCryptoStatus=getCryptoStatusFromOutgoingIntraActorPlugin(transactionHash);
                        System.out.println("CN Transaction Hash: "+transactionHash);
                        CryptoTransaction cryptoGenesisTransaction=getGenesisTransactionFromAssetVault(transactionHash);
                        transactionCryptoStatus= cryptoGenesisTransaction.getCryptoStatus();
                        assetIssuingTransactionDao.updateDigitalAssetCryptoStatusByTransactionHash(transactionHash, transactionCryptoStatus);
                        //String genesisTransaction=assetIssuingTransactionDao.getDigitalAssetGenesisTransactionByHash(transactionHash);
                        digitalAssetIssuingVault.deliverDigitalAssetMetadataToAssetWallet(cryptoGenesisTransaction, AssetBalanceType.BOOK);
                    }
                }

                if (isTransactionToBeNotified(CryptoStatus.REVERSED_ON_CRYPTO_NETWORK)){
                    transactionHashList=assetIssuingTransactionDao.getTransactionsHashByCryptoStatus(CryptoStatus.REVERSED_ON_CRYPTO_NETWORK);
                    for(String transactionHash: transactionHashList){
                        //transactionCryptoStatus=getCryptoStatusFromOutgoingIntraActorPlugin(transactionHash);
                        CryptoTransaction cryptoGenesisTransaction=getGenesisTransactionFromAssetVault(transactionHash);
                        transactionCryptoStatus= cryptoGenesisTransaction.getCryptoStatus();
                        assetIssuingTransactionDao.updateDigitalAssetCryptoStatusByTransactionHash(transactionHash, transactionCryptoStatus);
                    }
                }

                if (isTransactionToBeNotified(CryptoStatus.REVERSED_ON_BLOCKCHAIN)){
                    transactionHashList=assetIssuingTransactionDao.getTransactionsHashByCryptoStatus(CryptoStatus.REVERSED_ON_BLOCKCHAIN);
                    for(String transactionHash: transactionHashList){
                        //transactionCryptoStatus=getCryptoStatusFromOutgoingIntraActorPlugin(transactionHash);
                        CryptoTransaction cryptoGenesisTransaction=getGenesisTransactionFromAssetVault(transactionHash);
                        transactionCryptoStatus= cryptoGenesisTransaction.getCryptoStatus();
                        assetIssuingTransactionDao.updateDigitalAssetCryptoStatusByTransactionHash(transactionHash, transactionCryptoStatus);
                    }
                }

                if (isTransactionToBeNotified(CryptoStatus.ON_BLOCKCHAIN)){
                    transactionHashList=assetIssuingTransactionDao.getTransactionsHashByCryptoStatus(CryptoStatus.ON_BLOCKCHAIN);
                    for(String transactionHash: transactionHashList){
                        System.out.println("BCH Transaction Hash: "+transactionHash);
                        //transactionCryptoStatus=getCryptoStatusFromOutgoingIntraActorPlugin(transactionHash);
                        CryptoTransaction cryptoGenesisTransaction=getGenesisTransactionFromAssetVault(transactionHash);
                        transactionCryptoStatus= cryptoGenesisTransaction.getCryptoStatus();
                        assetIssuingTransactionDao.updateDigitalAssetCryptoStatusByTransactionHash(transactionHash, transactionCryptoStatus);
                        assetIssuingTransactionDao.updateDigitalAssetTransactionStatusByTransactionHash(transactionHash, TransactionStatus.DELIVERING);
                        String publicKey=this.assetIssuingTransactionDao.getPublicKeyByTransactionHash(transactionHash);
                        this.assetIssuingTransactionDao.updateAssetsGeneratedCounter(publicKey);
                        digitalAssetIssuingVault.deliverDigitalAssetMetadataToAssetWallet(cryptoGenesisTransaction, AssetBalanceType.AVAILABLE);
                    }
                }

                if (isReceivedDigitalAssets()){
                    List<String> genesisTransactionsFromAssetsReceived=getGenesisTransactionsFromDigitalAssetsReceived();
                    for(String genesisTransaction: genesisTransactionsFromAssetsReceived){
                        digitalAssetIssuingVault.deleteDigitalAssetMetadataFromLocalStorage(genesisTransaction);
                    }
                }

                if (isDeliveredDigitalAssets()){
                    List<String> transactionHashFromAssetsDelivered=assetIssuingTransactionDao.getTransactionHashByDeliveredStatus();
                    for(String transactionHash: transactionHashFromAssetsDelivered){
                        CryptoTransaction cryptoGenesisTransaction=getGenesisTransactionFromAssetVault(transactionHash);
                        String digitalAssetPublicKey=assetIssuingTransactionDao.getPublicKeyByTransactionHash(transactionHash);
                        if(digitalAssetIssuingVault.isAssetTransactionHashAvailableBalanceInAssetWallet(transactionHash, digitalAssetPublicKey)){
                            assetIssuingTransactionDao.updateDigitalAssetTransactionStatusByTransactionHash(transactionHash, TransactionStatus.RECEIVED);
                            continue;
                        }
                        digitalAssetIssuingVault.deliverDigitalAssetMetadataToAssetWallet(cryptoGenesisTransaction, AssetBalanceType.AVAILABLE);
                    }
                }

                if(!isPendingAssets()){
                    threadWorking=false;
                }


            } catch (CantExecuteDatabaseOperationException exception) {
                throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, exception, "Exception in asset Issuing monitor agent","Cannot execute database operation");
            } /*catch (OutgoingIntraActorCantGetCryptoStatusException exception) {
                throw new CantCheckAssetIssuingProgressException(exception,"Exception in asset Issuing monitor agent","Exception in OutgoingIntraActor plugin");
            }*/ catch (UnexpectedResultReturnedFromDatabaseException exception) {
                throw new CantCheckAssetIssuingProgressException(exception,"Exception in asset Issuing monitor agent","Unexpected result in database query");
            } catch (CantGetGenesisTransactionException exception){
                throw new CantCheckAssetIssuingProgressException(exception,"Exception in asset Issuing monitor agent","Cannot get genesis transaction from asset vault");
            } catch (CantDeleteDigitalAssetFromLocalStorageException exception) {
                this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_ISSUING_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            } catch (DAPException exception) {
                throw new CantCheckAssetIssuingProgressException(exception,"Exception in asset Issuing monitor agent","Cannot check the asset issuing progress");
            }

        }

        private boolean isTransactionToBeNotified(CryptoStatus cryptoStatus) throws CantExecuteQueryException {
            boolean isPending =assetIssuingTransactionDao.isPendingTransactions(cryptoStatus);
            return isPending;
        }

        private boolean isPendingEvents() throws CantExecuteQueryException {
            boolean isPending=assetIssuingTransactionDao.isPendingEvents();
            return isPending;
        }

        private List<String> getPendingEvents() throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
            return assetIssuingTransactionDao.getPendingEvents();
        }

        private boolean isReceivedDigitalAssets() throws CantExecuteQueryException {
            boolean isPending=assetIssuingTransactionDao.isReceivedDigitalAssets();
            return isPending;
        }

        private boolean isDeliveredDigitalAssets() throws CantExecuteQueryException {
            boolean isPending=assetIssuingTransactionDao.isDeliveredDigitalAssets();
            return isPending;
        }

        private List<String> getGenesisTransactionsFromDigitalAssetsReceived() throws CantCheckAssetIssuingProgressException, UnexpectedResultReturnedFromDatabaseException {
            return assetIssuingTransactionDao.getGenesisTransactionsFromDigitalAssetsReceived();
        }

        private boolean isPendingAssets() throws CantCheckAssetIssuingProgressException {
            return assetIssuingTransactionDao.isAnyPendingAsset();
        }

        //I comment this method, now I'm gonna use the asset vault to check the transaction status.
        /*private CryptoStatus getCryptoStatusFromOutgoingIntraActorPlugin(String transactionHash) throws OutgoingIntraActorCantGetCryptoStatusException {
            return outgoingIntraActorManager.getTransactionStatus(transactionHash);
        }*/
        /*private CryptoStatus getCryptoStatusFromAssetVault(String transactionHash) throws CantGetGenesisTransactionException {
            CryptoTransaction cryptoTransaction=assetVaultManager.getGenesisTransaction(transactionHash);
            return cryptoTransaction.getCryptoStatus();
        }*/

        //I left working this method for testing porpoises
        private CryptoTransaction getGenesisTransactionFromAssetVault(String transactionHash) throws CantGetGenesisTransactionException {
            //CryptoTransaction cryptoTransaction=assetVaultManager.getGenesisTransaction(transactionHash);
            //This mock is for testing porpoises
            CryptoTransaction mockCryptoTransaction=new CryptoTransaction();
            mockCryptoTransaction.setTransactionHash("d21633ba23f70118185227be58a63527675641ad37967e2aa461559f577aec43");
            mockCryptoTransaction.setCryptoStatus(CryptoStatus.ON_BLOCKCHAIN);
            return mockCryptoTransaction;
        }

    }
}
