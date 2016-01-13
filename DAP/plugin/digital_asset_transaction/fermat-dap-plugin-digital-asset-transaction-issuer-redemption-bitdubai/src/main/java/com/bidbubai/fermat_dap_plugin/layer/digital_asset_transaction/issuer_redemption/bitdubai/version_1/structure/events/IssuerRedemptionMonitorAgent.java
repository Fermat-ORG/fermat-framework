package com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.structure.events;


import com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.exceptions.CantLoadIssuerRedemptionEventListException;
import com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.structure.database.IssuerRedemptionDao;
import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookRecord;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantGetIssuerNetworkServiceMessageListException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions.CantGetAssetStatisticException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetIssuerWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util.AssetVerification;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

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
    private ActorAssetIssuerManager actorAssetIssuerManager;
    private CryptoAddressBookManager cryptoAddressBookManager;
    private AssetVaultManager assetVaultManager;

    public IssuerRedemptionMonitorAgent(AssetIssuerWalletManager assetIssuerWalletManager,
                                        ActorAssetIssuerManager actorAssetIssuerManager,
                                        BitcoinNetworkManager bitcoinNetworkManager,
                                        CryptoAddressBookManager cryptoAddressBookManager,
                                        ErrorManager errorManager,
                                        UUID pluginId,
                                        PluginDatabaseSystem pluginDatabaseSystem,
                                        AssetVaultManager assetVaultManager) throws CantSetObjectException, CantExecuteDatabaseOperationException {
        this.assetIssuerWalletManager = Validate.verifySetter(assetIssuerWalletManager, "assetIssuerWalletManager is null");
        this.errorManager = Validate.verifySetter(errorManager, "errorManager is null");
        this.bitcoinNetworkManager = Validate.verifySetter(bitcoinNetworkManager, "bitcoinNetworkManager is null");
        this.actorAssetIssuerManager = Validate.verifySetter(actorAssetIssuerManager, "actorAssetIssuerManager is null");
        this.cryptoAddressBookManager = Validate.verifySetter(cryptoAddressBookManager, "cryptoAddressBookManager is null");
        this.assetVaultManager = Validate.verifySetter(assetVaultManager, "assetVaultManager is null");
        issuerRedemptionDao = new IssuerRedemptionDao(pluginId, pluginDatabaseSystem);
    }

    @Override
    public void start() throws CantStartAgentException {
        agent = new MonitorAgent();
        Thread agentThread = new Thread(agent);
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
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    agentRunning = false;
                    /*If this happen there's a chance that the information remains
                    in a corrupt state. That probably would be fixed in a next run.
                    */
                    errorManager.reportUnexpectedPluginException(Plugins.ISSUER_REDEMPTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                } catch (Exception e) {
                    e.printStackTrace();
                    errorManager.reportUnexpectedPluginException(Plugins.ISSUER_REDEMPTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }

        }


        private void doTheMainTask() throws Exception {
            checkPendingCryptoRouterEvents();
        }

        public void checkPendingCryptoRouterEvents() throws Exception {
            for (String eventId : issuerRedemptionDao.getPendingCryptoRouterEvents()) {
                boolean notify = true;
                switch (issuerRedemptionDao.getEventTypeById(eventId)) {
                    case INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_REDEMPTION: {
                        AssetIssuerWallet wallet = assetIssuerWalletManager.loadAssetIssuerWallet("walletPublicKeyTest");
                        for (DigitalAssetMetadata digitalAssetMetadata : wallet.getAllUsedAssets()) {
                            CryptoTransaction cryptoTransactionOnCryptoNetwork = AssetVerification.getCryptoTransactionFromCryptoNetworkByCryptoStatus(bitcoinNetworkManager, digitalAssetMetadata.getGenesisTransaction(), CryptoStatus.ON_CRYPTO_NETWORK);
                            if (cryptoTransactionOnCryptoNetwork == null) {
                                notify = false;
                                continue; //NOT TODAY KID.
                            }
                            String publicKeyFrom = wallet.getUserDeliveredToPublicKey(digitalAssetMetadata.getDigitalAsset().getPublicKey());
                            digitalAssetMetadata.setGenesisBlock(cryptoTransactionOnCryptoNetwork.getBlockHash());
                            digitalAssetMetadata.setGenesisTransaction(cryptoTransactionOnCryptoNetwork.getTransactionHash());
                            digitalAssetMetadata.getDigitalAsset().setGenesisAmount(cryptoTransactionOnCryptoNetwork.getCryptoAmount());

                            String publicKeyTo = actorAssetIssuerManager.getActorAssetIssuer().getActorPublicKey();
                            AssetIssuerWalletTransactionRecordWrapper recordWrapper = new AssetIssuerWalletTransactionRecordWrapper(digitalAssetMetadata, cryptoTransactionOnCryptoNetwork, publicKeyFrom, publicKeyTo);
                            wallet.getBalance().credit(recordWrapper, BalanceType.BOOK);
                        }
                        break;
                    }
                    case INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_REDEMPTION: {
                        AssetIssuerWallet wallet = assetIssuerWalletManager.loadAssetIssuerWallet("walletPublicKeyTest");
                        for (DigitalAssetMetadata digitalAssetMetadata : wallet.getAllUsedAssets()) {
                            CryptoTransaction cryptoTx;
                            try {
                                cryptoTx = getCryptoTx(digitalAssetMetadata);
                            } catch (CantGetCryptoTransactionException e) {
                                //WAIT FOR IT...
                                notify = false;
                                continue;
                            }
                            CryptoAddressBookRecord bookRecord = cryptoAddressBookManager.getCryptoAddressBookRecordByCryptoAddress(cryptoTx.getAddressTo());
                            String publicKeyFrom = wallet.getUserDeliveredToPublicKey(digitalAssetMetadata.getDigitalAsset().getPublicKey());
                            String publicKeyTo = actorAssetIssuerManager.getActorAssetIssuer().getActorPublicKey();
                            AssetIssuerWalletTransactionRecordWrapper recordWrapper = new AssetIssuerWalletTransactionRecordWrapper(digitalAssetMetadata, cryptoTx, publicKeyFrom, publicKeyTo);
                            wallet.getBalance().credit(recordWrapper, BalanceType.AVAILABLE);
                            wallet.assetRedeemed(digitalAssetMetadata.getDigitalAsset().getPublicKey(), null, bookRecord.getDeliveredToActorPublicKey());

                            /**
                             * Notifies the Asset Vault that the address of this Redeem Point, has been used.
                             */
                            assetVaultManager.notifyUsedRedeemPointAddress(bookRecord.getCryptoAddress(), bookRecord.getDeliveredToActorPublicKey());
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

        private CryptoTransaction getCryptoTx(DigitalAssetMetadata digitalAssetMetadata) throws CantGetCryptoTransactionException {
            CryptoTransaction lastTx = bitcoinNetworkManager.getLastChildCryptoTransaction(null, digitalAssetMetadata.getGenesisTransaction(), digitalAssetMetadata.getGenesisBlock());
            return bitcoinNetworkManager.getCryptoTransaction(lastTx.getTransactionHash());
        }

        public void stopAgent() {
            agentRunning = false;
        }

        public void startAgent() {
            agentRunning = true;
        }
    }
}
