package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.loss_protected_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserActor;


import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * The Class <code>CryptoWalletWalletModuleWalletContact</code>
 * implements the functionality of a CryptoWalletWalletContact.
 * <p/>
 *
 * Created Natalia Cortez on 07/03/2016.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class LossProtectedWalletModuleWalletContact implements Serializable,LossProtectedWalletContact {

    private final String              actorName            ;
    private final String              actorPublicKey       ;
    private final Actors              actorType            ;
    private final Compatibility       compatibility        ;
    private final UUID                contactId            ;
    private final HashMap<BlockchainNetworkType,CryptoAddress> receivedCryptoAddress;
    private final byte[]              profilePicture       ;
    private final String              walletPublicKey      ;
    private boolean                   isConnection  ;

    public LossProtectedWalletModuleWalletContact(final WalletContactRecord walletContactRecord,
                                                 final byte[]              profilePicture     ) {

        this.contactId             = walletContactRecord.getContactId()                    ;
        this.walletPublicKey       = walletContactRecord.getWalletPublicKey()              ;
        this.actorType             = walletContactRecord.getActorType()                    ;
        this.receivedCryptoAddress = walletContactRecord.getCryptoAddresses()              ;
        this.actorPublicKey        = walletContactRecord.getActorPublicKey()               ;
        this.actorName             = walletContactRecord.getActorAlias()                   ;
        this.profilePicture        = profilePicture != null ? profilePicture : null;
        this.compatibility         = walletContactRecord.getCompatibility()                ;
        this.isConnection          = false;
    }


    public LossProtectedWalletModuleWalletContact(final LossProtectedWalletIntraUserActor intraUserConnection, String walletPublicKey) {

        this.contactId             =  UUID.randomUUID()                  ;
        this.walletPublicKey       =  walletPublicKey              ;
        this.actorType             = Actors.CCM_INTRA_WALLET_USER                   ;
        this.receivedCryptoAddress = new  HashMap<BlockchainNetworkType,CryptoAddress>();
        this.actorPublicKey        = intraUserConnection.getPublicKey()               ;
        this.actorName             = intraUserConnection.getAlias()                   ;
        this.profilePicture        = new byte[0];
        this.compatibility         = Compatibility.NONE                ;
        this.isConnection          = true;
    }


    public LossProtectedWalletModuleWalletContact(WalletContactRecord walletContactRecord) {
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
    public  HashMap<BlockchainNetworkType,CryptoAddress> getReceivedCryptoAddress() {
        return receivedCryptoAddress;
    }

    @Override
    public byte[] getProfilePicture() {
        return profilePicture != null ? profilePicture : new byte[0];
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
