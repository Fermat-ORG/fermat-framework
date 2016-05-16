package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.ReceptionStatus;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetMetadataContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.DAPContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.DistributionStatusUpdateContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendMessageException;
import org.fermat.fermat_dap_api.layer.all_definition.util.ActorUtils;
import org.fermat.fermat_dap_api.layer.dap_actor.DAPActor;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendTransactionNewStatusNotificationException;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingTransactionNotificationAgent;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.AssetReceptionDigitalAssetTransactionPluginRoot;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.exceptions.CantCheckAssetReceptionProgressException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.DigitalAssetReceptionVault;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.DigitalAssetReceptor;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.database.AssetReceptionDao;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/10/15.
 */
public class AssetReceptionMonitorAgent implements Agent {

    private Thread agentThread;
    private final LogManager logManager;
    AssetReceptionDigitalAssetTransactionPluginRoot assetReceptionDigitalAssetTransactionPluginRoot;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final UUID pluginId;
    private final DigitalAssetReceptionVault digitalAssetReceptionVault;
    private final org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.DigitalAssetReceptor digitalAssetReceptor;
    private final AssetTransmissionNetworkServiceManager assetTransmissionManager;
    private final BitcoinNetworkManager bitcoinNetworkManager;
    private final ActorAssetUserManager actorAssetUserManager;
    private final ActorAssetIssuerManager assetIssuerManager;
    private final ActorAssetRedeemPointManager redeemPointManager;
    private final IncomingCryptoManager incomingCryptoManager;
    private final TransactionProtocolManager<CryptoTransaction> protocolManager;

    public AssetReceptionMonitorAgent(PluginDatabaseSystem pluginDatabaseSystem,
                                      AssetReceptionDigitalAssetTransactionPluginRoot assetReceptionDigitalAssetTransactionPluginRoot,
                                      UUID pluginId,
                                      LogManager logManager,
                                      BitcoinNetworkManager bitcoinNetworkManager,
                                      AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager,
                                      ActorAssetUserManager actorAssetUserManager,
                                      ActorAssetIssuerManager issuerManager,
                                      ActorAssetRedeemPointManager redeemPointManager,
                                      DigitalAssetReceptor digitalAssetReceptor,
                                      DigitalAssetReceptionVault digitalAssetReceptionVault,
                                      IncomingCryptoManager incomingCryptoManager) {

        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.assetReceptionDigitalAssetTransactionPluginRoot = assetReceptionDigitalAssetTransactionPluginRoot;
        this.pluginId = pluginId;
        this.logManager = logManager;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.assetTransmissionManager = assetTransmissionNetworkServiceManager;
        this.actorAssetUserManager = actorAssetUserManager;
        this.assetIssuerManager = issuerManager;
        this.redeemPointManager = redeemPointManager;
        this.digitalAssetReceptor = digitalAssetReceptor;
        this.digitalAssetReceptionVault = digitalAssetReceptionVault;
        this.digitalAssetReceptionVault.setActorAssetUserManager(actorAssetUserManager);
        this.incomingCryptoManager = incomingCryptoManager;
        this.protocolManager = incomingCryptoManager.getTransactionManager();
    }

    @Override
    public void start() throws CantStartAgentException {
        try {
            MonitorAgent monitorAgent = new MonitorAgent(assetReceptionDigitalAssetTransactionPluginRoot);
            monitorAgent.setPluginDatabaseSystem(this.pluginDatabaseSystem);
            this.agentThread = new Thread(monitorAgent, "Asset Reception MonitorAgent");
            this.agentThread.start();
        } catch (Exception e) {
            throw new CantStartAgentException(e, null, null);
        }
    }

    @Override
    public void stop() {
        this.agentThread.interrupt();
    }


    private class MonitorAgent implements AssetIssuingTransactionNotificationAgent, DealsWithPluginDatabaseSystem, Runnable {

