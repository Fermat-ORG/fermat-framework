package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.events;

import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.CantStopAgentException;
import com.bitdubai.fermat_api.FermatAgent;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantDeleteRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BroadcastStatus;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantBroadcastTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBroadcastStatusException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantCreateBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.transactions.DraftTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantCreateDraftTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantSignTransactionException;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetMovementType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetSellStatus;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetMovementContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetNegotiationContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetSellContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendMessageException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantExecuteLockOperationException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.AssetSellerDigitalAssetTransactionPluginRoot;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.database.AssetSellerDAO;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 9/02/16.
 */
public class AssetSellerMonitorAgent extends FermatAgent {

    //VARIABLE DECLARATION
    private SellerAgent sellerAgent;

    private final AssetUserWalletManager userWalletManager;
    private final ActorAssetUserManager actorAssetUserManager;
    private final org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.database.AssetSellerDAO dao;
    private final AssetTransmissionNetworkServiceManager assetTransmission;
    private final AssetVaultManager assetVaultManager;
    private final CryptoVaultManager cryptoVaultManager;
    private final BitcoinNetworkManager bitcoinNetworkManager;
    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final ExtraUserManager extraUserManager;
    AssetSellerDigitalAssetTransactionPluginRoot assetSellerDigitalAssetTransactionPluginRoot;
    //CONSTRUCTORS

