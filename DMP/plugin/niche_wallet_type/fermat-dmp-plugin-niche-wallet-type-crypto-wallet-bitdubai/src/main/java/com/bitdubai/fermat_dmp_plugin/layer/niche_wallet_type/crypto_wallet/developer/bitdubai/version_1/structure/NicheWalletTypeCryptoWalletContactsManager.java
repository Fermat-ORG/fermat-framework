package com.bitdubai.fermat_dmp_plugin.layer.niche_wallet_type.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.ActorAddressBook;
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
    private ActorAddressBookManager actorAddressBookManager;

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
    public WalletContact createWalletContact(CryptoAddress receivedCryptoAddress, String actorName, Actors actorType, UUID walletId) throws CantCreateWalletContactException {
        WalletContact walletContact;
        try {
            walletContact = walletContactsManager.getWalletContactByNameAndWalletId(actorName, walletId);
        } catch (CantGetWalletContactException cantGetWalletContactException) {
            throw new CantCreateWalletContactException(cantGetWalletContactException.getMessage());
        }
        if (walletContact == null) {
            CryptoAddress deliveredCryptoAddress = null; // TODO REQUEST AN ADDRESS TO CRYPTO VAULT
            UUID actorId = null;
            try {
                actorId = createAndRegisterActor(actorType, actorName, deliveredCryptoAddress);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateWalletContactException(e.getMessage());
            }

            walletContactsManager.createWalletContact(actorId, actorName, actorType, receivedCryptoAddress, walletId);
        } else {
            throw new CantCreateWalletContactException("Contact already exists.");
        }
        return walletContact;
    }

    private UUID createAndRegisterActor(Actors actorType, String actorName, CryptoAddress cryptoAddress) throws CantCreateExtraUserRegistry {
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
                actorAddressBookManager.registerActorAddressBook(actorId, actorType, cryptoAddress);
            } catch (Exception e) {
                errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                throw new CantCreateExtraUserRegistry();
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_NICHE_WALLET_TYPE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
            throw new CantCreateExtraUserRegistry();
        }
        return actorId;
    }

    @Override
    public void updateWalletContact(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) throws CantUpdateWalletContactException {
        walletContactsManager.updateWalletContact(contactId, receivedCryptoAddress, actorName);
    }

    @Override
    public void deleteWalletContact(UUID contactId) throws CantDeleteWalletContactException {
        walletContactsManager.deleteWalletContact(contactId);
    }

    @Override
    public WalletContact getWalletContactByContainsLikeAndWalletId(String actorName, UUID walletId) throws CantGetWalletContactException {
        return walletContactsManager.getWalletContactByNameContainsAndWalletId(actorName, walletId);
    }


    /**
     * DealsWithActorAddressBook Interface implementation.
     */
    @Override
    public void setUserAddressBookManager(ActorAddressBookManager actorAddressBookManager) {
        this.actorAddressBookManager = actorAddressBookManager;
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
     * DealsWithWalletContacts Interface implementation.
     */
    @Override
    public void setWalletContactsManager(WalletContactsManager walletContactsManager) {
        this.walletContactsManager = walletContactsManager;
    }
}
