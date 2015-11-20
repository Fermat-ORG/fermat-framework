package com.bitdubai.fermat_ccm_api.layer.actor.intra_wallet_user.interfaces;


import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;

/**
 * The interface <code>IntraWalletUser</code>
 * provides the methods to consult the information of an Intra Wallet User
 */
public interface IntraWalletUser {

    /**
     * The metho <code>getPublicKey</code> gives us the public key of the represented intra wallet user
     *
     * @return the public key
     */
    String getPublicKey();

    /**
     * The method <code>getName</code> gives us the name of the represented intra wallet user
     *
     * @return the name of the intra user
     */
    String getName();

    /**
     * The method <code>getContactRegistrationDate</code> gives us the date when both intra wallet users
     * exchanged their information and accepted each other as contacts.
     *
     * @return the date
     */
    long getContactRegistrationDate();

    /**
     * The method <coda>getProfileImage</coda> gives us the profile image of the represented intra wallet user
     *
     * @return the image
     */
    byte[] getProfileImage();

    /**
     * The method <code>getContactState</code> gives us the contact state of the represented intra
     * wallet user
     *
     * @return the contact state
     */
    ConnectionState getContactState();

}
