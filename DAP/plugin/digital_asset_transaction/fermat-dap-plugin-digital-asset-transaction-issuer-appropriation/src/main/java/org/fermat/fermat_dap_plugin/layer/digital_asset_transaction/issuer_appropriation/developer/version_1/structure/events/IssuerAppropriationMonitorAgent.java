package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
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
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantLoadAssetAppropriationTransactionListException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.AssetIssuerWalletTransactionRecordWrapper;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AppropriationTransactionRecord;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.IssuerAppropriationDigitalAssetTransactionPluginRoot;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.exceptions.CantLoadIssuerAppropriationEventListException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.database.IssuerAppropriationDAO;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.issuer_appropriation.developer.version_1.structure.functional.IssuerAppropriationVault;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class IssuerAppropriationMonitorAgent implements Agent {

    //VARIABLE DECLARATION
    private ServiceStatus status;

    {
        this.status = ServiceStatus.CREATED;
    }

    IssuerAppropriationDigitalAssetTransactionPluginRoot issuerAppropriationDigitalAssetTransactionPluginRoot;
    private final LogManager logManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final IssuerAppropriationVault assetVault;
    private final UUID pluginId;
    private final AssetVaultManager assetVaultManager;
    private final BlockchainManager bitcoinNetworkManager;
    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final CryptoVaultManager cryptoVaultManager;
    private final IntraWalletUserIdentityManager intraWalletUserIdentityManager;
    private final AssetIssuerWalletManager assetIssuerWalletManager;
    private final ExtraUserManager extraUserManager;
    private final ActorAssetIssuerManager issuerManager;


    private AppropriationAgent appropriationAgent;
    //VARIABLES ACCESSED BY AGENT INNER CLASS.
    //NEEDS TO BE VOLATILE SINCE THEY'RE BEING USED ON ANOTHER THREAD.
    //I NEED THREAD TO NOTICE ASAP.
    private volatile CountDownLatch latch;

    //CONSTRUCTORS


    public IssuerAppropriationMonitorAgent(IssuerAppropriationVault assetVault,
                                           PluginDatabaseSystem pluginDatabaseSystem,
                                           LogManager logManager,
                                           IssuerAppropriationDigitalAssetTransactionPluginRoot issuerAppropriationDigitalAssetTransactionPluginRoot,
                                           UUID pluginId,
                                           AssetVaultManager assetVaultManager,
                                           BlockchainManager bitcoinNetworkManager,
                                           CryptoAddressBookManager cryptoAddressBookManager,
                                           CryptoVaultManager cryptoVaultManager,
                                           IntraWalletUserIdentityManager intraWalletUserIdentityManager,
                                           AssetIssuerWalletManager assetIssuerWalletManager,
                                           ExtraUserManager extraUserManager,
                                           ActorAssetIssuerManager issuerManager) throws CantSetObjectException {
        this.assetVault = assetVault;
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.logManager = Validate.verifySetter(logManager, "logManager is null");
        this.issuerAppropriationDigitalAssetTransactionPluginRoot = Validate.verifySetter(issuerAppropriationDigitalAssetTransactionPluginRoot, "errorManager is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.assetVaultManager = Validate.verifySetter(assetVaultManager, "assetVaultManager is null");
        this.bitcoinNetworkManager = Validate.verifySetter(bitcoinNetworkManager, "bitcoinNetworkManager is null");
        this.cryptoAddressBookManager = Validate.verifySetter(cryptoAddressBookManager, "cryptoAddressBookManager is null");
        this.cryptoVaultManager = Validate.verifySetter(cryptoVaultManager, "cryptoVaultManager is null");
        this.intraWalletUserIdentityManager = Validate.verifySetter(intraWalletUserIdentityManager, "intraWalletUserIdentityManager is null");
        this.assetIssuerWalletManager = Validate.verifySetter(assetIssuerWalletManager, "assetIssuerWalletManager is null");
        this.extraUserManager = Validate.verifySetter(extraUserManager, "extraUserManager is null");
        this.issuerManager = Validate.verifySetter(issuerManager, "issuerManager is null");
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            logManager.log(IssuerAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: starting...", null, null);
            latch = new CountDownLatch(1);

            appropriationAgent = new AppropriationAgent();
            Thread eventThread = new Thread(appropriationAgent, "Issuer Appropriation MonitorAgent");
            eventThread.start();
        } catch (Exception e) {
            issuerAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantStartAgentException();
        }
        this.status = ServiceStatus.STARTED;
        logManager.log(IssuerAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: successfully started...", null, null);
    }

    @Override
    public void stop() {
        logManager.log(IssuerAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: stopping...", null, null);
        appropriationAgent.stopAgent();
        try {
            latch.await(); //WAIT UNTIL THE LAST RUN FINISH
        } catch (InterruptedException e) {
            issuerAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        appropriationAgent = null; //RELEASE RESOURCES.
        logManager.log(IssuerAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: successfully stopped...", null, null);
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
                    issuerAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                } catch (Exception e) {
                    issuerAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    e.printStackTrace();
                }
            }
            latch.countDown();
        }

        private void doTheMainTask() {
            try {
                IssuerAppropriationDAO dao = new IssuerAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault);
                statusMonitoring(dao);

            } catch (InvalidParameterException | CantRegisterDebitException | CantLoadWalletException | CantGetCryptoTransactionException e) {
                issuerAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            } catch (CantLoadIssuerAppropriationEventListException | CantGetTransactionsException | CantLoadAssetAppropriationTransactionListException e) {
                issuerAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            } catch (RecordsNotFoundException e) {
                issuerAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            } catch (Exception e) {
                issuerAppropriationDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void statusMonitoring(IssuerAppropriationDAO dao) throws Exception {
            for (AppropriationTransactionRecord record : dao.getUnsendedTransactions()) {
                IssuerAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation(dao.getUnsendedTransactions().size() + " unsended transactions were found.");
                switch (record.status()) {
                    case APPROPRIATION_STARTED:
                        IssuerAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("getting crypto address and saving it..." + record.transactionRecordId());
                        CryptoAddress cryptoAddress = cryptoVaultManager.getAddress(record.networkType());
                        IssuerAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Address: " + cryptoAddress.getAddress());
                        dao.updateCryptoAddress(cryptoAddress, record.transactionRecordId());
                        dao.updateTransactionStatusCryptoAddressObtained(record.transactionRecordId());
                        IssuerAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("statuses updated");
                        break;
                    case CRYPTOADDRESS_OBTAINED:
                        IssuerAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("registering crypto address in crypto book. : " + record.transactionRecordId());
                        IntraWalletUserIdentity assetIdentity = getIntraUserIdentity(record);
                        Actor extraUser = getExtraUser(record.assetMetadata());
                        cryptoAddressBookManager.registerCryptoAddress(record.addressTo(),
                                assetIdentity.getPublicKey(),
                                Actors.INTRA_USER,
                                extraUser.getActorPublicKey(),
                                Actors.EXTRA_USER,
                                Platforms.CRYPTO_CURRENCY_PLATFORM,
                                VaultType.CRYPTO_CURRENCY_VAULT,
                                CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                                record.btcWalletPublicKey(),
                                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);
                        dao.updateTransactionStatusCryptoAddressRegistered(record.transactionRecordId());
                        IssuerAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Transaction Registered on crypto book.");
                        break;
                    case CRYPTOADDRESS_REGISTERED:
                        IssuerAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Sending asset bitcoins : " + record.transactionRecordId());
                        if (record.addressTo() == null) {
                            IssuerAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("This transaction failed to have a crypto address... Returning to previous state");
                            dao.updateTransactionStatusAppropriationStarted(record.transactionRecordId());
                        } else {
                            String newTx = assetVaultManager.sendAssetBitcoins(record.assetMetadata().getLastTransactionHash(), record.assetMetadata().getLastTransactionBlock(), record.addressTo(), record.networkType());
                            assetVault.updateMetadataTransactionChain(record.transactionRecordId(), newTx, null, null);
                            dao.updateTransactionStatusBitcoinsSent(record.transactionRecordId());
                            IssuerAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Bitcoins sent!");
                        }
                        break;
                    case BITCOINS_SENT: {
                        CryptoTransaction cryptoTransaction = AssetVerification.foundCryptoTransaction(bitcoinNetworkManager, record.assetMetadata(), CryptoTransactionType.OUTGOING, record.addressTo());
                        if (cryptoTransaction == null) continue;
                        ActorAssetIssuer mySelf = issuerManager.getActorAssetIssuer();
                        AssetIssuerWallet issuerWallet = assetIssuerWalletManager.loadAssetIssuerWallet(record.walletPublicKey(), cryptoTransaction.getBlockchainNetworkType());
                        AssetIssuerWalletBalance balance = issuerWallet.getBalance();
                        AssetIssuerWalletTransactionRecordWrapper walletRecord = new AssetIssuerWalletTransactionRecordWrapper(record.assetMetadata(),
                                cryptoTransaction,
                                mySelf.getActorPublicKey(),
                                Actors.DAP_ASSET_ISSUER,
                                mySelf.getActorPublicKey(),
                                Actors.DAP_ASSET_ISSUER,
                                WalletUtilities.DEFAULT_MEMO_APPROPRIATION);
                        balance.debit(walletRecord, BalanceType.AVAILABLE);
                        dao.updateTransactionStatusAssetDebited(record.transactionRecordId());
                        break;
                    }
                    case ASSET_DEBITED: {
                        switch (bitcoinNetworkManager.getCryptoStatus(record.assetMetadata().getLastTransactionHash())) {
                            case ON_BLOCKCHAIN:
                            case IRREVERSIBLE:
                                CryptoTransaction cryptoTransaction = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, record.assetMetadata(), CryptoStatus.ON_BLOCKCHAIN);
                                if (cryptoTransaction == null) continue;
                                ActorAssetIssuer mySelf = issuerManager.getActorAssetIssuer();
                                AssetIssuerWallet issuerWallet = assetIssuerWalletManager.loadAssetIssuerWallet(record.walletPublicKey(), cryptoTransaction.getBlockchainNetworkType());
                                AssetIssuerWalletBalance balance = issuerWallet.getBalance();
                                AssetIssuerWalletTransactionRecordWrapper walletRecord = new AssetIssuerWalletTransactionRecordWrapper(record.assetMetadata(),
                                        cryptoTransaction,
                                        mySelf.getActorPublicKey(),
                                        Actors.DAP_ASSET_ISSUER,
                                        mySelf.getActorPublicKey(),
                                        Actors.DAP_ASSET_ISSUER,
                                        WalletUtilities.DEFAULT_MEMO_APPROPRIATION);
                                balance.debit(walletRecord, BalanceType.BOOK);
                                dao.completeAppropriationSuccessful(record.transactionRecordId());
                                break;
                        }
                        break;
                    }
                    default:
                        //This should never happen.
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


        private IntraWalletUserIdentity getIntraUserIdentity(AppropriationTransactionRecord record) throws CantListIntraWalletUsersException, CantCreateNewIntraWalletUserException {
            List<IntraWalletUserIdentity> allIdentities = intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();
            String alias = "Issuer Appropriation: " + record.digitalAsset().getName();
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
                byte[] image = digitalAssetMetadata.getDigitalAsset().getResources().isEmpty() ? new byte[0] : digitalAssetMetadata.getDigitalAsset().getResources().get(0).getResourceBinayData();
                return extraUserManager.createActor(digitalAssetMetadata.getDigitalAsset().getName(), image);
            }
        }
    }
}
