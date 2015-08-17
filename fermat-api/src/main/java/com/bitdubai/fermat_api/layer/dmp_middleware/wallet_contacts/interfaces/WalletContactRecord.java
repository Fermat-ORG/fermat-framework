package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetContactProfileImageException;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.middleware.wallet_contacts.WalletContactRecord</code>
 * indicates the functionality of a WalletContactRecord and its attributes
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 08/06/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface WalletContactRecord {

    /*****************************************************
     * TODO: The actor id will be changed from UUID to an
     *       String representing the public key of the actur
     *       The same will happen with the wallet id
     *       On the other hand, the contact id can stay as
     *       a UUID
     *****************************************************/

    /**
     * The method <code>getContactProfileImage</code> returns the contact profile image
     *
     * @return the profile image
     * @throws CantGetContactProfileImageException
     */
    byte[] getContactProfileImage();

    void setPhoto(byte[] photo);

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

}
