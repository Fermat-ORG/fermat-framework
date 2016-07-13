package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;

import com.bitdubai.fermat_bch_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetSendCryptoTransactionHashException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantSaveStatisticException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.AssetMetadataFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.IssuingRecord;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/03/16.
 */

//TODO: Victor no podes hacer esto, este agente está todo el tiempo corriendo sin hacer nada y pidiendo los actores del identity de ccp por lo que veo
public class AssetIssuingMonitorAgent extends FermatAgent {

    //VARIABLE DECLARATION

    private final IncomingCryptoManager incomingCryptoManager;
    private final TransactionProtocolManager<CryptoTransaction> protocolManager;
    private final ErrorManager errorManager;
    private final BlockchainManager bitcoinNetworkManager;
    private final OutgoingIntraActorManager outgoingIntraActorManager;
    private final ActorAssetIssuerManager actorAssetIssuerManager;
    private final AssetVaultManager assetVaultManager;
    private final CryptoWalletManager cryptoWalletManager;
    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final IntraWalletUserIdentityManager intraWalletUserIdentityManager;
    private final AssetIssuerWalletManager assetIssuerWalletManager;
    private final org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO dao;

    private IssuingAgent issuingAgent;
    private FactoryAgent factoryAgent;
    private ExecutorService executor;
    public static final int MINIMUN_KEY_COUNT = 10;
    public static final int ACCEPTABLE_KEY_COUNT = 100;
    private static final int WAIT_TIME = 5 * 1000; //SECONDS

    //CONSTRUCTORS

