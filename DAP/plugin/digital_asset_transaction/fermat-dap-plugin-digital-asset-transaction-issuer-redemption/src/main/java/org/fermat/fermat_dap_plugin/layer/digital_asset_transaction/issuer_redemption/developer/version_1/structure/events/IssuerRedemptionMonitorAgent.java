package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.developer.version_1.structure.events;


import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.developer.version_1.structure.database.IssuerRedemptionDao;
import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetMovementContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.util.ActorUtils;
import org.fermat.fermat_dap_api.layer.all_definition.util.DAPStandardFormats;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetIssuerWalletTransactionRecordWrapper;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.issuer_appropriation.interfaces.IssuerAppropriationManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/10/15.
 */
public class IssuerRedemptionMonitorAgent implements Agent {

    private final ErrorManager errorManager;
    private MonitorAgent agent;
    private IssuerRedemptionDao issuerRedemptionDao;
    private AssetIssuerWalletManager assetIssuerWalletManager;
    private BitcoinNetworkManager bitcoinNetworkManager;
    private CryptoAddressBookManager cryptoAddressBookManager;
    private AssetVaultManager assetVaultManager;
    private IssuerAppropriationManager issuerAppropriationManager;
    private AssetTransmissionNetworkServiceManager assetTransmissionManager;
    //ActorManagers
    private ActorAssetUserManager actorAssetUserManager;
    private ActorAssetIssuerManager actorAssetIssuerManager;
    private ActorAssetRedeemPointManager actorAssetRedeemPointManager;
    //TODO REMOVE HARDCODE!!!
    private String issuerPublicKeyWallet = WalletUtilities.WALLET_PUBLIC_KEY;
    private String btcWallet;
    private final IncomingCryptoManager incomingCryptoManager;
    private final TransactionProtocolManager<CryptoTransaction> protocolManager;

    public IssuerRedemptionMonitorAgent(AssetIssuerWalletManager assetIssuerWalletManager,
                                        ActorAssetIssuerManager actorAssetIssuerManager,
                                        BitcoinNetworkManager bitcoinNetworkManager,
                                        CryptoAddressBookManager cryptoAddressBookManager,
                                        ErrorManager errorManager,
                                        UUID pluginId,
                                        PluginDatabaseSystem pluginDatabaseSystem,
                                        AssetVaultManager assetVaultManager,
                                        IssuerAppropriationManager issuerAppropriationManager,
                                        WalletManagerManager walletMiddlewareManager, AssetTransmissionNetworkServiceManager assetTransmissionManager, ActorAssetUserManager actorAssetUserManager, ActorAssetRedeemPointManager actorAssetRedeemPointManager,
                                        IncomingCryptoManager incomingCryptoManager) throws CantSetObjectException, CantExecuteDatabaseOperationException {
        this.assetTransmissionManager = assetTransmissionManager;
        this.actorAssetUserManager = actorAssetUserManager;
        this.actorAssetRedeemPointManager = actorAssetRedeemPointManager;
        this.assetIssuerWalletManager = Validate.verifySetter(assetIssuerWalletManager, "assetIssuerWalletManager is null");
        this.errorManager = Validate.verifySetter(errorManager, "errorManager is null");
        this.bitcoinNetworkManager = Validate.verifySetter(bitcoinNetworkManager, "bitcoinNetworkManager is null");
        this.actorAssetIssuerManager = Validate.verifySetter(actorAssetIssuerManager, "actorAssetIssuerManager is null");
        this.cryptoAddressBookManager = Validate.verifySetter(cryptoAddressBookManager, "cryptoAddressBookManager is null");
        this.assetVaultManager = Validate.verifySetter(assetVaultManager, "assetVaultManager is null");
        this.issuerAppropriationManager = Validate.verifySetter(issuerAppropriationManager, "issuerAppropriationManager is null");
        issuerRedemptionDao = new IssuerRedemptionDao(pluginId, pluginDatabaseSystem);
        List<InstalledWallet> installedWallets;
        try {
            installedWallets = walletMiddlewareManager.getInstalledWallets();
            //TODO REMOVE HARDCODE
            InstalledWallet installedWallet = installedWallets.get(0);
            btcWallet = installedWallet.getWalletPublicKey();
        } catch (CantListWalletsException e) {
            e.printStackTrace();
        }
        this.incomingCryptoManager = incomingCryptoManager;
        this.protocolManager = incomingCryptoManager.getTransactionManager();
    }