        AssetReceptionDigitalAssetTransactionPluginRoot assetReceptionDigitalAssetTransactionPluginRoot;
        PluginDatabaseSystem pluginDatabaseSystem;
        public final int SLEEP_TIME = AssetIssuingTransactionNotificationAgent.AGENT_SLEEP_TIME;
        int iterationNumber = 0;
        AssetReceptionDao assetReceptionDao;

        public MonitorAgent(AssetReceptionDigitalAssetTransactionPluginRoot assetReceptionDigitalAssetTransactionPluginRoot) {
            this.assetReceptionDigitalAssetTransactionPluginRoot = assetReceptionDigitalAssetTransactionPluginRoot;
        }

        @Override
        public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem) {
            this.pluginDatabaseSystem = pluginDatabaseSystem;
        }

        @Override
        public void run() {

            logManager.log(AssetReceptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Reception Protocol Notification Agent: running...", null, null);
            while (true) {
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

                    logManager.log(AssetReceptionDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Iteration number " + iterationNumber, null, null);
                    doTheMainTask();
                } catch (CantCheckAssetReceptionProgressException | CantExecuteQueryException e) {
                    assetReceptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        private void doTheMainTask() throws CantExecuteQueryException, CantCheckAssetReceptionProgressException {
            try {
                assetReceptionDao = new AssetReceptionDao(pluginDatabaseSystem, pluginId);
                if (assetReceptionDao.isPendingNetworkLayerEvents()) {
                    System.out.println("ASSET RECEPTION is network layer pending events");
                    //In this plugin we listen both from Asset Transfer and Asset Distribution
                    List<DAPMessage> newMetadata = assetTransmissionManager.getUnreadDAPMessageBySubject(DAPMessageSubject.ASSET_RECEPTION);
                    for (DAPMessage message : newMetadata) {
                        AssetMetadataContentMessage content = (AssetMetadataContentMessage) message.getMessageContent();
                        DigitalAssetMetadata digitalAssetMetadataReceived = content.getAssetMetadata();
                        String genesisTransaction = digitalAssetMetadataReceived.getGenesisTransaction();
                        //We store the sender of this message on its respective plugin
                        ActorUtils.storeDAPActor(message.getActorSender(), actorAssetUserManager, redeemPointManager, assetIssuerManager);
                        //And now I am the last owner!
                        digitalAssetMetadataReceived.setLastOwner(actorAssetUserManager.getActorAssetUser());
                        if (assetReceptionDao.isGenesisTransactionRegistered(genesisTransaction)) {
                            System.out.println("ASSET RECEPTION This genesisTransaction is already registered in database: " + genesisTransaction);
                            digitalAssetReceptor.verifyAsset(digitalAssetMetadataReceived);
                        } else {
                            System.out.println("ASSET RECEPTION Digital Asset Metadata Received: " + digitalAssetMetadataReceived);
                            digitalAssetReceptor.receiveDigitalAssetMetadata(digitalAssetMetadataReceived, message.getActorSender().getActorPublicKey(), ActorUtils.getActorType(message.getActorSender()));
                        }
                        assetTransmissionManager.confirmReception(message);
                    }
                    assetReceptionDao.updateEventStatus(assetReceptionDao.getPendingNetworkLayerEvents().get(0));
                }

                if (assetReceptionDao.isAcceptedAssets()) {
                    System.out.println("ASSET RECEPTION there are accepted assets");
                    checkTransactionsByReceptionStatus(ReceptionStatus.ASSET_ACCEPTED);
                }

                if (assetReceptionDao.isRejectedByContract()) {
                    System.out.println("ASSET RECEPTION there are rejected by contract assets");
                    checkTransactionsByReceptionStatus(ReceptionStatus.REJECTED_BY_CONTRACT);
                }

                if (assetReceptionDao.isRejectedByHash()) {
                    System.out.println("ASSET RECEPTION there are rejected by hash assets");
                    checkTransactionsByReceptionStatus(ReceptionStatus.REJECTED_BY_HASH);
                }


                if (assetReceptionDao.isPendingIncomingCryptoEvents()) {
                    System.out.println("ASSET RECEPTION is crypto pending events");
                    checkPendingTransactions();
                    List<String> pendingEventsList = assetReceptionDao.getIncomingCryptoEvents();
                    System.out.println("ASSET RECEPTION is " + pendingEventsList.size() + " events");
                }

            } catch (CantExecuteDatabaseOperationException e) {
                assetReceptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantExecuteQueryException(CantExecuteDatabaseOperationException.DEFAULT_MESSAGE, e, "Exception in asset distribution monitor agent", "Cannot execute database operation");
            } catch (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.exceptions.CantReceiveDigitalAssetException e) {
                assetReceptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckAssetReceptionProgressException(e, "Exception in asset reception monitor agent", "Cannot receive digital asset");
            } catch (CantAssetUserActorNotFoundException e) {
                assetReceptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckAssetReceptionProgressException(e, "Exception in asset reception monitor agent", "Cannot find Asset user actor");
            } catch (CantSendTransactionNewStatusNotificationException e) {
                assetReceptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckAssetReceptionProgressException(e, "Exception in asset reception monitor agent", "Cannot send new status to asset issuer");
            } catch (CantGetAssetUserActorsException e) {
                assetReceptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckAssetReceptionProgressException(e, "Exception in asset reception monitor agent", "Cannot get asset actor user");
            } catch (UnexpectedResultReturnedFromDatabaseException e) {
                assetReceptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckAssetReceptionProgressException(e, "Exception in asset reception monitor agent", "Unexpected results in database query");
            } catch (CantGetAssetIssuerActorsException e) {
                assetReceptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckAssetReceptionProgressException(e, "Exception in asset reception monitor agent", "Cannot get asset actor issuer");
            } catch (CantDeliverDigitalAssetToAssetWalletException e) {
                assetReceptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckAssetReceptionProgressException(e, "Exception in asset reception monitor agent", "Cannot deliver the digital asset metadata to asset user wallet");
            } catch (CantGetCryptoTransactionException e) {
                assetReceptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCheckAssetReceptionProgressException(e, "Exception in asset reception monitor agent", "Cannot get the genesis transaction from Crypto Network");
            } catch (DAPException | CantRegisterCreditException | CantRegisterDebitException | CantLoadWalletException | CantGetTransactionsException | CantDeliverPendingTransactionsException | CantConfirmTransactionException | CantSetObjectException e) {
                assetReceptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                e.printStackTrace();
            }
        }

        /**
         * This method check the pending transactions registered in database and take actions according to CryptoStatus
         *
         * @throws CantExecuteQueryException
         * @throws CantCheckAssetReceptionProgressException
         * @throws CantGetCryptoTransactionException
         * @throws UnexpectedResultReturnedFromDatabaseException
         * @throws CantGetDigitalAssetFromLocalStorageException
         * @throws CantDeliverDigitalAssetToAssetWalletException
         */
        private void checkPendingTransactions() throws CantExecuteQueryException,
                CantCheckAssetReceptionProgressException,
                CantGetCryptoTransactionException,
                UnexpectedResultReturnedFromDatabaseException,
                //CantGetDigitalAssetFromLocalStorageException,
                CantDeliverDigitalAssetToAssetWalletException, CantGetDigitalAssetFromLocalStorageException, CantCreateDigitalAssetFileException, CantGetTransactionsException, CantGetAssetUserActorsException, CantRegisterDebitException, CantAssetUserActorNotFoundException, CantLoadWalletException, CantGetAssetIssuerActorsException, CantRegisterCreditException, CantDeliverPendingTransactionsException, CantConfirmTransactionException {
            System.out.println("ASSET RECEPTION is crypto pending events");
            List<String> eventIdList = assetReceptionDao.getIncomingCryptoEvents();
            System.out.println("ASSET RECEPTION is " + eventIdList.size() + " events");
            String eventType;
            List<String> genesisTransactionList;
            for (String eventId : eventIdList) {
                System.out.println("ASSET RECEPTION event Id: " + eventId);
                eventType = assetReceptionDao.getEventTypeById(eventId);
                System.out.println("ASSET RECEPTION event Type: " + eventType);
                if (eventType.equals(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER.getCode())) {
                    if (isTransactionToBeNotified(CryptoStatus.PENDING_SUBMIT)) {
                        genesisTransactionList = assetReceptionDao.getGenesisTransactionListByCryptoStatus(CryptoStatus.PENDING_SUBMIT);
                        System.out.println("ASSET RECEPTION genesisTransactionList on pending submit has " + genesisTransactionList.size() + " events");
                        for (String genesisTransaction : genesisTransactionList) {
                            DigitalAssetMetadata metadata = digitalAssetReceptionVault.getDigitalAssetMetadataFromLocalStorage(genesisTransaction);
                            System.out.println("ASSET RECEPTION CN genesis transaction: " + genesisTransaction);
                            Transaction<CryptoTransaction> transaction = transactionForHash(metadata);
                            if (transaction == null) {
                                System.out.println("ASSET RECEPTION the genesis transaction from Crypto Network is null");
                                continue;
                            }
                            String actorIssuerPublicKey = assetReceptionDao.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);
                            Actors senderType = assetReceptionDao.getSenderTypeByGenesisTransaction(genesisTransaction);
                            digitalAssetReceptionVault.updateWalletBalance(metadata, transaction.getInformation(), BalanceType.BOOK, TransactionType.CREDIT, DAPTransactionType.RECEPTION, actorIssuerPublicKey, senderType, WalletUtilities.DEFAULT_MEMO_DISTRIBUTION);
                            assetReceptionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.ON_CRYPTO_NETWORK);
                            protocolManager.confirmReception(transaction.getTransactionID());
                        }
                    }
                }
                if (eventType.equals(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER.getCode())) {
                    if (isTransactionToBeNotified(CryptoStatus.ON_CRYPTO_NETWORK)) {
                        genesisTransactionList = assetReceptionDao.getGenesisTransactionListByCryptoStatus(CryptoStatus.ON_CRYPTO_NETWORK);
                        System.out.println("ASSET RECEPTION genesisTransactionList has " + genesisTransactionList.size() + " events");
                        for (String genesisTransaction : genesisTransactionList) {
                            System.out.println("ASSET RECEPTION BCH Transaction Hash: " + genesisTransaction);
                            DigitalAssetMetadata metadata = digitalAssetReceptionVault.getDigitalAssetMetadataFromLocalStorage(genesisTransaction);
                            System.out.println("ASSET RECEPTION CN genesis transaction: " + genesisTransaction);
                            Transaction<CryptoTransaction> transaction = transactionForHash(metadata);
                            if (transaction == null) {
                                System.out.println("ASSET RECEPTION the genesis transaction from Crypto Network is null");
                                continue;
                            }
                            assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.CRYPTO_RECEIVED, genesisTransaction);
                            String actorIssuerPublicKey = assetReceptionDao.getActorUserPublicKeyByGenesisTransaction(genesisTransaction);
                            metadata = digitalAssetReceptionVault.updateMetadataTransactionChain(genesisTransaction, transaction.getInformation());
                            Actors senderType = assetReceptionDao.getSenderTypeByGenesisTransaction(genesisTransaction);
                            digitalAssetReceptionVault.updateWalletBalance(metadata, transaction.getInformation(), BalanceType.AVAILABLE, TransactionType.CREDIT, DAPTransactionType.RECEPTION, actorIssuerPublicKey, senderType, WalletUtilities.DEFAULT_MEMO_DISTRIBUTION);
                            assetReceptionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.ON_BLOCKCHAIN);
                            protocolManager.confirmReception(transaction.getTransactionID());
                        }
                    }
                }
                assetReceptionDao.updateEventStatus(eventId);
            }
        }

