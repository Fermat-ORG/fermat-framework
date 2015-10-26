package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletIntraUserActor;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;

import java.util.List;
import java.util.UUID;

/**
 * The Class <code>CryptoWalletWalletModuleWalletContact</code>
 * implements the functionality of a CryptoWalletWalletContact.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/08/2015.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoWalletWalletModuleWalletContact implements CryptoWalletWalletContact {

    private final String              actorName            ;
    private final String              actorPublicKey       ;
    private final Actors              actorType            ;
    private final Compatibility       compatibility        ;
    private final UUID                contactId            ;
    private final List<CryptoAddress> receivedCryptoAddress;
    private final byte[]              profilePicture       ;
    private final String              walletPublicKey      ;
    private boolean                   isConnection  ;

    public CryptoWalletWalletModuleWalletContact(final WalletContactRecord walletContactRecord,
                                                 final byte[]              profilePicture     ) {

        this.contactId             = walletContactRecord.getContactId()                    ;
        this.walletPublicKey       = walletContactRecord.getWalletPublicKey()              ;
        this.actorType             = walletContactRecord.getActorType()                    ;
        this.receivedCryptoAddress = walletContactRecord.getCryptoAddresses()              ;
        this.actorPublicKey        = walletContactRecord.getActorPublicKey()               ;
        this.actorName             = walletContactRecord.getActorAlias()                   ;
        this.profilePicture        = profilePicture != null ? profilePicture.clone() : null;
        this.compatibility         = walletContactRecord.getCompatibility()                ;
        this.isConnection          = true;
    }


    public CryptoWalletWalletModuleWalletContact(final CryptoWalletIntraUserActor intraUserConnection, String walletPublicKey) {

        this.contactId             =  UUID.randomUUID()                  ;
        this.walletPublicKey       =  walletPublicKey              ;
        this.actorType             = Actors.CCM_INTRA_WALLET_USER                   ;
        this.receivedCryptoAddress = null              ;
        this.actorPublicKey        = intraUserConnection.getPublicKey()               ;
        this.actorName             = intraUserConnection.getAlias()                   ;
        this.profilePicture        = intraUserConnection.getProfileImage() != null ? intraUserConnection.getProfileImage().clone() : null;
        this.compatibility         = Compatibility.NONE                ;
        this.isConnection          = false;
    }


    public CryptoWalletWalletModuleWalletContact(WalletContactRecord walletContactRecord) {
        this(walletContactRecord, null);
    }

    @Override
    public String getActorName() {
        return actorName;
    }

    @Override
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    @Override
    public Actors getActorType() {
        return actorType;
    }

    @Override
    public UUID getContactId() {
        return contactId;
    }

    @Override
    public List<CryptoAddress> getReceivedCryptoAddress() {
        return receivedCryptoAddress;
    }

    @Override
    public byte[] getProfilePicture() {
        return profilePicture != null ? profilePicture.clone() : null;
    }

    @Override
    public String getWalletPublicKey() {
        return walletPublicKey;
    }

    @Override
    public Compatibility getCompatibility() {
        return compatibility;
    }

    @Override
    public String toString() {
        return "CryptoWalletWalletModuleWalletContact{" +
                "actorName='" + actorName + '\'' +
                '}';
    }

    @Override
    public boolean isConnection(){
        return this.isConnection;
    }
}
