package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantCreateDraftTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantSignTransactionException;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_draft.OutgoingDraftManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetNegotiationContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetSellContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantGetDAPMessagesException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantUpdateMessageStatusException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.database.AssetBuyerDAO;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.AssetBuyerTransactionManager;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.BuyingRecord;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_buyer.developer.version_1.structure.functional.NegotiationRecord;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetBuyerMonitorAgent extends FermatAgent {

    //VARIABLE DECLARATION
    private BuyerAgent buyerAgent;

    private final ErrorManager errorManager;
    private final AssetBuyerDAO dao;
    private final AssetBuyerTransactionManager transactionManager;
    private final AssetUserWalletManager userWalletManager;
    private final ActorAssetUserManager actorAssetUserManager;
    private final AssetTransmissionNetworkServiceManager assetTransmission;
    private final CryptoVaultManager cryptoVaultManager;
    private final BitcoinNetworkManager bitcoinNetworkManager;
    private final OutgoingDraftManager outgoingDraftManager;
    private final IntraWalletUserIdentityManager intraWalletUserIdentityManager;
    private final ExtraUserManager extraUserManager;
    private final TransactionProtocolManager<CryptoTransaction> protocolManager;

    //CONSTRUCTORS

    public AssetBuyerMonitorAgent(ErrorManager errorManager, AssetBuyerDAO dao, AssetBuyerTransactionManager transactionManager, AssetUserWalletManager userWalletManager, ActorAssetUserManager actorAssetUserManager, AssetTransmissionNetworkServiceManager assetTransmission, CryptoVaultManager cryptoVaultManager, BitcoinNetworkManager bitcoinNetworkManager, OutgoingDraftManager outgoingDraftManager, IntraWalletUserIdentityManager intraWalletUserIdentityManager, ExtraUserManager extraUserManager, IncomingCryptoManager incomingCryptoManager) {
        this.errorManager = errorManager;
        this.dao = dao;
        this.transactionManager = transactionManager;
        this.userWalletManager = userWalletManager;
        this.actorAssetUserManager = actorAssetUserManager;
        this.assetTransmission = assetTransmission;
        this.cryptoVaultManager = cryptoVaultManager;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.outgoingDraftManager = outgoingDraftManager;
        this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
        this.extraUserManager = extraUserManager;
        this.protocolManager = incomingCryptoManager.getTransactionManager();
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            buyerAgent = new BuyerAgent();
            Thread agentThread = new Thread(buyerAgent, this.getClass().getSimpleName());
            agentThread.start();
            super.start();
        } catch (Exception e) {
            throw new CantStartAgentException(FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public void stop() throws CantStopAgentException {
        try {
            buyerAgent.stopAgent();
            buyerAgent = null; //RELEASE RESOURCES UNTIL WE START IT AGAIN.
            super.stop();
        } catch (Exception e) {
            throw new CantStopAgentException(FermatException.wrapException(e), null, null);
        }
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES

    private class BuyerAgent implements Runnable {

        public BuyerAgent() {
            startAgent();
        }

        private boolean agentRunning;
        private static final int WAIT_TIME = 5 * 1000; //SECONDS

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
                    errorManager.reportUnexpectedPluginException(Plugins.ASSET_BUYER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                } finally {
                    try {
                        Thread.sleep(WAIT_TIME);
                    } catch (InterruptedException e) {
                        errorManager.reportUnexpectedPluginException(Plugins.ASSET_BUYER, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        agentRunning = false;
                    }
                }
            }
        }

        private void doTheMainTask() throws DAPException, CantInsertRecordException, CantUpdateRecordException, CantLoadTableToMemoryException, CantSignTransactionException, CantGetTransactionsException, CantLoadWalletException, InvalidParameterException, CantGetCryptoTransactionException, CantRegisterCreditException, CantCreateDraftTransactionException, CantListIntraWalletUsersException, CantCreateExtraUserException, CantConfirmTransactionException, CantDeliverPendingTransactionsException {
            checkPendingMessages();
            checkNegotiationStatus();
            checkBuyingStatus();
            checkPendingEvents();
        }

        private void checkPendingMessages() throws CantInsertRecordException, CantGetDAPMessagesException, CantCreateDigitalAssetFileException, CantUpdateMessageStatusException, RecordsNotFoundException, CantLoadTableToMemoryException, CantUpdateRecordException {
            for (DAPMessage message : assetTransmission.getUnreadDAPMessageBySubject(DAPMessageSubject.NEW_NEGOTIATION_STARTED)) {
                System.out.println("New Negotiation Started...");
                AssetNegotiationContentMessage contentMessage = (AssetNegotiationContentMessage) message.getMessageContent();
                dao.saveAssetNegotiation(contentMessage.getAssetNegotiation(), message.getActorSender().getActorPublicKey());
                assetTransmission.confirmReception(message);
            }
            for (DAPMessage message : assetTransmission.getUnreadDAPMessageBySubject(DAPMessageSubject.NEW_SELL_STARTED)) {
                System.out.println("New Sell Started...");
                AssetSellContentMessage contentMessage = (AssetSellContentMessage) message.getMessageContent();
                dao.saveNewBuying(contentMessage, message.getActorSender().getActorPublicKey());
                assetTransmission.confirmReception(message);
            }
            for (DAPMessage message : assetTransmission.getUnreadDAPMessageBySubject(DAPMessageSubject.NEGOTIATION_CANCELLED)) {
                System.out.println("Negotiation Cancelled...");
                AssetNegotiationContentMessage contentMessage = (AssetNegotiationContentMessage) message.getMessageContent();
                dao.updateNegotiationStatus(contentMessage.getAssetNegotiation().getNegotiationId(), contentMessage.getSellStatus());
                assetTransmission.confirmReception(message);
            }
            for (DAPMessage message : assetTransmission.getUnreadDAPMessageBySubject(DAPMessageSubject.SIGNATURE_REJECTED)) {
                System.out.println("Signature Rejected...");
                //TODO
            }
        }

        private void checkNegotiationStatus() throws DAPException, CantUpdateRecordException, CantLoadTableToMemoryException {
            for (NegotiationRecord record : dao.getNegotiationAnswer()) {
                System.out.println("New negotiation answer: " + record.getNegotiationStatus().name());
                try {
                    assetTransmission.sendMessage(transactionManager.constructNegotiationMessage(record));
                } catch (CantSetObjectException e) {
                    e.printStackTrace();
                }
                dao.updateNegotiationStatus(record.getNegotiation().getNegotiationId(), AssetSellStatus.NO_ACTION_REQUIRED);
            }
        }

        private void checkBuyingStatus() throws DAPException, CantLoadTableToMemoryException, CantUpdateRecordException, CantSignTransactionException, CantCreateDraftTransactionException, CantListIntraWalletUsersException, CantCreateExtraUserException {
            for (BuyingRecord buyingRecord : dao.getActionRequiredBuying()) {
                switch (buyingRecord.getStatus()) {
                    case WAITING_FIRST_SIGNATURE: {
                        System.out.println("Adding inputs...");
                        NegotiationRecord negotiationRecord = dao.getNegotiationRecord(buyingRecord.getNegotiationId());
                        UUID outgoingId = UUID.randomUUID();
                        dao.updateOutgoingId(buyingRecord.getRecordId(), outgoingId);
                        IntraWalletUserIdentity mySelf = intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser().get(0);
                        Actor externalUser = getExtraUser(buyingRecord.getSeller());
                        outgoingDraftManager.addInputsToDraftTransaction(
                                outgoingId,
                                buyingRecord.getSellerTransaction(),
                                negotiationRecord.getNegotiation().getTotalAmount(),
                                buyingRecord.getSellerCryptoAddress(),
                                negotiationRecord.getBtcWalletPublicKey(),
                                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                                WalletUtilities.DEFAULT_MEMO_BUY,
                                externalUser.getActorPublicKey(),
                                Actors.EXTRA_USER,
                                mySelf.getPublicKey(),
                                Actors.INTRA_USER,
                                negotiationRecord.getNegotiation().getNetworkType());
                        dao.updateSellingStatus(buyingRecord.getRecordId(), AssetSellStatus.ADDING_INPUTS);
                        break;
                    }
                    case INPUTS_ADDED: {
                        System.out.println("Signing transaction...");
                        DraftTransaction buyerTx = cryptoVaultManager.signTransaction(buyingRecord.getBuyerTransaction());
                        dao.updateSellingStatus(buyingRecord.getRecordId(), AssetSellStatus.PARTIALLY_SIGNED);
                        dao.updateBuyerTransaction(buyingRecord.getRecordId(), buyerTx);
                        break;
                    }
                    case PARTIALLY_SIGNED: {
                        System.out.println("Sending message...");
                        DAPMessage message = null;
                        try {
                            message = transactionManager.constructSellingMessage(buyingRecord, AssetSellStatus.PARTIALLY_SIGNED);
                        } catch (CantSetObjectException e) {
                            e.printStackTrace();
                        }
                        assetTransmission.sendMessage(message);
                        dao.updateSellingStatus(buyingRecord.getRecordId(), AssetSellStatus.WAITING_COMPLETE_SIGNATURE);
                    }
                }
            }
        }

        private void checkPendingEvents() throws CantLoadTableToMemoryException, DAPException, InvalidParameterException, CantGetTransactionsException, CantLoadWalletException, CantRegisterCreditException, CantGetCryptoTransactionException, CantUpdateRecordException, CantConfirmTransactionException, CantDeliverPendingTransactionsException {
            for (String eventId : dao.getPendingOutgoingDraftEvents()) {
                for (BuyingRecord buyingRecord : dao.getAddingInputsBuying()) {
                    DraftTransaction signedTx = outgoingDraftManager.getPending(buyingRecord.getOutgoingId());
                    outgoingDraftManager.markRead(buyingRecord.getOutgoingId());
                    dao.updateSellingStatus(buyingRecord.getRecordId(), AssetSellStatus.INPUTS_ADDED);
                    dao.updateBuyerTransaction(buyingRecord.getRecordId(), signedTx);
                }
                dao.notifyEvent(eventId);
            }

            for (String eventId : dao.getPendingEvents()) {
                switch (dao.getEventType(eventId)) {
                    case INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER: {
                        for (BuyingRecord record : dao.getWaitingCompleteSignature()) {
                            creditUserWallet(record, BalanceType.BOOK);
                            Transaction<CryptoTransaction> transaction = transactionForHash(record.getMetadata());
                            if (transaction != null) {
                                protocolManager.confirmReception(transaction.getTransactionID());
                            }
                            dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.COMPLETE_SIGNATURE);
                        }
                        break;
                    }
                    case INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER: {
                        for (BuyingRecord record : dao.getCompletedSignedBuying()) {
                            creditUserWallet(record, BalanceType.AVAILABLE);
                            Transaction<CryptoTransaction> transaction = transactionForHash(record.getMetadata());
                            if (transaction != null) {
                                protocolManager.confirmReception(transaction.getTransactionID());
                            }
                            dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.SELL_FINISHED);
                        }
                        break;
                    }
                }
                dao.notifyEvent(eventId);
            }
        }


        private void creditUserWallet(BuyingRecord record, BalanceType balance) throws DAPException, CantLoadWalletException, CantGetCryptoTransactionException, CantGetTransactionsException, CantRegisterCreditException {
            ActorAssetUser mySelf = actorAssetUserManager.getActorAssetUser();
            DigitalAssetMetadata metadata = record.getMetadata();
            AssetUserWallet userWallet = userWalletManager.loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY, metadata.getNetworkType());
            CryptoTransaction cryptoTransaction = null;
            dance:
            for (CryptoTransaction cryptoTx : bitcoinNetworkManager.getCryptoTransactions(record.getMetadata().getNetworkType(), record.getBuyerCryptoAddress(), CryptoTransactionType.INCOMING)) {
                switch (balance) {
                    case BOOK: {
                        switch (cryptoTx.getCryptoStatus()) {
                            case ON_CRYPTO_NETWORK:
                                cryptoTransaction = cryptoTx;
                                break dance;

                        }
                        break;
                    }
                    case AVAILABLE: {
                        switch (cryptoTx.getCryptoStatus()) {
                            case ON_BLOCKCHAIN:
                            case IRREVERSIBLE:
                                cryptoTransaction = cryptoTx;
                                break dance;
                        }
                        break;
                    }
                }
            }
            if (cryptoTransaction == null) {
                throw new CantGetTransactionsException(null, null, null, null);
            }
            metadata.addNewTransaction(cryptoTransaction);
            AssetUserWalletTransactionRecord transactionRecord = new AssetUserWalletTransactionRecordWrapper(metadata, cryptoTransaction, record.getSeller(), mySelf, WalletUtilities.DEFAULT_MEMO_BUY);
            userWallet.getBalance().credit(transactionRecord, balance);
        }


        private Actor getExtraUser(ActorAssetUser actorAssetUser) throws CantCreateExtraUserException {
            try {
                return extraUserManager.getActorByPublicKey(actorAssetUser.getActorPublicKey());
            } catch (CantGetExtraUserException | ExtraUserNotFoundException e) {
                return extraUserManager.createActor(actorAssetUser.getName(), actorAssetUser.getProfileImage());
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
            List<Transaction<CryptoTransaction>> pendingTransactions = protocolManager.getPendingTransactions(Specialist.ASSET_USER_SPECIALIST);
            for (Transaction<CryptoTransaction> transaction : pendingTransactions) {
                if (transaction.getInformation().getTransactionHash().equals(metadata.getLastTransactionHash())) {
                    return transaction;
                }
            }
            return null;
        }


    }
}
