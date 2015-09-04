package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRecord</code>
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
     * Represent the contactId
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

    private List<CryptoAddress> cryptoAddresses = new ArrayList<>();

    /**
     * Constructor with parameters
     *
     * @param contactId contact's id
     * @param receivedCryptoAddress contact's cryptoAddress (address + cryptoCurrency)
     * @param actorAlias actor's id
     */
    public WalletContactsMiddlewareRecord(UUID contactId, CryptoAddress receivedCryptoAddress, String actorAlias) {
        this.contactId = contactId;
        cryptoAddresses.add(receivedCryptoAddress);
        this.actorAlias = actorAlias;
    }

    /**
     * Constructor with parameters
     *
     * @param actorPublicKey actor's public key
     * @param receivedCryptoAddress contact's cryptoAddress (address + cryptoCurrency)
     * @param contactId contact's id
     * @param actorAlias actor's id
     * @param actorType actor's type
     * @param walletPublicKey wallet's public Key
     */
    public WalletContactsMiddlewareRecord(String actorPublicKey, String actorAlias, Actors actorType, UUID contactId, CryptoAddress receivedCryptoAddress, String walletPublicKey) {
        this.actorPublicKey = actorPublicKey;
        this.actorAlias = actorAlias;
        this.actorType = actorType;
        this.contactId = contactId;
        cryptoAddresses.add(receivedCryptoAddress);
        this.walletPublicKey = walletPublicKey;
    }

    /**
     * Return the contactId
     *
     * @return UUID
     */
    @Override
    public UUID getContactId() {
        return contactId;
    }

    /**
     * Return the walletPublicKey
     *
     * @return String
     */
    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    /**
     * Return the actorType
     *
     * @return Actors
     */
    @Override
    public Actors getActorType() {
        return actorType;
    }

    /**
     * Return the actorId
     *
     * @return UUID
     */
    @Override
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    /**
     * Return the actorName
     *
     * @return String
     */
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
}