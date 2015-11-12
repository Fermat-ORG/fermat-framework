package com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantDeleteCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.WalletContactsRegistry</code>
 * indicates the functionality of a WalletContactsRegistry
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletContactsRegistry {

    /**
     * Through the method <code>createWalletContact</code> you can create a new wallet contact
     *
     * @param actorPublicKey    actor's public key
     * @param actorAlias        actor's alias
     * @param actorFirstName    actor's first name
     * @param actorLastName     actor's last name
     * @param actorType         actor's type
     * @param cryptoAddresses   all crypto address linked to the wallet contact
     * @param walletPublicKey   public key of the wallet to which it belongs
     *
     * @return an instance of the wallet contact created
     *
     * @throws CantCreateWalletContactException if something goes wrong
     */
    WalletContactRecord createWalletContact(String               actorPublicKey,
                                            String               actorAlias,
                                            String               actorFirstName,
                                            String               actorLastName,
                                            Actors               actorType,
                                            List<CryptoAddress>  cryptoAddresses,
                                            String               walletPublicKey) throws CantCreateWalletContactException;

    // create wallet contact without addresses
    WalletContactRecord createWalletContact(String actorPublicKey,
                                            String actorAlias,
                                            String actorFirstName,
                                            String actorLastName,
                                            Actors actorType,
                                            String walletPublicKey) throws CantCreateWalletContactException;

    /**
     * Through the method <code>addCryptoAddressToWalletContact</code> you can add a crypto address to an existent contact.
     *
     * @param contactId      contact id of the actor to whom you want to add a crypto address.
     * @param cryptoAddress  that we want to add.
     *
     * @throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantAddCryptoAddressException if something goes wrong.
     * @throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException if i can't find the wallet contact.
     */
    void addCryptoAddressToWalletContact(UUID          contactId,
                                         CryptoAddress cryptoAddress) throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantAddCryptoAddressException, com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;

    /**
     * Through the method <code>deleteCryptoAddressToWalletContact</code> you can delete a crypto address for an existent contact.
     *
     * @param contactId      contact id of the actor to whom you want to delete the crypto address.
     * @param cryptoAddress  that we want to delete.
     *
     * @throws CantDeleteCryptoAddressException if something goes wrong
     * @throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException if i can't find the wallet contact
     */
    void deleteCryptoAddressToWalletContact(UUID          contactId,
                                            CryptoAddress cryptoAddress) throws CantDeleteCryptoAddressException, com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;

    /**
     * Through the method <code>addCryptoAddressToWalletContact</code> you can add a crypto address to an existent contact.
     *
     * @param actorPublicKey   public key of the actor to whom we need to add the crypto address.
     * @param walletPublicKey  public key of the wallet where we need to add the crypto address.
     * @param cryptoAddress    that we want to add.
     *
     * @throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantAddCryptoAddressException if something goes wrong
     * @throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException if i can't find the wallet contact
     */
    void addCryptoAddressToWalletContact(String        actorPublicKey,
                                         String        walletPublicKey,
                                         CryptoAddress cryptoAddress) throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantAddCryptoAddressException, com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;

    /**
     * Through the method <code>updateWalletContact</code> you can update the modifiable fields of a wallet contact
     *
     * @param contactId         contact id of the actor that you want to update
     * @param actorAlias        alias for the actor
     * @param actorFirstName    first name for the actor
     * @param actorLastName     last name for the actor
     * @param cryptoAddresses   list of crypto addresses for the actor
     *
     * @throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantUpdateWalletContactException if something goes wrong
     */
    void updateWalletContact(UUID                contactId,
                             String              actorAlias,
                             String              actorFirstName,
                             String              actorLastName,
                             List<CryptoAddress> cryptoAddresses) throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantUpdateWalletContactException, com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;

    /**
     * Through the method <code>deleteWalletContact</code> you can delete a wallet contact
     *
     * @param contactId contact id of the actor that you want to delete
     *
     * @throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantDeleteWalletContactException if something goes wrong
     * @throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException if i can't find the wallet contact
     */
    void deleteWalletContact(UUID contactId) throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.CantDeleteWalletContactException, com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;

    /**
     * The method <code>searchWalletContact</code> gives us an interface to manage a search for a particular
     * wallet contact
     *
     * @param walletPublicKey of the wallet whom we're working with
     *
     * @return a searching interface
     */
     WalletContactsSearch searchWalletContact(String walletPublicKey);

    /**
     * Through the method <code>getWalletContactByActorPublicKeyAndWalletPublicKey</code> you can find a wallet contact throw actor and wallet public key
     *
     * @param actorPublicKey    public key of the actor you want to find
     * @param walletPublicKey   public key of the wallet you want to find
     *
     * @return a WalletContactRecord instance
     *
     * @throws CantGetWalletContactException if something goes wrong
     * @throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException if the actor doesn't exist for the wallet
     */
    WalletContactRecord getWalletContactByActorAndWalletPublicKey(String actorPublicKey,
                                                                  String walletPublicKey) throws CantGetWalletContactException, com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;

    /**
     * Through the method <code>getWalletContactByContactId</code> you can find a wallet contact throw his primary key
     *
     * @param contactId wallet contact's primary key
     *
     * @return an instance of the wallet contact
     *
     * @throws CantGetWalletContactException if something goes wrong
     * @throws com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException if i can't find a wallet with the given contact id
     */
    WalletContactRecord getWalletContactByContactId(UUID contactId) throws CantGetWalletContactException, com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;

    WalletContactRecord getWalletContactByAliasAndWalletPublicKey(String actorAlias,
                                                                  String walletPublicKey) throws CantGetWalletContactException, com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException;
}
