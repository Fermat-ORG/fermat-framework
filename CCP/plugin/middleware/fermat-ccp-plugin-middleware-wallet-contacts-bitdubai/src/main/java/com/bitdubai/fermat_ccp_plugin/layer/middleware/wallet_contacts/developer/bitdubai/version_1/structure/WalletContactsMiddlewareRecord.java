package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>WalletContactsMiddlewareRecord</code>
 * is the implementation of the representation of a Crypto Wallet Contact in the platform
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletContactsMiddlewareRecord implements WalletContactRecord {

    /**
     * Represent the contact id
     */
    private UUID contactId;

    /**
     * Represent the id of the wallet
     */
    private String walletPublicKey;

    /**
     * Represent the actor public key
     */
    private String actorPublicKey;

    /**
     * Represent the actorFirstName
     */
    private String actorFirstName;

    /**
     * Represent the actorFirstName
     */
    private String actorLastName;

    /**
     * Represent the actorFirstName
     */
    private String actorAlias;

    /**
     * Represent the actorType
     */
    private Actors actorType;

    private List<CryptoAddress> cryptoAddresses;

    /**
     * Constructor with parameters
     *
     * @param contactId first key
     * @param actorAlias alias of the actor
     * @param actorFirstName first name of the actor
     * @param actorLastName last name of the actor
     */
    public WalletContactsMiddlewareRecord(UUID contactId,
                                          String actorAlias,
                                          String actorFirstName,
                                          String actorLastName,
                                          List<CryptoAddress> cryptoAddresses) {
        this.contactId = contactId;
        this.actorAlias = actorAlias;
        this.actorFirstName = actorFirstName;
        this.actorLastName = actorLastName;
        this.cryptoAddresses = cryptoAddresses;
    }

    /**
     * Constructor with parameters
     *
     * @param contactId first key
     * @param actorPublicKey actor's public key
     * @param actorAlias alias of the actor
     * @param actorFirstName first name of the actor
     * @param actorLastName last name of the actor
     * @param actorType actor's type
     * @param cryptoAddresses contact's cryptoAddresses (address + cryptoCurrency)
     * @param walletPublicKey wallet's public Key
     */
    public WalletContactsMiddlewareRecord(UUID contactId,
                                          String actorPublicKey,
                                          String actorAlias,
                                          String actorFirstName,
                                          String actorLastName,
                                          Actors actorType,
                                          List<CryptoAddress> cryptoAddresses,
                                          String walletPublicKey) {
        this.contactId = contactId;
        this.actorPublicKey = actorPublicKey;
        this.actorAlias = actorAlias;
        this.actorFirstName = actorFirstName;
        this.actorLastName = actorLastName;
        this.actorType = actorType;
        this.cryptoAddresses = cryptoAddresses;
        this.walletPublicKey = walletPublicKey;
    }

    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public Actors getActorType() {
        return actorType;
    }

    @Override
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    @Override
    public String getActorFirstName() {
        return actorFirstName;
    }

    @Override
    public String getActorAlias() {
        return actorAlias;
    }

    @Override
    public String getActorLastName() {
        return actorLastName;
    }

    @Override
    public List<CryptoAddress> getCryptoAddresses() {
        return cryptoAddresses;
    }

    @Override
    public UUID getContactId() {
        return contactId;
    }
}