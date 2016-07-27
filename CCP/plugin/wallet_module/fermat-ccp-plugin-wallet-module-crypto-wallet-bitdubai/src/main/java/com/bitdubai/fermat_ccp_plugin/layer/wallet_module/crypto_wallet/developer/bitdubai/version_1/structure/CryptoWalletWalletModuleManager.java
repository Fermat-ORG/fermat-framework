package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.core.MethodDetail;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.modules.ModuleManagerImpl;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_api.layer.osa_android.location_system.LocationManager;
import com.bitdubai.fermat_api.layer.osa_android.location_system.exceptions.CantGetDeviceLocationException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetBlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_network.faucet.BitcoinFaucetManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.faucet.CantGetCoinsFromFaucetException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.util.BlockchainDownloadProgress;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.CantImportSeedException;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.enums.EventType;
import com.bitdubai.fermat_ccp_api.all_definition.enums.Frequency;
import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSetPhotoException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.IntraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetMnemonicTextException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletTransactionSummary;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.OutgoingExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.CantGetTransactionManagerException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.CantSendFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorCantSendFundsExceptions;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.exceptions.OutgoingIntraActorInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.transfer_intra_wallet_users.exceptions.CantSendTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.transfer_intra_wallet_users.exceptions.TransferIntraWalletUsersNotEnoughFundsException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.transfer_intra_wallet_users.interfaces.TransferIntraWalletUsersManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantCreateNewIntraWalletUserException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.exceptions.CantListIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactRegistryException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactsSearch;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantSendAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces.CryptoAddressesManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantApproveCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGenerateCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRegistryException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantRejectCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CryptoPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPayment;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentManager;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentRegistry;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantApproveRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantFindWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetActorTransactionHistoryException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllIntraUserConnectionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListPaymentRequestDateOrderException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListReceivePaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListSentPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantRefuseRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSaveTransactionDescriptionException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.PaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.RequestPaymentInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.TransactionNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.ActorTransactionSummary;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.structure.CryptoWalletWalletModuleWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSendLossProtectedCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedInsufficientFundsException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.event_handlers.BlockchainDownloadUpToDateEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantCreateOrRegisterActorException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantEnrichIntraUserException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantEnrichTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantGetActorException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantInitializeCryptoWalletManagerException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.exceptions.CantRequestOrRegisterCryptoAddressException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import org.apache.commons.collections.CollectionUtils;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager</code>
 * haves all methods for the contacts activity of a bitcoin wallet
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/06/15.
 * Modified and reviewed by nattyco & furszy.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
@PluginInfo(createdBy = "Leon Acosta", maintainerMail = "nattyco@gmail.com", platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.WALLET_MODULE, plugin = Plugins.CRYPTO_WALLET)
public class CryptoWalletWalletModuleManager extends ModuleManagerImpl<BitcoinWalletSettings> implements CryptoWallet,Serializable {


    private final CryptoWalletManager cryptoWalletManager;
    private final CryptoAddressBookManager       cryptoAddressBookManager      ;
    private final CryptoAddressesManager         cryptoAddressesNSManager      ;
    private final CryptoPaymentManager           cryptoPaymentManager          ;
    private final CryptoVaultManager cryptoVaultManager            ;

    private final ExtraUserManager               extraUserManager              ;
    private final IntraWalletUserActorManager    intraUserManager              ;
    private final IntraWalletUserIdentityManager intraWalletUserIdentityManager;
    private final OutgoingExtraUserManager       outgoingExtraUserManager      ;
    private final OutgoingIntraActorManager      outgoingIntraActorManager     ;
    private final WalletContactsManager          walletContactsManager         ;
//    private final UUID pluginId;
//    private final PluginFileSystem pluginFileSystem;
    private final EventManager eventManager;
    private final BlockchainManager<ECKey, Transaction> bitcoinNetworkManager                   ;
    private final Broadcaster                   broadcaster                     ;
    private final WalletManagerManager walletManagerManager;
    private final TransferIntraWalletUsersManager transferIntraWalletUsersManager;
    private final ErrorManager errorManager;
    private LocationManager locationManager;

    private final List<FermatEventListener> listenersAdded = new ArrayList<>();

    public CryptoWalletWalletModuleManager(final CryptoWalletManager cryptoWalletManager,
                                           final CryptoAddressBookManager cryptoAddressBookManager,
                                           final CryptoAddressesManager cryptoAddressesNSManager,
                                           final CryptoPaymentManager cryptoPaymentManager,
                                           final CryptoVaultManager cryptoVaultManager,
                                           final ErrorManager errorManager,
                                           final ExtraUserManager extraUserManager,
                                           final IntraWalletUserActorManager intraUserManager,
                                           final IntraWalletUserIdentityManager intraWalletUserIdentityManager,
                                           final OutgoingExtraUserManager outgoingExtraUserManager,
                                           final OutgoingIntraActorManager outgoingIntraActorManager,
                                           final WalletContactsManager walletContactsManager, UUID pluginId, PluginFileSystem pluginFileSystem,
                                           final EventManager eventManager, BlockchainManager<ECKey, Transaction> bitcoinNetworkManager, Broadcaster broadcaster,
                                           final WalletManagerManager walletManagerManager,
                                           final TransferIntraWalletUsersManager transferIntraWalletUsersManager,
                                           final LocationManager locationManager) {
        super(pluginFileSystem,pluginId);

        this.cryptoWalletManager = cryptoWalletManager;
        this.cryptoAddressBookManager       = cryptoAddressBookManager      ;
        this.cryptoAddressesNSManager       = cryptoAddressesNSManager      ;
        this.cryptoPaymentManager           = cryptoPaymentManager          ;
        this.cryptoVaultManager             = cryptoVaultManager            ;
        this.errorManager                   = errorManager                  ;
        this.extraUserManager               = extraUserManager              ;
        this.intraUserManager               = intraUserManager              ;
        this.intraWalletUserIdentityManager = intraWalletUserIdentityManager;
        this.outgoingExtraUserManager       = outgoingExtraUserManager      ;
        this.outgoingIntraActorManager      = outgoingIntraActorManager     ;
        this.walletContactsManager          = walletContactsManager         ;
//        this.pluginId = pluginId;
//        this.pluginFileSystem = pluginFileSystem;
        this.eventManager = eventManager;

        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.broadcaster = broadcaster;
        this.walletManagerManager = walletManagerManager;
        this.transferIntraWalletUsersManager = transferIntraWalletUsersManager;
        this.locationManager = locationManager;

        try {
            initialize();
        } catch (CantInitializeCryptoWalletManagerException e) {
            e.printStackTrace();
        }
    }

    private CryptoPaymentRegistry  cryptoPaymentRegistry ;
    private WalletContactsRegistry walletContactsRegistry;


    public final void initialize() throws CantInitializeCryptoWalletManagerException {

        try {

            cryptoPaymentRegistry  = cryptoPaymentManager .getCryptoPaymentRegistry();

            walletContactsRegistry = walletContactsManager.getWalletContactsRegistry();

            /**
             * Listener Crypto Network Blockchain Download Up To Date Event
             */

            FermatEventListener fermatEventListener;
            FermatEventHandler fermatEventHandler;

            fermatEventListener = eventManager.getNewListener(EventType.BLOCKCHAIN_DOWNLOAD_UP_TO_DATE);
            fermatEventHandler = new BlockchainDownloadUpToDateEventHandler(this.broadcaster);

            fermatEventListener.setEventHandler(fermatEventHandler);

            eventManager.addListener(fermatEventListener);
            listenersAdded.add(fermatEventListener);

        } catch (final CantGetWalletContactRegistryException e) {

            throw new CantInitializeCryptoWalletManagerException(e, "", "Error trying to get wallet manager registry.");
        } catch(final CantGetCryptoPaymentRegistryException e) {

            throw new CantInitializeCryptoWalletManagerException(e, "", "Error get crypto Payment Registry object");
        }  catch (final Exception e){

            throw new CantInitializeCryptoWalletManagerException(e, "", "Unhandled error.");
        }
    }