    public AssetIssuingMonitorAgent(IncomingCryptoManager incomingCryptoManager, ErrorManager errorManager, BlockchainManager bitcoinNetworkManager, OutgoingIntraActorManager outgoingIntraActorManager, ActorAssetIssuerManager actorAssetIssuerManager, AssetVaultManager assetVaultManager, CryptoWalletManager cryptoWalletManager, CryptoAddressBookManager cryptoAddressBookManager, IntraWalletUserIdentityManager intraWalletUserIdentityManager, AssetIssuerWalletManager assetIssuerWalletManager, org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO dao) {
        this.incomingCryptoManager = incomingCryptoManager;
        this.protocolManager = incomingCryptoManager.getTransactionManager();
        this.errorManager = errorManager;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.outgoingIntraActorManager = outgoingIntraActorManager;
        this.actorAssetIssuerManager = actorAssetIssuerManager;
        this.assetVaultManager = assetVaultManager;
        this.cryptoWalletManager = cryptoWalletManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
        this.assetIssuerWalletManager = assetIssuerWalletManager;
        this.dao = dao;
    }


    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            executor = Executors.newFixedThreadPool(5);
            issuingAgent = new IssuingAgent();
            factoryAgent = new FactoryAgent();
            Thread agentThread = new Thread(issuingAgent, this.getClass().getSimpleName() + " - Issuing Agent");
            agentThread.start();
            Thread factoryThread = new Thread(factoryAgent, this.getClass().getSimpleName() + " - Factory Agent");
            factoryThread.start();
            super.start();
        } catch (Exception e) {
            throw new CantStartAgentException(FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public void stop() throws CantStopAgentException {
        try {
            issuingAgent.stopAgent();
            factoryAgent.stopAgent();
            factoryAgent = null;
            issuingAgent = null; //RELEASE RESOURCES UNTIL WE START IT AGAIN.
            executor.shutdown();
            super.stop();
        } catch (Exception e) {
            throw new CantStopAgentException(FermatException.wrapException(e), null, null);
        }
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES

    private class IssuingAgent implements Runnable {
        private boolean agentRunning;

        public IssuingAgent() {
            startAgent();
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            while (agentRunning) {
                try {
                    doTheMainTask();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorManager.reportUnexpectedPluginException(Plugins.ASSET_ISSUING, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                } finally {
                    try {
                        Thread.sleep(WAIT_TIME);
                    } catch (InterruptedException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.ASSET_ISSUING, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        agentRunning = false;
                    }
                }
            }
        }

        private void doTheMainTask() throws CantLoadTableToMemoryException, CantGetDigitalAssetFromLocalStorageException, RecordsNotFoundException, InvalidParameterException, CantUpdateRecordException, CantGetCryptoTransactionException, CantCreateDigitalAssetFileException, CantGetBroadcastStatusException, CantGetOutgoingIntraActorTransactionManagerException, CantSaveStatisticException, CantLoadWalletException, OutgoingIntraActorCantGetSendCryptoTransactionHashException, CantDeliverDigitalAssetToAssetWalletException, org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.exceptions.CantCreateDigitalAssetTransactionException, CantConfirmTransactionException, CantDeliverPendingTransactionsException {
            checkPendingEvents();
            checkSendingBitcoins();
        }

        private void checkPendingEvents() throws CantLoadTableToMemoryException, RecordsNotFoundException, InvalidParameterException, CantGetDigitalAssetFromLocalStorageException, CantGetCryptoTransactionException, CantUpdateRecordException, CantDeliverDigitalAssetToAssetWalletException, org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.exceptions.CantCreateDigitalAssetTransactionException, CantDeliverPendingTransactionsException, CantConfirmTransactionException {
            for (String eventId : dao.getPendingEvents()) {
                switch (dao.getEventType(eventId)) {
                    case INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER: {
                        for (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.MetadataRecord record : dao.getMetadataForStatus(IssuingStatus.WAITING_CONFIRMATION)) {
                            Transaction<CryptoTransaction> transaction = transactionForHash(record.getAssetMetadata());
                            if (transaction == null) {
                                System.out.println("ASSET ISSUING The genesis transaction " + record.getAssetMetadata().getLastTransactionHash() + " could not be found in crypto network");
                                continue;
                            }
                            dao.updateWalletBalance(transaction.getInformation(), record.getTransactionId(), BalanceType.BOOK);
                            dao.updateMetadataIssuingStatus(record.getTransactionId(), IssuingStatus.ON_CRYPTO_NETWORK);
                            protocolManager.confirmReception(transaction.getTransactionID());
                        }
                        break;
                    }

                    case INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER: {
                        for (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.MetadataRecord record : dao.getMetadataForStatus(IssuingStatus.ON_CRYPTO_NETWORK)) {
                            Transaction<CryptoTransaction> transaction = transactionForHash(record.getAssetMetadata());
                            if (transaction == null) {
                                System.out.println("ASSET ISSUING The genesis transaction " + record.getAssetMetadata().getLastTransactionHash() + " could not be found in crypto network");
                                continue;
                            }
                            dao.updateWalletBalance(transaction.getInformation(), record.getTransactionId(), BalanceType.AVAILABLE);
                            dao.updateMetadataIssuingStatus(record.getTransactionId(), IssuingStatus.ON_BLOCK_CHAIN);
                            dao.assetCompleted(record.getAssetMetadata().getDigitalAsset().getPublicKey());
                            protocolManager.confirmReception(transaction.getTransactionID());
                        }
                        break;
                    }
                }
                dao.notifyEvent(eventId);
            }
        }

        private void checkSendingBitcoins() throws CantGetDigitalAssetFromLocalStorageException, InvalidParameterException, CantLoadTableToMemoryException, CantGetOutgoingIntraActorTransactionManagerException, OutgoingIntraActorCantGetSendCryptoTransactionHashException, CantGetCryptoTransactionException, CantCreateDigitalAssetFileException, CantLoadWalletException, CantSaveStatisticException, RecordsNotFoundException, CantUpdateRecordException, CantGetBroadcastStatusException {
            for (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.MetadataRecord record : dao.getMetadataForStatus(IssuingStatus.SENDING_CRYPTO)) {
                String genesisTransaction = outgoingIntraActorManager.getTransactionManager().getSendCryptoTransactionHash(record.getOutgoingId());
                if (!Validate.isValidString(genesisTransaction) || genesisTransaction.equals("UNKNOWN YET")) {
                    System.out.println("ASSET ISSUING is null - continue asking");
                    continue;
                }
                CryptoTransaction cryptoTransaction = bitcoinNetworkManager.getCryptoTransaction(genesisTransaction, CryptoTransactionType.INCOMING, record.getAssetMetadata().getDigitalAsset().getGenesisAddress());
                dao.updateGenesisTx(record.getTransactionId(), record.getAssetMetadata(), cryptoTransaction);
                assetIssuerWalletManager.loadAssetIssuerWallet(WalletUtilities.WALLET_PUBLIC_KEY, cryptoTransaction.getBlockchainNetworkType()).createdNewAsset(record.getAssetMetadata());
                dao.updateMetadataIssuingStatus(record.getTransactionId(), IssuingStatus.WAITING_CONFIRMATION);
                dao.unProcessingAsset(record.getAssetMetadata().getDigitalAsset().getPublicKey());
            }

            for (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional.MetadataRecord record : dao.getMetadataForStatus(IssuingStatus.WAITING_CONFIRMATION)) {
                BroadcastStatus broadcastStatus = bitcoinNetworkManager.getBroadcastStatus(record.getAssetMetadata().getLastTransactionHash());
                switch (broadcastStatus.getStatus()) {
                    case CANCELLED:
                        System.out.println("TRANSACTION CANCELLED!!: " + record.getAssetMetadata());
                        break;
                    default:
                        //I don't care, everything went ok.
                        break;
                }
            }
        }

        public boolean isAgentRunning() {
            return agentRunning;
        }

        public void stopAgent() {
            agentRunning = false;
        }

        public void startAgent() {
            agentRunning = true;
        }

        private Transaction<CryptoTransaction> transactionForHash(DigitalAssetMetadata metadata) throws CantDeliverPendingTransactionsException {
            List<Transaction<CryptoTransaction>> pendingTransactions = protocolManager.getPendingTransactions(Specialist.ASSET_ISSUER_SPECIALIST);
            for (Transaction<CryptoTransaction> transaction : pendingTransactions) {
                if (transaction.getInformation().getTransactionHash().equals(metadata.getLastTransactionHash())) {
                    return transaction;
                }
            }
            return null;
        }

    }

    public class FactoryAgent implements Runnable {

        private volatile ActorAssetIssuer issuer;
        private volatile IntraWalletUserIdentity intraActor;
        private boolean running;
        private boolean outOfKeys;

        public FactoryAgent() {
            startAgent();
            outOfKeys = false;
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            while (running) {
                try {
                    doTheMainTask();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(WAIT_TIME);
                    } catch (InterruptedException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.ASSET_ISSUING, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                        running = false;
                    }
                }
            }
        }

        private void doTheMainTask() throws CantGetAssetIssuerActorsException, InvalidParameterException, InterruptedException, ExecutionException, CantUpdateRecordException, RecordsNotFoundException, CantGetDigitalAssetFromLocalStorageException, CantLoadTableToMemoryException, CantGetOutgoingIntraActorTransactionManagerException {
            issuer = actorAssetIssuerManager.getActorAssetIssuer();
            intraActor = getIntraUser();
            if (checkKeyCount()) {
                checkGeneratingAssets();
            }
        }

        private void checkGeneratingAssets() throws ExecutionException, InterruptedException, CantGetDigitalAssetFromLocalStorageException, InvalidParameterException, CantLoadTableToMemoryException, RecordsNotFoundException, CantUpdateRecordException, CantGetOutgoingIntraActorTransactionManagerException {
            for (IssuingRecord record : dao.getRecordsForStatus(IssuingStatus.ISSUING)) {
                dao.processingAsset(record.getAsset().getPublicKey());
                if (assetVaultManager.getAvailableKeyCount() <= MINIMUN_KEY_COUNT) {
                    dao.unProcessingAsset(record.getAsset().getPublicKey());
                    outOfKeys = true;
                    return;
                } else {
                    AssetMetadataFactory assetMetadataFactory = new AssetMetadataFactory(record, assetVaultManager, issuer, intraActor, dao, outgoingIntraActorManager.getTransactionManager(), cryptoAddressBookManager, cryptoWalletManager);
                    Future<Boolean> result = executor.submit(assetMetadataFactory);
                }
            }
        }

        public IntraWalletUserIdentity getIntraUser() {
            try {
                if (intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().isEmpty()) {
                    return null;
                }
                return intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().get(0);
            } catch (CantListIntraWalletUsersException e) {
                return null;
            }
        }

        private boolean checkKeyCount() {
            if (outOfKeys) {
                if (assetVaultManager.getAvailableKeyCount() >= ACCEPTABLE_KEY_COUNT) {
                    outOfKeys = false;
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        }

        public void stopAgent() {
            running = false;
        }

        public void startAgent() {
            running = true;
        }
    }
}
