package com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.ActorAddressBookManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.DealsWithWalletContacts;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.WalletContact;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.WalletContactsManager;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantDeleteWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantUpdateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.NicheWalletTypeCryptoWalletManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_api.layer.pip_user.UserTypes;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.DealsWithExtraUsers;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.ExtraUserManager;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.exceptions.CantCreateExtraUserRegistry;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.DealsWithActorAddressBook;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure.NicheWalletTypeCryptoWalletContactsManager</code>
 * haves all methods for the contacts activity of a bitcoin wallet
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 11/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NicheWalletTypeCryptoWalletContactsManager implements DealsWithErrors, DealsWithExtraUsers, DealsWithActorAddressBook, DealsWithWalletContacts, NicheWalletTypeCryptoWalletManager {

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithExtraUsers Interface member variables.
     */
    ExtraUserManager extraUserManager;

    /**
     * DealsWithActorAddressBook Interface member variable
     */
    private ActorAddressBookManager ActorAddressBook;

    /**
     * DealsWithWalletContacts Interface member variable
     */
    WalletContactsManager walletContactsManager;

    @Override
    public List<WalletContact> listWalletContacts(UUID walletId) throws CantGetAllWalletContactsException {
        return walletContactsManager.listWalletContacts(walletId);
    }

    @Override
    public List<WalletContact> listWalletContactsScrolling(UUID walletId, Integer max, Integer offset) throws CantGetAllWalletContactsException {
        return walletContactsManager.listWalletContactsScrolling(walletId, max, offset);
    }

    @Override
    public WalletContact createWalletContact(CryptoAddress receivedCryptoAddress, String userName, UUID walletId) throws CantCreateWalletContactException {
        WalletContact walletContact;
        try {
            walletContact = walletContactsManager.getWalletContactByNameAndWalletId(userName, walletId);
        } catch (CantGetWalletContactException cantGetWalletContactException) {
            throw new CantCreateWalletContactException(cantGetWalletContactException.getMessage());
        }
        if (walletContact == null) {
            CryptoAddress deliveredCryptoAddress = null; // TODO REQUEST AN ADDRESS TO CRYPTO VAULT
            UserTypes userType = UserTypes.EXTRA_USER;
            User user = null;
            try {
                user = createAndRegisterUser(userType, userName, deliveredCryptoAddress);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateWalletContactException(e.getMessage());
            }

            walletContactsManager.createWalletContact(deliveredCryptoAddress, receivedCryptoAddress, user.getId(), userName, userType, walletId);
        } else {
            throw new CantCreateWalletContactException("Contact already exists.");
        }
        return walletContact;
    }

    private User createAndRegisterUser(UserTypes userType, String userName, CryptoAddress cryptoAddress) throws CantCreateExtraUserRegistry {
        User user;

        try {
            user = extraUserManager.createUser(userName);
            try {
                ActorAddressBook.registerActorCryptoAddress(userType, user.getId(), cryptoAddress);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateExtraUserRegistry();
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateExtraUserRegistry();
        }
        return user;
    }

    @Override
    public void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String userName) throws CantUpdateWalletContactException {
        walletContactsManager.updateWalletContact(contactId, receivedCryptoAddress, userName);
    }

    @Override
    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException {
        walletContactsManager.deleteWalletContact(contactId);
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
     * DealsWithActorAddressBook Interface implementation.
     */
    @Override
    public void setUserAddressBookManager(ActorAddressBookManager actorAddressBook) {
        this.ActorAddressBook = ActorAddressBook;
    }

    /**
     * DealsWithWalletContacts Interface implementation.
     */
    @Override
    public void setWalletContactsManager(WalletContactsManager walletContactsManager) {
        this.walletContactsManager = walletContactsManager;
    }

}
