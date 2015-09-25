package com.bitdubai.fermat_api.layer.ccp_network_service.crypto_addressees.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.ccp_network_service.crypto_addressees.enums.ContactRequestState;
import com.bitdubai.fermat_api.layer.ccp_network_service.crypto_addressees.exceptions.CantAcceptContactRequestException;
import com.bitdubai.fermat_api.layer.ccp_network_service.crypto_addressees.exceptions.CantConfirmContactRequestException;
import com.bitdubai.fermat_api.layer.ccp_network_service.crypto_addressees.exceptions.CantCreateContactRequestException;
import com.bitdubai.fermat_api.layer.ccp_network_service.crypto_addressees.exceptions.CantDenyContactRequestException;
import com.bitdubai.fermat_api.layer.ccp_network_service.crypto_addressees.exceptions.CantGetPendingContactRequestException;
import com.bitdubai.fermat_api.layer.ccp_network_service.crypto_addressees.exceptions.CantGetPendingContactRequestsListException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.ccp_network_service.crypto_addressees.interfaces.CryptoAddressesManager</code>
 * provide the methods to negotiate contact relationships.
 *
 * Created by Ezequiel Postan
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 14/08/15.
 */
public interface CryptoAddressesManager {

    /**
     * The method <code>createContactRequest</code> sends a contact request to an intra-user actor
     *
     * @param walletPublicKey                wallet which is sending the request
     * @param referenceWallet                type of reference wallet that is sending the request
     * @param cryptoAddressToSend            a cryptoaddress generated that is sended to the actor
     * @param intraUserToContactPublicKey    the intra-user with whom is wanted to be contacts
     * @param requesterIntraUserPublicKey    the intra-user public key that is sending the request
     * @param requesterIntraUserName         the intra-user name that is sending the request
     * @param requesterIntraUserProfileImage the intra-user profile image that is sending the request
     * @throws CantCreateContactRequestException
     */
    void createContactRequest(String walletPublicKey,
                              ReferenceWallet referenceWallet,
                              CryptoAddress cryptoAddressToSend,
                              String intraUserToContactPublicKey,
                              String requesterIntraUserPublicKey,
                              String requesterIntraUserName,
                              String requesterIntraUserProfileImage) throws CantCreateContactRequestException;

    /**
     * The method <code>acceptContactRequest</code> is used to accept a contact request
     *
     * @param requestId                             the id of the request sended before
     * @param walletAcceptingTheRequestPublicKey    wallet which is accepting the request
     * @param referenceWallet                       type of reference wallet which is accepting the request
     * @param cryptoAddressReceived                 a cryptoaddress that is received
     * @param intraUserAcceptingTheRequestPublicKey the intra-user public key that is accepting the request
     * @throws CantAcceptContactRequestException
     */
    void acceptContactRequest(UUID requestId,
                              String walletAcceptingTheRequestPublicKey,
                              ReferenceWallet referenceWallet,
                              CryptoAddress cryptoAddressReceived,
                              String intraUserAcceptingTheRequestPublicKey) throws CantAcceptContactRequestException;

    /**
     * The method <code>denyContactRequest</code> is used to deny a contact request
     *
     * @param requestId the id of the request denied
     * @throws CantDenyContactRequestException
     */
    void denyContactRequest(UUID requestId) throws CantDenyContactRequestException;

    /**
     * The method <code>listPendingRequests</code> return the list of requests
     *
     * @param intraUserLoggedInPublicKey The public key of the intra user asking for the pending requests directed to him
     * @param walletPublicKey            is the public Key of the wallet IN where the user is working
     * @param contactRequestState        is the State of the contact requests that you want to list, if is null, the return all.
     * @return a list a request that can be handled by the intra user
     * @throws CantGetPendingContactRequestsListException
     */
    List<PendingContactRequest> listPendingRequests(String intraUserLoggedInPublicKey,
                                                    String walletPublicKey,
                                                    ContactRequestState contactRequestState) throws CantGetPendingContactRequestsListException;

    /**
     * The method <code>getPendingRequest</code> return the PendingContactRequest that is needed to work with
     *
     * @param requestId the id of the request to retrieve.
     * @return a PendingContactRequest with the request information
     * @throws CantGetPendingContactRequestException
     */
    PendingContactRequest getPendingRequest(UUID requestId) throws CantGetPendingContactRequestException;

    /**
     * The method <code>confirmContactRequest</code> deletes the finalized requests
     *
     * @param requestId the id of the request to delete.
     * @throws CantConfirmContactRequestException
     */
    void confirmContactRequest(UUID requestId) throws CantConfirmContactRequestException;
}
