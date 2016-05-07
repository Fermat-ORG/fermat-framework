package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.exceptions.CantRegisterCryptoAddressBookRecordException;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantLoadExistingVaultSeed;

import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSetPhotoException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSignExtraUserMessageException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.ExtraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.interfaces.ExtraUserManager;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraUserException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantGetIntraWalletUsersException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.IntraUserNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActor;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionState;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions.CantGetExchangeProviderIdException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.exceptions.CantSaveExchangeProviderIdException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletManager;

import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletSpend;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.loss_protected_wallet.interfaces.BitcoinLossProtectedWalletTransactionSummary;

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
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;
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
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListReceivePaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListSentPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantApproveLossProtectedRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantCreateLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantDeleteLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantFindLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetActorLossProtectedTransactionHistoryException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetAllIntraUserLossProtectedConnectionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetAllLossProtectedWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetBasicWalletExchangeProviderException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCurrencyExchangeException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCurrencyExchangeProviderException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetLossProtectedBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedReceivePaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedSentPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedSpendingException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantRefuseLossProtectedRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantRequestLossProtectedAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSaveLossProtectedTransactionDescriptionException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSendLossProtectedCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSendLossProtectedPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSetBasicWalletExchangeProviderException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantUpdateLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedRequestPaymentInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedTransactionNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedActorTransactionSummary;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserActor;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;

