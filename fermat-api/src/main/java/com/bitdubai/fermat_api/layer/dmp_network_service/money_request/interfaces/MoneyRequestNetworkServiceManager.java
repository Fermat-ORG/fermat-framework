package com.bitdubai.fermat_api.layer.dmp_network_service.money_request.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantRejectRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantSendCryptoRequestException;
import com.bitdubai.fermat_api.layer.dmp_network_service.money_request.exceptions.CantSendMoneyRequestException;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_network_service.money_request.interfaces.MoneyRequestNetworkServiceManager</code>
 * provides the methods to send crypto and fiat requests to other users and reject requests from other users.
 */
public interface MoneyRequestNetworkServiceManager {

    /**
     * The method <code>requestCrypto</code> sends a crypto request to another user
     *
     * @param requestId                       The request identifier
     * @param addressToSendThePayment         The address to send the payment if accepted
     * @param cryptoAmount                    The amount of crypto to send
     * @param loggedInIntraUserPublicKey      The public key of the sender of the request
     * @param intraUserToSendRequestPublicKey The public key of the user to send the request to
     * @param description                     A description of the request (what is the request paying?)
     * @throws CantSendCryptoRequestException
     */
    public void requestCrypto(UUID requestId,
                              CryptoAddress addressToSendThePayment,
                              long cryptoAmount,
                              String loggedInIntraUserPublicKey,
                              String intraUserToSendRequestPublicKey,
                              String description) throws CantSendCryptoRequestException;

    /**
     * The method <code>requestMoney</code> sends a money request to another user
     *
     * @param requestSenderPublicKey      The public key of the sender of the request
     * @param requestDestinationPublicKey The public key of the destination of the request
     * @param requestDescription          A description of the request (what is the request paying?)
     * @param addressToSendThePayment     An address to send the payment to if accepted
     * @param fiatCurrency                The fiat currency of the request
     * @param fiatAmount                  The amount of fiat currency to pay
     * @throws CantSendMoneyRequestException
     */
    public void requestMoney(String requestSenderPublicKey,
                                 String requestDestinationPublicKey,
                                 String requestDescription,
                                 CryptoAddress addressToSendThePayment,
                                 FiatCurrency fiatCurrency,
                                 long fiatAmount) throws CantSendMoneyRequestException;

    /**
     * The method <code>rejectRequest</code> notifies the rejection of a payment request
     *
     * @param requestId                            An identifier of the request
     * @param intraUserThatSentTheRequestPublicKey The public key of the user that sent the request
     * @throws CantRejectRequestException
     */
    public void rejectRequest(UUID requestId,
                              String intraUserThatSentTheRequestPublicKey) throws CantRejectRequestException;
}
