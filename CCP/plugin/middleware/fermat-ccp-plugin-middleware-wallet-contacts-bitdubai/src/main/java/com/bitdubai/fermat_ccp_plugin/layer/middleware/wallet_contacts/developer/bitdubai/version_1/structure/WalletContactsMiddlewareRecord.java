package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility;
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

    private String              actorAlias     ;
    private String              actorFirstName ;
    private String              actorLastName  ;
    private String              actorPublicKey ;
    private Actors              actorType      ;
    private Compatibility       compatibility  ;
    private List<CryptoAddress> cryptoAddresses;
    private UUID                contactId      ;

    private String        walletPublicKey;

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

    public WalletContactsMiddlewareRecord(UUID                contactId      ,
                                          String              actorPublicKey ,
                                          String              actorAlias     ,
                                          String              actorFirstName ,
                                          String              actorLastName  ,
                                          Actors              actorType      ,
                                          List<CryptoAddress> cryptoAddresses,
                                          String              walletPublicKey,
                                          Compatibility       compatibility  ) {

        this.contactId       = contactId      ;
        this.actorPublicKey  = actorPublicKey ;
        this.actorAlias      = actorAlias     ;
        this.actorFirstName  = actorFirstName ;
        this.actorLastName   = actorLastName  ;
        this.actorType       = actorType      ;
        this.cryptoAddresses = cryptoAddresses;
        this.walletPublicKey = walletPublicKey;
        this.compatibility   = compatibility  ;
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

    @Override
    public Compatibility getCompatibility() {
        return compatibility;
    }

    @Override
    public String toString() {
        return "WalletContactsMiddlewareRecord{" +
                "actorAlias='" + actorAlias + '\'' +
                ", actorFirstName='" + actorFirstName + '\'' +
                ", actorLastName='" + actorLastName + '\'' +
                ", actorPublicKey='" + actorPublicKey + '\'' +
                ", actorType=" + actorType +
                ", compatibility=" + compatibility +
                ", cryptoAddresses=" + cryptoAddresses +
                ", contactId=" + contactId +
                ", walletPublicKey='" + walletPublicKey + '\'' +
                '}';
    }
}