import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.exceptions.CantEnrichLossProtectedIntraUserException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.exceptions.CantEnrichLossProtectedTransactionException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.exceptions.CantGetLossProtectedActorException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.exceptions.CantInitializeLossProtectedWalletManagerException;
import com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.exceptions.CantRequestOrRegisterLossProtectedAddressException;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.all_definition.utils.CurrencyPairImpl;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.UnsupportedCurrencyPairException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantGetMnemonicTextException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import org.apache.commons.collections.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleManager</code>
 * haves all methods for the contacts activity of a bitcoin wallet
 * <p/>
 *
 * Created Natalia Cortez on 07/03/2016.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class LossProtectedWalletModuleManager implements LossProtectedWallet {

    private final BitcoinLossProtectedWalletManager bitcoinWalletManager          ;
    private final CryptoAddressBookManager       cryptoAddressBookManager      ;
    private final CryptoAddressesManager         cryptoAddressesNSManager      ;
    private final CryptoPaymentManager           cryptoPaymentManager          ;
    private final CryptoVaultManager            cryptoVaultManager             ;
    private final ErrorManager                   errorManager                  ;
    private final ExtraUserManager               extraUserManager              ;
    private final IntraWalletUserActorManager    intraUserManager              ;
    private final IntraWalletUserIdentityManager intraWalletUserIdentityManager;
    private final OutgoingExtraUserManager       outgoingExtraUserManager      ;
    private final OutgoingIntraActorManager      outgoingIntraActorManager     ;
    private final WalletContactsManager          walletContactsManager         ;
    private final CurrencyExchangeProviderFilterManager exchangeProviderFilterManagerproviderFilter;
    private final WalletManagerManager walletManagerManager;
    private final TransferIntraWalletUsersManager transferIntraWalletUsersManager;



    public LossProtectedWalletModuleManager(final BitcoinLossProtectedWalletManager bitcoinWalletManager          ,
                                           final CryptoAddressBookManager       cryptoAddressBookManager      ,
                                           final CryptoAddressesManager         cryptoAddressesNSManager      ,
                                           final CryptoPaymentManager           cryptoPaymentManager          ,
                                           final CryptoVaultManager cryptoVaultManager            ,
                                           final ErrorManager                   errorManager                  ,
                                           final ExtraUserManager               extraUserManager              ,
                                           final IntraWalletUserActorManager    intraUserManager              ,
                                           final IntraWalletUserIdentityManager intraWalletUserIdentityManager,
                                           final OutgoingExtraUserManager       outgoingExtraUserManager      ,
                                           final OutgoingIntraActorManager      outgoingIntraActorManager     ,
                                           final WalletContactsManager          walletContactsManager,
                                            final CurrencyExchangeProviderFilterManager exchangeProviderFilterManagerproviderFilter,
                                            final WalletManagerManager walletManagerManager,
                                            final TransferIntraWalletUsersManager transferIntraWalletUsersManager) {


        this.bitcoinWalletManager           = bitcoinWalletManager          ;
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
        this.exchangeProviderFilterManagerproviderFilter = exchangeProviderFilterManagerproviderFilter;
        this.walletManagerManager = walletManagerManager;
        this.transferIntraWalletUsersManager = transferIntraWalletUsersManager;


    }

    private CryptoPaymentRegistry  cryptoPaymentRegistry ;
    private WalletContactsRegistry walletContactsRegistry;


    public final void initialize() throws CantInitializeLossProtectedWalletManagerException {

        try {

            cryptoPaymentRegistry  = cryptoPaymentManager .getCryptoPaymentRegistry();

            walletContactsRegistry = walletContactsManager.getWalletContactsRegistry();

        } catch (final CantGetWalletContactRegistryException e) {

            throw new CantInitializeLossProtectedWalletManagerException(e, "", "Error trying to get wallet manager registry.");
        } catch(final CantGetCryptoPaymentRegistryException e) {

            throw new CantInitializeLossProtectedWalletManagerException(e, "", "Error get crypto Payment Registry object");
        }  catch (final Exception e){

            throw new CantInitializeLossProtectedWalletManagerException(e, "", "Unhandled error.");
        }
    }

    @Override
    public double getEarningOrLostsWallet(String walletPublicKey) {

        double exchangeRate = 0;
        double amountEarnOrLost = 0;
        double totalEarnOrLost = 0;
        try {

            BitcoinLossProtectedWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            List<BitcoinLossProtectedWalletTransaction> bitcoinWalletTransactionList = bitcoinWalletWallet.listTransactions(BalanceType.REAL, TransactionType.CREDIT, 20, 0);
            List<BitcoinLossProtectedWalletSpend> bitcoinWalletSpendingList = new ArrayList<>();

            for (BitcoinLossProtectedWalletTransaction transactionList: bitcoinWalletTransactionList) {

                bitcoinWalletSpendingList = bitcoinWalletWallet.listTransactionsSpending(transactionList.getTransactionId());

                for (BitcoinLossProtectedWalletSpend spentList:bitcoinWalletSpendingList) {

                        exchangeRate = spentList.getExchangeRate() - transactionList.getExchangeRate();

                        if (spentList.getExchangeRate() >= transactionList.getExchangeRate()){

                            amountEarnOrLost += spentList.getAmount()*exchangeRate;
                            totalEarnOrLost += amountEarnOrLost/spentList.getExchangeRate();

                        }else{

                            amountEarnOrLost += spentList.getAmount()*exchangeRate;
                            totalEarnOrLost += amountEarnOrLost/spentList.getExchangeRate();
                        }


                }
            }


        }catch (Exception e){

        }
        return totalEarnOrLost;
    }

    @Override
    public List<LossProtectedWalletContact> listWalletContacts(String walletPublicKey,String intraUserLoggedInPublicKey) throws CantGetAllLossProtectedWalletContactsException {
        try {


            List<LossProtectedWalletContact> finalRecordList = new ArrayList<>();
            finalRecordList.clear();
            WalletContactsSearch walletContactsSearch = walletContactsRegistry.searchWalletContact(walletPublicKey);
            for(WalletContactRecord r : walletContactsSearch.getResult()){

                byte[] image = getImageByActorType(r.getActorType(), r.getActorPublicKey(), intraUserLoggedInPublicKey);

                finalRecordList.add(new LossProtectedWalletModuleWalletContact(r, image));
            }
            return  finalRecordList;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException e) {
            throw new CantGetAllLossProtectedWalletContactsException(CantGetAllLossProtectedWalletContactsException.DEFAULT_MESSAGE, e);
        }  catch (Exception e) {
            throw new CantGetAllLossProtectedWalletContactsException(CantGetAllLossProtectedWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<LossProtectedWalletContact> listAllActorContactsAndConnections(String walletPublicKey,String intraUserLoggedInPublicKey) throws CantGetAllLossProtectedWalletContactsException {
        try {
            Map<String, LossProtectedWalletContact> contactMap = new HashMap<>();

            //get wallet contacts
            WalletContactsSearch walletContactsSearch = walletContactsRegistry.searchWalletContact(walletPublicKey);


            for(WalletContactRecord r : walletContactsSearch.getResult()){
                // System.out.println("wallet contact: "+r);
                byte[] image = getImageByActorType(r.getActorType(), r.getActorPublicKey(),intraUserLoggedInPublicKey);
                contactMap.put(r.getActorPublicKey(), new LossProtectedWalletModuleWalletContact(r, image));
            }

            // get intra user connections
            List<IntraWalletUserActor> intraUserList = intraUserManager.getConnectedIntraWalletUsers(intraUserLoggedInPublicKey);

            for(IntraWalletUserActor intraUser : intraUserList) {
                // System.out.println("intra user: " + intraUser);
                if (!contactMap.containsKey(intraUser.getPublicKey()))
                {
                    contactMap.put(intraUser.getPublicKey(), new LossProtectedWalletModuleWalletContact( new LossProtectedWalletModuleIntraUserActor(
                            intraUser.getName(),
                            false,
                            intraUser.getProfileImage(),
                            intraUser.getPublicKey()),
                            walletPublicKey));

                }

            }
            return new ArrayList<>(contactMap.values());


        } catch (CantGetAllLossProtectedWalletContactsException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAllLossProtectedWalletContactsException(CantGetAllLossProtectedWalletContactsException.DEFAULT_MESSAGE, e);
        }  catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantGetAllLossProtectedWalletContactsException(CantGetAllLossProtectedWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    private byte[] getImageByActorType(final Actors actorType     ,
                                       final String actorPublicKey, final String intraUserLoggedInPublicKey) throws CantGetAllLossProtectedWalletContactsException,
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
                        throw new CantGetAllLossProtectedWalletContactsException(CantGetAllLossProtectedWalletContactsException.DEFAULT_MESSAGE, e);
                    }

                default:
                    throw new CantGetAllLossProtectedWalletContactsException("UNEXPECTED ACTOR TYPE", null, "", "incomplete switch");
            }
        } catch (Exception e) {
            return new byte[0];
        }
    }


    @Override
    public List<LossProtectedWalletContact> listWalletContactsScrolling(String  walletPublicKey,
                                                                       String intraUserLoggedInPublicKey,
                                                                       Integer max,
                                                                       Integer offset) throws CantGetAllLossProtectedWalletContactsException {
        try {
            Actor actor;
            List<LossProtectedWalletContact> finalRecordList = new ArrayList<>();
            finalRecordList.clear();
            WalletContactsSearch walletContactsSearch = walletContactsRegistry.searchWalletContact(walletPublicKey);
            for(WalletContactRecord r : walletContactsSearch.getResult(max, offset)){

                byte[] image = getImageByActorType(r.getActorType(), r.getActorPublicKey(), intraUserLoggedInPublicKey);

                finalRecordList.add(new LossProtectedWalletModuleWalletContact(r, image));
            }
            return  finalRecordList;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException e) {
            throw new CantGetAllLossProtectedWalletContactsException(CantGetAllLossProtectedWalletContactsException.DEFAULT_MESSAGE, e);
        }
        catch (Exception e) {
            throw new CantGetAllLossProtectedWalletContactsException(CantGetAllLossProtectedWalletContactsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<LossProtectedWalletIntraUserActor> listAllIntraUserConnections(String  intraUserIdentityPublicKey,
                                                                        String  walletPublicKey,
                                                                        Integer max,
                                                                        Integer offset) throws CantGetAllIntraUserLossProtectedConnectionsException {
        try {
            List<LossProtectedWalletIntraUserActor> intraUserActorList = new ArrayList<>();

            List<IntraWalletUserActor> intraUserList = intraUserManager.getAllIntraWalletUsers(intraUserIdentityPublicKey, max, offset);

            List<LossProtectedWalletContact> lstContacts = listWalletContacts(walletPublicKey, intraUserIdentityPublicKey);
            for(final IntraWalletUserActor intraUser : intraUserList) {
                boolean isContact = CollectionUtils.exists(lstContacts,
                        new org.apache.commons.collections.Predicate() {
                            public boolean evaluate(Object object) {
                                LossProtectedWalletContact cryptoWalletWalletContact = (LossProtectedWalletContact) object;
                                return cryptoWalletWalletContact.getActorPublicKey().equals(intraUser.getPublicKey());

                            }
                        });
                if(!isContact)
                intraUserActorList.add(new LossProtectedWalletModuleIntraUserActor(
                        intraUser.getName(),
                        isContact,
                        intraUser.getProfileImage(),
                        intraUser.getPublicKey()));
            }
            return intraUserActorList;
        } catch (CantGetIntraWalletUsersException e) {
            throw new CantGetAllIntraUserLossProtectedConnectionsException(CantGetAllIntraUserLossProtectedConnectionsException.DEFAULT_MESSAGE, e, "", "Problem trying yo get actors from Intra-User Actor plugin.");
        } catch (Exception e) {
            throw new CantGetAllIntraUserLossProtectedConnectionsException(CantGetAllIntraUserLossProtectedConnectionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }


    private LossProtectedWalletIntraUserActor enrichIntraUser(IntraWalletUserActor intraWalletUser,
                                                       String walletPublicKey) throws CantEnrichLossProtectedIntraUserException {
        try {
            walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(intraWalletUser.getPublicKey(), walletPublicKey);

            return new LossProtectedWalletModuleIntraUserActor(
                    intraWalletUser.getName(),
                    true,
                    intraWalletUser.getProfileImage(),
                    intraWalletUser.getPublicKey()
            );
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
            return new LossProtectedWalletModuleIntraUserActor(
                    intraWalletUser.getName(),
                    false,
                    intraWalletUser.getProfileImage(),
                    intraWalletUser.getPublicKey()
            );
        } catch (CantGetWalletContactException e) {
            throw new CantEnrichLossProtectedIntraUserException(CantEnrichLossProtectedIntraUserException.DEFAULT_MESSAGE, e, "", "There was a problem trying to enrich the intra user record.");
        }
    }

    @Override
    public LossProtectedWalletContact convertConnectionToContact( String        actorAlias,
                                                                 Actors        actorConnectedType,
                                                                 String        actorConnectedPublicKey,
                                                                 byte[]        actorPhoto,
                                                                 Actors        actorWalletType ,
                                                                 String        identityWalletPublicKey,
                                                                 String        walletPublicKey,
                                                                 CryptoCurrency walletCryptoCurrency,
                                                                 BlockchainNetworkType blockchainNetworkType) throws CantCreateLossProtectedWalletContactException, ContactNameAlreadyExistsException{
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

                return new LossProtectedWalletModuleWalletContact(walletContactRecord);

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




                return new LossProtectedWalletModuleWalletContact(walletContactRecord, actorPhoto);
            }

        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantCreateLossProtectedWalletContactException(CantUpdateLossProtectedWalletContactException.DEFAULT_MESSAGE, e);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateLossProtectedWalletContactException(CantUpdateLossProtectedWalletContactException.DEFAULT_MESSAGE, e, "Error creation a wallet contact.", null);
        } catch (Exception e) {
            throw new CantCreateLossProtectedWalletContactException(CantUpdateLossProtectedWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
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
    public LossProtectedWalletContact createWalletContactWithPhoto(CryptoAddress receivedCryptoAddress,
                                                                  String        actorAlias,
                                                                  String        actorFirstName,
                                                                  String        actorLastName,
                                                                  Actors        actorType,
                                                                  String        walletPublicKey,
                                                                  byte[]        photo,
                                                                  BlockchainNetworkType blockchainNetworkType) throws CantCreateLossProtectedWalletContactException, ContactNameAlreadyExistsException {
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
                return new LossProtectedWalletModuleWalletContact(walletContactRecord, photo);
            }

        } catch (ContactNameAlreadyExistsException e) {
            throw e;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantCreateLossProtectedWalletContactException(CantCreateLossProtectedWalletContactException.DEFAULT_MESSAGE, e);
        }     catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateLossProtectedWalletContactException(CantCreateLossProtectedWalletContactException.DEFAULT_MESSAGE, e, "Error creation a wallet contact.", null);
        } catch (Exception e) {
            throw new CantCreateLossProtectedWalletContactException(CantCreateLossProtectedWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public LossProtectedWalletContact createWalletContact(CryptoAddress receivedCryptoAddress,
                                                         String        actorAlias,
                                                         String        actorFirstName,
                                                         String        actorLastName,
                                                         Actors        actorType,
                                                         String        walletPublicKey,
                                                         BlockchainNetworkType blockchainNetworkType) throws CantCreateLossProtectedWalletContactException, ContactNameAlreadyExistsException {
        try{
            try {
                walletContactsRegistry.getWalletContactByAliasAndWalletPublicKey(actorAlias, walletPublicKey);
                throw new ContactNameAlreadyExistsException(ContactNameAlreadyExistsException.DEFAULT_MESSAGE, null, null, null);

            } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {

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
                return new LossProtectedWalletModuleWalletContact(walletContactRecord);
            }

        } catch (ContactNameAlreadyExistsException e) {
            throw e;
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantCreateLossProtectedWalletContactException(CantUpdateLossProtectedWalletContactException.DEFAULT_MESSAGE, e);
          } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateLossProtectedWalletContactException(CantCreateLossProtectedWalletContactException.DEFAULT_MESSAGE, e, "Error creation a wallet contact.", null);
        } catch (Exception e) {
            throw new CantCreateLossProtectedWalletContactException(CantCreateLossProtectedWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void updateContactPhoto(String actorPublicKey, Actors actor, byte[] photo) throws CantUpdateLossProtectedWalletContactException {
        try {
            switch (actor) {
                case EXTRA_USER:
                    this.extraUserManager.setPhoto(actorPublicKey, photo);
                    break;
                case INTRA_USER:
                    this.intraUserManager.setPhoto(actorPublicKey, photo);
                    break;
                default:
                    throw new CantUpdateLossProtectedWalletContactException("Actor not expected", null, "The actor type is:" + actor.getCode(), "Incomplete switch");
            }
        } catch (ExtraUserNotFoundException e) {
            throw new CantUpdateLossProtectedWalletContactException(CantUpdateLossProtectedWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " i cannot find the actor ");
        } catch (CantSetPhotoException e) {
            throw new CantUpdateLossProtectedWalletContactException(CantUpdateLossProtectedWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " error trying to get the actor photo");
        }
        catch (com.bitdubai.fermat_ccp_api.layer.actor.intra_user.exceptions.CantSetPhotoException e) {
            throw new CantUpdateLossProtectedWalletContactException(CantUpdateLossProtectedWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " error trying to get the actor photo");
        }
        catch (IntraUserNotFoundException e) {
            throw new CantUpdateLossProtectedWalletContactException(CantUpdateLossProtectedWalletContactException.DEFAULT_MESSAGE, null, "The actor type is:" + actor.getCode(), " i cannot find the actor");
        }
    }

    @Override
    public LossProtectedWalletContact findWalletContactById(UUID contactId,String intraUserLoggedInPublicKey) throws CantFindLossProtectedWalletContactException, WalletContactNotFoundException {
        try {
            WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByContactId(contactId);


            byte[] image = getImageByActorType(walletContactRecord.getActorType(), walletContactRecord.getActorPublicKey(), intraUserLoggedInPublicKey);


            return new LossProtectedWalletModuleWalletContact(walletContactRecord, image);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantFindLossProtectedWalletContactException(CantFindLossProtectedWalletContactException.DEFAULT_MESSAGE, e);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
            throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            throw new CantFindLossProtectedWalletContactException(CantFindLossProtectedWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public LossProtectedWalletContact findWalletContactByName(String alias,String walletPublicKey,String intraUserLoggedInPublicKey) throws CantFindLossProtectedWalletContactException, WalletContactNotFoundException {
        try {
            WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByAliasAndWalletPublicKey(alias, walletPublicKey);


            byte[] image = getImageByActorType(walletContactRecord.getActorType(), walletContactRecord.getActorPublicKey(), intraUserLoggedInPublicKey);


            return new LossProtectedWalletModuleWalletContact(walletContactRecord, image);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
            throw new CantFindLossProtectedWalletContactException(CantFindLossProtectedWalletContactException.DEFAULT_MESSAGE, e);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
            throw new WalletContactNotFoundException(WalletContactNotFoundException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            throw new CantFindLossProtectedWalletContactException(CantFindLossProtectedWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public LossProtectedWalletContact addIntraUserActorLikeContact(String intraUserPublicKey,
                                                                  String alias,
                                                                  String firstName,
                                                                  String lastName,
                                                                  String walletPublicKey) throws CantCreateLossProtectedWalletContactException {
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

            return new LossProtectedWalletModuleWalletContact(walletContactRecord);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException e) {
            throw new CantCreateLossProtectedWalletContactException();
        } catch (Exception e) {
            throw new CantCreateLossProtectedWalletContactException(CantCreateLossProtectedWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
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
                                                      ReferenceWallet walletType) throws CantRequestLossProtectedAddressException {
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
                                                   BlockchainNetworkType blockchainNetworkType) throws CantRequestLossProtectedAddressException {
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
         } catch (CantRegisterCryptoAddressBookRecordException e) {
            throw new CantRequestLossProtectedAddressException(CantRequestLossProtectedAddressException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
        } catch(Exception exception){
            throw new CantRequestLossProtectedAddressException(CantRequestLossProtectedAddressException.DEFAULT_MESSAGE, FermatException.wrapException(exception));
        }
    }

    @Override
    public void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName, BlockchainNetworkType blockchainNetworkType) throws CantUpdateLossProtectedWalletContactException {
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
            throw new CantUpdateLossProtectedWalletContactException(CantUpdateLossProtectedWalletContactException.DEFAULT_MESSAGE, e);
        }  catch (Exception e) {
            throw new CantUpdateLossProtectedWalletContactException(CantUpdateLossProtectedWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void deleteWalletContact(UUID contactId) throws CantDeleteLossProtectedWalletContactException {
        try {
            walletContactsRegistry.deleteWalletContact(contactId);
        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantDeleteWalletContactException e) {
            throw new CantDeleteLossProtectedWalletContactException(CantDeleteLossProtectedWalletContactException.DEFAULT_MESSAGE, e);
        } catch (Exception e) {
            throw new CantDeleteLossProtectedWalletContactException(CantDeleteLossProtectedWalletContactException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public long getRealBalance( String walletPublicKey,
                           BlockchainNetworkType blockchainNetworkType) throws CantGetLossProtectedBalanceException {
        try {

            BitcoinLossProtectedWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            return bitcoinWalletWallet.getBalance(BalanceType.AVAILABLE).getRealBalance(blockchainNetworkType);
        } catch (CantCalculateBalanceException e) {
            throw new CantGetLossProtectedBalanceException(CantGetLossProtectedBalanceException.DEFAULT_MESSAGE, e, "", "Cant Calculate Balance of the wallet.");
        } catch(Exception e){
            throw new CantGetLossProtectedBalanceException(CantGetLossProtectedBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public long geBookBalance(String walletPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantGetLossProtectedBalanceException {
        try {
            BitcoinLossProtectedWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            return bitcoinWalletWallet.getBalance(BalanceType.BOOK).getBalance(blockchainNetworkType);
        } catch (CantCalculateBalanceException e) {
            throw new CantGetLossProtectedBalanceException(CantGetLossProtectedBalanceException.DEFAULT_MESSAGE, e, "", "Cant Calculate Balance of the wallet.");
        } catch(Exception e){
            throw new CantGetLossProtectedBalanceException(CantGetLossProtectedBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public long getBalance(BalanceType balanceType, //available balance only
                           String walletPublicKey,
                           BlockchainNetworkType blockchainNetworkType,
                           String exchangeRate) throws CantGetLossProtectedBalanceException {
        try {
            BitcoinLossProtectedWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            return bitcoinWalletWallet.getBalance(balanceType).getBalance(blockchainNetworkType, exchangeRate);
        } catch (CantLoadWalletException e) {
            throw new CantGetLossProtectedBalanceException(CantGetLossProtectedBalanceException.DEFAULT_MESSAGE, e, "", "Cant Load Wallet.");
        }  catch (CantCalculateBalanceException e) {
            throw new CantGetLossProtectedBalanceException(CantGetLossProtectedBalanceException.DEFAULT_MESSAGE, e, "", "Cant Calculate Balance of the wallet.");
        } catch(Exception e){
            throw new CantGetLossProtectedBalanceException(CantGetLossProtectedBalanceException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override

    public List<LossProtectedWalletTransaction> getTransactions(String intraUserLoggedInPublicKey,
                                                         BalanceType balanceType, final TransactionType transactionType,
                                                         String walletPublicKey,
                                                         int max,
                                                         int offset) throws CantListLossProtectedTransactionsException {
        List<LossProtectedWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
        try {
            if(intraUserLoggedInPublicKey!=null) {
                BitcoinLossProtectedWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
                List<BitcoinLossProtectedWalletTransaction> bitcoinWalletTransactionList = bitcoinWalletWallet.listTransactions(balanceType, transactionType, max, offset);

                for (BitcoinLossProtectedWalletTransaction bwt : bitcoinWalletTransactionList) {
                    cryptoWalletTransactionList.add(enrichTransaction(bwt, walletPublicKey, intraUserLoggedInPublicKey));
                }
            }
            return cryptoWalletTransactionList;
        } catch(Exception e){
            throw new CantListLossProtectedTransactionsException(CantListLossProtectedTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<LossProtectedWalletTransaction> listTransactionsByActor(BalanceType balanceType,
                                                                 String walletPublicKey,
                                                                 String actorPublicKey,
                                                                 String intraUserLoggedInPublicKey,
                                                                 int max,
                                                                 int offset) throws CantListLossProtectedTransactionsException {
        try {
            BitcoinLossProtectedWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            List<LossProtectedWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
            List<BitcoinLossProtectedWalletTransaction> bitcoinWalletTransactionList = bitcoinWalletWallet.listTransactionsByActor(actorPublicKey, balanceType, max, offset);

            for (BitcoinLossProtectedWalletTransaction bwt : bitcoinWalletTransactionList) {
                cryptoWalletTransactionList.add(enrichTransaction(bwt,walletPublicKey,intraUserLoggedInPublicKey));
            }

            return cryptoWalletTransactionList;
        } catch (com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException e) {
            throw new CantListLossProtectedTransactionsException(CantListLossProtectedTransactionsException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantListLossProtectedTransactionsException(CantListLossProtectedTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<LossProtectedWalletTransaction> listTransactionsByActorAndType(BalanceType balanceType,
                                                                        TransactionType transactionType,
                                                                        String walletPublicKey,
                                                                        String actorPublicKey, String intraUserLoggedInPublicKey,
                                                                        BlockchainNetworkType blockchainNetworkType,
                                                                        int max,
                                                                        int offset) throws CantListLossProtectedTransactionsException {
        try {
            BitcoinLossProtectedWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            List<LossProtectedWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
            List<BitcoinLossProtectedWalletTransaction> bitcoinWalletTransactionList = bitcoinWalletWallet.listTransactionsByActorAndType(actorPublicKey, balanceType, transactionType, max, offset);


                List<BitcoinLossProtectedWalletTransaction> bitcoinWalletTransactionList1 = new ArrayList<>();

                for (BitcoinLossProtectedWalletTransaction bwt : bitcoinWalletTransactionList) {

                    if (bwt.getBlockchainNetworkType().getCode().equals(blockchainNetworkType.getCode())){
                        bitcoinWalletTransactionList1.add(bwt);
                    }
                }


            for (BitcoinLossProtectedWalletTransaction bwt : bitcoinWalletTransactionList1) {
                cryptoWalletTransactionList.add(enrichTransaction(bwt,walletPublicKey,intraUserLoggedInPublicKey));
            }

            return cryptoWalletTransactionList;
        } catch (com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantListTransactionsException e) {
            throw new CantListLossProtectedTransactionsException(CantListLossProtectedTransactionsException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantListLossProtectedTransactionsException(CantListLossProtectedTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public List<BitcoinLossProtectedWalletSpend> listSpendingBlocksValue(String walletPublicKey,UUID transactionId) throws CantListLossProtectedSpendingException, CantLoadWalletException {
        List<BitcoinLossProtectedWalletSpend> bitcoinLossProtectedWalletSpendList = new ArrayList<>();

        try {

            BitcoinLossProtectedWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);

            bitcoinLossProtectedWalletSpendList = bitcoinWalletWallet.listTransactionsSpending(transactionId);


            if (bitcoinLossProtectedWalletSpendList.size()==0) {

                BitcoinLossProtectedWalletSpend spendingLsit = new BitcoinLossProtectedWalletSpend() {
                    @Override
                    public UUID getSpendId() {
                        return UUID.randomUUID();
                    }

                    @Override
                    public UUID getTransactionId() {
                        return UUID.randomUUID();
                    }

                    @Override
                    public long getTimestamp() {
                        return 0;
                    }

                    @Override
                    public long getAmount() {return  (long)9.2; }

                    @Override
                    public double getExchangeRate() {
                        return 422.1;
                    }
                };

                BitcoinLossProtectedWalletSpend spendingLsit2 = new BitcoinLossProtectedWalletSpend() {
                    @Override
                    public UUID getSpendId() {
                        return UUID.randomUUID();
                    }

                    @Override
                    public UUID getTransactionId() {
                        return UUID.randomUUID();
                    }

                    @Override
                    public long getTimestamp() {
                        return 0;
                    }

                    @Override
                    public long getAmount() {return (long)0.8;}

                    @Override
                    public double getExchangeRate() {return 427.14;}
                };

                bitcoinLossProtectedWalletSpendList.add(spendingLsit);
                bitcoinLossProtectedWalletSpendList.add(spendingLsit2);

            }

            return bitcoinLossProtectedWalletSpendList;

        } catch(Exception e){
            throw new CantListLossProtectedSpendingException(CantGetActorLossProtectedTransactionHistoryException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }



    @Override
    public LossProtectedActorTransactionSummary getActorTransactionHistory(BalanceType balanceType,
                                                              String walletPublicKey,
                                                              String actorPublicKey) throws CantGetActorLossProtectedTransactionHistoryException {
        try {
            BitcoinLossProtectedWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            return constructActorTransactionSummary(bitcoinWalletWallet.getActorTransactionSummary(actorPublicKey, balanceType));
        } catch (CantGetActorTransactionSummaryException e) {
            throw new CantGetActorLossProtectedTransactionHistoryException(CantGetActorLossProtectedTransactionHistoryException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantGetActorLossProtectedTransactionHistoryException(CantGetActorLossProtectedTransactionHistoryException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }


    @Override
    public List<LossProtectedWalletTransaction> listLastActorTransactionsByTransactionType(BalanceType balanceType,
                                                                                    final TransactionType transactionType,
                                                                                    String walletPublicKey,
                                                                                    String intraUserLoggedInPublicKey,
                                                                                    BlockchainNetworkType blockchainNetworkType,
                                                                                    int max,
                                                                                    int offset) throws CantListLossProtectedTransactionsException {


        List<LossProtectedWalletTransaction> cryptoWalletTransactionList = new ArrayList<>();
        try {
            if(intraUserLoggedInPublicKey!=null){
                BitcoinLossProtectedWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
                List<BitcoinLossProtectedWalletTransaction> bitcoinWalletTransactionList = bitcoinWalletWallet.listLastActorTransactionsByTransactionType(
                        balanceType,
                        transactionType,
                        max,
                        offset
                );

                List<BitcoinLossProtectedWalletTransaction> bitcoinWalletTransactionList1 = new ArrayList<>();

                for (BitcoinLossProtectedWalletTransaction bwt : bitcoinWalletTransactionList)
                    if (bwt.getBlockchainNetworkType().getCode().equals(blockchainNetworkType.getCode()))
                        if (bitcoinWalletTransactionList1.isEmpty()) {
                            bitcoinWalletTransactionList1.add(bwt);
                        } else {
                            int count = 0;
                            for (BitcoinLossProtectedWalletTransaction bwt1 : bitcoinWalletTransactionList1) {
                                if (bwt1.getActorToPublicKey().equals(bwt.getActorToPublicKey())) {
                                    count++;
                                }
                            }
                            if (count == 0) {
                                bitcoinWalletTransactionList1.add(bwt);
                            }


                        }


                for (BitcoinLossProtectedWalletTransaction bwt : bitcoinWalletTransactionList1) {
                    cryptoWalletTransactionList.add(enrichTransaction(bwt, walletPublicKey, intraUserLoggedInPublicKey));
                }


                if(cryptoWalletTransactionList.size() == 0)
                {
                    Actor actor = new Actor() {
                        @Override
                        public String getActorPublicKey() {
                            return "ActorPublicKey";
                        }
                        @Override
                        public String getName() {
                            return "username";
                        }
                        @Override
                        public String getPhrase() {
                            return null;
                        }
                        @Override
                        public Actors getType() {
                            return Actors.INTRA_USER;
                        }
                        @Override
                        public byte[] getPhoto() {
                            return new byte[0];
                        }
                        @Override
                        public String createMessageSignature(String message) throws CantSignExtraUserMessageException {
                            return null;
                        }
                    };

                    BitcoinLossProtectedWalletTransaction transaction = new BitcoinLossProtectedWalletTransaction() {
                        @Override
                        public UUID getTransactionId() {

                            return UUID.randomUUID();
                        }
                        @Override
                        public String getTransactionHash() {
                            return "transactionhash";
                        }
                        @Override
                        public CryptoAddress getAddressFrom() {
                            return new CryptoAddress();
                        }
                        @Override
                        public CryptoAddress getAddressTo() {
                            return new CryptoAddress();
                        }
                        @Override
                        public String getActorToPublicKey() {
                            return "ActorToPublicKey";
                        }
                        @Override
                        public String getActorFromPublicKey() {
                            return "ActorFromPublicKey";
                        }
                        @Override
                        public Actors getActorToType() {
                            return Actors.INTRA_USER;
                        }
                        @Override
                        public Actors getActorFromType() {
                            return Actors.INTRA_USER;
                        }
                        @Override
                        public BalanceType getBalanceType() {
                            return BalanceType.AVAILABLE;
                        }
                        @Override
                        public TransactionType getTransactionType() {
                            return TransactionType.DEBIT;
                        }
                        @Override
                        public long getTimestamp() {
                            return 0;
                        }
                        @Override
                        public long getAmount() {
                            return (long) 10;
                        }
                        @Override
                        public long getRunningBookBalance() {
                            return 11;
                        }
                        @Override
                        public long getRunningAvailableBalance() {
                            return 25;
                        }
                        @Override
                        public String getMemo() {
                            return null;
                        }
                        @Override
                        public TransactionState getTransactionState() {
                            return null;
                        }
                        @Override
                        public BlockchainNetworkType getBlockchainNetworkType() {
                            return BlockchainNetworkType.REG_TEST;
                        }
                        @Override
                        public long getExchangeRate() {
                            return 420;
                        }
                    };

                    LossProtectedWalletModuleTransaction lstObjet = new LossProtectedWalletModuleTransaction(transaction, null, actor);

                    cryptoWalletTransactionList.add(lstObjet);
                }

                Actor actor = new Actor() {
                    @Override
                    public String getActorPublicKey() {
                        return "ActorPublicKey";
                    }

                    @Override
                    public String getName() {
                        return "username";
                    }

                    @Override
                    public String getPhrase() {
                        return null;
                    }

                    @Override
                    public Actors getType() {
                        return Actors.INTRA_USER;
                    }

                    @Override
                    public byte[] getPhoto() {
                        return new byte[0];
                    }

                    @Override
                    public String createMessageSignature(String message) throws CantSignExtraUserMessageException {
                        return null;
                    }
                };

                BitcoinLossProtectedWalletTransaction transaction = new BitcoinLossProtectedWalletTransaction() {
                    @Override
                    public UUID getTransactionId() {return UUID.randomUUID();}

                    @Override
                    public String getTransactionHash() {
                        return "transactionhash";
                    }

                    @Override
                    public CryptoAddress getAddressFrom() {
                        return new CryptoAddress();
                    }

                    @Override
                    public CryptoAddress getAddressTo() {
                        return new CryptoAddress();
                    }

                    @Override
                    public String getActorToPublicKey() {
                        return "ActorToPublicKey";
                    }

                    @Override
                    public String getActorFromPublicKey() {
                        return "ActorFromPublicKey";
                    }

                    @Override
                    public Actors getActorToType() {
                        return Actors.INTRA_USER;
                    }

                    @Override
                    public Actors getActorFromType() {
                        return Actors.INTRA_USER;
                    }

                    @Override
                    public BalanceType getBalanceType() {
                        return BalanceType.AVAILABLE;
                    }

                    @Override
                    public TransactionType getTransactionType() {
                        return TransactionType.DEBIT;
                    }

                    @Override
                    public long getTimestamp() {
                        return 0;
                    }

                    @Override
                    public long getAmount() {
                        return 2;
                    }

                    @Override
                    public long getRunningBookBalance() {
                        return 10;
                    }

                    @Override
                    public long getRunningAvailableBalance() {
                        return 12;
                    }

                    @Override
                    public String getMemo() {
                        return null;
                    }

                    @Override
                    public TransactionState getTransactionState() {
                        return null;
                    }

                    @Override
                    public BlockchainNetworkType getBlockchainNetworkType() {
                        return BlockchainNetworkType.REG_TEST;
                    }

                    @Override
                    public long getExchangeRate() {
                        return 21;
                    }
                };


                cryptoWalletTransactionList.add(new LossProtectedWalletModuleTransaction(transaction, null, actor));
            }

            return cryptoWalletTransactionList;
        } catch(Exception e){
            throw new CantListLossProtectedTransactionsException(CantListLossProtectedTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }

    }

    @Override
    public LossProtectedWalletTransaction getTransaction(UUID transactionId, String walletPublicKey,String intraUserLoggedInPublicKey) throws CantListLossProtectedTransactionsException
    {

        try {
            LossProtectedWalletTransaction cryptoWalletTransaction = null;
            BitcoinLossProtectedWallet bitcoinWalletWallet;
            bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);

            BitcoinLossProtectedWalletTransaction bwt = bitcoinWalletWallet.getTransactionById(transactionId);

            if(bwt != null)
             cryptoWalletTransaction =enrichTransaction(bwt, walletPublicKey, intraUserLoggedInPublicKey);
            else {

                //TODO hacoder quitar

                Actor actor = new Actor() {
                    @Override
                    public String getActorPublicKey() {
                        return "ActorPublicKey";
                    }
                    @Override
                    public String getName() {
                        return "username";
                    }
                    @Override
                    public String getPhrase() {
                        return null;
                    }
                    @Override
                    public Actors getType() {
                        return Actors.INTRA_USER;
                    }
                    @Override
                    public byte[] getPhoto() {
                        return new byte[0];
                    }
                    @Override
                    public String createMessageSignature(String message) throws CantSignExtraUserMessageException {
                        return null;
                    }
                };

                BitcoinLossProtectedWalletTransaction transaction = new BitcoinLossProtectedWalletTransaction() {
                    @Override
                    public UUID getTransactionId() {

                        return UUID.randomUUID();
                    }
                    @Override
                    public String getTransactionHash() {
                        return "transactionhash";
                    }
                    @Override
                    public CryptoAddress getAddressFrom() {
                        return new CryptoAddress();
                    }
                    @Override
                    public CryptoAddress getAddressTo() {
                        return new CryptoAddress();
                    }
                    @Override
                    public String getActorToPublicKey() {
                        return "ActorToPublicKey";
                    }
                    @Override
                    public String getActorFromPublicKey() {
                        return "ActorFromPublicKey";
                    }
                    @Override
                    public Actors getActorToType() {
                        return Actors.INTRA_USER;
                    }
                    @Override
                    public Actors getActorFromType() {
                        return Actors.INTRA_USER;
                    }
                    @Override
                    public BalanceType getBalanceType() {
                        return BalanceType.AVAILABLE;
                    }
                    @Override
                    public TransactionType getTransactionType() {
                        return TransactionType.DEBIT;
                    }
                    @Override
                    public long getTimestamp() {
                        return 0;
                    }
                    @Override
                    public long getAmount() {
                        return (long) 10;
                    }
                    @Override
                    public long getRunningBookBalance() {
                        return 11;
                    }
                    @Override
                    public long getRunningAvailableBalance() {
                        return 25;
                    }
                    @Override
                    public String getMemo() {
                        return null;
                    }
                    @Override
                    public TransactionState getTransactionState() {
                        return null;
                    }
                    @Override
                    public BlockchainNetworkType getBlockchainNetworkType() {
                        return BlockchainNetworkType.REG_TEST;
                    }
                    @Override
                    public long getExchangeRate() {
                        return 420;
                    }
                };

                cryptoWalletTransaction = new LossProtectedWalletModuleTransaction(transaction, null, actor);

            }

            return  cryptoWalletTransaction;

        } catch (CantFindTransactionException e) {
            throw new CantListLossProtectedTransactionsException(CantListLossProtectedTransactionsException.DEFAULT_MESSAGE, e);
        } catch (CantEnrichLossProtectedTransactionException e) {
            throw new CantListLossProtectedTransactionsException(CantListLossProtectedTransactionsException.DEFAULT_MESSAGE, e);
        } catch (CantLoadWalletException e) {
            throw new CantListLossProtectedTransactionsException(CantListLossProtectedTransactionsException.DEFAULT_MESSAGE, e);
        }
    }

    @Override
    public void setTransactionDescription(String walletPublicKey,
                                          UUID   transactionID,
                                          String description) throws CantSaveLossProtectedTransactionDescriptionException, LossProtectedTransactionNotFoundException {

        try {
            BitcoinLossProtectedWallet bitcoinWalletWallet = bitcoinWalletManager.loadWallet(walletPublicKey);
            bitcoinWalletWallet.setTransactionDescription(transactionID, description);
        } catch (CantStoreMemoException e) {
            throw new CantSaveLossProtectedTransactionDescriptionException(CantSaveLossProtectedTransactionDescriptionException.DEFAULT_MESSAGE, e);
        } catch (CantFindTransactionException e) {
            throw new LossProtectedTransactionNotFoundException(LossProtectedTransactionNotFoundException.DEFAULT_MESSAGE, e);
        } catch(Exception e){
            throw new CantSaveLossProtectedTransactionDescriptionException(CantSaveLossProtectedTransactionDescriptionException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    private LossProtectedActorTransactionSummary constructActorTransactionSummary(BitcoinLossProtectedWalletTransactionSummary transactionSummary) {
        return new LossProtectedWalletModuleActorTransactionSummary(
                transactionSummary.getSentTransactionsNumber(),
                transactionSummary.getReceivedTransactionsNumber(),
                transactionSummary.getSentAmount(),
                transactionSummary.getReceivedAmount()
        );
    }

    @Override
    public void send(long cryptoAmount, CryptoAddress destinationAddress, String notes, String walletPublicKey, String deliveredByActorPublicKey, Actors deliveredByActorType, String deliveredToActorPublicKey, Actors deliveredToActorType,ReferenceWallet referenceWallet, BlockchainNetworkType blockchainNetworkType) throws CantSendLossProtectedCryptoException, LossProtectedInsufficientFundsException {
        try {

            switch (deliveredToActorType) {
                case EXTRA_USER:
                    System.out.println("Sending throw outgoing Extra User ...");
                    outgoingExtraUserManager.getTransactionManager().send(walletPublicKey, destinationAddress, cryptoAmount, notes, deliveredByActorPublicKey, deliveredByActorType, deliveredToActorPublicKey, deliveredToActorType, blockchainNetworkType);

                    break;
                case INTRA_USER:
                    System.out.println("Sending throw outgoing Intra Actor ...");
                    outgoingIntraActorManager.getTransactionManager().sendCrypto(walletPublicKey, destinationAddress, cryptoAmount, notes, deliveredByActorPublicKey,  deliveredToActorPublicKey,deliveredByActorType, deliveredToActorType,referenceWallet,blockchainNetworkType);

                    break;
            }

        }
        catch (com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_extra_user.exceptions.InsufficientFundsException e) {
            throw new LossProtectedInsufficientFundsException(LossProtectedInsufficientFundsException.DEFAULT_MESSAGE, e);
        }
        catch(OutgoingIntraActorCantSendFundsExceptions| OutgoingIntraActorInsufficientFundsException ex){
            throw new CantSendLossProtectedCryptoException(CantSendLossProtectedCryptoException.DEFAULT_MESSAGE, ex);

        }
        catch (CantSendFundsException | CantGetTransactionManagerException e) {
            throw new CantSendLossProtectedCryptoException(CantSendLossProtectedCryptoException.DEFAULT_MESSAGE, e);
        }
        catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE,UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
            throw new CantSendLossProtectedCryptoException(CantSendLossProtectedCryptoException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }
    }

    @Override
    public void sendToWallet(long cryptoAmount, String sendingWalletPublicKey,String receivingWalletPublicKey, String notes, Actors deliveredToActorType, ReferenceWallet sendingWallet, ReferenceWallet receivingWallet, BlockchainNetworkType blockchainNetworkType) throws CantSendLossProtectedCryptoException, LossProtectedInsufficientFundsException {

       try {
           transferIntraWalletUsersManager.getOutgoingDeviceUser().sendToWallet("",cryptoAmount,notes,deliveredToActorType,sendingWallet,receivingWallet,sendingWalletPublicKey,receivingWalletPublicKey,blockchainNetworkType);
       } catch (CantSendTransactionException e) {
           throw new CantSendLossProtectedCryptoException("CAN'T SEND CRYPTO TO WALLET EXCEPTION", e);

       } catch (TransferIntraWalletUsersNotEnoughFundsException e) {
           throw new LossProtectedInsufficientFundsException("TRANSFER TO WALLET Insufficient Funds", e,"","");

       }


    }

    @Override
    public List<LossProtectedPaymentRequest> listSentPaymentRequest(String walletPublicKey,BlockchainNetworkType blockchainNetworkType,int max,int offset) throws CantListLossProtectedSentPaymentRequestException {
        try {
            List<LossProtectedPaymentRequest> lst =  new ArrayList<>();
            LossProtectedWalletModuleWalletContact cryptoWalletWalletContact = null;
            byte[] profilePicture = null;

            //find received payment request
            for (CryptoPayment paymentRecord :  cryptoPaymentRegistry.listCryptoPaymentRequestsByType(walletPublicKey, CryptoPaymentType.SENT, blockchainNetworkType,max, offset)) {

                WalletContactRecord walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(paymentRecord.getActorPublicKey(),walletPublicKey);

                if (getImageByActorType(paymentRecord.getActorType(),paymentRecord.getActorPublicKey(),paymentRecord.getIdentityPublicKey()) != null)
                    profilePicture = getImageByActorType(paymentRecord.getActorType(),paymentRecord.getActorPublicKey(),paymentRecord.getIdentityPublicKey());

                if (walletContactRecord != null)
                    cryptoWalletWalletContact = new LossProtectedWalletModuleWalletContact(walletContactRecord, profilePicture);



                LossProtectedPaymentRequest cryptoWalletPaymentRequest = new LossProtectedWalletModulePaymentRequest(
                        paymentRecord.getRequestId(),
                        convertTime(paymentRecord.getStartTimeStamp()),
                        paymentRecord.getDescription(),
                        paymentRecord.getAmount(),
                        cryptoWalletWalletContact,
                        LossProtectedPaymentRequest.SEND_PAYMENT,
                        paymentRecord.getState());
                lst.add(cryptoWalletPaymentRequest);
            }

            return lst;
        } catch (Exception e) {
            throw new CantListLossProtectedSentPaymentRequestException(CantListLossProtectedSentPaymentRequestException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }

    }

    @Override
    public LossProtectedPaymentRequest getPaymentRequest(UUID requestId) throws CantListReceivePaymentRequestException {

        try {

            CryptoPayment paymentRecord = cryptoPaymentRegistry.getRequestById(requestId);

           return  new LossProtectedWalletModulePaymentRequest(
                    paymentRecord.getRequestId(),
                    convertTime(paymentRecord.getStartTimeStamp()),
                    paymentRecord.getDescription(),
                    paymentRecord.getAmount(),
                    null,
                    LossProtectedPaymentRequest.RECEIVE_PAYMENT,
                    paymentRecord.getState());

        } catch (Exception e) {
            throw new CantListReceivePaymentRequestException(CantListSentPaymentRequestException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }


    }

    @Override
    public long getActualExchangeRate() throws CantListReceivePaymentRequestException {
        return 400;
    }


    public List<LossProtectedPaymentRequest> listReceivedPaymentRequest(String walletPublicKey,BlockchainNetworkType blockchainNetworkType,int max,int offset) throws CantListLossProtectedReceivePaymentRequestException {

        try {
            List<LossProtectedPaymentRequest> lst =  new ArrayList<>();

            LossProtectedWalletModuleWalletContact cryptoWalletWalletContact = null;
            byte[] profilePicture = null;

            //find received payment request
            for (CryptoPayment paymentRecord :  cryptoPaymentRegistry.listCryptoPaymentRequestsByType(
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
                        cryptoWalletWalletContact = new LossProtectedWalletModuleWalletContact(walletContactRecord,profilePicture);

                }
                catch(com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e)
                {

                }
                LossProtectedPaymentRequest cryptoWalletPaymentRequest = new LossProtectedWalletModulePaymentRequest(
                        paymentRecord.getRequestId(),
                        convertTime(paymentRecord.getStartTimeStamp()),
                        paymentRecord.getDescription(),
                        paymentRecord.getAmount(),
                        cryptoWalletWalletContact,
                        LossProtectedPaymentRequest.RECEIVE_PAYMENT,
                        paymentRecord.getState()
                );

                lst.add(cryptoWalletPaymentRequest);
            }


            return lst;
        } catch (Exception e) {
            throw new CantListLossProtectedReceivePaymentRequestException(CantListLossProtectedReceivePaymentRequestException.DEFAULT_MESSAGE, FermatException.wrapException(e));
        }


    }


    /**
     * Throw the method <code>refuseRequest</code> you can refuse a request.
     *
     * @param requestId
     * @throws CantRejectCryptoPaymentRequestException
     * @throws CryptoPaymentRequestNotFoundException
     */
    public void refuseRequest(UUID requestId) throws CantRefuseLossProtectedRequestPaymentException,LossProtectedPaymentRequestNotFoundException
    {
        try {
            cryptoPaymentRegistry.refuseRequest(requestId);
        }
        catch(CantRejectCryptoPaymentRequestException e)
        {
            throw new CantRefuseLossProtectedRequestPaymentException(CantRefuseLossProtectedRequestPaymentException.DEFAULT_MESSAGE,e);
        }
        catch(CryptoPaymentRequestNotFoundException e)
        {
            throw new LossProtectedPaymentRequestNotFoundException(LossProtectedPaymentRequestNotFoundException.DEFAULT_MESSAGE,e);
        }
        catch(Exception e)
        {
            throw new CantRefuseLossProtectedRequestPaymentException(CantRefuseLossProtectedRequestPaymentException.DEFAULT_MESSAGE,FermatException.wrapException(e));
        }
    }


    /**
     * Throw the method <code>approveRequest</code> you can approve a request and send the specified crypto.
     * @param requestId
     * @throws CantApproveCryptoPaymentRequestException
     * @throws CryptoPaymentRequestNotFoundException
     * @throws com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFundsException
     */

    public void approveRequest(UUID requestId, String intraUserLoggedInPublicKey) throws CantApproveLossProtectedRequestPaymentException,
            LossProtectedPaymentRequestNotFoundException,
            LossProtectedRequestPaymentInsufficientFundsException
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

            cryptoPaymentRegistry.approveRequest(requestId);


        }
        catch(CantApproveCryptoPaymentRequestException e)
        {
            throw new CantApproveLossProtectedRequestPaymentException(CantApproveLossProtectedRequestPaymentException.DEFAULT_MESSAGE,e);
        }
        catch(CryptoPaymentRequestNotFoundException e)
        {
            throw new LossProtectedPaymentRequestNotFoundException(LossProtectedPaymentRequestNotFoundException.DEFAULT_MESSAGE,e);
        }
        catch(com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFundsException e)
        {
            throw new LossProtectedRequestPaymentInsufficientFundsException(LossProtectedRequestPaymentInsufficientFundsException.DEFAULT_MESSAGE,e);
        }
        catch(Exception e)
        {
            throw new CantApproveLossProtectedRequestPaymentException(CantApproveLossProtectedRequestPaymentException.DEFAULT_MESSAGE,FermatException.wrapException(e));
        }
    }






    @Override
    public boolean isValidAddress(CryptoAddress cryptoAddress) {
        return cryptoVaultManager.isValidAddress(cryptoAddress);
    }

    @Override
    public List<LossProtectedWalletIntraUserIdentity> getAllIntraWalletUsersFromCurrentDeviceUser() throws CantListLossProtectedWalletIntraUserIdentityException {

        try {

            List<LossProtectedWalletIntraUserIdentity> cryptoWalletIntraUserIdentityList = new ArrayList<LossProtectedWalletIntraUserIdentity>();

            for (IntraWalletUserIdentity intraWalletUser : this.intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser()) {

                LossProtectedWalletIntraUserIdentity cryptoWalletIntraUserIdentity = new LossProtectedWalletIntraUserIdentityWallet(intraWalletUser.getPublicKey(), intraWalletUser.getAlias(), intraWalletUser.getImage());

                cryptoWalletIntraUserIdentityList.add(cryptoWalletIntraUserIdentity);
            }

            return cryptoWalletIntraUserIdentityList;

        } catch (CantListIntraWalletUsersException e) {
            throw new CantListLossProtectedWalletIntraUserIdentityException(CantListLossProtectedWalletIntraUserIdentityException.DEFAULT_MESSAGE, e, "", "");
        } catch (Exception e) {
            throw new CantListLossProtectedWalletIntraUserIdentityException(CantListLossProtectedWalletIntraUserIdentityException.DEFAULT_MESSAGE, e, "", "unknown error");
        }

    }

    @Override
    public List<IntraWalletUserIdentity> getActiveIdentities() {

        try{

            return intraWalletUserIdentityManager.getAllIntraWalletUsersFromCurrentDeviceUser();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private String createActor(String actorName, Actors actorType, byte[] photo) throws CantRequestOrRegisterLossProtectedAddressException {
        switch (actorType){
            case EXTRA_USER:
                try {
                    Actor actor = extraUserManager.createActor(actorName, photo);
                    return actor.getActorPublicKey();
                } catch (CantCreateExtraUserException e) {
                    throw new CantRequestOrRegisterLossProtectedAddressException(CantRequestOrRegisterLossProtectedAddressException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
                }
            case INTRA_USER:
                // THERE'S NO NEED TO CREATE A NEW INTRA ACTOR. PLEASE DONT ADD ANYTHING HERE

            default:
                throw new CantRequestOrRegisterLossProtectedAddressException(CantRequestOrRegisterLossProtectedAddressException.DEFAULT_MESSAGE, null, "", "ActorType is not Compatible.");
        }
    }


    private String createActor(String actorName, Actors actorType) throws CantRequestOrRegisterLossProtectedAddressException {
        switch (actorType){
            case EXTRA_USER:
                try {
                    Actor actor = extraUserManager.createActor(actorName);
                    return actor.getActorPublicKey();
                } catch (CantCreateExtraUserException e) {
                    throw new CantRequestOrRegisterLossProtectedAddressException(CantRequestOrRegisterLossProtectedAddressException.DEFAULT_MESSAGE, e, "", "Check if all the params are sended.");
                }
            default:
                throw new CantRequestOrRegisterLossProtectedAddressException(CantRequestOrRegisterLossProtectedAddressException.DEFAULT_MESSAGE, null, "", "ActorType is not Compatible.");
        }
    }

    private CryptoAddress requestCryptoAddressByReferenceWallet(ReferenceWallet referenceWallet,BlockchainNetworkType blockchainNetworkType) throws CantRequestOrRegisterLossProtectedAddressException {
        switch (referenceWallet){
            case BASIC_WALLET_LOSS_PROTECTED_WALLET:
                return cryptoVaultManager.getAddress(blockchainNetworkType);
            default:
                throw new CantRequestOrRegisterLossProtectedAddressException(CantRequestOrRegisterLossProtectedAddressException.DEFAULT_MESSAGE, null, "", "ReferenceWallet is not Compatible.");
        }
    }

    private LossProtectedWalletTransaction enrichTransaction(BitcoinLossProtectedWalletTransaction bitcoinWalletTransaction, String walletPublicKey, String intraUserLoggedInPublicKey) throws CantEnrichLossProtectedTransactionException {
        try {
            Actor involvedActor = null;
            UUID contactId = null;
            WalletContactRecord walletContactRecord =  null;

            switch (bitcoinWalletTransaction.getTransactionType()) {
                case CREDIT:
                    try {
                        if(!bitcoinWalletTransaction.getActorFromType().equals(Actors.DEVICE_USER)){
                            if(bitcoinWalletTransaction.getActorFromType() == Actors.INTRA_USER)
                            {
                                involvedActor = getActorByActorPublicKeyAndType(bitcoinWalletTransaction.getActorToPublicKey(), bitcoinWalletTransaction.getActorToType(), intraUserLoggedInPublicKey);

                                walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(bitcoinWalletTransaction.getActorFromPublicKey(), walletPublicKey);

                            }

                            if(involvedActor==null)
                            {
                                involvedActor = getActorByActorPublicKeyAndType(bitcoinWalletTransaction.getActorFromPublicKey(), bitcoinWalletTransaction.getActorFromType(), intraUserLoggedInPublicKey);

                                walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(bitcoinWalletTransaction.getActorToPublicKey(), walletPublicKey);

                            }

                        }else{
                            involvedActor = null;
                            walletContactRecord = null;
                        }

                         if (walletContactRecord != null)
                            contactId = walletContactRecord.getContactId();

                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
                        throw new CantEnrichLossProtectedTransactionException(CantEnrichLossProtectedTransactionException.DEFAULT_MESSAGE, e, "Cant get Contact Information", "");
                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                        contactId = null;

                    } catch ( CantGetLossProtectedActorException e) {
                        contactId = null;
                    }

                    break;
                case DEBIT:
                    try {
                        if(!bitcoinWalletTransaction.getActorFromType().equals(Actors.DEVICE_USER)){
                            involvedActor = getActorByActorPublicKeyAndType(bitcoinWalletTransaction.getActorToPublicKey(), bitcoinWalletTransaction.getActorToType(),intraUserLoggedInPublicKey);
                            walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(bitcoinWalletTransaction.getActorToPublicKey(), walletPublicKey);

                            if(involvedActor==null)
                            {
                                involvedActor = getActorByActorPublicKeyAndType(bitcoinWalletTransaction.getActorFromPublicKey(), bitcoinWalletTransaction.getActorFromType(), intraUserLoggedInPublicKey);

                                walletContactRecord = walletContactsRegistry.getWalletContactByActorAndWalletPublicKey(bitcoinWalletTransaction.getActorToPublicKey(), walletPublicKey);

                            }
                        }else{
                            involvedActor = null;
                            walletContactRecord = null;
                        }

                        if (walletContactRecord != null)
                            contactId = walletContactRecord.getContactId();

                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException e) {
                        throw new CantEnrichLossProtectedTransactionException(CantEnrichLossProtectedTransactionException.DEFAULT_MESSAGE, e, "Cant get Contact Information", "");
                    } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                        contactId = null;
                    }
                    break;
            }
            return new LossProtectedWalletModuleTransaction(bitcoinWalletTransaction, contactId, involvedActor);
        } catch (Exception e) {
            throw new CantEnrichLossProtectedTransactionException(CantEnrichLossProtectedTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(e), "", "");
        }
    }

    private Actor getActorByActorPublicKeyAndType(String actorPublicKey, Actors actorType, String intraUserLoggedInPublicKey) throws CantGetLossProtectedActorException {
        Actor actor;
        switch (actorType) {
            case EXTRA_USER:
                try {
                    actor = extraUserManager.getActorByPublicKey(actorPublicKey);
                    return actor;
                } catch (CantGetExtraUserException | ExtraUserNotFoundException e) {
                    throw new CantGetLossProtectedActorException(CantGetLossProtectedActorException.DEFAULT_MESSAGE, e, null, "Cant get Extra User on DataBase");
                }
            case INTRA_USER:
                try {
                    //find actor connected with logget identity
                    actor = intraUserManager.getActorByPublicKey(intraUserLoggedInPublicKey,actorPublicKey);
                    return actor;
                } catch (CantGetIntraUserException| IntraUserNotFoundException e) {
                    throw new CantGetLossProtectedActorException(CantGetLossProtectedActorException.DEFAULT_MESSAGE, e, null, "Cant get Intra User on DataBase");
                }
            case DEVICE_USER:
                    //there is not going to find an actor because it is a transaction from another wallet
                    actor = null;
                    return actor;

            default:
                throw new CantGetLossProtectedActorException(CantGetLossProtectedActorException.DEFAULT_MESSAGE, null, null, null);
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
                                               final ReferenceWallet       referenceWallet) throws CantSendLossProtectedPaymentRequestException {

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
                    referenceWallet
            );
        } catch (CantGenerateCryptoPaymentRequestException e) {

            throw new CantSendLossProtectedPaymentRequestException(e, "", "Error found in crypto payment request plugin.");
        } catch (Exception e) {

            throw new CantSendLossProtectedPaymentRequestException(e, "", "Unhandled error.");
        }

    }

    @Override
    public void createIntraUser(String name, String phrase, byte[] image) throws CantCreateNewIntraWalletUserException {
        intraWalletUserIdentityManager.createNewIntraWalletUser(name, phrase, image);
    }


    @Override
    public void registerIdentities(){
        intraWalletUserIdentityManager.registerIdentities();
    }

    @Override
    public List<String> getMnemonicText() throws CantGetMnemonicTextException {
        try {
            return cryptoVaultManager.getMnemonicCode();
        } catch (CantLoadExistingVaultSeed e) {
            throw new CantGetMnemonicTextException("CANT GET WALLET Mnemonic TEXT",e, "", "Crypto vault error.");
        }
    }

    @Override
    public ExchangeRate getCurrencyExchange(UUID rateProviderManagerId) throws CantGetCurrencyExchangeException {

        ExchangeRate rate = null;
        try {
            CurrencyPair wantedCurrencyPair = new CurrencyPairImpl(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR);
            CurrencyExchangeRateProviderManager  rateProviderManager = exchangeProviderFilterManagerproviderFilter.getProviderReference(rateProviderManagerId);
                 //your exchange rate.
                rate = rateProviderManager.getCurrentExchangeRate(wantedCurrencyPair);

         } catch (CantGetExchangeRateException e) {
            throw new CantGetCurrencyExchangeException(CantGetCurrencyExchangeException.DEFAULT_MESSAGE,e, "", "ExchangeRate error.");
        } catch (UnsupportedCurrencyPairException e) {
            throw new CantGetCurrencyExchangeException(CantGetCurrencyExchangeException.DEFAULT_MESSAGE, e, "", "UnsupportedCurrencyPair error.");

        }catch(Exception e){
                throw new CantGetCurrencyExchangeException(CantGetCurrencyExchangeException.DEFAULT_MESSAGE, e, "", "unknown error.");

            }

            return rate;
        }

        @Override
    public  Collection<CurrencyExchangeRateProviderManager> getExchangeRateProviderManagers() throws CantGetCurrencyExchangeProviderException {
        Collection<CurrencyExchangeRateProviderManager> filteredProviders = null;
        try {
            CurrencyPair wantedCurrencyPair = new CurrencyPairImpl(CryptoCurrency.BITCOIN, FiatCurrency.US_DOLLAR);

            filteredProviders = exchangeProviderFilterManagerproviderFilter.getProviderReferencesFromCurrencyPair(wantedCurrencyPair);

        } catch (CantGetProviderException e) {
            throw new CantGetCurrencyExchangeProviderException(CantGetCurrencyExchangeException.DEFAULT_MESSAGE,e, "", "Provider error.");
        }
        catch (Exception e) {
            throw new CantGetCurrencyExchangeProviderException(CantGetCurrencyExchangeException.DEFAULT_MESSAGE,e, "", "unknown error.");
        }

        return filteredProviders;
    }

    private  String convertTime(long time){
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.US);

        return sdf.format(date);
    }

    @Override
    public List<InstalledWallet> getInstalledWallets() throws CantListWalletsException {
        return walletManagerManager.getInstalledWallets();
    }



    @Override
    public UUID getExchangeProvider() throws CantGetBasicWalletExchangeProviderException {

        try {
            return bitcoinWalletManager.getExchangeProviderId();


        } catch (CantGetExchangeProviderIdException e) {
            throw new CantGetBasicWalletExchangeProviderException(CantGetBasicWalletExchangeProviderException.DEFAULT_MESSAGE, e, "CantGetSettingsException: " + e.toString(), "");

        }
    }

    @Override
    public void setExchangeProvider(UUID idProvider) throws CantSetBasicWalletExchangeProviderException
    {
        try {
            bitcoinWalletManager.saveExchangeProviderIdFile(idProvider);


        } catch (CantSaveExchangeProviderIdException e) {
            throw new CantSetBasicWalletExchangeProviderException(CantSetBasicWalletExchangeProviderException.DEFAULT_MESSAGE, e, "CantGetSettingsException: " + e.toString(), "");

        }

    }

}
