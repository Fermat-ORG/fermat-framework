package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.events;

import com.bitdubai.fermat_api.Agent;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AppropriationStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPMessageType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.EventStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetAppropriationContentMessage;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.exceptions.CantSendMessageException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_issuer.interfaces.AssetIssuerActorNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.exceptions.CantLoadAssetAppropriationTransactionListException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_appropriation.interfaces.AssetAppropriationTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.AssetAppropriationDigitalAssetTransactionPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.exceptions.CantLoadAssetAppropriationEventListException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.database.AssetAppropriationDAO;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.structure.functional.AssetAppropriationVault;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

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

    private final ErrorManager errorManager;
    private final LogManager logManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final AssetAppropriationVault assetVault;
    private final UUID pluginId;
    private final AssetVaultManager assetVaultManager;
    private final AssetUserWalletManager assetUserWalletManager;
    private final BitcoinNetworkManager bitcoinNetworkManager;
    private final CryptoAddressBookManager cryptoAddressBookManager;
    private final CryptoVaultManager cryptoVaultManager;
    private final IntraWalletUserIdentityManager intraWalletUserIdentityManager;
    private final AssetIssuerActorNetworkServiceManager assetIssuerActorNetworkServiceManager;
    private final AssetIssuerWalletManager assetIssuerWalletManager;
    private final ActorAssetUserManager actorAssetUserManager;

    private AppropriationAgent appropriationAgent;
    //VARIABLES ACCESSED BY AGENT INNER CLASS.
    //NEEDS TO BE VOLATILE SINCE THEY'RE BEING USED ON ANOTHER THREAD.
    //I NEED THREAD TO NOTICE ASAP.
    private volatile CountDownLatch latch;

    //CONSTRUCTORS


    public AssetAppropriationMonitorAgent(AssetAppropriationVault assetVault,
                                          PluginDatabaseSystem pluginDatabaseSystem,
                                          LogManager logManager,
                                          ErrorManager errorManager,
                                          UUID pluginId,
                                          AssetVaultManager assetVaultManager,
                                          AssetUserWalletManager assetUserWalletManager,
                                          BitcoinNetworkManager bitcoinNetworkManager,
                                          CryptoAddressBookManager cryptoAddressBookManager,
                                          CryptoVaultManager cryptoVaultManager,
                                          IntraWalletUserIdentityManager intraWalletUserIdentityManager,
                                          AssetIssuerActorNetworkServiceManager assetIssuerActorNetworkServiceManager,
                                          AssetIssuerWalletManager assetIssuerWalletManager,
                                          ActorAssetUserManager actorAssetUserManager) throws CantSetObjectException {
        this.assetVault = assetVault;
        this.pluginDatabaseSystem = Validate.verifySetter(pluginDatabaseSystem, "pluginDatabaseSystem is null");
        this.logManager = Validate.verifySetter(logManager, "logManager is null");
        this.errorManager = Validate.verifySetter(errorManager, "errorManager is null");
        this.pluginId = Validate.verifySetter(pluginId, "pluginId is null");
        this.assetVaultManager = Validate.verifySetter(assetVaultManager, "assetVaultManager is null");
        this.assetUserWalletManager = Validate.verifySetter(assetUserWalletManager, "assetUserWalletManager is null");
        this.bitcoinNetworkManager = Validate.verifySetter(bitcoinNetworkManager, "bitcoinNetworkManager is null");
        this.cryptoAddressBookManager = Validate.verifySetter(cryptoAddressBookManager, "cryptoAddressBookManager is null");
        this.cryptoVaultManager = Validate.verifySetter(cryptoVaultManager, "cryptoVaultManager is null");
        this.intraWalletUserIdentityManager = Validate.verifySetter(intraWalletUserIdentityManager, "intraWalletUserIdentityManager is null");
        this.assetIssuerActorNetworkServiceManager = Validate.verifySetter(assetIssuerActorNetworkServiceManager, "assetIssuerActorNetworkServiceManager is null");
        this.assetIssuerWalletManager = Validate.verifySetter(assetIssuerWalletManager, "assetIssuerWalletManager is null");
        this.actorAssetUserManager = Validate.verifySetter(actorAssetUserManager, "actorAssetUserManager is null");
    }

    //PUBLIC METHODS

    @Override
    public void start() throws CantStartAgentException {
        try {
            logManager.log(AssetAppropriationDigitalAssetTransactionPluginRoot.getLogLevelByClass(this.getClass().getName()), "Asset Appropriation Protocol Notification Agent: starting...", null, null);
            latch = new CountDownLatch(1);

            appropriationAgent = new AppropriationAgent();
            Thread eventThread = new Thread(appropriationAgent);
            eventThread.start();
        } catch (Exception e) {
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
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
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
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
                } catch (Exception e) {
                    e.printStackTrace();
                    errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                }
            }

            latch.countDown();
        }

        private void doTheMainTask() {
            try (AssetAppropriationDAO dao = new AssetAppropriationDAO(pluginDatabaseSystem, pluginId, assetVault)) {

                eventMonitoring(dao);

                statusMonitoring(dao);

            } catch (InvalidParameterException | CantRegisterDebitException | CantLoadWalletException | CantGetCryptoTransactionException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            } catch (CantLoadAssetAppropriationEventListException | CantGetTransactionsException | CantLoadAssetAppropriationTransactionListException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            } catch (RecordsNotFoundException e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.NOT_IMPORTANT, e);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_APPROPRIATION_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            }
        }

        private void eventMonitoring(AssetAppropriationDAO dao) throws Exception {
            for (String eventId : dao.getPendingActorAssetUserEvents()) {
                switch (dao.getEventTypeById(eventId)) {
                    //TODO CHANGE THESE EVENTS TO THE NEW ONES.
                    case INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER:
                        for (AssetAppropriationTransactionRecord record : dao.getTransactionsForStatus(AppropriationStatus.BITCOINS_SENT)) {
                            if ("-".equals(record.genesisTransaction())) {
                                //If for some reason it wasn't updated then the bitcoins wasn't sent.
                                dao.updateTransactionStatusCryptoAddressObtained(record.transactionRecordId());
                                continue;
                            }
                            for (CryptoTransaction cryptoTransaction : bitcoinNetworkManager.getCryptoTransaction(record.genesisTransaction())) {
                                if (cryptoTransaction.getCryptoStatus() == CryptoStatus.ON_CRYPTO_NETWORK) {
                                    AssetUserWallet userWallet = assetUserWalletManager.loadAssetUserWallet(record.userWalletPublicKey());
                                    AssetUserWalletBalance balance = userWallet.getBalance();
                                    AssetUserWalletTransactionRecordWrapper walletRecord = new AssetUserWalletTransactionRecordWrapper(record.digitalAsset(),
                                            cryptoTransaction,
                                            record.digitalAsset().getPublicKey(),
                                            Actors.DAP_ASSET_USER,
                                            record.addressTo().getAddress(),
                                            Actors.EXTRA_USER);
                                    balance.debit(walletRecord, BalanceType.AVAILABLE);
                                    dao.updateTransactionStatusAssetDebited(record.transactionRecordId());
                                }
                            }
                        }
                        dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                        break;

                    case INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER:
                        for (AssetAppropriationTransactionRecord record : dao.getTransactionsForStatus(AppropriationStatus.ASSET_DEBITED)) {
                            for (CryptoTransaction cryptoTransaction : bitcoinNetworkManager.getCryptoTransaction(record.genesisTransaction())) {
                                if (cryptoTransaction.getCryptoStatus() == CryptoStatus.ON_BLOCKCHAIN) {
                                    AssetUserWallet userWallet = assetUserWalletManager.loadAssetUserWallet(record.userWalletPublicKey());
                                    AssetUserWalletBalance balance = userWallet.getBalance();
                                    AssetUserWalletTransactionRecordWrapper walletRecord = new AssetUserWalletTransactionRecordWrapper(record.digitalAsset(),
                                            cryptoTransaction,
                                            record.digitalAsset().getPublicKey(),
                                            Actors.DAP_ASSET_USER,
                                            record.addressTo().getAddress(),
                                            Actors.EXTRA_USER);
                                    balance.debit(walletRecord, BalanceType.BOOK);

                                    sendMessageAssetAppropriated(record.digitalAsset());

                                    dao.completeAppropriationSuccessful(record.transactionRecordId());
                                }
                            }
                        }
                        dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                        break;

                    default:
                        //THIS CAN'T HAPPEN. But if it happen, this event is not for me and I don't care...
                        dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                        break;
                }
            }

            for (String eventId : dao.getPendingIssuerNetworkServiceEvents()) {
                switch (dao.getDAPEventTypeById(eventId)) {
                    case NEW_RECEIVE_MESSAGE_ACTOR:
                        for (DAPMessage message : assetIssuerActorNetworkServiceManager.getUnreadDAPMessagesByType(DAPMessageType.ASSET_APPROPRIATION)) {
                            if (message.getMessageContent() instanceof AssetAppropriationContentMessage) { //Just a security measure, this SHOULD always be true.
                                AssetAppropriationContentMessage contentMessage = (AssetAppropriationContentMessage) message.getMessageContent();
                                //TODO REMOVE HARDCODE.
                                AssetIssuerWallet wallet = assetIssuerWalletManager.loadAssetIssuerWallet("walletPublicKeyTest");
                                wallet.assetAppropriated(contentMessage.getDigitalAssetAppropriated().getPublicKey(), contentMessage.getUserThatAppropriate().getActorPublicKey());
                            }
                        }
                        dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                        break;
                    default:
                        //I can't do anything with this event...
                        dao.updateEventStatus(EventStatus.NOTIFIED, eventId);
                        break;
                }

            }
        }

        private void statusMonitoring(AssetAppropriationDAO dao) throws Exception {
            for (AssetAppropriationTransactionRecord record : dao.getUnsendedTransactions()) {
                AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation(dao.getUnsendedTransactions().size() + " unsended transactions were found.");
                switch (record.status()) {
                    case APPROPRIATION_STARTED:
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("getting crypto address and saving it..." + record.transactionRecordId());
                        CryptoAddress cryptoAddress = cryptoVaultManager.getAddress();
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Address: " + cryptoAddress.getAddress());
                        dao.updateCryptoAddress(cryptoAddress, record.transactionRecordId());
                        dao.updateTransactionStatusCryptoAddressObtained(record.transactionRecordId());
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("statuses updated");
                        break;
                    case CRYPTOADDRESS_OBTAINED:
                        AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("registering crypto address in crypto book. : " + record.transactionRecordId());
                        IntraWalletUserIdentity assetIdentity = intraWalletUserIdentityManager.createNewIntraWalletUser("Asset Appropriation: " + record.digitalAsset().getName(), null);
                        cryptoAddressBookManager.registerCryptoAddress(record.addressTo(),
                                assetIdentity.getPublicKey(),
                                Actors.INTRA_USER,
                                record.btcWalletPublicKey(),
                                Actors.EXTRA_USER,
                                Platforms.CRYPTO_CURRENCY_PLATFORM,
                                VaultType.CRYPTO_CURRENCY_VAULT,
                                CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                                record.userWalletPublicKey(),
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
                            String genesisTransaction = assetVaultManager.sendAssetBitcoins(record.assetMetadata().getGenesisTransaction(), record.addressTo(), record.digitalAsset().getGenesisAmount());
                            dao.updateGenesisTransaction(genesisTransaction, record.transactionRecordId());
                            dao.updateTransactionStatusBitcoinsSent(record.transactionRecordId());
                            AssetAppropriationDigitalAssetTransactionPluginRoot.debugAssetAppropriation("Bitcoins sent!");
                        }
                        break;
                    default:
                        //This should never happen.
                        break;
                }
            }
        }


        public void sendMessageAssetAppropriated(final DigitalAsset assetAppropriated) throws CantGetAssetUserActorsException, CantSetObjectException, CantSendMessageException {
            ActorAssetIssuer actorAssetIssuer = new ActorAssetIssuer() {
                /**
                 * The method <code>getActorPublicKey</code> gives us the public key of the represented a Actor
                 *
                 * @return the publicKey
                 */
                @Override
                public String getActorPublicKey() {
                    return assetAppropriated.getIdentityAssetIssuer().getPublicKey();
                }

                /**
                 * The method <code>getName</code> gives us the name of the represented a Actor
                 *
                 * @return the name of the intra user
                 */
                @Override
                public String getName() {
                    return assetAppropriated.getIdentityAssetIssuer().getAlias();
                }

                /**
                 * The method <coda>getProfileImage</coda> gives us the profile image of the represented a Actor
                 *
                 * @return the image
                 */
                @Override
                public byte[] getProfileImage() {
                    return assetAppropriated.getIdentityAssetIssuer().getProfileImage();
                }

                /**
                 * The method <code>getRegistrationDate</code> gives us the date when both Asset Issuers
                 * exchanged their information and accepted each other as contacts.
                 *
                 * @return the date
                 */
                @Override
                public long getRegistrationDate() {
                    return 0;
                }

                /**
                 * The method <code>getLastConnectionDate</code> gives us the Las Connection Date of the represented
                 * Asset Issuer
                 *
                 * @return the Connection Date
                 */
                @Override
                public long getLastConnectionDate() {
                    return 0;
                }

                /**
                 * The method <code>getConnectionState</code> gives us the connection state of the represented
                 * Asset Issuer
                 *
                 * @return the Connection state
                 */
                @Override
                public DAPConnectionState getDapConnectionState() {
                    return DAPConnectionState.REGISTERED_OFFLINE;
                }

                /**
                 * Método {@code getDescription}
                 * The Method return a description about Issuer
                 * acerca de él mismo.
                 *
                 * @return {@link String} con la descripción del {@link ActorAssetIssuer}
                 */
                @Override
                public String getDescription() {
                    return null;
                }

                /**
                 * The method <code>getLocation</code> gives us the Location of the represented
                 * Asset Issuer
                 *
                 * @return the Location
                 */
                @Override
                public Location getLocation() {
                    return null;
                }

                /**
                 * The method <code>getLocationLatitude</code> gives us the Location of the represented
                 * Asset Issuer
                 *
                 * @return the Location Latitude
                 */
                @Override
                public Double getLocationLatitude() {
                    return null;
                }

                /**
                 * The method <code>getLocationLongitude</code> gives us the Location of the represented
                 * Asset Issuer
                 *
                 * @return the Location Longitude
                 */
                @Override
                public Double getLocationLongitude() {
                    return null;
                }
            };
            ActorAssetUser actorAssetUser = actorAssetUserManager.getActorAssetUser(); //The user of this device, whom appropriate the asset.
            DAPMessage message = new DAPMessage(DAPMessageType.ASSET_APPROPRIATION, new AssetAppropriationContentMessage(assetAppropriated, actorAssetUser), actorAssetUser, actorAssetIssuer);
            assetIssuerActorNetworkServiceManager.sendMessage(message); //FROM: USER. TO:ISSUER.
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
