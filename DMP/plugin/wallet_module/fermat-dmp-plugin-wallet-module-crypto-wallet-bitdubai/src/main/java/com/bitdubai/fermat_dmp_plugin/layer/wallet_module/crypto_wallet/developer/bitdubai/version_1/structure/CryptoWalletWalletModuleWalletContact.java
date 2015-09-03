package com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure.CryptoWalletWalletModuleWalletContact</code>
 * implements the functionality of a CryptoWalletWalletContact.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/08/2015.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoWalletWalletModuleWalletContact implements CryptoWalletWalletContact {

    private UUID contactId;

    private String walletPublicKey;

    private Actors actorType;

    private CryptoAddress receivedCryptoAddress;

    private String actorPublicKey;

    private String actorName;

    private byte[] profilePicture;

    public CryptoWalletWalletModuleWalletContact(WalletContactRecord walletContactRecord, byte[] profilePicture) {
        this.contactId = walletContactRecord.getContactId();
        this.walletPublicKey = walletContactRecord.getWalletPublicKey();
        this.actorType = walletContactRecord.getActorType();
        this.receivedCryptoAddress = walletContactRecord.getReceivedCryptoAddress();
        this.actorPublicKey = walletContactRecord.getActorPublicKey();
        this.actorName = walletContactRecord.getActorName();
        this.profilePicture = profilePicture != null ? profilePicture.clone() : null;
    }

    public CryptoWalletWalletModuleWalletContact(WalletContactRecord walletContactRecord) {
        this(walletContactRecord, null);
    }

    @Override
    public UUID getContactId() {
        return contactId;
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
    public CryptoAddress getReceivedCryptoAddress() {
        return receivedCryptoAddress;
    }

    @Override
    public String getActorPublicKey() {
        return actorPublicKey;
    }

    @Override
    public String getActorName() {
        return actorName;
    }

    @Override
    public byte[] getProfilePicture() {
        return profilePicture != null ? profilePicture.clone() : null;
    }
}
