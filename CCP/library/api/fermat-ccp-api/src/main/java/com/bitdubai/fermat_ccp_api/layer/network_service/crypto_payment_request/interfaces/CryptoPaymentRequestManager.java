package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantConfirmRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantGetRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformDenialException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformReceptionException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantListPendingRequestsException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantSendRequestException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.RequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformApprovalException;
import com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.exceptions.CantInformRefusalException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_payment_request.interfaces.CryptoPaymentRequestManager</code>
 * provide the methods to negotiate crypto payment requests.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 */
public interface CryptoPaymentRequestManager extends FermatManager {

    /**
     * Throw the method <code>sendCryptoPaymentRequest</code> you can send a crypto payment request to the actor with
     * public key receiverActorPublicKey the actors are always intra-wallet-user of CCP.
     *
     * @param requestId           uuid assigned to the request.
     * @param identityPublicKey   public key of the identity sending the request.
     * @param identityType        type of actor that generates the request,
     * @param actorPublicKey      public key of the actor whom will receive the request.
     * @param actorType           type of actor to whom is generated the request.
     * @param cryptoAddress       crypto address where the identity wants to receive the payment.
     * @param description         text describing the crypto payment request.
     * @param amount              amount of crypto expected.
     * @param startTimeStamp      start time stamp assigned to the request.
     * @param networkType         blockchain network type where we will work.
     *
     * @throws CantSendRequestException if something goes wrong.
     */
    void sendCryptoPaymentRequest(UUID                  requestId        ,
                                  String                identityPublicKey,
                                  Actors                identityType     ,
                                  String                actorPublicKey   ,
                                  Actors                actorType        ,
                                  CryptoAddress         cryptoAddress    ,
                                  String                description      ,
                                  long                  amount           ,
                                  long                  startTimeStamp   ,
                                  BlockchainNetworkType networkType      ,
                                  ReferenceWallet       referenceWallet,
                                  String walletPublicKey,
                                  CryptoCurrency cryptoCurrency) throws CantSendRequestException;

    /**
     * Throw the method <code>informRefusal</code> you can inform the rejection of a request to its requester.
     *
     * @param requestId  uuid identifying the request to reject.
     *
     * @throws CantInformRefusalException     if something goes wrong.
     * @throws RequestNotFoundException       if we can't find the payment request.
     */
    void informRefusal(UUID requestId) throws RequestNotFoundException  ,
                                              CantInformRefusalException;

    /**
     * Throw the method <code>informDenial</code> you can inform the denial of a request to its requester.
     *
     * @param requestId  uuid identifying the request to deny.
     *
     * @throws CantInformDenialException     if something goes wrong.
     * @throws RequestNotFoundException       if we can't find the payment request.
     */
    void informDenial(UUID requestId) throws RequestNotFoundException ,
                                             CantInformDenialException;

    /**
     * Throw the method <code>informApproval</code> you can inform the approval of a request to its requester.
     *
     * @param requestId  uuid identifying the request to approve.
     *
     * @throws CantInformApprovalException   if something goes wrong.
     * @throws RequestNotFoundException      if we can't find the payment request.
     */
    void informApproval(UUID requestId) throws CantInformApprovalException,
                                               RequestNotFoundException   ;

    /**
     * Throw the method <code>informReception</code> you can inform the reception of a request to its requester.
     *
     * @param requestId  uuid identifying the request to reception.
     *
     * @throws CantInformReceptionException  if something goes wrong.
     * @throws RequestNotFoundException      if we can't find the payment request.
     */
    void informReception(UUID requestId) throws CantInformReceptionException,
                                                RequestNotFoundException    ;

    /**
     * Throw the method <code>confirmRequest</code> you can confirm and delete the request.
     * I confirm the request when i receive an approval or a refusal by the counterpart.
     * First i receive the event, and after handle it i confirm the request.
     *
     * @param requestId  uuid identifying the request to confirm.
     *
     * @throws CantConfirmRequestException   if something goes wrong.
     * @throws RequestNotFoundException      if we can't find the payment request.
     */
    void confirmRequest(UUID requestId) throws CantConfirmRequestException,
                                               RequestNotFoundException   ;

    /**
     * Throw the method <code>getRequestById</code> you can get a specific request.
     *
     * @param requestId  uuid identifying the request to bring.
     *
     * @return the specified instance of Crypto Payment Request.
     *
     * @throws CantGetRequestException    if something goes wrong.
     * @throws RequestNotFoundException   if we can't find the payment request.
     */
    CryptoPaymentRequest getRequestById(UUID requestId) throws CantGetRequestException ,
                                                               RequestNotFoundException;

    /**
     * Throw the method <code>listPendingCryptoAddressRequests</code> you can list all the pending crypto payment requests.
     *
     * @return a list of instance of Crypto Payment Requests.
     *
     * @throws CantListPendingRequestsException if something goes wrong.
     */
    List<CryptoPaymentRequest> listPendingRequests() throws CantListPendingRequestsException;

}
