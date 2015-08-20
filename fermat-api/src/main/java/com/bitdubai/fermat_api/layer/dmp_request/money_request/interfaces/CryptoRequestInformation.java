package com.bitdubai.fermat_api.layer.dmp_request.money_request.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_request.money_request.interfaces.CryptoRequestInformation</code>
 * provides the methods to access the information of a crypto request
 */
public interface CryptoRequestInformation {

    /**
     * The method <code>getRequestId</code> returns the identifier of the request
     *
     * @return the identifier of the request
     */
    UUID getRequestId();

    /**
     * The method <code>getSenderWalletPublicKey</code> returns the public key of the wallet sending
     * the request
     *
     * @return the said public key
     */
    String getSenderWalletPublicKey();

    /**
     * The method <code>getReceptorWalletPublicKey</code> returns the public key of the wallet that is
     * destination of the request
     *
     * @return the said public key
     */
    String getReceptorWalletPublicKey();

    /**
     * The method <code>getRequestSenderPublicKey</code> returns the public key of the identity that sent of the request
     *
     * @return the public key
     */
    String getRequestSenderPublicKey();

    /**
     * The method <code>getRequestDestinationPublicKey</code> returns the public key of the identity that sent of the request
     *
     * @return the public key
     */
    String getRequestDestinationPublicKey();

    /**
     * The method <code>getRequestDescription</code> returns the description of the request
     *
     * @return the description of the request
     */
    String getRequestDescription();

    /**
     * The method <code>getAddressToSendThePayment</code> gives us the crypto address to send the
     * payment to if the request is accepted
     *
     * @return the crypto address to send the payment to
     */
    CryptoAddress getAddressToSendThePayment();

    /**
     * The method <code>getCryptoAmount</code> returns the crypto amount associated to the request
     *
     * @return the said amount
     */
    long getCryptoAmount();
}