    @Override
    public List<CryptoWalletWalletContact> listWalletContacts(String walletPublicKey,String intraUserLoggedInPublicKey) throws CantGetAllWalletContactsException {
        try {


            List<CryptoWalletWalletContact> finalRecordList = new ArrayList<>();
            finalRecordList.clear();
            WalletContactsSearch walletContactsSearch = walletContactsRegistry.searchWalletContact(walletPublicKey);
            for(WalletContactRecord r : walletContactsSearch.getResult()){

                byte[] image = getImageByActorType(r.getActorType(), r.getActorPublicKey(),intraUserLoggedInPublicKey);

                finalRecordList.add(new CryptoWalletWalletModuleWalletContact(r, image));
            }
            return  finalRecordList;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e);
        }  catch (Exception e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<CryptoWalletWalletContact> listAllActorContactsAndConnections(String walletPublicKey,String intraUserLoggedInPublicKey) throws CantGetAllWalletContactsException {
        try {
            Map<String, CryptoWalletWalletContact> contactMap = new HashMap<>();

            //get wallet contacts
            WalletContactsSearch walletContactsSearch = walletContactsRegistry.searchWalletContact(walletPublicKey);


            for(WalletContactRecord r : walletContactsSearch.getResult()){
                // System.out.println("wallet contact: "+r);
                byte[] image = getImageByActorType(r.getActorType(), r.getActorPublicKey(),intraUserLoggedInPublicKey);
                contactMap.put(r.getActorPublicKey(), new CryptoWalletWalletModuleWalletContact(r, image));
            }

            // get intra user connections
            List<IntraWalletUserActor> intraUserList = intraUserManager.getConnectedIntraWalletUsers(intraUserLoggedInPublicKey);

            for(IntraWalletUserActor intraUser : intraUserList) {
                // System.out.println("intra user: " + intraUser);
                if (!contactMap.containsKey(intraUser.getPublicKey()))
                {
                    contactMap.put(intraUser.getPublicKey(), new CryptoWalletWalletModuleWalletContact( new CryptoWalletWalletModuleIntraUserActor(
                            intraUser.getName(),
                            false,
                            intraUser.getProfileImage(),
                            intraUser.getPublicKey()),
                            walletPublicKey));

                }

            }
            return new ArrayList<>(contactMap.values());


        } catch (CantGetAllWalletContactsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e);
        }  catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    private byte[] getImageByActorType(final Actors actorType     ,
                                       final String actorPublicKey, final String intraUserLoggedInPublicKey) throws CantGetAllWalletContactsException,
            ExtraUserNotFoundException       ,
            CantGetExtraUserException        {

        try {
            Actor actor;
            switch (actorType) {
                case EXTRA_USER:
                    actor = extraUserManager.getActorByPublicKey(actorPublicKey);
                    return actor.getPhoto();
                case INTRA_USER:
                    try {
                        actor = intraUserManager.getActorByPublicKey(intraUserLoggedInPublicKey, actorPublicKey);
                        return actor.getPhoto();

                    } catch (CantGetIntraUserException | IntraUserNotFoundException e) {
                        throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e);
                    }

                default:
                    throw new CantGetAllWalletContactsException("UNEXPECTED ACTOR TYPE", null, "", "incomplete switch");
            }
        } catch (Exception e) {
            return new byte[0];
        }
    }


