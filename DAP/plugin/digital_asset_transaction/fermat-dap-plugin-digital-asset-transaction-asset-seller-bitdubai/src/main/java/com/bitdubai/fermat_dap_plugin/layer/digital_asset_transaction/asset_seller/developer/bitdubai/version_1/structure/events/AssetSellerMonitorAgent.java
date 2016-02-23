package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantCreateBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantCreateDraftTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantSignTransactionException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetNegotiationContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetSellContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.bitdubai.version_1.structure.database.AssetSellerDAO;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.bitdubai.version_1.structure.functional.SellingRecord;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetSellerMonitorAgent extends FermatAgent {

    //VARIABLE DECLARATION
    private SellerAgent sellerAgent;

    private final AssetUserWalletManager userWalletManager;
    private final ActorAssetUserManager actorAssetUserManager;
    private final AssetSellerDAO dao;
    private final ErrorManager errorManager;
    private final AssetTransmissionNetworkServiceManager assetTransmission;
    private final AssetVaultManager assetVaultManager;
    private final BitcoinNetworkManager bitcoinNetworkManager;

    //CONSTRUCTORS

    public AssetSellerMonitorAgent(AssetUserWalletManager userWalletManager, ActorAssetUserManager actorAssetUserManager, AssetSellerDAO dao, ErrorManager errorManager, AssetTransmissionNetworkServiceManager assetTransmission, AssetVaultManager assetVaultManager, BitcoinNetworkManager bitcoinNetworkManager) {
        this.userWalletManager = userWalletManager;
        this.actorAssetUserManager = actorAssetUserManager;
        this.dao = dao;
        this.errorManager = errorManager;
        this.assetTransmission = assetTransmission;
        this.assetVaultManager = assetVaultManager;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            sellerAgent = new SellerAgent();
            Thread agentThread = new Thread(sellerAgent, this.getClass().getSimpleName());
            agentThread.start();
            super.start();
        } catch (Exception e) {
            throw new CantStartAgentException(FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public void stop() throws CantStopAgentException {
        try {
            sellerAgent.stopAgent();
            sellerAgent = null; //RELEASE RESOURCES UNTIL WE START IT AGAIN.
            super.stop();
        } catch (Exception e) {
            throw new CantStopAgentException(FermatException.wrapException(e), null, null);
        }
    }

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES

    private class SellerAgent implements Runnable {
        private boolean agentRunning;
        private static final int WAIT_TIME = 5 * 1000; //SECONDS

        public SellerAgent() {
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
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    errorManager.reportUnexpectedPluginException(Plugins.ASSET_SELLER, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                    agentRunning = false;
                } catch (Exception e) {
                    e.printStackTrace();
                    errorManager.reportUnexpectedPluginException(Plugins.ASSET_SELLER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }
        }

        private void doTheMainTask() {
            try {
                checkUnreadMessages();
                checkPendingSells();
            } catch (CantLoadTableToMemoryException | CantUpdateRecordException | DAPException | CantLoadWalletException | CantSignTransactionException | CantCreateDraftTransactionException | CantDeleteRecordException | CantBroadcastTransactionException | CantCreateBitcoinTransactionException | CantGetTransactionsException | CantRegisterDebitException | CantGetBroadcastStatusException | CantGetCryptoTransactionException e) {
                //TODO EXCEPTION HANDLING.
                e.printStackTrace();
            }
        }

        private void checkUnreadMessages() throws DAPException, CantLoadTableToMemoryException, CantUpdateRecordException, CantDeleteRecordException {
            //NEGOTIATION
            for (DAPMessage message : assetTransmission.getUnreadDAPMessagesByType(DAPMessageType.ASSET_NEGOTIATION)) {
                AssetNegotiationContentMessage content = (AssetNegotiationContentMessage) message.getMessageContent();
                SellingRecord sellingRecord = dao.getLastSellingRecord(content.getAssetNegotiation().getNegotiationId());
                switch (content.getSellStatus()) {
                    case NEGOTIATION_CONFIRMED: {
                        dao.updateSellingStatus(sellingRecord.getRecordId(), AssetSellStatus.NEGOTIATION_CONFIRMED);
                        break;
                    }
                    case NEGOTIATION_REJECTED: {
                        //TODO UNLOCK IN WALLET.
                        dao.deleteSellingRecord(sellingRecord.getRecordId());
                        break;
                    }
                }
                assetTransmission.confirmReception(message);
            }
            //SIGNED TRANSACTIONS
            for (DAPMessage message : assetTransmission.getUnreadDAPMessagesByType(DAPMessageType.ASSET_SELL)) {
                AssetSellContentMessage content = (AssetSellContentMessage) message.getMessageContent();
                dao.updateSellingStatus(content.getSellingId(), content.getSellStatus());
                dao.updateBuyerTransaction(content.getSellingId(), content.getSerializedTransaction());
                assetTransmission.confirmReception(message);
            }
        }

        private void checkPendingSells() throws DAPException, CantCreateDraftTransactionException, CantUpdateRecordException, CantLoadTableToMemoryException, CantSignTransactionException, CantCreateBitcoinTransactionException, CantBroadcastTransactionException, CantGetTransactionsException, CantRegisterDebitException, CantLoadWalletException, CantGetCryptoTransactionException, CantGetBroadcastStatusException {
            for (SellingRecord record : dao.getActionRequiredSellings()) {
                switch (record.getStatus()) {
                    case NEGOTIATION_CONFIRMED: {
                        ActorAssetUser mySelf = actorAssetUserManager.getActorAssetUser();
                        DraftTransaction draftTransaction = assetVaultManager.createDraftTransaction(record.getMetadata().getLastTransactionHash(), record.getBuyer().getCryptoAddress());
                        AssetSellContentMessage contentMessage = new AssetSellContentMessage(record.getRecordId(), draftTransaction.serialize(), AssetSellStatus.WAITING_FIRST_SIGNATURE, record.getMetadata(), record.getNegotiationId());
                        DAPMessage dapMessage = new DAPMessage(UUID.randomUUID(),contentMessage, mySelf, record.getBuyer());
                        assetTransmission.sendMessage(dapMessage);
                        dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.WAITING_FIRST_SIGNATURE);
                        break;
                    }
                    case PARTIALLY_SIGNED: {
                        if (!Validate.isValidTransaction(record.getBuyerTransaction(), record.getSellerTransaction())) {
                            AssetSellContentMessage content = new AssetSellContentMessage(record.getRecordId(), null, AssetSellStatus.SIGNATURE_REJECTED, null, record.getNegotiationId());
                            ActorAssetUser mySelf = actorAssetUserManager.getActorAssetUser();
                            DAPMessage message = new DAPMessage(UUID.randomUUID(),content, mySelf, record.getBuyer());
                            //WE NOTIFY TO THE BUYER THAT THE SIGNATURE HAS BEEN REJECTED.
                            assetTransmission.sendMessage(message);
                            dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.SIGNATURE_REJECTED);
                        }
                        DraftTransaction draftTransaction = assetVaultManager.signTransaction(record.getBuyerTransaction());
                        dao.updateSellerTransaction(record.getRecordId(), draftTransaction.serialize());
                        String txHash = assetVaultManager.createBitcoinTransaction(draftTransaction);
                        dao.updateTransactionHash(record.getRecordId(), txHash);
                        dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.COMPLETE_SIGNATURE);

                        break;
                    }
                    case COMPLETE_SIGNATURE: {
                        debitUserWallet(record, BalanceType.BOOK);
                        dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.WALLET_DEBITED);
                        break;
                    }
                    case WALLET_DEBITED: {
                        bitcoinNetworkManager.broadcastTransaction(record.getBroadcastingTxHash());
                        dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.BROADCASTING);
                    }
                    case BROADCASTING: {
                        BroadcastStatus broadcast = bitcoinNetworkManager.getBroadcastStatus(record.getBroadcastingTxHash());
                        switch (broadcast.getStatus()) {
                            case BROADCASTED: {
                                debitUserWallet(record, BalanceType.AVAILABLE);
                                dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.SELL_FINISHED);
                                break;
                            }
                            case WITH_ERROR: {
                                //WE'LL RETRY TO THE INFINITE AND BEYOND.
                                bitcoinNetworkManager.broadcastTransaction(record.getBroadcastingTxHash());
                                break;
                            }
                        }
                    }
                }
            }
        }

        private void debitUserWallet(SellingRecord record, BalanceType balance) throws CantGetAssetUserActorsException, CantLoadWalletException, CantGetCryptoTransactionException, CantGetTransactionsException, CantRegisterDebitException {
            ActorAssetUser mySelf = actorAssetUserManager.getActorAssetUser();
            AssetUserWallet userWallet = userWalletManager.loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY, record.getMetadata().getNetworkType());
            CryptoTransaction cryptoTransaction = bitcoinNetworkManager.getCryptoTransaction(record.getBroadcastingTxHash());
            AssetUserWalletTransactionRecord transactionRecord = new AssetUserWalletTransactionRecordWrapper(record.getMetadata(), cryptoTransaction, mySelf, record.getBuyer());
            userWallet.getBalance().debit(transactionRecord, balance);
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
}