    @Override
    public void start() throws CantStartAgentException {
        agent = new MonitorAgent();
        Thread agentThread = new Thread(agent, "Issuer Redemption MonitorAgent");
        agentThread.start();
    }

    @Override
    public void stop() {
        agent.stopAgent();
    }

    /**
     * Private class which implements runnable and is started by the Agent
     * Based on MonitorAgent created by Rodrigo Acosta
     */
    private class MonitorAgent implements Runnable {

        private int WAIT_TIME = 5 * 1000;
        private boolean agentRunning;

        {
            startAgent();
        }

        @Override
        public void run() {
            while (agentRunning) {
                try {
                    doTheMainTask();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorManager.reportUnexpectedPluginException(Plugins.ISSUER_REDEMPTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                } finally {
                    try {
                        Thread.sleep(WAIT_TIME);
                    } catch (InterruptedException e) {
                        agentRunning = false;
                    /*If this happen there's a chance that the information remains
                    in a corrupt state. That probably would be fixed in a next run.
                    */
                        errorManager.reportUnexpectedPluginException(Plugins.ISSUER_REDEMPTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                    }
                }
            }

        }


        private void doTheMainTask() throws Exception {
            checkPendingCryptoRouterEvents();
            checkAssetMovements();
        }

        private void checkAssetMovements() throws DAPException, CantLoadWalletException {
            for (DAPMessage message : assetTransmissionManager.getUnreadDAPMessageBySubject(DAPMessageSubject.ASSET_MOVEMENT)) {
                AssetMovementContentMessage content = (AssetMovementContentMessage) message.getMessageContent();
                ActorUtils.storeDAPActor(content.getSystemUser(), actorAssetUserManager, actorAssetRedeemPointManager, actorAssetIssuerManager);
                ActorUtils.storeDAPActor(content.getNewUser(), actorAssetUserManager, actorAssetRedeemPointManager, actorAssetIssuerManager);
                assetIssuerWalletManager.loadAssetIssuerWallet(WalletUtilities.WALLET_PUBLIC_KEY, content.getNetworkType()).newMovement(content.getNewUser(), content.getSystemUser(), content.getAssetPublicKey(), content.getMovementType());
                assetTransmissionManager.confirmReception(message);
            }
        }

        public void checkPendingCryptoRouterEvents() throws Exception {
            for (String eventId : issuerRedemptionDao.getPendingCryptoRouterEvents()) {
                boolean notify = true;
                switch (issuerRedemptionDao.getEventTypeById(eventId)) {
                    case INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEMPTION: {
                        List<DigitalAssetMetadata> allUsedAssets = new ArrayList<>();
                        allUsedAssets.addAll(assetIssuerWalletManager.loadAssetIssuerWallet(issuerPublicKeyWallet, BlockchainNetworkType.TEST_NET).getAllUnusedAssets());
                        allUsedAssets.addAll(assetIssuerWalletManager.loadAssetIssuerWallet(issuerPublicKeyWallet, BlockchainNetworkType.PRODUCTION).getAllUnusedAssets());
                        allUsedAssets.addAll(assetIssuerWalletManager.loadAssetIssuerWallet(issuerPublicKeyWallet, BlockchainNetworkType.REG_TEST).getAllUnusedAssets());
                        for (DigitalAssetMetadata digitalAssetMetadata : allUsedAssets) {
                            List<CryptoTransaction> allChildTx = bitcoinNetworkManager.getChildTransactionsFromParent(digitalAssetMetadata.getLastTransactionHash());
                            if (allChildTx.isEmpty()) {
                                notify = false;
                                continue;
                            }
                            CryptoTransaction lastChild = allChildTx.get(0);
                            digitalAssetMetadata.addNewTransaction(lastChild.getTransactionHash(), lastChild.getBlockHash());
                            Transaction<CryptoTransaction> transaction = transactionForHash(digitalAssetMetadata);
                            if (transaction == null) {
                                notify = false;
                                continue; //NOT TODAY KID.
                            }
                            AssetIssuerWallet wallet = assetIssuerWalletManager.loadAssetIssuerWallet(issuerPublicKeyWallet, transaction.getInformation().getBlockchainNetworkType());
                            String publicKeyFrom = wallet.getUserDeliveredToPublicKey(digitalAssetMetadata.getMetadataId());
                            String publicKeyTo = actorAssetIssuerManager.getActorAssetIssuer().getActorPublicKey();
                            AssetIssuerWalletTransactionRecordWrapper recordWrapper = new AssetIssuerWalletTransactionRecordWrapper(digitalAssetMetadata, transaction.getInformation(), publicKeyFrom, Actors.DAP_ASSET_USER, publicKeyTo, Actors.DAP_ASSET_ISSUER, WalletUtilities.DEFAULT_MEMO_REDEMPTION);
                            issuerRedemptionDao.assetReceived(digitalAssetMetadata, transaction.getInformation().getBlockchainNetworkType());
                            wallet.getBalance().credit(recordWrapper, BalanceType.BOOK);
                            protocolManager.confirmReception(transaction.getTransactionID());
                            notify = true;
                        }
                        break;
                    }
                    case INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION: {
                        for (Map.Entry<BlockchainNetworkType, String> genesisTx : issuerRedemptionDao.getToBeAppliedGenesisTransaction().entrySet()) {
                            AssetIssuerWallet wallet = assetIssuerWalletManager.loadAssetIssuerWallet(issuerPublicKeyWallet, genesisTx.getKey());
                            DigitalAssetMetadata digitalAssetMetadata = wallet.getDigitalAssetMetadata(genesisTx.getValue());
                            Transaction<CryptoTransaction> transaction = transactionForHash(digitalAssetMetadata);
                            if (transaction == null) {
                                notify = false;
                                continue;
                            }
                            CryptoAddressBookRecord bookRecord = cryptoAddressBookManager.getCryptoAddressBookRecordByCryptoAddress(transaction.getInformation().getAddressTo());
                            String publicKeyFrom = wallet.getUserDeliveredToPublicKey(digitalAssetMetadata.getMetadataId());
                            String publicKeyTo = actorAssetIssuerManager.getActorAssetIssuer().getActorPublicKey();
                            wallet.assetRedeemed(digitalAssetMetadata.getMetadataId(), null, bookRecord.getDeliveredToActorPublicKey());
                            digitalAssetMetadata.getDigitalAsset().setGenesisAmount(transaction.getInformation().getCryptoAmount());
                            digitalAssetMetadata.setMetadataId(UUID.randomUUID());
                            digitalAssetMetadata.addNewTransaction(transaction.getInformation().getTransactionHash(), transaction.getInformation().getBlockHash());
                            wallet.createdNewAsset(digitalAssetMetadata);
                            /**
                             * Notifies the Asset Vault that the address of this Redeem Point, has been used.
                             */
                            assetVaultManager.notifyUsedRedeemPointAddress(bookRecord.getCryptoAddress(), bookRecord.getDeliveredToActorPublicKey());
                            AssetIssuerWalletTransactionRecordWrapper recordWrapper = new AssetIssuerWalletTransactionRecordWrapper(digitalAssetMetadata, transaction.getInformation(), publicKeyFrom, Actors.DAP_ASSET_USER, publicKeyTo, Actors.DAP_ASSET_ISSUER, WalletUtilities.DEFAULT_MEMO_REDEMPTION);
                            wallet.getBalance().credit(recordWrapper, BalanceType.AVAILABLE);
                            issuerRedemptionDao.redemptionFinished(digitalAssetMetadata);
                            protocolManager.confirmReception(transaction.getTransactionID());
                            if (transaction.getInformation().getCryptoAmount() < DAPStandardFormats.MINIMUN_SATOSHI_AMOUNT) {
                                System.out.println("ASSET AMOUNT IS NOT ENOUGH TO START ANOTHER CYCLE, AUTOMATIC APPROPRIATING IT...");
                                issuerAppropriationManager.appropriateAsset(digitalAssetMetadata, issuerPublicKeyWallet, btcWallet, genesisTx.getKey());
                            }
                            notify = true;
                        }
                    }
                    break;
                    case INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEMPTION:
                        break;
                    case INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION:
                        break;
                }
                if (notify) {
                    issuerRedemptionDao.notifyEvent(eventId);
                }
            }
        }

        public void stopAgent() {
            agentRunning = false;
        }

        public void startAgent() {
            agentRunning = true;
        }

        private Transaction<CryptoTransaction> transactionForHash(DigitalAssetMetadata metadata) throws CantDeliverPendingTransactionsException {
            List<Transaction<CryptoTransaction>> pendingTransactions = protocolManager.getPendingTransactions(Specialist.ASSET_REDEMPTION_SPECIALIST);
            for (Transaction<CryptoTransaction> transaction : pendingTransactions) {
                if (transaction.getInformation().getTransactionHash().equals(metadata.getLastTransactionHash())) {
                    return transaction;
                }
            }
            return null;
        }
    }
}