    @Override
    public List<CryptoWalletWalletContact> listWalletContactsScrolling(String  walletPublicKey,
                                                                       String intraUserLoggedInPublicKey,
                                                                       Integer max,
                                                                       Integer offset) throws CantGetAllWalletContactsException {
        try {
            Actor actor;
            List<CryptoWalletWalletContact> finalRecordList = new ArrayList<>();
            finalRecordList.clear();
            WalletContactsSearch walletContactsSearch = walletContactsRegistry.searchWalletContact(walletPublicKey);
            for(WalletContactRecord r : walletContactsSearch.getResult(max, offset)){

                byte[] image = getImageByActorType(r.getActorType(), r.getActorPublicKey(), intraUserLoggedInPublicKey);

                finalRecordList.add(new CryptoWalletWalletModuleWalletContact(r, image));
            }
            return  finalRecordList;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, e);
        }
        catch (Exception e) {
            throw new CantGetAllWalletContactsException(CantGetAllWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND,timeout = 30,timeoutUnit = TimeUnit.SECONDS)
    public List<CryptoWalletIntraUserActor> listAllIntraUserConnections(String  intraUserIdentityPublicKey,
                                                                        String  walletPublicKey,
                                                                        Integer max,
                                                                        Integer offset) throws CantGetAllIntraUserConnectionsException {
        try {
            List<CryptoWalletIntraUserActor> intraUserActorList = new ArrayList<>();

            List<IntraWalletUserActor> intraUserList = intraUserManager.getAllIntraWalletUsers(intraUserIdentityPublicKey, max, offset);

            List<CryptoWalletWalletContact> lstContacts = listWalletContacts(walletPublicKey, intraUserIdentityPublicKey);
            for(final IntraWalletUserActor intraUser : intraUserList) {
                boolean isContact = CollectionUtils.exists(lstContacts,
                        new org.apache.commons.collections.Predicate() {
                            public boolean evaluate(Object object) {
                                CryptoWalletWalletContact cryptoWalletWalletContact = (CryptoWalletWalletContact) object;
                                return cryptoWalletWalletContact.getActorPublicKey().equals(intraUser.getPublicKey());

                            }
                        });
                if(!isContact)
                intraUserActorList.add(new CryptoWalletWalletModuleIntraUserActor(
                        intraUser.getName(),
                        isContact,
                        intraUser.getProfileImage(),
                        intraUser.getPublicKey()));
            }
            return intraUserActorList;
        } catch (CantGetIntraWalletUsersException e) {
            throw new CantGetAllIntraUserConnectionsException(CantGetAllIntraUserConnectionsException.DEFAULT_MESSAGE, e, "", "Problem trying yo get actors from Intra-User Actor plugin.");
        } catch (Exception e) {
            throw new CantGetAllIntraUserConnectionsException(CantGetAllIntraUserConnectionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }


    private CryptoWalletIntraUserActor enrichIntraUser(IntraWalletUserActor intraWalletUser,
                                                       String walletPublicKey) throws CantEnrichIntraUserException {
        try {
            walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(intraWalletUser.getPublicKey(), walletPublicKey);

            return new CryptoWalletWalletModuleIntraUserActor(
                    intraWalletUser.getName(),
                    true,
                    intraWalletUser.getProfileImage(),
                    intraWalletUser.getPublicKey()
            );
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
            return new CryptoWalletWalletModuleIntraUserActor(
                    intraWalletUser.getName(),
                    false,
                    intraWalletUser.getProfileImage(),
                    intraWalletUser.getPublicKey()
            );
        } catch (CantGetWalletContactException e) {
            throw new CantEnrichIntraUserException(CantEnrichIntraUserException.DEFAULT_MESSAGE, e, "", "There was a problem trying to enrich the intra user record.");
        }
    }

    @Override
    public CryptoWalletWalletContact convertConnectionToContact( String        actorAlias,
                                                                 Actors        actorConnectedType,
                                                                 String        actorConnectedPublicKey,
                                                                 byte[]        actorPhoto,
                                                                 Actors        actorWalletType ,
                                                                 String        identityWalletPublicKey,
                                                                 String        walletPublicKey,
                                                                 CryptoCurrency walletCryptoCurrency,
                                                                 BlockchainNetworkType blockchainNetworkType) throws CantCreateWalletContactException, ContactNameAlreadyExistsException{
        try{

            try {
                WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(actorConnectedPublicKey, walletPublicKey);

                //get to Crypto Address NS the intra user actor address
                cryptoAddressesNSManager.sendAddressExchangeRequest(walletPublicKey,
                        walletCryptoCurrency,
                        actorWalletType,
                        actorConnectedType,
                        identityWalletPublicKey,
                        actorConnectedPublicKey,
                        CryptoAddressDealers.CRYPTO_WALLET,
                        blockchainNetworkType);

                return new CryptoWalletWalletModuleWalletContact(walletContactRecord);

            } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {

                WalletContactRecord walletContactRecord = walletContactsRegistry.createWalletContact(
                        actorConnectedPublicKey,
                        actorAlias,
                        "",
                        "",
                        actorConnectedType,
                        walletPublicKey,
                        blockchainNetworkType
                );

                //get to Crypto Address NS the intra user actor address
                cryptoAddressesNSManager.sendAddressExchangeRequest(walletPublicKey,
                        walletCryptoCurrency,
                        actorWalletType,
                        actorConnectedType,
                        identityWalletPublicKey,
                        actorConnectedPublicKey,
                        CryptoAddressDealers.CRYPTO_WALLET,
                        blockchainNetworkType);




                return new CryptoWalletWalletModuleWalletContact(walletContactRecord, actorPhoto);
            }

        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creation a wallet contact.", null);
        } catch (Exception e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void sendAddressExchangeRequest(String        actorAlias,
                                           Actors        actorConnectedType,
                                           String        actorConnectedPublicKey,
                                           byte[]        actorPhoto,
                                           Actors        actorWalletType ,
                                           String        identityWalletPublicKey,
                                           String        walletPublicKey,
                                           CryptoCurrency walletCryptoCurrency,
                                           BlockchainNetworkType blockchainNetworkType){

        try {
            //get to Crypto Address NS the intra user actor address
            cryptoAddressesNSManager.sendAddressExchangeRequest(walletPublicKey,
                    walletCryptoCurrency,
                    actorWalletType,
                    actorConnectedType,
                    identityWalletPublicKey,
                    actorConnectedPublicKey,
                    CryptoAddressDealers.CRYPTO_WALLET,
                    blockchainNetworkType);

        } catch (CantSendAddressExchangeRequestException e) {
            e.printStackTrace();
        }
    }



    @Override
    public CryptoWalletWalletContact createWalletContactWithPhoto(CryptoAddress receivedCryptoAddress,
                                                                  String        actorAlias,
                                                                  String        actorFirstName,
                                                                  String        actorLastName,
                                                                  Actors        actorType,
                                                                  String        walletPublicKey,
                                                                  byte[]        photo,
                                                                  BlockchainNetworkType blockchainNetworkType) throws CantCreateWalletContactException, ContactNameAlreadyExistsException {
        try{
            try {
                walletContactsRegistry.getWalletContactByAliasAndWalletPublicKey(actorAlias, walletPublicKey);
                throw new ContactNameAlreadyExistsException(ContactNameAlreadyExistsException.DEFAULT_MESSAGE, null, null, null);

            } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                String actorPublicKey = createActor(actorAlias, actorType, photo);
                HashMap<BlockchainNetworkType,CryptoAddress>  cryptoAddresses = new HashMap<>();
                cryptoAddresses.put(blockchainNetworkType, receivedCryptoAddress);
                WalletContactRecord walletContactRecord = walletContactsRegistry.createWalletContact(
                        actorPublicKey,
                        actorAlias,
                        actorFirstName,
                        actorLastName,
                        actorType,
                        cryptoAddresses,
                        walletPublicKey
                );
                return new CryptoWalletWalletModuleWalletContact(walletContactRecord, photo);
            }

        } catch (ContactNameAlreadyExistsException e) {
            throw e;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e);
        } catch (CantCreateOrRegisterActorException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creating or registering actor.", null);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creation a wallet contact.", null);
        } catch (Exception e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public CryptoWalletWalletContact createWalletContact(CryptoAddress receivedCryptoAddress,
                                                         String        actorAlias,
                                                         String        actorFirstName,
                                                         String        actorLastName,
                                                         Actors        actorType,
                                                         String        walletPublicKey,
                                                         BlockchainNetworkType blockchainNetworkType) throws CantCreateWalletContactException, ContactNameAlreadyExistsException {
        try{
           /* try {
                walletContactsRegistry.getWalletContactByAliasAndWalletPublicKey(actorAlias, walletPublicKey);
                throw new ContactNameAlreadyExistsException(ContactNameAlreadyExistsException.DEFAULT_MESSAGE, null, null, null);

            } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
            */

                String actorPublicKey = createActor(actorAlias, actorType);
                HashMap<BlockchainNetworkType,CryptoAddress> cryptoAddresses = new HashMap<>();
                cryptoAddresses.put(blockchainNetworkType, receivedCryptoAddress);
                WalletContactRecord walletContactRecord = walletContactsRegistry.createWalletContact(
                        actorPublicKey,
                        actorAlias,
                        actorFirstName,
                        actorLastName,
                        actorType,
                        cryptoAddresses,
                        walletPublicKey
                );
                return new CryptoWalletWalletModuleWalletContact(walletContactRecord);
           // }

       // } catch (ContactNameAlreadyExistsException e) {
       //     throw e;
      //  } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
        //    throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e);
        } catch (CantCreateOrRegisterActorException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creating or registering actor.", null);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, e, "Error creation a wallet contact.", null);
        } catch (Exception e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void updateContactPhoto(String actorPublicKey, Actors actor, byte[] photo) throws CantUpdateWalletContactException {
        try {
            switch (actor) {
                case EXTRA_USER:
                    this.extraUserManager.setPhoto(actorPublicKey, photo);
                    break;
                case INTRA_USER:
                    this.intraUserManager.setPhoto(actorPublicKey, photo);
                    break;
                default:
                    throw new CantUpdateWalletContactException("Actor not expected", null, "The actor type is:" + actor.getCode(), "Incomplete switch");
            }
        } catch (ExtraUserNotFoundException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " i cannot find the actor ");
        } catch (CantSetPhotoException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " error trying to get the actor photo");
        }
        catch (com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantSetPhotoException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " error trying to get the actor photo");
        }
        catch (IntraUserNotFoundException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " i cannot find the actor");
        }
    }

    @Override
    public CryptoWalletWalletContact findWalletContactById(UUID contactId,String intraUserLoggedInPublicKey) throws CantFindWalletContactException, WalletContactNotFoundException {
        try {
            WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByContactId(contactId);


            byte[] image = getImageByActorType(walletContactRecord.getActorType(), walletContactRecord.getActorPublicKey(), intraUserLoggedInPublicKey);


            return new CryptoWalletWalletModuleWalletContact(walletContactRecord, image);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantFindWalletContactException(CantFindWalletContactException.DEFAULT_MESSAGE, e);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
            throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            throw new CantFindWalletContactException(CantFindWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public CryptoWalletWalletContact findWalletContactByName(String alias,String walletPublicKey,String intraUserLoggedInPublicKey) throws CantFindWalletContactException, WalletContactNotFoundException {
        try {
            WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByAliasAndWalletPublicKey(alias, walletPublicKey);


            byte[] image = getImageByActorType(walletContactRecord.getActorType(), walletContactRecord.getActorPublicKey(), intraUserLoggedInPublicKey);


            return new CryptoWalletWalletModuleWalletContact(walletContactRecord, image);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantFindWalletContactException(CantFindWalletContactException.DEFAULT_MESSAGE, e);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
            throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            throw new CantFindWalletContactException(CantFindWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public CryptoWalletWalletContact addIntraUserActorLikeContact(String intraUserPublicKey,
                                                                  String alias,
                                                                  String firstName,
                                                                  String lastName,
                                                                  String walletPublicKey) throws CantCreateWalletContactException {
        try {
            WalletContactRecord walletContactRecord = walletContactsRegistry.createWalletContact(
                    intraUserPublicKey,
                    alias,
                    firstName,
                    lastName,
                    Actors.INTRA_USER,
                    null,
                    walletPublicKey
            );

            return new CryptoWalletWalletModuleWalletContact(walletContactRecord);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateWalletContactException();
        } catch (Exception e) {
            throw new CantCreateWalletContactException(CantCreateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public CryptoAddress requestAddressToNewExtraUser(String deliveredByActorPublicKey,
                                                      Actors deliveredByActorType,
                                                      String deliveredToActorName,
                                                      Platforms platform,
                                                      VaultType vaultType,
                                                      String vaultIdentifier,
                                                      String walletPublicKey,
                                                      ReferenceWallet walletType) throws CantRequestCryptoAddressException {
        // TODO implement this method
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    @Override
    public CryptoAddress requestAddressToKnownUser(String deliveredByActorPublicKey,
                                                   Actors deliveredByActorType,
                                                   String deliveredToActorPublicKey,
                                                   Actors deliveredToActorType,
                                                   Platforms platform,
                                                   VaultType vaultType,
                                                   String vaultIdentifier,
                                                   String walletPublicKey,
                                                   ReferenceWallet walletType,
                                                   BlockchainNetworkType blockchainNetworkType) throws CantRequestCryptoAddressException {
        try {
            CryptoAddress deliveredCryptoAddress;
            deliveredCryptoAddress = requestCryptoAddressByReferenceWallet(walletType,blockchainNetworkType);
            cryptoAddressBookManager.registerCryptoAddress(
                    deliveredCryptoAddress,
                    deliveredByActorPublicKey,
                    deliveredByActorType,
                    deliveredToActorPublicKey,
                    deliveredToActorType,
                    platform,
                    vaultType,
                    vaultIdentifier,
                    walletPublicKey,
                    walletType
            );
            System.out.println("im a delivered address: " + deliveredCryptoAddress.getAddress());
            return deliveredCryptoAddress;
        } catch (CantRequestOrRegisterCryptoAddressException e) {
            throw new CantRequestCryptoAddressException(CantRequestCryptoAddressException.DEFAULT_MESSAGE, e);
        } catch (CantRegisterCryptoAddressBookRecordException e) {
            throw new CantRequestCryptoAddressException(CantRequestCryptoAddressException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
        } catch(Exception exception){
            throw new CantRequestCryptoAddressException(CantRequestCryptoAddressException.DEFAULT_MESSAGE, FermatException.wrapException(exception));
        }
    }

    @Override
    public void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName, BlockchainNetworkType blockchainNetworkType) throws CantUpdateWalletContactException {
        try {
            HashMap<BlockchainNetworkType,CryptoAddress> cryptoAddresses = new HashMap<>();
            cryptoAddresses.put(blockchainNetworkType, receivedCryptoAddress);
            walletContactsRegistry.updateWalletContact(
                    contactId,
                    actorName,
                    null,
                    null,
                    cryptoAddresses
            );
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantUpdateWalletContactException e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, e);
        }  catch (Exception e) {
            throw new CantUpdateWalletContactException(CantUpdateWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException {
        try {
            walletContactsRegistry.deleteWalletContact(contactId);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantDeleteWalletContactException e) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            throw new CantDeleteWalletContactException(CantDeleteWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public long getBalance(BalanceType balanceType,
                           String walletPublicKey,
                           BlockchainNetworkType blockchainNetworkType) throws CantGetBalanceException {
        try {
            CryptoWalletWallet cryptoWalletWallet = cryptoWalletManager.loadWallet(walletPublicKey);
            return cryptoWalletWallet.getBalance(balanceType).getBalance(blockchainNetworkType);
        } catch (CantLoadWalletsException e) {
            throw new CantGetBalanceException(CantGetBalanceException.DEFAULT_MESSAGE, e, "", "Cant Load Wallet.");
        }  catch (CantCalculateBalanceException e) {
            throw new CantGetBalanceException(CantGetBalanceException.DEFAULT_MESSAGE, e, "", "Cant Calculate Balance of the wallet.");
        } catch(Exception e){
            throw new CantGetBalanceException(CantGetBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction> getTransactions(String intraUserLoggedInPublicKey,
                                                         BalanceType balanceType, final TransactionType transactionType,
                                                         String walletPublicKey,
                                                         int max,
                                                         int offset) throws CantListTransactionsException {
        List<com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
        try {
            if(intraUserLoggedInPublicKey!=null) {
                CryptoWalletWallet cryptoWalletWallet = cryptoWalletManager.loadWallet(walletPublicKey);
                List<CryptoWalletTransaction> bitcoinWalletTransactionList = cryptoWalletWallet.listTransactions(balanceType, transactionType, max, offset);

                for (CryptoWalletTransaction bwt : bitcoinWalletTransactionList) {
                    cryptoWalletTransactionList.add(enrichTransaction(bwt, walletPublicKey, intraUserLoggedInPublicKey));
                }
            }
            return cryptoWalletTransactionList;
        } catch(Exception e){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction> listTransactionsByActor(BalanceType balanceType,
                                                                 String walletPublicKey,
                                                                 String actorPublicKey,
                                                                 String intraUserLoggedInPublicKey,
                                                                 int max,
                                                                 int offset) throws CantListTransactionsException {
        try {
            CryptoWalletWallet cryptoWalletWallet = cryptoWalletManager.loadWallet(walletPublicKey);
            List<com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
            List<CryptoWalletTransaction> bitcoinWalletTransactionList = cryptoWalletWallet.listTransactionsByActor(actorPublicKey, balanceType, max, offset);

            for (CryptoWalletTransaction bwt : bitcoinWalletTransactionList) {
                cryptoWalletTransactionList.add(enrichTransaction(bwt,walletPublicKey,intraUserLoggedInPublicKey));
            }

            return cryptoWalletTransactionList;
        } catch (CantLoadWalletsException | com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException e) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    public void testNetGiveMeCoins(BlockchainNetworkType blockchainNetworkType, CryptoAddress cryptoAddress) throws CantGetCoinsFromFaucetException{

            BitcoinFaucetManager.giveMeCoins(blockchainNetworkType,cryptoAddress , 100000000);

    }



    @Override
    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND,timeout = 40,timeoutUnit = TimeUnit.SECONDS,methodParallelQuantity = 1)
    public List<com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction> listTransactionsByActorAndType(BalanceType balanceType,
                                                                        TransactionType transactionType,
                                                                        String walletPublicKey,
                                                                        String actorPublicKey, String intraUserLoggedInPublicKey,
                                                                        BlockchainNetworkType blockchainNetworkType,
                                                                        int max,
                                                                        int offset) throws CantListTransactionsException {
        try {
            CryptoWalletWallet cryptoWalletWallet = cryptoWalletManager.loadWallet(walletPublicKey);
            List<com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
            List<CryptoWalletTransaction> bitcoinWalletTransactionList = cryptoWalletWallet.listTransactionsByActorAndType(actorPublicKey, balanceType, transactionType, max, offset,blockchainNetworkType);


                List<CryptoWalletTransaction> cryptoWalletTransactionList1 = new ArrayList<>();

                for (CryptoWalletTransaction bwt : bitcoinWalletTransactionList) {

                    if (bwt.getBlockchainNetworkType().getCode().equals(blockchainNetworkType.getCode())){
                        cryptoWalletTransactionList1.add(bwt);
                    }
                }






            for (CryptoWalletTransaction bwt : cryptoWalletTransactionList1) {
                cryptoWalletTransactionList.add(enrichTransaction(bwt,walletPublicKey,intraUserLoggedInPublicKey));
            }

            return cryptoWalletTransactionList;
        } catch (CantLoadWalletsException | com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException e) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public ActorTransactionSummary getActorTransactionHistory(BalanceType balanceType,
                                                              String walletPublicKey,
                                                              String actorPublicKey) throws CantGetActorTransactionHistoryException {
        try {
            CryptoWalletWallet cryptoWalletWallet = cryptoWalletManager.loadWallet(walletPublicKey);
            return constructActorTransactionSummary(cryptoWalletWallet.getActorTransactionSummary(actorPublicKey, balanceType));
        } catch (CantLoadWalletsException | CantGetActorTransactionSummaryException e) {
            throw new CantGetActorTransactionHistoryException(CantGetActorTransactionHistoryException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantGetActorTransactionHistoryException(CantGetActorTransactionHistoryException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }




    @Override
    @MethodDetail(looType = MethodDetail.LoopType.BACKGROUND,timeout = 40,timeoutUnit = TimeUnit.SECONDS,methodParallelQuantity = 1)
    public List<com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType balanceType,
                                                                                    final TransactionType transactionType,
                                                                                    String walletPublicKey,
                                                                                    String intraUserLoggedInPublicKey,
                                                                                    BlockchainNetworkType blockchainNetworkType,
                                                                                    int max,
                                                                                    int offset) throws CantListTransactionsException {


        List<com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
        try {
            if(intraUserLoggedInPublicKey!=null){
                CryptoWalletWallet cryptoWalletWallet = cryptoWalletManager.loadWallet(walletPublicKey);
                List<CryptoWalletTransaction> bitcoinWalletTransactionList = cryptoWalletWallet.listLastActorTransactionsByTransactionType(
                        balanceType,
                        transactionType,
                        max,
                        offset,
                        blockchainNetworkType
                );

                //List<CryptoWalletTransaction> cryptoWalletTransactionList1 = new ArrayList<>();

                for (CryptoWalletTransaction bwt : bitcoinWalletTransactionList) {
                        if (cryptoWalletTransactionList.isEmpty()){
                           // cryptoWalletTransactionList1.add(bwt);
                            cryptoWalletTransactionList.add(enrichTransaction(bwt, walletPublicKey, intraUserLoggedInPublicKey));
                        }else {
                            int count = 0;
                            for (com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction bwt1 : cryptoWalletTransactionList) {
                                if (bwt1.getActorToPublicKey().equals(bwt.getActorToPublicKey())) {
                                    count++;
                                }
                            }
                            if (count == 0)
                                cryptoWalletTransactionList.add(enrichTransaction(bwt, walletPublicKey, intraUserLoggedInPublicKey));
                               // cryptoWalletTransactionList1.add(bwt);

                        }
                }


               /* for (CryptoWalletTransaction bwt : cryptoWalletTransactionList1) {
                    cryptoWalletTransactionList.add(enrichTransaction(bwt, walletPublicKey, intraUserLoggedInPublicKey));
                }*/
            }
            return cryptoWalletTransactionList;
        } catch(Exception e){
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction getTransaction(UUID transactionId, String walletPublicKey,String intraUserLoggedInPublicKey) throws CantListTransactionsException
    {

        try {
            com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction cryptoWalletTransaction;
            CryptoWalletWallet cryptoWalletWallet;
            cryptoWalletWallet = cryptoWalletManager.loadWallet(walletPublicKey);

            CryptoWalletTransaction bwt = cryptoWalletWallet.getTransactionById(transactionId);
            cryptoWalletTransaction =enrichTransaction(bwt, walletPublicKey, intraUserLoggedInPublicKey);

            return  cryptoWalletTransaction;

        } catch (CantLoadWalletsException e) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, e);
        } catch (CantFindTransactionException e) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, e);
        } catch (CantEnrichTransactionException e) {
            throw new CantListTransactionsException(CantListTransactionsException.DEFAULT_MESSAGE, e);
        }

    }

    @Override
    public void setTransactionDescription(String walletPublicKey,
                                          UUID   transactionID,
                                          String description) throws CantSaveTransactionDescriptionException, TransactionNotFoundException {

        try {
            CryptoWalletWallet cryptoWalletWallet = cryptoWalletManager.loadWallet(walletPublicKey);
            cryptoWalletWallet.setTransactionDescription(transactionID, description);
        } catch (CantLoadWalletsException | CantStoreMemoException e) {
            throw new CantSaveTransactionDescriptionException(CantSaveTransactionDescriptionException.DEFAULT_MESSAGE, e);
        } catch (CantFindTransactionException e) {
            throw new TransactionNotFoundException(TransactionNotFoundException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantSaveTransactionDescriptionException(CantSaveTransactionDescriptionException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    private ActorTransactionSummary constructActorTransactionSummary(CryptoWalletTransactionSummary transactionSummary) {
        return new CryptoWalletWalletModuleActorTransactionSummary(
                transactionSummary.getSentTransactionsNumber(),
                transactionSummary.getReceivedTransactionsNumber(),
                transactionSummary.getSentAmount(),
                transactionSummary.getReceivedAmount()
        );
    }

    @Override
    public void send(long cryptoAmount, CryptoAddress destinationAddress, String notes, String walletPublicKey, String deliveredByActorPublicKey, Actors deliveredByActorType, String deliveredToActorPublicKey, Actors deliveredToActorType,ReferenceWallet referenceWallet,
                     BlockchainNetworkType blockchainNetworkType,
                     CryptoCurrency cryptoCurrency,
                     long fee,
                     FeeOrigin feeOrigin) throws CantSendCryptoException, InsufficientFundsException {
        try {

            switch (deliveredToActorType) {
                case EXTRA_USER:
                    System.out.println("Sending throw outgoing Extra User ...");
                    outgoingExtraUserManager.getTransactionManager().send(walletPublicKey, destinationAddress, cryptoAmount, notes, deliveredByActorPublicKey, deliveredByActorType, deliveredToActorPublicKey, deliveredToActorType, referenceWallet,blockchainNetworkType, cryptoCurrency,fee,feeOrigin);

                    break;
                case INTRA_USER:
                    System.out.println("Sending throw outgoing Intra Actor ...");
                    outgoingIntraActorManager.getTransactionManager().sendCrypto(walletPublicKey, destinationAddress, cryptoAmount, notes, deliveredByActorPublicKey,  deliveredToActorPublicKey,deliveredByActorType, deliveredToActorType,referenceWallet,blockchainNetworkType,cryptoCurrency,fee,feeOrigin);
                    break;
            }

        }
        catch (com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.InsufficientFundsException e) {
            throw new InsufficientFundsException(InsufficientFundsException.DEFAULT_MESSAGE, e);
        }
        catch(OutgoingIntraActorCantSendFundsExceptions| OutgoingIntraActorInsufficientFundsException ex){
            throw new CantSendCryptoException(CantSendCryptoException.DEFAULT_MESSAGE, ex);

        }
        catch (CantSendFundsException | CantGetTransactionManagerException e) {
            throw new CantSendCryptoException(CantSendCryptoException.DEFAULT_MESSAGE, e);
        }
        catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantSendCryptoException(CantSendCryptoException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<PaymentRequest> listSentPaymentRequest(String walletPublicKey,BlockchainNetworkType blockchainNetworkType,int max,int offset) throws CantListSentPaymentRequestException {
        try {
            List<PaymentRequest> lst =  new ArrayList<>();
            CryptoWalletWalletModuleWalletContact cryptoWalletWalletContact = null;
            byte[] profilePicture = null;

            //find received payment request
            for (CryptoPayment paymentRecord :  cryptoPaymentRegistry.listCryptoPaymentRequestsByType(walletPublicKey, CryptoPaymentType.SENT,blockchainNetworkType, max, offset)) {

                WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(paymentRecord.getActorPublicKey(),walletPublicKey);

                if (getImageByActorType(paymentRecord.getActorType(),paymentRecord.getActorPublicKey(),paymentRecord.getIdentityPublicKey()) != null)
                    profilePicture = getImageByActorType(paymentRecord.getActorType(),paymentRecord.getActorPublicKey(),paymentRecord.getIdentityPublicKey());

                if (walletContactRecord != null)
                    cryptoWalletWalletContact = new CryptoWalletWalletModuleWalletContact(walletContactRecord, profilePicture);



                CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest(
                        paymentRecord.getRequestId(),
                        convertTime(paymentRecord.getStartTimeStamp()),
                        paymentRecord.getDescription(),
                        paymentRecord.getAmount(),
                        cryptoWalletWalletContact,
                        PaymentRequest.SEND_PAYMENT,
                        paymentRecord.getState());
                lst.add(cryptoWalletPaymentRequest);
            }

            return lst;
        } catch (Exception e) {
            throw new CantListSentPaymentRequestException(CantListSentPaymentRequestException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }

    }

    @Override
    public PaymentRequest getPaymentRequest(UUID requestId) throws CantListReceivePaymentRequestException {

        try {

            CryptoPayment paymentRecord = cryptoPaymentRegistry.getRequestById(requestId);

           return  new CryptoWalletWalletModulePaymentRequest(
                    paymentRecord.getRequestId(),
                    convertTime(paymentRecord.getStartTimeStamp()),
                    paymentRecord.getDescription(),
                    paymentRecord.getAmount(),
                    null,
                    PaymentRequest.RECEIVE_PAYMENT,
                    paymentRecord.getState());

        } catch (Exception e) {
            throw new CantListReceivePaymentRequestException(CantListSentPaymentRequestException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }


    }

    @Override
    public List<PaymentRequest> listReceivedPaymentRequest(String walletPublicKey,BlockchainNetworkType blockchainNetworkType,int max,int offset) throws CantListReceivePaymentRequestException {

        try {
            List<PaymentRequest> lst =  new ArrayList<>();

            CryptoWalletWalletModuleWalletContact cryptoWalletWalletContact = null;
            byte[] profilePicture = null;

            //find received payment request
            for (CryptoPayment paymentRecord :  cryptoPaymentRegistry.listCryptoPaymentRequestsByTypeAndNetwork(
                    walletPublicKey,
                    CryptoPaymentType.RECEIVED,
                    blockchainNetworkType,
                    max,
                    offset
            )) {

                try
                {
                    WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(
                            paymentRecord.getActorPublicKey(),
                            walletPublicKey
                    );

                    if (getImageByActorType(paymentRecord.getActorType(),paymentRecord.getActorPublicKey(),paymentRecord.getIdentityPublicKey()) != null)
                        profilePicture = getImageByActorType(paymentRecord.getActorType(),paymentRecord.getActorPublicKey(),paymentRecord.getIdentityPublicKey());

                    if (walletContactRecord != null)
                        cryptoWalletWalletContact = new CryptoWalletWalletModuleWalletContact(walletContactRecord,profilePicture);

                }
                catch(com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e)
                {

                }
                CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest(
                        paymentRecord.getRequestId(),
                        convertTime(paymentRecord.getStartTimeStamp()),
                        paymentRecord.getDescription(),
                        paymentRecord.getAmount(),
                        cryptoWalletWalletContact,
                        PaymentRequest.RECEIVE_PAYMENT,
                        paymentRecord.getState()
                );

                lst.add(cryptoWalletPaymentRequest);
            }


            return lst;
        } catch (Exception e) {
            throw new CantListReceivePaymentRequestException(CantListSentPaymentRequestException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }


    }

    @Override
    public BlockchainDownloadProgress getBlockchainDownloadProgress(BlockchainNetworkType blockchainNetworkType) throws CantGetBlockchainDownloadProgress {
        try {
            return this.bitcoinNetworkManager.getBlockchainDownloadProgress(blockchainNetworkType);

        }
        catch (CantGetBlockchainDownloadProgress e) {
            throw new CantGetBlockchainDownloadProgress("ERROR GET Blockchain Download Progress", e,"","Crypto Network error");
         }
        catch (Exception e) {
            throw new CantGetBlockchainDownloadProgress("ERROR GET Blockchain Download Progress", FermatException.wrapException(e),"","Unknow error");
        }
  }


    /**
     * Throw the method <code>refuseRequest</code> you can refuse a request.
     *
     * @param requestId
     * @throws CantRejectCryptoPaymentRequestException
     * @throws CryptoPaymentRequestNotFoundException
     */
    public void refuseRequest(UUID requestId) throws CantRefuseRequestPaymentException,PaymentRequestNotFoundException
    {
        try {
            cryptoPaymentRegistry.refuseRequest(requestId);
        }
        catch(CantRejectCryptoPaymentRequestException e)
        {
            throw new CantRefuseRequestPaymentException(CantRefuseRequestPaymentException.DEFAULT_MESSAGE,e);
        }
        catch(CryptoPaymentRequestNotFoundException e)
        {
            throw new PaymentRequestNotFoundException(PaymentRequestNotFoundException.DEFAULT_MESSAGE,e);
        }
        catch(Exception e)
        {
            throw new CantRefuseRequestPaymentException(CantRefuseRequestPaymentException.DEFAULT_MESSAGE,FermatException.wrapException(e));
        }
    }


    /**
     * Throw the method <code>approveRequest</code> you can approve a request and send the specified crypto.
     * @param requestId
     * @throws CantApproveCryptoPaymentRequestException
     * @throws CryptoPaymentRequestNotFoundException
     * @throws com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFundsException
     */

    public void approveRequest(UUID requestId, String intraUserLoggedInPublicKey, long fee, FeeOrigin feeOrigin) throws CantApproveRequestPaymentException,
                                                     PaymentRequestNotFoundException,
                                                     RequestPaymentInsufficientFundsException
    {
        try {

            //check if actor to send me a request if a wallet contact
            //if not I add it as a wallet contact
            CryptoPayment paymentRequest =  cryptoPaymentRegistry.getRequestById(requestId);

            try {
                intraUserManager.acceptIntraWalletUser(paymentRequest.getIdentityPublicKey(),paymentRequest.getActorPublicKey());

                walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(paymentRequest.getActorPublicKey(), paymentRequest.getWalletPublicKey());

            } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                try {
                    Actor actor = getActorByActorPublicKeyAndType(paymentRequest.getActorPublicKey(),paymentRequest.getActorType(), intraUserLoggedInPublicKey);
                    this.convertConnectionToContact(
                            actor.getName(),
                            Actors.INTRA_USER,
                            paymentRequest.getActorPublicKey(),
                            actor.getPhoto(),
                            Actors.INTRA_USER,
                            intraUserLoggedInPublicKey,
                            paymentRequest.getWalletPublicKey() ,
                            CryptoCurrency.BITCOIN,
                            paymentRequest.getNetworkType());
                }
                catch (Exception e1)
                {

                }
            }

            cryptoPaymentRegistry.approveRequest(requestId, fee,  feeOrigin);


        }
        catch(CantApproveCryptoPaymentRequestException e)
        {
            throw new CantApproveRequestPaymentException(CantApproveRequestPaymentException.DEFAULT_MESSAGE,e);
        }
        catch(CryptoPaymentRequestNotFoundException e)
        {
            throw new PaymentRequestNotFoundException(PaymentRequestNotFoundException.DEFAULT_MESSAGE,e);
        }
        catch(com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFundsException e)
        {
            throw new RequestPaymentInsufficientFundsException(RequestPaymentInsufficientFundsException.DEFAULT_MESSAGE,e);
        }
        catch(Exception e)
        {
            throw new CantApproveRequestPaymentException(CantApproveRequestPaymentException.DEFAULT_MESSAGE,FermatException.wrapException(e));
        }
    }




    @Override
    public List<PaymentRequest> listPaymentRequestDateOrder(String walletPublicKey,int max,int offset) throws CantListPaymentRequestDateOrderException {
        try {
            //Request order by date desc

            List<PaymentRequest> lst =  new ArrayList<>();

            CryptoWalletWalletContact cryptoWalletWalletContact = null;

//            for (CryptoPayment paymentRecord :  cryptoPaymentRegistry.listCryptoPaymentRequests(walletPublicKey, max,offset)) {
//
//                WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(paymentRecord.getActorPublicKey(),walletPublicKey);
//                if (walletContactRecord != null)
//                    cryptoWalletWalletContact = new CryptoWalletWalletModuleWalletContact(walletContactRecord);
//
//                CryptoWalletWalletModulePaymentRequest cryptoWalletPaymentRequest = new CryptoWalletWalletModulePaymentRequest(convertTime(paymentRecord.getStartTimeStamp()),paymentRecord.getDescription(),paymentRecord.getAmount(),cryptoWalletWalletContact,PaymentRequest.SEND_PAYMENT,paymentRecord.getState().name());
//                lst.add(cryptoWalletPaymentRequest);
//            }



            return lst;
        } catch (Exception e) {
            throw new CantListPaymentRequestDateOrderException(CantListSentPaymentRequestException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public boolean isValidAddress(CryptoAddress cryptoAddress, BlockchainNetworkType blockchainNetworkType) {
        //todo natalia corregir
        return cryptoVaultManager.isValidAddress(cryptoAddress, blockchainNetworkType);
    }

    @Override
    public ArrayList<CryptoWalletIntraUserIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListCryptoWalletIntraUserIdentityException {

        try {

            ArrayList<CryptoWalletIntraUserIdentity> cryptoWalletIntraUserIdentityList = new ArrayList<CryptoWalletIntraUserIdentity>();

            for (IntraWalletUserIdentity intraWalletUser : this.intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser()) {

                CryptoWalletIntraUserIdentity cryptoWalletIntraUserIdentity = new CryptoWalletWalletIntraUserIdentity(intraWalletUser.getPublicKey(), intraWalletUser.getAlias(), intraWalletUser.getImage());

                cryptoWalletIntraUserIdentityList.add(cryptoWalletIntraUserIdentity);
            }

            return cryptoWalletIntraUserIdentityList;

        } catch (CantListIntraWalletUsersException e) {
            throw new CantListCryptoWalletIntraUserIdentityException(CantListIntraWalletUsersException.DEFAULT_MESSAGE, e, "", "");
        } catch (Exception e) {
            throw new CantListCryptoWalletIntraUserIdentityException(CantListIntraWalletUsersException.DEFAULT_MESSAGE, e, "", "unknown error");
        }

    }

    @Override
    public ArrayList<IntraWalletUserIdentity> getActiveIdentities() {

        try{

            ArrayList<IntraWalletUserIdentity> lst = intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();
            //return intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();
            return lst;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private String createActor(String actorName, Actors actorType, byte[] photo) throws CantCreateOrRegisterActorException {
        switch (actorType){
            case EXTRA_USER:
                try {
                    Actor actor = extraUserManager.createActor(actorName, photo);
                    return actor.getActorPublicKey();
                } catch (CantCreateExtraUserException e) {
                    throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
                }
            case INTRA_USER:
                // THERE'S NO NEED TO CREATE A NEW INTRA ACTOR. PLEASE DONT ADD ANYTHING HERE

            default:
                throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, null, "", "ActorType is not Compatible.");
        }
    }


    private String createActor(String actorName, Actors actorType) throws CantCreateOrRegisterActorException {
        switch (actorType){
            case EXTRA_USER:
                try {
                    Actor actor = extraUserManager.createActor(actorName);
                    return actor.getActorPublicKey();
                } catch (CantCreateExtraUserException e) {
                    throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
                }
            default:
                throw new CantCreateOrRegisterActorException(CantCreateOrRegisterActorException.DEFAULT_MESSAGE, null, "", "ActorType is not Compatible.");
        }
    }

    private CryptoAddress requestCryptoAddressByReferenceWallet(ReferenceWallet referenceWallet,BlockchainNetworkType blockchainNetworkType) throws CantRequestOrRegisterCryptoAddressException {
        switch (referenceWallet){
            case BASIC_WALLET_BITCOIN_WALLET:
                return cryptoVaultManager.getAddress(blockchainNetworkType);
            default:
                throw new CantRequestOrRegisterCryptoAddressException(CantRequestOrRegisterCryptoAddressException.DEFAULT_MESSAGE, null, "", "ReferenceWallet is not Compatible.");
        }
    }

    private com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction enrichTransaction(CryptoWalletTransaction cryptoWalletTransaction, String walletPublicKey, String intraUserLoggedInPublicKey) throws CantEnrichTransactionException {
        try {
            Actor involvedActor = null;
            UUID contactId = null;
            WalletContactRecord walletContactRecord = null;
            switch (cryptoWalletTransaction.getTransactionType()) {
                case CREDIT:
                    try {

                        if(cryptoWalletTransaction.getActorFromType() == Actors.INTRA_USER){
                            involvedActor = getActorByActorPublicKeyAndType(cryptoWalletTransaction.getActorToPublicKey(), cryptoWalletTransaction.getActorToType(), intraUserLoggedInPublicKey);
                            walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(cryptoWalletTransaction.getActorFromPublicKey(), walletPublicKey);
                            if(involvedActor==null)
                            {
                                involvedActor = getActorByActorPublicKeyAndType(cryptoWalletTransaction.getActorFromPublicKey(), cryptoWalletTransaction.getActorFromType(), intraUserLoggedInPublicKey);
                                walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(cryptoWalletTransaction.getActorToPublicKey(), walletPublicKey);

                            }
                        }else {
                            involvedActor = null;
                            walletContactRecord = null;
                        }
                        if (walletContactRecord != null)
                            contactId = walletContactRecord.getContactId();
                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
                        throw new CantEnrichTransactionException(CantEnrichTransactionException.DEFAULT_MESSAGE, e, "Cant get Contact Information", "");
                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                        contactId = null;
                    } catch ( CantGetActorException e) {
                        try{
                            involvedActor = getActorByActorPublicKeyAndType(cryptoWalletTransaction.getActorFromPublicKey(), cryptoWalletTransaction.getActorToType(), intraUserLoggedInPublicKey);
                            walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(cryptoWalletTransaction.getActorToPublicKey(), walletPublicKey);
                            if (walletContactRecord != null)
                                contactId = walletContactRecord.getContactId();
                        }catch (CantGetActorException exe){
                            contactId = null;
                        }catch (Exception ex){
                            contactId = null;
                        }
                    }
                    break;
                case DEBIT:
                    try {

                        if (cryptoWalletTransaction.getActorFromType() == Actors.INTRA_USER) {
                            involvedActor = getActorByActorPublicKeyAndType(cryptoWalletTransaction.getActorToPublicKey(), cryptoWalletTransaction.getActorToType(), intraUserLoggedInPublicKey);
                            walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(cryptoWalletTransaction.getActorToPublicKey(), walletPublicKey);
                            if (involvedActor == null) {
                                involvedActor = getActorByActorPublicKeyAndType(cryptoWalletTransaction.getActorFromPublicKey(), cryptoWalletTransaction.getActorFromType(), intraUserLoggedInPublicKey);
                                walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(cryptoWalletTransaction.getActorFromPublicKey(), walletPublicKey);

                            }
                        }else {
                            involvedActor = null;
                            walletContactRecord = null;
                        }
                        if (walletContactRecord != null)
                            contactId = walletContactRecord.getContactId();
                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
                        throw new CantEnrichTransactionException(CantEnrichTransactionException.DEFAULT_MESSAGE, e, "Cant get Contact Information", "");
                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                        contactId = null;
                    }
                    break;
            }
            return new CryptoWalletWalletModuleTransaction(cryptoWalletTransaction, contactId, involvedActor);
        } catch (Exception e) {
            throw new CantEnrichTransactionException(CantEnrichTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }


    private Actor getActorByActorPublicKeyAndType(String actorPublicKey, Actors actorType, String intraUserLoggedInPublicKey) throws CantGetActorException {
        Actor actor;
        switch (actorType) {
            case EXTRA_USER:
                try {
                    actor = extraUserManager.getActorByPublicKey(actorPublicKey);
                    return actor;
                } catch (CantGetExtraUserException | ExtraUserNotFoundException e) {
                    throw new CantGetActorException(CantGetActorException.DEFAULT_MESSAGE, e, null, "Cant get Extra User on DataBase");
                }
            case INTRA_USER:
                try {
                    //find actor connected with logget identity
                    actor = intraUserManager.getActorByPublicKey(intraUserLoggedInPublicKey,actorPublicKey);
                    return actor;
                } catch (CantGetIntraUserException| IntraUserNotFoundException e) {
                    throw new CantGetActorException(CantGetActorException.DEFAULT_MESSAGE, e, null, "Cant get Intra User on DataBase");
                }

            default:
                throw new CantGetActorException(CantGetActorException.DEFAULT_MESSAGE, null, null, null);
        }
    }

    @Override
    public final void sendCryptoPaymentRequest(final String                walletPublicKey  ,
                                               final String                identityPublicKey,
                                               final Actors                identityType     ,
                                               final String                actorPublicKey   ,
                                               final Actors                actorType        ,
                                               final CryptoAddress         cryptoAddress    ,
                                               final String                description      ,
                                               final long                  amount           ,
                                               final BlockchainNetworkType networkType      ,
                                               final ReferenceWallet       referenceWallet,
                                               final CryptoCurrency         cryptoCurrency) throws CantSendCryptoPaymentRequestException {

        try {

            cryptoPaymentRegistry.generateCryptoPaymentRequest(
                    walletPublicKey,
                    identityPublicKey,
                    identityType,
                    actorPublicKey,
                    actorType,
                    cryptoAddress,
                    description,
                    amount,
                    networkType,
                    referenceWallet,
                    cryptoCurrency
            );
        } catch (CantGenerateCryptoPaymentRequestException e) {

            throw new CantSendCryptoPaymentRequestException(e, "", "Error found in crypto payment request plugin.");
        } catch (Exception e) {

            throw new CantSendCryptoPaymentRequestException(e, "", "Unhandled error.");
        }

    }

    @Override
    public void createIntraUser(String name, String phrase, byte[] image) throws CantCreateNewIntraWalletUserException {
        try {
           intraWalletUserIdentityManager.createNewIntraWalletUser(name, phrase, image, Long.parseLong("100"), Frequency.NORMAL, getLocationManager());
      } catch (CantGetDeviceLocationException e) {
            throw new CantCreateNewIntraWalletUserException("Cant Create intra user wallet",e, "", "CantCreateNewIntraWalletUserException");
       }
    }


    @Override
    public void registerIdentities(){
        intraWalletUserIdentityManager.registerIdentities();
    }

    @Override
    public List<String> getMnemonicText() throws CantGetMnemonicTextException {
        //todo this needs to be improved.
        try {
            List<String> textToShow = new ArrayList<>();
            textToShow.add("mNemonic code: " + cryptoVaultManager.exportCryptoVaultSeed().getMnemonicPhrase());
            textToShow.add("Date: " + cryptoVaultManager.exportCryptoVaultSeed().getCreationTimeSeconds());

            return textToShow;
        } catch (Exception e) {
            throw new CantGetMnemonicTextException("CANT GET WALLET Mnemonic TEXT",e, "", "Crypto vault error.");
        }
    }

    private  String convertTime(long time){
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.US);

        return sdf.format(date);
    }


    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        try {
            List<IntraWalletUserIdentity> lst = getActiveIdentities();
            if(lst.isEmpty()){
                return null;
            }else{
                return lst.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<InstalledWallet> getInstalledWallets() throws CantListWalletsException {
        return walletManagerManager.getInstalledWallets();
    }


    @Override
    public void sendToWallet(long cryptoAmount, String sendingWalletPublicKey,String receivingWalletPublicKey, String notes,  Actors actortypeFrom, Actors actortypeTo, ReferenceWallet sendingWallet, ReferenceWallet receivingWallet, BlockchainNetworkType blockchainNetworkType,CryptoCurrency cryptoCurrency) throws CantSendLossProtectedCryptoException, LossProtectedInsufficientFundsException {

        try {
            transferIntraWalletUsersManager.getOutgoingDeviceUser().sendToWallet("", cryptoAmount, notes, actortypeFrom, actortypeTo, sendingWallet, receivingWallet, sendingWalletPublicKey, receivingWalletPublicKey, blockchainNetworkType, cryptoCurrency);
        } catch (CantSendTransactionException e) {
            throw new CantSendLossProtectedCryptoException("CAN'T SEND CRYPTO TO WALLET EXCEPTION", e);

        } catch (TransferIntraWalletUsersNotEnoughFundsException e) {
            throw new LossProtectedInsufficientFundsException("TRANSFER TO WALLET Insufficient Funds", e,"","");

        }


    }

    @Override
    public void importMnemonicCode(List<String> mnemonicCode, long date, BlockchainNetworkType defaultBlockchainNetworkType) throws CantImportSeedException {
//       cryptoVaultManager.importSeedFromMnemonicCode(mnemonicCode,date);
//        /**
//         * Importing a new seed is a complex process. A new Key Hierarchy is created, new keys are derived on this hierarchy,
//         * the keys generate public Keys and addresses that are passed to the CryptoNetwork for monitoring.
//         * Once that is completed, we need to register those generated addresses into the addressBook so that any generated transaction
//         * is redirected to the appropiate wallet.
//         */
//        String extraUserPK;
//        try {
//            // I create a fake actor to identify these transactions
//            extraUserPK = extraUserManager.createActor("Imported seed").getActorPublicKey();
//        } catch (CantCreateExtraUserException e) {
//            extraUserPK = UUID.randomUUID().toString();
//        }
//
//        List<CryptoAddress> cryptoAddressList = null;
//        boolean running = true;
//        while (running){
//            // I get the list of imported address from the cryptoNetwork. This process is async, so I don't know when
//            // they will be imported. I need to try until I get it.
//            cryptoAddressList = bitcoinNetworkManager.getImportedAddresses(defaultBlockchainNetworkType);
//
//            if (!cryptoAddressList.isEmpty())
//                running = false;
//        }
//
//        // I add the imported address into the address book.
//        for (CryptoAddress cryptoAddress : cryptoAddressList ){
//            try {
//                cryptoAddressBookManager.registerCryptoAddress(cryptoAddress, extraUserPK, Actors.EXTRA_USER, extraUserPK, Actors.DAP_ASSET_USER, Platforms.CRYPTO_CURRENCY_PLATFORM, VaultType.CRYPTO_CURRENCY_VAULT, VaultType.CRYPTO_CURRENCY_VAULT.getCode(), "", ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET);
//            } catch (CantRegisterCryptoAddressBookRecordException e) {
//                e.printStackTrace();
//            }
//        }


    }

    int i = 2;

    @Override
    public void launchNotification() {
//        FermatBundle fermatBundle = new FermatBundle();
//        fermatBundle.put(NotificationBundleConstants.SOURCE_PLUGIN,Plugins.CRYPTO_WALLET.getCode());
//        Owner owner = new Owner();
//        owner.setOwnerAppPublicKey(WalletsPublicKeys.CCP_REFERENCE_WALLET.getCode());
//    fermatBundle.put(NotificationBundleConstants.APP_NOTIFICATION_PAINTER_FROM,owner );
//        if(i%2==0){
//            fermatBundle.put(NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY, SubAppsPublicKeys.CHT_OPEN_CHAT.getCode());
////            fermatBundle.put(APP_TO_OPEN_PUBLIC_KEY,SubAppsPublicKeys.CHT_COMMUNITY.getCode());
//            fermatBundle.put(NotificationBundleConstants.NOTIFICATION_ID,20);
//            fermatBundle.put(NotificationBundleConstants.APP_ACTIVITY_TO_OPEN_CODE, Activities.CHT_CHAT_OPEN_CHATLIST.getCode());
//            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE,fermatBundle);
//        }else{
//            fermatBundle.put(NotificationBundleConstants.APP_TO_OPEN_PUBLIC_KEY,SubAppsPublicKeys.CHT_COMMUNITY.getCode());
//            fermatBundle.put(NotificationBundleConstants.NOTIFICATION_ID,30);
//            broadcaster.publish(BroadcasterType.NOTIFICATION_SERVICE, SubAppsPublicKeys.CHT_COMMUNITY.getCode(),fermatBundle);
//        }
//        i++;

    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {
        createIntraUser(name, phrase, profile_img);
    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }


    public Location getLocationManager() throws CantGetDeviceLocationException
    {
        return locationManager.getLocation();
    }
}
