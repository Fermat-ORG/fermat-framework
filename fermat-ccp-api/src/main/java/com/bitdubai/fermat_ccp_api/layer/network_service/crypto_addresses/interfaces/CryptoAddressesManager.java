package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.AddressExchangeRequestState;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantAcceptAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantDenyAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantGetPendingAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantSendAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingAddressExchangeRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>CryptoAddressesManager</code>
 * provide the methods to exchange crypto addresses.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public interface CryptoAddressesManager {

    /**
     * The method <code>sendAddressExchangeRequest</code> sends to an actor a request for a crypto address.
     *
     * @param walletPublicKey         wallet which is sending the request
     * @param cryptoAddressToSend     a crypto address generated that will be sent to the actor
     * @param actorTypeBy             actor type who wants to exchange addresses
     * @param actorTypeTo             actor type with whom wants to exchange addresses
     * @param requesterActorPublicKey the actor public key that is sending the request
     * @param actorToRequestPublicKey the actor with whom we want to exchange addresses
     * @param blockchainNetworkType   network type in which we're working
     *
     * @throws CantSendAddressExchangeRequestException if something goes wrong.
     */
    void sendAddressExchangeRequest(String                walletPublicKey,
                                    CryptoAddress         cryptoAddressToSend,
                                    Actors                actorTypeBy,
                                    Actors                actorTypeTo,
                                    String                requesterActorPublicKey,
                                    String                actorToRequestPublicKey,
                                    BlockchainNetworkType blockchainNetworkType) throws CantSendAddressExchangeRequestException;

    /**
     * The method <code>acceptAddressExchangeRequest</code> is used to accept an address exchange request-
     * If the actor can return a crypto address, he'll send it.
     *
     * @param requestId             the id of the request sent before
     * @param cryptoAddressReceived a crypto address that is received
     *
     * @throws CantAcceptAddressExchangeRequestException if something goes wrong.
     */
    void acceptAddressExchangeRequest(UUID          requestId,
                                      CryptoAddress cryptoAddressReceived) throws CantAcceptAddressExchangeRequestException;

    /**
     * The method <code>listPendingRequests</code> return the list of requests
     *
     * @param actorType                   The public key of the actor asking for the pending requests directed to him
     * @param addressExchangeRequestState is the State of the exchange address request that you want to list, if is null, then return all.
     *
     * @return a list a request that can be handled by the actor
     *
     * @throws CantListPendingAddressExchangeRequestsException if something goes wrong.
     */
    List<PendingAddressExchangeRequest> listPendingRequests(Actors                      actorType,
                                                            AddressExchangeRequestState addressExchangeRequestState) throws CantListPendingAddressExchangeRequestsException;

    /**
     * Throw the method <code>getPendingRequest</code>
     *
     * @param requestId identifier of the request.
     * @return an instance of a PendingAddressExchangeRequest
     * @throws CantGetPendingAddressExchangeRequestException
     */
    PendingAddressExchangeRequest getPendingRequest(UUID requestId) throws CantGetPendingAddressExchangeRequestException, PendingRequestNotFoundException;

    /**
     * The method <code>confirmAddressExchangeRequest</code> deletes the finalized requests.
     *
     * @param requestId the id of the request to delete.
     *
     * @throws CantConfirmAddressExchangeRequestException if something goes wrong.
     */
    void confirmAddressExchangeRequest(UUID requestId) throws CantConfirmAddressExchangeRequestException;

    /**
     * The method <code>denyAddressExchangeRequest</code> deny an address exchange request by incompatibility.
     *
     * @param requestId the id of the request to deny.
     *
     * @throws CantDenyAddressExchangeRequestException if something goes wrong.
     */
    void denyAddressExchangeRequest(UUID requestId) throws CantDenyAddressExchangeRequestException;
}
