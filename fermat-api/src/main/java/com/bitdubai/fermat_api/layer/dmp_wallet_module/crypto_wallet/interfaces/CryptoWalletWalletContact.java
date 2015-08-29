package com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact</code>
 * indicates the functionality of a CryptoWalletWalletContact and its attributes
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/08/2015.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface CryptoWalletWalletContact {

    /**
     * Return the contactId
     *
     * @return UUID
     */
    UUID getContactId();

    /**
     * Return the walletPublicKey
     *
     * @return String
     */
    String getWalletPublicKey();


    /**
     * Return the actorType
     *
     * @return Actors
     */
    Actors getActorType();

    /**
     * Return the deliveredCryptoAddress
     *
     * @return CryptoAddress
     */
    CryptoAddress getReceivedCryptoAddress();

    /**
     * Return the actorId
     *
     * @return UUID
     */
    UUID getActorId();

    /**
     * Return the actorName
     *
     * @return String
     */
    String getActorName();

    /**
     * Return the profilePicture
     *
     * @return byte[]
     */
    byte[] getProfilePicture();

}
