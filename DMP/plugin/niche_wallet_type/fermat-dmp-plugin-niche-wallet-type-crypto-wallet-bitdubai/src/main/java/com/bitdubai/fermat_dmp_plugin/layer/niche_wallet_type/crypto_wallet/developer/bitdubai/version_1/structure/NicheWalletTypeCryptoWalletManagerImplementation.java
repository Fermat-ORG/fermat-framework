package com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantCalculateBalanceException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.BitcoinTransaction;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.DealsWithOutgoingExtraUser;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_extrauser.OutgoingExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.DealsWithWalletContacts;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactsRegistry;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.NicheWalletTypeCryptoWalletManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.DealsWithActorAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.DealsWithWalletAddressBook;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRegistry;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.exceptions.CantCreateOrRegisterActorException;
import com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.exceptions.CantRequestOrRegisterCryptoAddressException;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure.NicheWalletTypeCryptoWalletManagerImplementation</code>
 * haves all methods for the contacts activity of a bitcoin wallet
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NicheWalletTypeCryptoWalletManagerImplementation implements DealsWithActorAddressBook, DealsWithBitcoinWallet, DealsWithCryptoVault, DealsWithErrors, DealsWithExtraUsers, DealsWithOutgoingExtraUser, DealsWithWalletContacts, DealsWithWalletAddressBook, NicheWalletTypeCryptoWalletManager {

    /**
     * DealsWithActorAddressBook Interface member variable
     */
    ActorAddressBookManager actorAddressBookManager;

    /**
     * DealsWithBitcoinWallet Interface member variables.
     */
    BitcoinWalletManager bitcoinWalletManager;

    /**
     * DealsWithCryptoVault Interface member variables.
     */
    CryptoVaultManager cryptoVaultManager;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithExtraUsers Interface member variables.
     */
    ExtraUserManager extraUserManager;

    /**
     * DealsWithOutgoingExtraUser Interface member variables.
     */
    OutgoingExtraUserManager outgoingExtraUserManager;

    /**
     * DealsWithWalletContacts Interface member variable
     */
    WalletContactsManager walletContactsManager;

    /**
     * DealsWithWalletAddressBook Interface member variable
     */
    WalletAddressBookManager walletAddressBookManager;


    @Override
    public List<WalletContactRecord> listWalletContacts(UUID walletId) throws CantGetAllWalletContactsException {
        List<WalletContactRecord> walletContactRecords;
        try {
            WalletContactsRegistry walletContactsRegistry = walletContactsManager.getWalletContactsRegistry();
            walletContactRecords = walletContactsRegistry.listWalletContacts(walletId);
        } catch (Exception e){
            throw new CantGetAllWalletContactsException();
        }
        return walletContactRecords;
    }

    @Override
    public List<WalletContactRecord> listWalletContactsScrolling(UUID walletId, Integer max, Integer offset) throws CantGetAllWalletContactsException {
        List<WalletContactRecord> walletContactRecords;
        try {
            WalletContactsRegistry walletContactsRegistry = walletContactsManager.getWalletContactsRegistry();
            walletContactRecords = walletContactsRegistry.listWalletContactsScrolling(walletId, max, offset);
        } catch (Exception e){
            throw new CantGetAllWalletContactsException();
        }
        return walletContactRecords;
    }

    @Override
    public WalletContactRecord createWalletContact(CryptoAddress receivedCryptoAddress, String actorName, Actors actorType, PlatformWalletType platformWalletType, UUID walletId) throws CantCreateWalletContactException {
        WalletContactRecord walletContactRecord;
        try {
            WalletContactsRegistry walletContactsRegistry = walletContactsManager.getWalletContactsRegistry();
            walletContactRecord = walletContactsRegistry.getWalletContactByNameAndWalletId(actorName, walletId);
        } catch (Exception e){
            throw new CantCreateWalletContactException(e.getMessage());
        }
        if (walletContactRecord == null) {

            CryptoAddress deliveredCryptoAddress;
            UUID actorId;
            try {
                deliveredCryptoAddress = requestAndRegisterCryptoAddress(walletId, platformWalletType);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateWalletContactException(e.getMessage());
            }

            try {
                actorId = createAndRegisterActor(actorName, actorType, deliveredCryptoAddress);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateWalletContactException(e.getMessage());
            }
            try {
                WalletContactsRegistry walletContactsRegistry = walletContactsManager.getWalletContactsRegistry();
                walletContactRecord = walletContactsRegistry.createWalletContact(actorId, actorName, actorType, receivedCryptoAddress, walletId);
            } catch (Exception e) {
                throw new CantCreateWalletContactException(e.getMessage());
            }
        } else {
            throw new CantCreateWalletContactException("Contact already exists.");
        }
        return walletContactRecord;
    }

    @Override
    public CryptoAddress requestAddress(String actorName, Actors actorType, PlatformWalletType platformWalletType, UUID walletId) throws CantRequestCryptoAddressException {
        CryptoAddress deliveredCryptoAddress;
        try {
            deliveredCryptoAddress = requestAndRegisterCryptoAddress(walletId, platformWalletType);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestCryptoAddressException();
        }

        try {
            createAndRegisterActor(actorName, actorType, deliveredCryptoAddress);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestCryptoAddressException();
        }
        return deliveredCryptoAddress;
    }

    private UUID createAndRegisterActor(String actorName, Actors actorType, CryptoAddress cryptoAddress) throws CantCreateOrRegisterActorException {
        UUID actorId;

        try {
            switch (actorType){
                case EXTRA_USER:
                    User user = extraUserManager.createUser(actorName);
                    actorId = user.getId();
                    break;
                default:
                    actorId = null ;
            }

            try {
                ActorAddressBookRegistry actorAddressBookRegistry = actorAddressBookManager.getActorAddressBookRegistry();
                actorAddressBookRegistry.registerActorAddressBook(actorId, actorType, cryptoAddress);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateOrRegisterActorException();
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateOrRegisterActorException();
        }
        return actorId;
    }

    private CryptoAddress requestAndRegisterCryptoAddress(UUID walletId, PlatformWalletType platformWalletType) throws CantRequestOrRegisterCryptoAddressException {
        CryptoAddress cryptoAddress;

        try {
            switch (platformWalletType){
                case BASIC_WALLET_BITCOIN_WALLET:
                    cryptoAddress = cryptoVaultManager.getAddress();
                    break;
                default:
                    cryptoAddress = null ;
            }

            try {
                WalletAddressBookRegistry walletAddressBookRegistry = walletAddressBookManager.getWalletAddressBookRegistry();
                walletAddressBookRegistry.registerWalletCryptoAddressBook(cryptoAddress, platformWalletType, walletId);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantRequestOrRegisterCryptoAddressException();
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantRequestOrRegisterCryptoAddressException();
        }
        return cryptoAddress;
    }

    @Override
    public void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) throws CantUpdateWalletContactException {
        try {
            WalletContactsRegistry walletContactsRegistry = walletContactsManager.getWalletContactsRegistry();
            walletContactsRegistry.updateWalletContact(contactId, receivedCryptoAddress, actorName);
        } catch (Exception e){
            throw new CantUpdateWalletContactException();
        }
    }

    @Override
    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException {
        try {
            WalletContactsRegistry walletContactsRegistry = walletContactsManager.getWalletContactsRegistry();
            walletContactsRegistry.deleteWalletContact(contactId);
        } catch (Exception e){
            throw new CantDeleteWalletContactException();
        }
    }

    @Override
    public WalletContactRecord getWalletContactByContainsLikeAndWalletId(String actorName, UUID walletId) throws CantGetWalletContactException {
        WalletContactRecord walletContactRecord;
        try {
            WalletContactsRegistry walletContactsRegistry = walletContactsManager.getWalletContactsRegistry();
            walletContactRecord = walletContactsRegistry.getWalletContactByNameContainsAndWalletId(actorName, walletId);
        } catch (Exception e){
            throw new CantGetWalletContactException();
        }
        return walletContactRecord;
    }

    @Override
    public long getBalance(UUID walletId) throws CantGetBalanceException {
        BitcoinWallet bitcoinWallet;
        long balance;
        try {
            bitcoinWallet = bitcoinWalletManager.loadWallet(walletId);
        } catch (CantLoadWalletException cantLoadWalletException) {
            throw new CantGetBalanceException();
        }
        try {
            balance = bitcoinWallet.getBalance();
        } catch (CantCalculateBalanceException cantCalculateBalanceException) {
            throw new CantGetBalanceException();
        }
        return balance;
    }

    @Override
    public List<BitcoinTransaction> getTransactions(int max, int offset, UUID walletId) throws CantGetTransactionsException {
        BitcoinWallet bitcoinWallet;
        try {
            bitcoinWallet = bitcoinWalletManager.loadWallet(walletId);
        } catch (CantLoadWalletException cantLoadWalletException) {
            throw new CantGetTransactionsException();
        }

        return bitcoinWallet.getTransactions(max, offset);
    }

    @Override
    public void send(UUID walletID, CryptoAddress destinationAddress, long cryptoAmount) throws CantSendCryptoException {
        outgoingExtraUserManager.getTransactionManager().send(walletID, destinationAddress, cryptoAmount);
    }


    /**
     * DealsWithActorAddressBook Interface implementation.
     */
    @Override
    public void setActorAddressBookManager(ActorAddressBookManager actorAddressBookManager) {
        this.actorAddressBookManager = actorAddressBookManager;
    }

    /**
     * DealsWithBitcoinWallet Interface implementation.
     */
    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }

    /**
     * DealsWithCryptoVault Interface implementation.
     */
    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }

    /**
     * DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /**
     * DealsWithExtraUsers Interface implementation.
     */
    @Override
    public void setExtraUserManager(ExtraUserManager extraUserManager) {
        this.extraUserManager = extraUserManager;
    }

    /**
     * DealsWithOutgoingExtraUser Interface implementation.
     */
    @Override
    public void setOutgoingExtraUserManager(OutgoingExtraUserManager outgoingExtraUserManager) {
        this.outgoingExtraUserManager = outgoingExtraUserManager;
    }

    /**
     * DealsWithWalletContacts Interface implementation.
     */
    @Override
    public void setWalletContactsManager(WalletContactsManager walletContactsManager) {
        this.walletContactsManager = walletContactsManager;
    }

    /**
     * DealsWithWalletAddressBook Interface implementation.
     */
    @Override
    public void setWalletAddressBookManager(WalletAddressBookManager walletAddressBookManager) {
        this.walletAddressBookManager = walletAddressBookManager;
    }
}
