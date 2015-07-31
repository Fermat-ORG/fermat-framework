package com.bitdubai.fermat_api.layer.dmp_request.money_request.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_request.money_request.exceptions.CryptoRequestUnexpectedErrorException;
import com.bitdubai.fermat_api.layer.dmp_request.money_request.exceptions.FailedToRejectTheRequestException;
import com.bitdubai.fermat_api.layer.dmp_request.money_request.exceptions.FiatRequestUnexpectedErrorException;

import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_request.money_request.interfaces.MoneyRequestManager</code>
 * provides the methods to send crypto and fiat requests to other users and reject request from other
 * users
 */
public interface MoneyRequestManager {

    /**
     * The method <code>sendCryptoRequest</code> send a crypto request for payment to the user with
     * public key requestDestinationPublicKey.
     *
     * @param requestSenderPublicKey      The public key of the sender
     * @param requestDestinationPublicKey The public key of the destination of the request
     * @param requestDescription          A text describing the payment request (what is it paying?)
     * @param addressToSendThePayment     The crypto address to send the payment to
     * @param cryptoAmount                The amount of crypto to send
     * @return an identifier of the request
     * @throws CryptoRequestUnexpectedErrorException
     */
    public UUID sendCryptoRequest(String requestSenderPublicKey,
                                  String requestDestinationPublicKey,
                                  String requestDescription,
                                  CryptoAddress addressToSendThePayment,
                                  long cryptoAmount) throws CryptoRequestUnexpectedErrorException;


    /**
     * The method <code>sendMoneyRequest</code> send a money request for payment to the user with
     * public key requestDestinationPublicKey.
     *
     * @param requestSenderPublicKey      The public key of the sender
     * @param requestDestinationPublicKey The public key of the destination of the request
     * @param requestDescription          A text describing the payment request (what is it paying?)
     * @param addressToSendThePayment     The crypto address to send the payment to
     * @param fiatCurrency                The fiat currency of the request
     * @param fiatAmount                  The amount of fiat currency to pay
     * @return an identifier of the request
     * @throws FiatRequestUnexpectedErrorException
     */
    public UUID sendMoneyRequest(String requestSenderPublicKey,
                                 String requestDestinationPublicKey,
                                 String requestDescription,
                                 CryptoAddress addressToSendThePayment,
                                 FiatCurrency fiatCurrency,
                                 long fiatAmount) throws FiatRequestUnexpectedErrorException;


    /**
     * The method <code>rejectRequest</code> informs the rejection of a request to its creator.
     * @param requestId                            The identifier of the request to reject
     * @throws FailedToRejectTheRequestException
     */
    public void rejectRequest(UUID requestId) throws FailedToRejectTheRequestException;
}
