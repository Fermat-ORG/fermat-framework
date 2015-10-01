package com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.enums.ContactRequestState;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress.PendingContactRequest</code>
 * provides the methods to
 */
public interface PendingContactRequest {

    /**
     * @return the request identifier
     */
    UUID getRequestId();

    // SENDER FIELDS

    /**
     *
     * @return wallet which is sending the request
     */
    String getWalletPublicKeyToSend();

    /**
     *
     * @return type of reference wallet that is sending the request
     */
    ReferenceWallet getReferenceWalletToSend();

    /**
     * @return a cryptoaddress generated that is sended to the actor
     */
    CryptoAddress getCryptoAddressToSend();

    /**
     *
     * @return the intra-user public key that is sending the request
     */
    String getRequesterIntraUserPublicKey();

    /**
     *
     * @return the intra-user name that is sending the request
     */
    String getRequesterIntraUserName();

    /**
     *
     * @return the intra-user profile image that is sending the request
     */
    byte[] getRequesterIntraUserProfileImage();

    // Receiver FIELDS

    /**
     *
     * @return wallet which is accepting the request
     */
    String getWalletAcceptingTheRequestPublicKey();

    /**
     *
     * @return type of reference wallet which is accepting the request
     */
    ReferenceWallet getReferenceWalletReceived();

    /**
     *
     * @return a cryptoaddress that is received when the intra-user accepts the request
     */
    CryptoAddress getCryptoAddressReceived();

    /**
     *
     * @return the intra-user public key that is accepting the request
     */
    String getIntraUserAcceptingTheRequestPublicKey();

    /**
     *
     * @return the current state of the contact request
     */
    ContactRequestState getState();
}
