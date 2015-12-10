package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.CryptoAddressDealers;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantAcceptAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantConfirmAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantDenyAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantGetPendingAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantSendAddressExchangeRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.CantListPendingCryptoAddressRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.exceptions.PendingRequestNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>CryptoAddressesManager</code>
 * provide the methods to exchange crypto addresses.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public interface CryptoAddressesManager extends FermatManager {

    /**
     * The method <code>sendAddressExchangeRequest</code> sends to an actor a request for a crypto address.
     *
     * @param walletPublicKey                 wallet which is sending the request
     * @param cryptoCurrency                  type of currency that we¿re requesting.
     * @param identityTypeRequesting          actor type who wants to exchange addresses
     * @param identityTypeResponding          actor type with whom wants to exchange addresses
     * @param identityPublicKeyRequesting     the actor public key that is sending the request
     * @param identityPublicKeyResponding     the actor with whom we want to exchange addresses
     * @param cryptoAddressDealer             who is dealing with the plugin.
     * @param blockchainNetworkType           network type in which we're working
     *
     * @throws CantSendAddressExchangeRequestException if something goes wrong.
     */
    void sendAddressExchangeRequest(String                walletPublicKey            ,
                                    CryptoCurrency        cryptoCurrency             ,
                                    Actors                identityTypeRequesting     ,
                                    Actors                identityTypeResponding     ,
                                    String                identityPublicKeyRequesting,
                                    String                identityPublicKeyResponding,
                                    CryptoAddressDealers  cryptoAddressDealer        ,
                                    BlockchainNetworkType blockchainNetworkType      ) throws CantSendAddressExchangeRequestException;

    /**
     * The method <code>acceptAddressExchangeRequest</code> is used to accept an address exchange request-
     * If the actor can return a crypto address, he'll send it.
     *
     * @param requestId             the id of the request sent before
     * @param cryptoAddressReceived a crypto address that is received
     *
     * @throws CantAcceptAddressExchangeRequestException  if something goes wrong.
     * @throws PendingRequestNotFoundException            if i can't find the pending address exchange request.
     */
    void acceptAddressExchangeRequest(UUID          requestId            ,
                                      CryptoAddress cryptoAddressReceived) throws CantAcceptAddressExchangeRequestException,
                                                                                  PendingRequestNotFoundException          ;

    /**
     * The method <code>listAllPendingRequests</code> return the list of requests waiting for a local action
     *
     * @return a list a request that can be handled by the actor
     *
     * @throws CantListPendingCryptoAddressRequestsException if something goes wrong.
     */
    List<CryptoAddressRequest> listAllPendingRequests() throws CantListPendingCryptoAddressRequestsException;

    /**
     * The method <code>listPendingCryptoAddressRequests</code> return the list of requests waiting for a local action.
     * Just the requests, not acceptance or denial messages.
     *
     * @return a list a request that can be handled by the crypto addresses middleware plugin.
     *
     * @throws CantListPendingCryptoAddressRequestsException if something goes wrong.
     */
    List<CryptoAddressRequest> listPendingCryptoAddressRequests() throws CantListPendingCryptoAddressRequestsException;

    /**
     * Throw the method <code>getPendingRequest</code> brings an unique pending address exchange request by request id
     *
     * @param requestId identifier of the request.
     *
     * @return an instance of a CryptoAddressRequest
     *
     * @throws CantGetPendingAddressExchangeRequestException  if something goes wrong.
     * @throws PendingRequestNotFoundException                if i can't find the pending address exchange request.
     */
    CryptoAddressRequest getPendingRequest(UUID requestId) throws CantGetPendingAddressExchangeRequestException,
                                                                    PendingRequestNotFoundException              ;

    /**
     * The method <code>confirmAddressExchangeRequest</code> deletes the finalized requests.
     *
     * @param requestId the id of the request to delete.
     *
     * @throws CantConfirmAddressExchangeRequestException  if something goes wrong.
     * @throws PendingRequestNotFoundException             if i can't find the pending address exchange request.
     */
    void confirmAddressExchangeRequest(UUID requestId) throws CantConfirmAddressExchangeRequestException,
                                                              PendingRequestNotFoundException           ;

    /**
     * The method <code>denyAddressExchangeRequest</code> deny an address exchange request by incompatibility.
     *
     * @param requestId the id of the request to deny.
     *
     * @throws CantDenyAddressExchangeRequestException   if something goes wrong.
     * @throws PendingRequestNotFoundException           if i can't find the pending address exchange request.
     */
    void denyAddressExchangeRequest(UUID requestId) throws CantDenyAddressExchangeRequestException,
                                                           PendingRequestNotFoundException        ;;
}
