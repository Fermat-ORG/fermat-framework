package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetContactProfileImageException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

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
    UUID contactId;

    /**
     * Represent the receivedCryptoAddress
     * address that the system gives to another actor to send this wallet money
     */
    CryptoAddress receivedCryptoAddress;

    /**
     * Represent the id of the wallet
     */
    String walletPublicKey;

    /**
     * Represent the actorId
     */
    UUID actorId;

    /**
     * Represent the actorName
     */
    String actorName;

    /**
     * Represent the actorType
     */
    Actors actorType;

    private byte[] photo = null;

    /**
     * Constructor with parameters
     *
     * @param contactId contact's id
     * @param receivedCryptoAddress contact's cryptoAddress (address + cryptoCurrency)
     * @param actorName actor's id
     */
    public WalletContactsMiddlewareRecord(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) {
        this.contactId = contactId;
        this.receivedCryptoAddress = receivedCryptoAddress;
        this.actorName = actorName;
    }

    /**
     * Constructor with parameters
     *
     * @param actorId actor's id
     * @param receivedCryptoAddress contact's cryptoAddress (address + cryptoCurrency)
     * @param contactId contact's id
     * @param actorName actor's id
     * @param actorType actor's type
     * @param walletPublicKey wallet's public Key
     */
    public WalletContactsMiddlewareRecord(UUID actorId, String actorName, Actors actorType, UUID contactId, CryptoAddress receivedCryptoAddress, String walletPublicKey) {
        this.actorId = actorId;
        this.actorName = actorName;
        this.actorType = actorType;
        this.contactId = contactId;
        this.receivedCryptoAddress = receivedCryptoAddress;
        this.walletPublicKey = walletPublicKey;
    }

    // TODO: IMPLEMENT THIS METHOD
    @Override
    public void setPhoto(byte[] photo){
        this.photo = photo;
    }

    @Override
    public byte[] getContactProfileImage() {
        return this.photo;
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
     * Return the deliveredCryptoAddress
     *
     * @return CryptoAddress
     */
    @Override
    public CryptoAddress getReceivedCryptoAddress() {
        return receivedCryptoAddress;
    }

    /**
     * Return the actorId
     *
     * @return UUID
     */
    @Override
    public UUID getActorId() {
        return actorId;
    }

    /**
     * Return the actorName
     *
     * @return String
     */
    @Override
    public String getActorName() {
        return actorName;
    }
}