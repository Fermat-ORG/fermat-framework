package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.CantGetOutgoingIntraActorTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantGetSendCryptoTransactionHashException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantSaveStatisticException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCreateDigitalAssetTransactionException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingDAO;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.functional.AssetMetadataFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.functional.IssuingRecord;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.functional.MetadataRecord;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/03/16.
 */
public class AssetIssuingMonitorAgent extends FermatAgent {

    //VARIABLE DECLARATION

    private final ErrorManager errorManager;
    private final BitcoinNetworkManager bitcoinNetworkManager;
    private final OutgoingIntraActorManager outgoingIntraActorManager;
    private final ActorAssetIssuerManager actorAssetIssuerManager;
    private final AssetVaultManager assetVaultManager;
    private final BitcoinWalletManager bitcoinWalletManager;
    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final IntraWalletUserIdentityManager intraWalletUserIdentityManager;
    private final AssetIssuerWalletManager assetIssuerWalletManager;
    private final AssetIssuingDAO dao;

    private IssuingAgent issuingAgent;
    private FactoryAgent factoryAgent;
    private ExecutorService executor;
    public static final int MINIMUN_KEY_COUNT = 10;
    public static final int ACCEPTABLE_KEY_COUNT = 100;
    private static final int WAIT_TIME = 5 * 1000; //SECONDS

    //CONSTRUCTORS

    public AssetIssuingMonitorAgent(ErrorManager errorManager, BitcoinNetworkManager bitcoinNetworkManager, OutgoingIntraActorManager outgoingIntraActorManager, ActorAssetIssuerManager actorAssetIssuerManager, AssetVaultManager assetVaultManager, BitcoinWalletManager bitcoinWalletManager, CryptoAddressBookManager cryptoAddressBookManager, IntraWalletUserIdentityManager intraWalletUserIdentityManager, AssetIssuerWalletManager assetIssuerWalletManager, AssetIssuingDAO dao) {
        this.errorManager = errorManager;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.outgoingIntraActorManager = outgoingIntraActorManager;
        this.actorAssetIssuerManager = actorAssetIssuerManager;
        this.assetVaultManager = assetVaultManager;
        this.bitcoinWalletManager = bitcoinWalletManager;
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

        private void doTheMainTask() throws CantLoadTableToMemoryException, CantGetDigitalAssetFromLocalStorageException, RecordsNotFoundException, InvalidParameterException, CantUpdateRecordException, CantGetCryptoTransactionException, CantCreateDigitalAssetFileException, CantGetBroadcastStatusException, CantGetOutgoingIntraActorTransactionManagerException, CantSaveStatisticException, CantLoadWalletException, OutgoingIntraActorCantGetSendCryptoTransactionHashException, CantDeliverDigitalAssetToAssetWalletException, CantCreateDigitalAssetTransactionException {
            checkPendingEvents();
            checkSendingBitcoins();
        }

        private void checkPendingEvents() throws CantLoadTableToMemoryException, RecordsNotFoundException, InvalidParameterException, CantGetDigitalAssetFromLocalStorageException, CantGetCryptoTransactionException, CantUpdateRecordException, CantDeliverDigitalAssetToAssetWalletException, CantCreateDigitalAssetTransactionException {
            for (String eventId : dao.getPendingEvents()) {
                switch (dao.getEventType(eventId)) {
                    case INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER: {
                        for (MetadataRecord record : dao.getMetadataForStatus(IssuingStatus.WAITING_CONFIRMATION)) {
                            CryptoTransaction cryptoGenesisTransaction = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, record.getAssetMetadata().getLastTransactionHash(), CryptoStatus.ON_CRYPTO_NETWORK);
                            if (cryptoGenesisTransaction == null) {
                                System.out.println("ASSET ISSUING The genesis transaction " + record.getAssetMetadata().getLastTransactionHash() + " could not be found in crypto network");
                                continue;
                            }
                            dao.updateWalletBalance(cryptoGenesisTransaction, record.getTransactionId(), BalanceType.BOOK);
                            dao.updateMetadataIssuingStatus(record.getTransactionId(), IssuingStatus.ON_CRYPTO_NETWORK);
                        }
                        break;
                    }

                    case INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER: {
                        for (MetadataRecord record : dao.getMetadataForStatus(IssuingStatus.ON_CRYPTO_NETWORK)) {
                            CryptoTransaction cryptoGenesisTransaction = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, record.getAssetMetadata().getLastTransactionHash(), CryptoStatus.ON_BLOCKCHAIN);
                            if (cryptoGenesisTransaction == null) {
                                System.out.println("ASSET ISSUING The genesis transaction " + record.getAssetMetadata().getLastTransactionHash() + " in crypto network is null");
                                continue;
                            }
                            dao.updateWalletBalance(cryptoGenesisTransaction, record.getTransactionId(), BalanceType.AVAILABLE);
                            dao.updateMetadataIssuingStatus(record.getTransactionId(), IssuingStatus.ON_BLOCK_CHAIN);
                            dao.newAssetGenerated(record.getAssetMetadata().getDigitalAsset().getPublicKey());
                        }
                        break;
                    }
                }
                dao.notifyEvent(eventId);
            }
        }

        private void checkSendingBitcoins() throws CantGetDigitalAssetFromLocalStorageException, InvalidParameterException, CantLoadTableToMemoryException, CantGetOutgoingIntraActorTransactionManagerException, OutgoingIntraActorCantGetSendCryptoTransactionHashException, CantGetCryptoTransactionException, CantCreateDigitalAssetFileException, CantLoadWalletException, CantSaveStatisticException, RecordsNotFoundException, CantUpdateRecordException, CantGetBroadcastStatusException {
            for (MetadataRecord record : dao.getMetadataForStatus(IssuingStatus.SENDING_CRYPTO)) {
                String genesisTransaction = outgoingIntraActorManager.getTransactionManager().getSendCryptoTransactionHash(record.getOutgoingId());
                if (!Validate.isValidString(genesisTransaction) || genesisTransaction.equals("UNKNOWN YET")) {
                    System.out.println("ASSET ISSUING is null - continue asking");
                    continue;
                }
                CryptoTransaction cryptoTransaction = bitcoinNetworkManager.getCryptoTransaction(genesisTransaction);
                dao.updateGenesisTx(record.getTransactionId(), record.getAssetMetadata(), cryptoTransaction);
                assetIssuerWalletManager.loadAssetIssuerWallet(WalletUtilities.WALLET_PUBLIC_KEY, cryptoTransaction.getBlockchainNetworkType()).createdNewAsset(record.getAssetMetadata());
                dao.updateMetadataIssuingStatus(record.getTransactionId(), IssuingStatus.WAITING_CONFIRMATION);
            }

            for (MetadataRecord record : dao.getMetadataForStatus(IssuingStatus.WAITING_CONFIRMATION)) {
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
                int missingAssets = record.getAssetsToGenerate() - record.getAssetsGenerated();
                for (int i = 0; i < missingAssets; i++) {
                    if (assetVaultManager.getAvailableKeyCount() <= MINIMUN_KEY_COUNT) {
                        dao.unProcessingAsset(record.getAsset().getPublicKey());
                        outOfKeys = true;
                        return;
                    } else {
                        AssetMetadataFactory assetMetadataFactory = new AssetMetadataFactory(record, assetVaultManager, issuer, getIntraUser(), dao, outgoingIntraActorManager.getTransactionManager(), cryptoAddressBookManager, bitcoinWalletManager);
                        Future<Boolean> result = executor.submit(assetMetadataFactory);
                    }
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
