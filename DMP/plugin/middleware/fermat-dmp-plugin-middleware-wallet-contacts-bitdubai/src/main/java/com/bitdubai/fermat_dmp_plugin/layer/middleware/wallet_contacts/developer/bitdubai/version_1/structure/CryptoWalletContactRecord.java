package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.CryptoWalletContactRecord</code>
 * is the implementation of the representation of a Crypto Wallet Contact in the platform
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 05/06/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoWalletContactRecord implements WalletContactRecord {

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
    UUID walletId;

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


    /**
     * Constructor with parameters
     *
     * @param contactId contact's id
     * @param receivedCryptoAddress contact's cryptoAddress (address + cryptoCurrency)
     * @param actorName actor's id
     */
    public CryptoWalletContactRecord(UUID contactId, CryptoAddress receivedCryptoAddress, String actorName) {
        this.contactId = contactId;
        this.receivedCryptoAddress = receivedCryptoAddress;
        this.actorName = actorName;
    }

    /**
     * Constructor with parameters
     *
     * @param contactId contact's id
     * @param receivedCryptoAddress contact's cryptoAddress (address + cryptoCurrency)
     * @param actorId actor's id
     * @param actorName actor's id
     * @param actorType actor's type
     * @param walletId wallet's id
     */
    public CryptoWalletContactRecord(UUID actorId, String actorName, Actors actorType, UUID contactId, CryptoAddress receivedCryptoAddress, UUID walletId) {
        this.actorId = actorId;
        this.actorName = actorName;
        this.actorType = actorType;
        this.contactId = contactId;
        this.receivedCryptoAddress = receivedCryptoAddress;
        this.walletId = walletId;

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
     * Return the walletId
     *
     * @return UUID
     */
    @Override
    public UUID getWalletId() {
        return walletId;
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