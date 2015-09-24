package com.bitdubai.fermat_dmp_plugin.layer.wallet_module.crypto_wallet.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;

import java.util.List;
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

    private final String              actorName;
    private final String              actorPublicKey;
    private final Actors              actorType;
    private final UUID                contactId;
    private final List<CryptoAddress> receivedCryptoAddress;
    private final byte[]              profilePicture;
    private final String              walletPublicKey;

    public CryptoWalletWalletModuleWalletContact(final WalletContactRecord walletContactRecord,
                                                 final byte[]              profilePicture) {

        this.contactId             = walletContactRecord.getContactId();
        this.walletPublicKey       = walletContactRecord.getWalletPublicKey();
        this.actorType             = walletContactRecord.getActorType();
        this.receivedCryptoAddress = walletContactRecord.getCryptoAddresses();
        this.actorPublicKey        = walletContactRecord.getActorPublicKey();
        this.actorName             = walletContactRecord.getActorAlias();
        this.profilePicture        = profilePicture != null ? profilePicture.clone() : null;
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
}