    public AssetSellerMonitorAgent(AssetUserWalletManager userWalletManager,
                                   ActorAssetUserManager actorAssetUserManager,
                                   AssetSellerDAO dao,
                                   AssetSellerDigitalAssetTransactionPluginRoot assetSellerDigitalAssetTransactionPluginRoot,
                                   AssetTransmissionNetworkServiceManager assetTransmission,
                                   AssetVaultManager assetVaultManager,
                                   BitcoinNetworkManager bitcoinNetworkManager,
                                   CryptoVaultManager cryptoVaultManager,
                                   CryptoAddressBookManager cryptoAddressBookManager,
                                   ExtraUserManager extraUserManager) {

        this.userWalletManager = userWalletManager;
        this.actorAssetUserManager = actorAssetUserManager;
        this.dao = dao;
        this.assetSellerDigitalAssetTransactionPluginRoot = assetSellerDigitalAssetTransactionPluginRoot;
        this.assetTransmission = assetTransmission;
        this.assetVaultManager = assetVaultManager;
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.cryptoVaultManager = cryptoVaultManager;
        this.cryptoAddressBookManager = cryptoAddressBookManager;
        this.extraUserManager = extraUserManager;
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
            assetSellerDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
            assetSellerDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
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
                } catch (Exception e) {
                    assetSellerDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(WAIT_TIME);
                    } catch (InterruptedException e) {
                        assetSellerDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                        agentRunning = false;
                    }
                }
            }
        }

        private void doTheMainTask() throws CantDeleteRecordException, CantUpdateRecordException, DAPException, CantLoadTableToMemoryException, CantBroadcastTransactionException, CantRegisterDebitException, CantCreateDraftTransactionException, CantLoadWalletException, CantGetCryptoTransactionException, CantGetTransactionsException, CantSignTransactionException, CantGetBroadcastStatusException, CantCreateBitcoinTransactionException, CantRegisterCryptoAddressBookRecordException, CantCreateExtraUserException, CantSetObjectException {
            checkTimeout();
            checkUnreadMessages();
            checkPendingSells();
        }

        private void checkUnreadMessages() throws DAPException, CantLoadTableToMemoryException, CantUpdateRecordException, CantDeleteRecordException, CantLoadWalletException {
            //NEGOTIATION
            for (DAPMessage message : assetTransmission.getUnreadDAPMessageBySubject(DAPMessageSubject.NEGOTIATION_ANSWER)) {
                try {
                    AssetNegotiationContentMessage content = (AssetNegotiationContentMessage) message.getMessageContent();
                    org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.functional.NegotiationRecord record = dao.getNegotiationForId(content.getAssetNegotiation().getNegotiationId());
                    if (record.getNegotiationStatus() == AssetSellStatus.NEGOTIATION_CANCELLED) {
                        System.out.println("This transaction was already cancelled.");
                        continue;
                    }
                    dao.updateNegotiationStatus(record.getNegotiation().getNegotiationId(), content.getSellStatus());
                    org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.functional.SellingRecord sellingRecord = dao.getLastSellingRecord(content.getAssetNegotiation().getNegotiationId());
                    switch (content.getSellStatus()) {
                        case NEGOTIATION_CONFIRMED: {
                            System.out.println("Negotiation confirmed...");
                            dao.updateSellingStatus(sellingRecord.getRecordId(), AssetSellStatus.NEGOTIATION_CONFIRMED);
                            break;
                        }
                        case NEGOTIATION_REJECTED: {
                            System.out.println("Negotiation rejected...");
                            unlockFunds(sellingRecord.getMetadata());
                            dao.deleteSellingRecord(sellingRecord.getRecordId());
                            break;
                        }
                    }
                } catch (RecordsNotFoundException e) {
                    //If we cannot found any record it means that the message was already cancelled but failed to notify the buyer
                    //Or the buyer answered before receiving the cancelled message, so we'll just ignore his answer.
                } finally {
                    assetTransmission.confirmReception(message);
                }
            }
            //SIGNED TRANSACTIONS
            for (DAPMessage message : assetTransmission.getUnreadDAPMessageBySubject(DAPMessageSubject.TRANSACTION_SIGNED)) {
                AssetSellContentMessage content = (AssetSellContentMessage) message.getMessageContent();
                DraftTransaction draftTransaction = DraftTransaction.deserialize(content.getAssetMetadata().getNetworkType(), content.getSerializedTransaction());
                draftTransaction.addValue(content.getTransactionValue());
                dao.updateBuyerTransaction(content.getSellingId(), draftTransaction);
                dao.updateSellingStatus(content.getSellingId(), content.getSellStatus());
                assetTransmission.confirmReception(message);
            }
        }

        private void checkPendingSells() throws DAPException, CantCreateDraftTransactionException, CantUpdateRecordException, CantLoadTableToMemoryException, CantSignTransactionException, CantCreateBitcoinTransactionException, CantBroadcastTransactionException, CantGetTransactionsException, CantRegisterDebitException, CantLoadWalletException, CantGetCryptoTransactionException, CantGetBroadcastStatusException, CantRegisterCryptoAddressBookRecordException, CantCreateExtraUserException, CantSetObjectException {
            for (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.functional.SellingRecord record : dao.getActionRequiredSellings()) {
                switch (record.getStatus()) {
                    case NEGOTIATION_CONFIRMED: {
                        System.out.println("Negotiation confirmed...");
                        ActorAssetUser mySelf = actorAssetUserManager.getActorAssetUser();
                        CryptoAddress sellerCryptoAddress = cryptoVaultManager.getAddress(record.getMetadata().getNetworkType());
                        dao.updateSellerCryptoAddress(record.getRecordId(), sellerCryptoAddress);

                        Actor actor = getExtraUser(record.getMetadata());
                        cryptoAddressBookManager.registerCryptoAddress(sellerCryptoAddress, mySelf.getActorPublicKey(), Actors.DAP_ASSET_USER, actor.getActorPublicKey(), Actors.EXTRA_USER, Platforms.CRYPTO_CURRENCY_PLATFORM, VaultType.CRYPTO_CURRENCY_VAULT, CryptoCurrencyVault.BITCOIN_VAULT.getCode(), "reference_wallet", ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);

                        DraftTransaction draftTransaction = assetVaultManager.createDraftTransaction(record.getMetadata().getLastTransactionHash(), record.getBuyer().getCryptoAddress(), record.getMetadata().getNetworkType());
                        dao.updateSellerTransaction(record.getRecordId(), draftTransaction);
                        AssetSellContentMessage contentMessage = new AssetSellContentMessage(record.getRecordId(), draftTransaction.serialize(), draftTransaction.getValue(), AssetSellStatus.WAITING_FIRST_SIGNATURE, record.getMetadata(), record.getNegotiationId(), sellerCryptoAddress, record.getBuyerCryptoAddress());
                        DAPMessage dapMessage = new DAPMessage(contentMessage, mySelf, record.getBuyer(), DAPMessageSubject.NEW_SELL_STARTED);
                        assetTransmission.sendMessage(dapMessage);
                        sendAssetMovement(record.getMetadata(), record.getBuyer());
                        dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.WAITING_FIRST_SIGNATURE);
                        break;
                    }
                    case PARTIALLY_SIGNED: {
                        System.out.println("Negotiation partially signed...");
                        if (org.fermat.fermat_dap_api.layer.all_definition.util.Validate.isValidTransaction(record.getBuyerTransaction(), record.getSellerTransaction())) {
                            DraftTransaction draftTransaction = assetVaultManager.signTransaction(record.getBuyerTransaction());
                            dao.updateSellerTransaction(record.getRecordId(), draftTransaction);
                            String txHash = assetVaultManager.createBitcoinTransaction(draftTransaction);
                            dao.updateTransactionHash(record.getRecordId(), txHash);
                            dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.COMPLETE_SIGNATURE);
                        } else {
                            AssetSellContentMessage content = new AssetSellContentMessage(record.getRecordId(), null, record.getSellerTransaction().getValue(), AssetSellStatus.SIGNATURE_REJECTED, null, record.getNegotiationId(), null, null);
                            ActorAssetUser mySelf = actorAssetUserManager.getActorAssetUser();
                            DAPMessage message = new DAPMessage(content, mySelf, record.getBuyer(), DAPMessageSubject.SIGNATURE_REJECTED);
                            //WE NOTIFY TO THE BUYER THAT THE SIGNATURE HAS BEEN REJECTED.
                            assetTransmission.sendMessage(message);
                            dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.SIGNATURE_REJECTED);
                            System.out.println("Signature rejected!!");
                        }
                        break;
                    }
                    case COMPLETE_SIGNATURE: {
                        System.out.println("Complete signed...");
                        debitUserWallet(record, BalanceType.BOOK);
                        dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.WALLET_DEBITED);
                        break;
                    }
                    case WALLET_DEBITED: {
                        System.out.println("Broadcasting...");
                        bitcoinNetworkManager.broadcastTransaction(record.getBroadcastingTxHash());
                        dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.BROADCASTING);
                    }
                    case BROADCASTING: {
                        BroadcastStatus broadcast = bitcoinNetworkManager.getBroadcastStatus(record.getBroadcastingTxHash());
                        switch (broadcast.getStatus()) {
                            case BROADCASTED: {
                                debitUserWallet(record, BalanceType.AVAILABLE);
                                unlockFunds(record.getMetadata());
                                dao.updateSellingStatus(record.getRecordId(), AssetSellStatus.SELL_FINISHED);
                                System.out.println("Asset Sold...");
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

        private void checkTimeout() throws DAPException, CantUpdateRecordException, CantLoadTableToMemoryException, CantDeleteRecordException, CantLoadWalletException, CantSetObjectException {
            for (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.functional.NegotiationRecord negotiation : dao.getWaitingConfirmationNegotiations()) {
                if (negotiation.isExpired()) {
                    cancellNegotiation(negotiation);
                }
            }
        }

        private void cancellNegotiation(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.functional.NegotiationRecord negotiation) throws DAPException, CantLoadTableToMemoryException, CantUpdateRecordException, CantDeleteRecordException, CantLoadWalletException, CantSetObjectException {
            System.out.println("Cancelling negotiation for asset: " + negotiation.getNegotiation().getAssetToOffer().getName());
            dao.updateNegotiationStatus(negotiation.getNegotiation().getNegotiationId(), AssetSellStatus.NEGOTIATION_CANCELLED);
            for (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.functional.SellingRecord record : dao.getAllSelingRecordsForNegotiation(negotiation.getNegotiation().getNegotiationId())) {
                unlockFunds(record.getMetadata());
                System.out.println("Deleting selling record: " + record.getRecordId());
                dao.deleteSellingRecord(record.getRecordId());
            }
            AssetNegotiationContentMessage content = new AssetNegotiationContentMessage(AssetSellStatus.NEGOTIATION_CANCELLED, negotiation.getNegotiation());
            ActorAssetUser user = actorAssetUserManager.getActorAssetUser();
            DAPMessage message = new DAPMessage(content, user, negotiation.getBuyer(), DAPMessageSubject.NEGOTIATION_CANCELLED);
            assetTransmission.sendMessage(message);
        }

        private void debitUserWallet(org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_seller.developer.version_1.structure.functional.SellingRecord record, BalanceType balance) throws CantGetAssetUserActorsException, CantLoadWalletException, CantGetCryptoTransactionException, CantGetTransactionsException, CantRegisterDebitException {
            ActorAssetUser mySelf = actorAssetUserManager.getActorAssetUser();
            AssetUserWallet userWallet = userWalletManager.loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY, record.getMetadata().getNetworkType());
            CryptoTransaction cryptoTransaction = bitcoinNetworkManager.getCryptoTransaction(record.getBroadcastingTxHash(), CryptoTransactionType.OUTGOING, record.getBuyerCryptoAddress());
            AssetUserWalletTransactionRecord transactionRecord = new AssetUserWalletTransactionRecordWrapper(record.getMetadata(), cryptoTransaction, mySelf, record.getBuyer(), WalletUtilities.DEFAULT_MEMO_SELL);
            userWallet.getBalance().debit(transactionRecord, balance);
        }

        private void unlockFunds(DigitalAssetMetadata assetMetadata) throws RecordsNotFoundException, CantExecuteLockOperationException, CantLoadWalletException {
            AssetUserWallet userWallet = userWalletManager.loadAssetUserWallet(WalletUtilities.WALLET_PUBLIC_KEY, assetMetadata.getNetworkType());
            userWallet.unlockFunds(assetMetadata);
        }

        private void sendAssetMovement(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser newUser) throws CantSetObjectException, CantGetAssetUserActorsException, CantSendMessageException {
            AssetMovementContentMessage content = new AssetMovementContentMessage(actorAssetUserManager.getActorAssetUser(), newUser, digitalAssetMetadata.getDigitalAsset().getPublicKey(), digitalAssetMetadata.getNetworkType(), AssetMovementType.ASSET_SOLD);
            final IdentityAssetIssuer identityAssetIssuer = digitalAssetMetadata.getDigitalAsset().getIdentityAssetIssuer();
            ActorAssetUser actorSender = actorAssetUserManager.getActorAssetUser();
            ActorAssetIssuer actorReceiver = new AssetIssuerActorRecord(identityAssetIssuer.getAlias(), identityAssetIssuer.getPublicKey());
            DAPMessage dapMessage = new DAPMessage(content, actorSender, actorReceiver, DAPMessageSubject.ASSET_MOVEMENT);
            assetTransmission.sendMessage(dapMessage);
        }

        private Actor getExtraUser(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateExtraUserException {
            try {
                return extraUserManager.getActorByPublicKey(digitalAssetMetadata.getDigitalAsset().getPublicKey());
            } catch (CantGetExtraUserException | ExtraUserNotFoundException e) {
                assetSellerDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
                byte[] image = digitalAssetMetadata.getDigitalAsset().getResources().isEmpty() ? new byte[0] : digitalAssetMetadata.getDigitalAsset().getResources().get(0).getResourceBinayData();
                String actorName = "Asset " + digitalAssetMetadata.getDigitalAsset().getName() + " sold.";
                return extraUserManager.createActor(actorName, image);
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
}
