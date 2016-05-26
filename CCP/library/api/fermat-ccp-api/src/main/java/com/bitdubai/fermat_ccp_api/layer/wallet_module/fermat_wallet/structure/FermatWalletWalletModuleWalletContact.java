package com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletIntraUserActor;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletWalletContact;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * The Class <code>FermatWalletWalletModuleWalletContact</code>
 * implements the functionality of a FermatWalletWalletContact.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/08/2015.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatWalletWalletModuleWalletContact implements FermatWalletWalletContact,Serializable{

    private final String              actorName            ;
    private final String              actorPublicKey       ;
    private final Actors              actorType            ;
    private final Compatibility       compatibility        ;
    private final UUID                contactId            ;
    private final HashMap<BlockchainNetworkType,CryptoAddress> receivedCryptoAddress;
    private final byte[]              profilePicture       ;
    private final String              walletPublicKey      ;
    private boolean                   isConnection  ;

    public FermatWalletWalletModuleWalletContact(final WalletContactRecord walletContactRecord,
                                                 final byte[] profilePicture) {

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


    public FermatWalletWalletModuleWalletContact(final FermatWalletIntraUserActor intraUserConnection, String walletPublicKey) {

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


    public FermatWalletWalletModuleWalletContact(WalletContactRecord walletContactRecord) {
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
        return "FermatWalletWalletModuleWalletContact{" +
                "actorName='" + actorName + '\'' +
                '}';
    }

    @Override
    public boolean isConnection(){
        return this.isConnection;
    }
}
