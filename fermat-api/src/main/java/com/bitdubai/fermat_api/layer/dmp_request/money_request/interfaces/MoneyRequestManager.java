package com.bitdubai.fermat_api.layer.dmp_request.money_request.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_request.money_request.exceptions.CantGetRequestInformationListException;
import com.bitdubai.fermat_api.layer.dmp_request.money_request.exceptions.CryptoRequestUnexpectedErrorException;
import com.bitdubai.fermat_api.layer.dmp_request.money_request.exceptions.FailedToRejectTheRequestException;
import com.bitdubai.fermat_api.layer.dmp_request.money_request.exceptions.FiatRequestUnexpectedErrorException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_api.layer.dmp_request.money_request.interfaces.MoneyRequestManager</code>
 * provides the methods to send crypto and fiat requests to other users and reject request from other
 * users
 */
public interface MoneyRequestManager {

    /**
     * The method <code>getCryptoRequestSent</code> returns the request sent by the wallet identified as parameter
     *
     * @param walletPublicKey The public key of the wallet we are consulting about
     * @return the list of requests
     * @throws CantGetRequestInformationListException
     */
    List<CryptoRequestInformation> getCryptoRequestSent(String walletPublicKey) throws CantGetRequestInformationListException;

    /**
     * The method <code>getReceivedCryptoRequest</code> returns the request sent to the wallet identified as parameter
     *
     * @param walletPublicKey The public key of the wallet we are consulting about
     * @return the list of requests
     * @throws CantGetRequestInformationListException
     */
    List<CryptoRequestInformation> getReceivedCryptoRequest(String walletPublicKey) throws CantGetRequestInformationListException;

    /**
     * The method <code>getMoneyRequestSent</code> returns the request sent by the wallet identified as parameter
     *
     * @param walletPublicKey The public key of the wallet we are consulting about
     * @return the list of requests
     * @throws CantGetRequestInformationListException
     */
    List<MoneyRequestInformation> getMoneyRequestSent(String walletPublicKey) throws CantGetRequestInformationListException;

    /**
     * The method <code>getReceivedMoneyRequest</code> returns the request sent to the wallet identified as parameter
     *
     * @param walletPublicKey The public key of the wallet we are consulting about
     * @return the list of requests
     * @throws CantGetRequestInformationListException
     */
    List<MoneyRequestInformation> getReceivedMoneyRequest(String walletPublicKey) throws CantGetRequestInformationListException;

    /**
     * The method <code>sendCryptoRequest</code> send a crypto request for payment to the user with
     * public key requestDestinationPublicKey.
     *
     * @param senderWalletPublicKey       The public key of the wallet we are sending the request from
     * @param requestSenderPublicKey      The public key of the sender
     * @param requestDestinationPublicKey The public key of the destination of the request
     * @param requestDescription          A text describing the payment request (what is it paying?)
     * @param addressToSendThePayment     The crypto address to send the payment to
     * @param cryptoAmount                The amount of crypto to send
     * @throws CryptoRequestUnexpectedErrorException
     */
    public void sendCryptoRequest(String senderWalletPublicKey,
                                  String requestSenderPublicKey,
                                  String requestDestinationPublicKey,
                                  String requestDescription,
                                  CryptoAddress addressToSendThePayment,
                                  long cryptoAmount) throws CryptoRequestUnexpectedErrorException;


    /**
     * The method <code>sendMoneyRequest</code> send a money request for payment to the user with
     * public key requestDestinationPublicKey.
     *
     * @param senderWalletPublicKey       The public key of the wallet we are sending the request from
     * @param requestSenderPublicKey      The public key of the sender
     * @param requestDestinationPublicKey The public key of the destination of the request
     * @param requestDescription          A text describing the payment request (what is it paying?)
     * @param addressToSendThePayment     The crypto address to send the payment to
     * @param fiatCurrency                The fiat currency of the request
     * @param fiatAmount                  The amount of fiat currency to pay
     * @throws FiatRequestUnexpectedErrorException
     */
    public void sendMoneyRequest(String senderWalletPublicKey,
                                 String requestSenderPublicKey,
                                 String requestDestinationPublicKey,
                                 String requestDescription,
                                 CryptoAddress addressToSendThePayment,
                                 FiatCurrency fiatCurrency,
                                 long fiatAmount) throws FiatRequestUnexpectedErrorException;


    /**
     * The method <code>rejectRequest</code> informs the rejection of a request to its creator.
     *
     * @param requestId  The identifier of the request to reject
     * @throws FailedToRejectTheRequestException
     */
    public void rejectRequest(UUID requestId) throws FailedToRejectTheRequestException;
}