        private void checkTransactionsByReceptionStatus(ReceptionStatus receptionStatus) throws CantAssetUserActorNotFoundException, CantGetAssetUserActorsException, CantCheckAssetReceptionProgressException, UnexpectedResultReturnedFromDatabaseException, CantGetAssetIssuerActorsException, CantSendTransactionNewStatusNotificationException, CantExecuteQueryException, CantSetObjectException, CantSendMessageException {
            DistributionStatus distributionStatus = DistributionStatus.ASSET_REJECTED_BY_CONTRACT;

            List<String> genesisTransactionList;
            ActorAssetUser actorAssetUser = actorAssetUserManager.getActorAssetUser();
            if (receptionStatus.getCode().equals(ReceptionStatus.ASSET_ACCEPTED.getCode())) {
                distributionStatus = DistributionStatus.ASSET_ACCEPTED;
            }
            if (receptionStatus.getCode().equals(ReceptionStatus.REJECTED_BY_HASH.getCode())) {
                distributionStatus = DistributionStatus.ASSET_REJECTED_BY_HASH;
            }
            if (receptionStatus.getCode().equals(ReceptionStatus.REJECTED_BY_CONTRACT.getCode())) {
                distributionStatus = DistributionStatus.ASSET_REJECTED_BY_CONTRACT;
            }
            genesisTransactionList = assetReceptionDao.getGenesisTransactionByReceptionStatus(receptionStatus);
            for (String genesisTransaction : genesisTransactionList) {
                String senderPublicKey = assetReceptionDao.getSenderIdByGenesisTransaction(genesisTransaction);
                Actors senderType = assetReceptionDao.getSenderTypeByGenesisTransaction(genesisTransaction);
                System.out.println("ASSET RECEPTION Genesis transaction " + receptionStatus + ":" + genesisTransaction);
                System.out.println("ASSET RECEPTION sender id  " + senderPublicKey + " - Type: " + senderType);

                DAPContentMessage content = new DistributionStatusUpdateContentMessage(distributionStatus, genesisTransaction);
                DAPMessageSubject subject;
                DAPActor receiver = ActorUtils.getActorFromPublicKey(senderPublicKey, senderType, actorAssetUserManager, redeemPointManager, assetIssuerManager);
                //There is no human way that this throws NPE!... I'll be debugging this soon, ik
                switch (ActorUtils.getActorType(receiver)) {
                    case DAP_ASSET_ISSUER:
                        subject = DAPMessageSubject.ASSET_DISTRIBUTION;
                        break;
                    case DAP_ASSET_USER:
                        subject = DAPMessageSubject.ASSET_TRANSFER;
                        break;
                    default:
                        subject = DAPMessageSubject.ASSET_TRANSFER;
                        break;
                }
                DAPMessage message = new DAPMessage(content, actorAssetUser, receiver, subject);

                assetTransmissionManager.sendMessage(message);
                assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.RECEPTION_FINISHED, genesisTransaction);
            }
        }

        private Transaction<CryptoTransaction> transactionForHash(DigitalAssetMetadata metadata) throws CantDeliverPendingTransactionsException {
            List<Transaction<CryptoTransaction>> pendingTransactions = protocolManager.getPendingTransactions(Specialist.ASSET_USER_SPECIALIST);
            for (Transaction<CryptoTransaction> transaction : pendingTransactions) {
                if (transaction.getInformation().getTransactionHash().equals(metadata.getLastTransactionHash())) {
                    return transaction;
                }
            }
            return null;
        }

        private boolean isTransactionToBeNotified(CryptoStatus cryptoStatus) throws CantExecuteQueryException {
            return assetReceptionDao.isPendingTransactions(cryptoStatus);
        }
    }
}
