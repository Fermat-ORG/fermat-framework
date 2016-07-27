package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;

import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.AssetMovementType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetAppropriationContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetMovementContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendDAPMessageException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.AssetIssuerActorRecord;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantLoadAssetAppropriationTransactionListException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AppropriationTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.AssetAppropriationDigitalAssetTransactionPluginRoot;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.exceptions.CantLoadAssetAppropriationEventListException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.structure.database.AssetAppropriationDAO;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.structure.functional.AssetAppropriationVault;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class AssetAppropriationMonitorAgent implements Agent {

    //VARIABLE DECLARATION
    private ServiceStatus status;

    {
        this.status = ServiceStatus.CREATED;
    }

    AssetAppropriationDigitalAssetTransactionPluginRoot assetAppropriationDigitalAssetTransactionPluginRoot;
    private final LogManager logManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.version_1.structure.functional.AssetAppropriationVault assetVault;
    private final UUID pluginId;
    private final AssetVaultManager assetVaultManager;
    private final AssetUserWalletManager assetUserWalletManager;
    private final BlockchainManager bitcoinNetworkManager;
    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final CryptoVaultManager cryptoVaultManager;
    private final IntraWalletUserIdentityManager intraWalletUserIdentityManager;
    private final AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager;
    private final AssetIssuerWalletManager assetIssuerWalletManager;
    private final ActorAssetUserManager actorAssetUserManager;
    private final ExtraUserManager extraUserManager;

    private AppropriationAgent appropriationAgent;
    //VARIABLES ACCESSED BY AGENT INNER CLASS.
    //NEEDS TO BE VOLATILE SINCE THEY'RE BEING USED ON ANOTHER THREAD.
    //I NEED THREAD TO NOTICE ASAP.
    private volatile CountDownLatch latch;

    //CONSTRUCTORS


    public AssetAppropriationMonitorAgent(AssetAppropriationVault assetVault,
                                          PluginDatabaseSystem pluginDatabaseSystem,
                                          LogManager logManager,
                                          AssetAppropriationDigitalAssetTransactionPluginRoot assetAppropriationDigitalAssetTransactionPluginRoot,
                                          UUID pluginId,
                                          AssetVaultManager assetVaultManager,
                                          AssetUserWalletManager assetUserWalletManager,
                                          BlockchainManager bitcoinNetworkManager,
                                          CryptoAddressBookManager cryptoAddressBookManager,
                                          CryptoVaultManager cryptoVaultManager,
                                          IntraWalletUserIdentityManager intraWalletUserIdentityManager,
                                          AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager,
                                          AssetIssuerWalletManager assetIssuerWalletManager,
                                          ActorAssetUserManager actorAssetUserManager,
                                          ExtraUserManager extraUserManager) throws CantSetObjectException {

        this.assetVault = assetVault;
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.logManager = Validate.verifySetter(logManager, "logManager is null");
        this.assetAppropriationDigitalAssetTransactionPluginRoot = Validate.verifySetter(assetAppropriationDigitalAssetTransactionPluginRoot, "errorManager is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.assetVaultManager = Validate.verifySetter(assetVaultManager, "assetVaultManager is null");
        this.assetUserWalletManager = Validate.verifySetter(assetUserWalletManager, "assetUserWalletManager is null");
        this.bitcoinNetworkManager = Validate.verifySetter(bitcoinNetworkManager, "bitcoinNetworkManager is null");
        this.cryptoAddressBookManager = Validate.verifySetter(cryptoAddressBookManager, "cryptoAddressBookManager is null");
        this.cryptoVaultManager = Validate.verifySetter(cryptoVaultManager, "cryptoVaultManager is null");
        this.intraWalletUserIdentityManager = Validate.verifySetter(intraWalletUserIdentityManager, "intraWalletUserIdentityManager is null");
        this.assetTransmissionNetworkServiceManager = Validate.verifySetter(assetTransmissionNetworkServiceManager, "assetIssuerActorNetworkServiceManager is null");
        this.assetIssuerWalletManager = Validate.verifySetter(assetIssuerWalletManager, "assetIssuerWalletManager is null");
        this.actorAssetUserManager = Validate.verifySetter(actorAssetUserManager, "actorAssetUserManager is null");
        this.extraUserManager = Validate.verifySetter(extraUserManager, "extraUserManager is null");
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: starting...", null, null);
            latch = new CountDownLatch(1);

            appropriationAgent = new AppropriationAgent();
            Thread eventThread = new Thread(appropriationAgent, "Asset Appropriation MonitorAgent");
            eventThread.start();
        } catch (Exception e) {
            assetAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartAgentException();
        }
        this.status = ServiceStatus.STARTED;
        logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: successfully started...", null, null);
    }

    @Override
    public void stop() {
        logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: stopping...", null, null);
        appropriationAgent.stopAgent();
        try {
            latch.await(); //WAIT UNTIL THE LAST RUN FINISH
        } catch (InterruptedException e) {
            assetAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
        }
        appropriationAgent = null; //RELEASE RESOURCES.
        logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: successfully stopped...", null, null);
        this.status = ServiceStatus.STOPPED;
    }

    public boolean isMonitorAgentActive() {
        return status == ServiceStatus.STARTED;
    }
    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
    private class AppropriationAgent implements Runnable {

        private volatile boolean agentRunning;
        private static final int WAIT_TIME = 5 * 1000; //SECONDS

        public AppropriationAgent() {
            startAgent();
        }

        @Override
        public void run() {
            while (agentRunning) {
                try {
                    doTheMainTask();
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    /*If this happen there's a chance that the information remains
                    in a corrupt state. That probably would be fixed in a next run.
                    */
                    assetAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                } catch (Exception e) {
                    assetAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    e.printStackTrace();
                }
            }

            latch.countDown();
        }

        private void doTheMainTask() {
            try (AssetAppropriationDAO dao = new AssetAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault)) {

                messageMonitoring();
                statusMonitoring(dao);

            } catch (InvalidParameterException | CantRegisterDebitException | CantLoadWalletException | CantGetCryptoTransactionException e) {
                assetAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            } catch (CantLoadAssetAppropriationEventListException | CantGetTransactionsException | CantLoadAssetAppropriationTransactionListException e) {
                assetAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            } catch (RecordsNotFoundException e) {
                assetAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            } catch (Exception e) {
                assetAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void messageMonitoring() throws Exception {
            for (DAPMessage message : assetTransmissionNetworkServiceManager.getUnreadDAPMessageBySubject(DAPMessageSubject.ASSET_APPROPRIATED)) {
                if (message.getMessageContent() instanceof AssetAppropriationContentMessage) { //Just a security measure, this SHOULD always be true.
                    AssetAppropriationContentMessage contentMessage = (AssetAppropriationContentMessage) message.getMessageContent();
                    //TODO REMOVE HARDCODE.
                    AssetIssuerWallet wallet = assetIssuerWalletManager.loadAssetIssuerWallet(WalletUtilities.WALLET_PUBLIC_KEY, contentMessage.getNetworkType());
                    wallet.assetAppropriated(contentMessage.getTransactionId(), contentMessage.getUserThatAppropriate());
                }
            }
        }

        private void statusMonitoring(AssetAppropriationDAO dao) throws Exception {
            for (AppropriationTransactionRecord record : dao.getUnsendedTransactions()) {
                AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation(dao.getUnsendedTransactions().size() + " unsended transactions were found.");
                switch (record.status()) {
                    case APPROPRIATION_STARTED:
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("getting crypto address and saving it..." + record.transactionRecordId());
                        CryptoAddress cryptoAddress = cryptoVaultManager.getAddress(record.networkType());
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Address: " + cryptoAddress.getAddress());
                        dao.updateCryptoAddress(cryptoAddress, record.transactionRecordId());
                        dao.updateTransactionStatusCryptoAddressObtained(record.transactionRecordId());
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("statuses updated");
                        break;
                    case CRYPTOADDRESS_OBTAINED:
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("registering crypto address in crypto book. : " + record.transactionRecordId());
                        IntraWalletUserIdentity assetIdentity = getIntraUserIdentity(record);
                        Actor actor = getExtraUser(record.assetMetadata());
                        cryptoAddressBookManager.registerCryptoAddress(record.addressTo(),
                                assetIdentity.getPublicKey(),
                                Actors.INTRA_USER,
                                actor.getActorPublicKey(),
                                Actors.EXTRA_USER,
                                Platforms.CRYPTO_CURRENCY_PLATFORM,
                                VaultType.CRYPTO_CURRENCY_VAULT,
                                CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                                record.btcWalletPublicKey(),
                                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);
                        dao.updateTransactionStatusCryptoAddressRegistered(record.transactionRecordId());
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Transaction Registered on crypto book.");
                        break;
                    case CRYPTOADDRESS_REGISTERED:
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Sending asset bitcoins : " + record.transactionRecordId());
                        if (record.addressTo() == null) {
                            AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("This transaction failed to have a crypto address... Returning to previous state");
                            dao.updateTransactionStatusAppropriationStarted(record.transactionRecordId());
                        } else {
                            String newTx = assetVaultManager.sendAssetBitcoins(record.assetMetadata().getLastTransactionHash(), record.assetMetadata().getLastTransactionBlock(), record.addressTo(), record.networkType());
                            assetVault.updateMetadataTransactionChain(record.transactionRecordId(), newTx, null, null);
                            dao.updateTransactionStatusBitcoinsSent(record.transactionRecordId());
                            AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Bitcoins sent!");
                        }
                        break;
                    case BITCOINS_SENT: {
                        dao.updateTransactionStatusAssetDebited(record.transactionRecordId());
                        break;
                    }
                    case ASSET_DEBITED: {
                        switch (bitcoinNetworkManager.getCryptoStatus(record.assetMetadata().getLastTransactionHash())) {
                            case ON_BLOCKCHAIN:
                            case IRREVERSIBLE:
                                CryptoTransaction cryptoTransaction = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, record.assetMetadata(), CryptoStatus.ON_BLOCKCHAIN);
                                if (cryptoTransaction == null) continue;
                                ActorAssetUser mySelf = actorAssetUserManager.getActorAssetUser();
                                AssetUserWallet userWallet = assetUserWalletManager.loadAssetUserWallet(record.walletPublicKey(), cryptoTransaction.getBlockchainNetworkType());
                                AssetUserWalletBalance balance = userWallet.getBalance();
                                AssetUserWalletTransactionRecordWrapper walletRecord = new AssetUserWalletTransactionRecordWrapper(record.assetMetadata(),
                                        cryptoTransaction,
                                        mySelf.getActorPublicKey(),
                                        Actors.DAP_ASSET_USER,
                                        mySelf.getActorPublicKey(),
                                        Actors.DAP_ASSET_USER,
                                        WalletUtilities.DEFAULT_MEMO_APPROPRIATION);
                                balance.debit(walletRecord, BalanceType.BOOK);
                                dao.updateStatusSendingMessage(record.transactionRecordId());
                                break;
                        }
                        break;
                    }
                    case SENDING_MESSAGE:
                        sendMessageAssetAppropriated(record.assetMetadata());
                        sendAssetMovement(record.assetMetadata(), record.networkType());
                        dao.completeAppropriationSuccessful(record.transactionRecordId());
                        break;
                    case CREATING_MOVEMENT:
                        break;
                    default:
                        //This should never happen.
                        break;
                }
            }
        }


        public void sendMessageAssetAppropriated(final DigitalAssetMetadata metadata) throws CantGetAssetUserActorsException, CantSetObjectException, CantSendDAPMessageException, CantGetCryptoTransactionException {
            ActorAssetIssuer actorAssetIssuer = new AssetIssuerActorRecord(metadata.getDigitalAsset().getIdentityAssetIssuer().getAlias(),
                    metadata.getDigitalAsset().getIdentityAssetIssuer().getPublicKey());

            ActorAssetUser actorAssetUser = actorAssetUserManager.getActorAssetUser(); //The user of this device, whom appropriate the asset.
            CryptoTransaction cryptoTransaction = AssetVerification.foundCryptoTransaction(bitcoinNetworkManager, metadata, CryptoTransactionType.OUTGOING, actorAssetUser.getCryptoAddress());
            DAPMessage message = new DAPMessage(new AssetAppropriationContentMessage(metadata.getMetadataId(), actorAssetUser.getActorPublicKey(), cryptoTransaction.getBlockchainNetworkType()), actorAssetUser, actorAssetIssuer, DAPMessageSubject.ASSET_APPROPRIATED);
            assetTransmissionNetworkServiceManager.sendMessage(message); //FROM: USER. TO:ISSUER.
        }

        private void sendAssetMovement(DigitalAssetMetadata digitalAssetMetadata, BlockchainNetworkType networkType) throws CantSetObjectException, CantGetAssetUserActorsException, CantSendDAPMessageException {
            AssetMovementContentMessage content = new AssetMovementContentMessage(actorAssetUserManager.getActorAssetUser(), actorAssetUserManager.getActorAssetUser(), digitalAssetMetadata.getDigitalAsset().getPublicKey(), networkType, AssetMovementType.ASSET_APPROPIATED);
            ActorAssetUser actorSender = actorAssetUserManager.getActorAssetUser();
            ActorAssetIssuer actorReceiver = new AssetIssuerActorRecord(digitalAssetMetadata.getDigitalAsset().getName(), digitalAssetMetadata.getDigitalAsset().getPublicKey());
            DAPMessage dapMessage = new DAPMessage(content, actorSender, actorReceiver, DAPMessageSubject.ASSET_MOVEMENT);
            assetTransmissionNetworkServiceManager.sendMessage(dapMessage);
        }

        private IntraWalletUserIdentity getIntraUserIdentity(AppropriationTransactionRecord record) throws CantListIntraWalletUsersException, CantCreateNewIntraWalletUserException {
            List<IntraWalletUserIdentity> allIdentities = intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();
            String alias = "Asset Appropriation: " + record.digitalAsset().getName();
            IntraWalletUserIdentity assetIdentity = null;
            for (IntraWalletUserIdentity identity : allIdentities) {
                if (identity.getAlias().equals(alias)) assetIdentity = identity;
            }
            if (assetIdentity == null)
                assetIdentity = intraWalletUserIdentityManager.createNewIntraWalletUser(alias, null, Long.parseLong("0"), Frequency.NONE);
            return assetIdentity;
        }

        private Actor getExtraUser(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateExtraUserException {
            try {
                return extraUserManager.getActorByPublicKey(digitalAssetMetadata.getDigitalAsset().getPublicKey());
            } catch (CantGetExtraUserException | ExtraUserNotFoundException e) {
                assetAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                byte[] image = digitalAssetMetadata.getDigitalAsset().getResources().isEmpty() ? new byte[0] : digitalAssetMetadata.getDigitalAsset().getResources().get(0).getResourceBinayData();
                return extraUserManager.createActor(digitalAssetMetadata.getDigitalAsset().getName(), image);
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
