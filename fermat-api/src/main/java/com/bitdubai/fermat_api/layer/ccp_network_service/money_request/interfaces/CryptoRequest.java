package com.bitdubai.fermat_api.layer.ccp_network_service.money_request.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.ccp_network_service.money_request.enums.CryptoRequestState;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.ccp_network_service.money_request.interfaces.CryptoRequest</code>
 * provides the method to get the information about a request.
 *
 * @author Ezequiel Postan
 */
public interface CryptoRequest {

    /**
     * The method <code>getRequestId</code> gives us the identifier of the request
     *
     * @return the said identifier
     */
    UUID getRequestId();

    /**
     * The method <code>getAddressToSendThePayment</code> gives us the address to send the payment if accepted
     *
     * @return the address
     */
    CryptoAddress getAddressToSendThePayment();

    /**
     * The method <code>getCryptoAmount</code> gives us the amount of crypto to send the payment if accepted
     *
     * @return the amount
     */
    long getCryptoAmount();

    /**
     * The method <code>getSenderPublicKey</code> gives us the public key of the identity that sent
     * the request
     *
     * @return the public key
     */
    String getSenderPublicKey();

    /**
     * The method <code>getReceptorPublicKey</code> gives us the public key of the identity that the
     * request is directed to
     *
     * @return the public key
     */
    String getReceptorPublicKey();

    /**
     * The method <code>getDescription</code> gives us the description of the request i.e. what is the
     * request for.
     *
     * @return the description
     */
    String getDescription();

    /**
     * The methos <code>getRequestState</code> gives us the state of the request
     *
     * @return the state of the request mesage sent by the network service
     */
    public CryptoRequestState getRequestState();
}
