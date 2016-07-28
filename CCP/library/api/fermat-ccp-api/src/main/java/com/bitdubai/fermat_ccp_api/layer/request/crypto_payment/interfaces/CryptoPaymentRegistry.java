package com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentState;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.enums.CryptoPaymentType;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantApproveCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGetCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantListCryptoPaymentRequestsException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantRejectCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantGenerateCryptoPaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CantUpdateRequestPaymentStateException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.CryptoPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.exceptions.InsufficientFundsException;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_ccp_api.layer.request.crypto_payment.interfaces.CryptoPaymentRegistry</code>
 * provide the methods to manage crypto payment requests.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 30/09/2015.
 */
public interface CryptoPaymentRegistry {

    /**
     * Throw the method <code>generateCryptoPaymentRequest</code> you can generate a crypto payment request and send it
     * to the actor with public key actorPublicKey; now the identities and actors are always intra-wallet-user of CCP.
     *
     * @param walletPublicKey     public key of the wallet sending the request.
     * @param identityPublicKey   public key of the identity sending the request.
     * @param identityType        type of actor that generates the request,
     * @param actorPublicKey      public key of the actor whom will receive the request.
     * @param actorType           type of actor to whom is generated the request.
     * @param cryptoAddress       crypto address where the identity wants to receive the payment.
     * @param description         text describing the crypto payment request.
     * @param amount              amount of crypto expected.
     * @param networkType         blockchain network type where we will work.
     *
     * @throws CantGenerateCryptoPaymentRequestException if something goes wrong.
     */
    void generateCryptoPaymentRequest(String                walletPublicKey  ,
                                      String                identityPublicKey,
                                      Actors                identityType     ,
                                      String                actorPublicKey   ,
                                      Actors                actorType        ,
                                      CryptoAddress         cryptoAddress    ,
                                      String                description      ,
                                      long                  amount           ,
                                      BlockchainNetworkType networkType      ,
                                      ReferenceWallet       referenceWallet,
                                      CryptoCurrency cryptoCurrency) throws CantGenerateCryptoPaymentRequestException;

    /**
     * Throw the method <code>refuseRequest</code> you can refuse a request.
     *
     * @param requestId  uuid identifying the request to reject.
     *
     * @throws CantRejectCryptoPaymentRequestException   if something goes wrong.
     * @throws CryptoPaymentRequestNotFoundException     if we can't find the payment request.
     */
    void refuseRequest(UUID requestId) throws CantRejectCryptoPaymentRequestException,
                                              CryptoPaymentRequestNotFoundException  ;

    /**
     * Throw the method <code>approveRequest</code> you can approve a request and send the specified crypto.
     *
     * @param requestId  uuid identifying the request to approve.
     *
     * @throws CantApproveCryptoPaymentRequestException   if something goes wrong.
     * @throws CryptoPaymentRequestNotFoundException      if we can't find the payment request.
     * @throws InsufficientFundsException                if there's not the enough amount of crypto to complete the request.
     */
    void approveRequest(UUID requestId,long fee, FeeOrigin feeOrigin) throws CantApproveCryptoPaymentRequestException,
                                               CryptoPaymentRequestNotFoundException   ,
            InsufficientFundsException;

    /**
     * Throw the method <code>getRequestById</code> you can get a specific request.
     *
     * @param requestId  uuid identifying the request to bring.
     *
     * @return the specified instance of Crypto Payment Request.
     *
     * @throws CantGetCryptoPaymentRequestException    if something goes wrong.
     * @throws CryptoPaymentRequestNotFoundException   if we can't find the payment request.
     */
    CryptoPayment getRequestById(UUID requestId) throws CantGetCryptoPaymentRequestException ,
                                                        CryptoPaymentRequestNotFoundException;

    /**
     * Throw the method <code>listCryptoPaymentRequests</code> you can get the list of the requests related with a wallet.
     *
     * @param walletPublicKey  public key of the wallet who needs to list the crypto payment requests.
     * @param max              quantity of CryptoPaymentRequests you want to return.
     * @param offset           the point of start in the list that you're trying to bring.
     *
     * @return the list of requests.
     *
     * @throws CantListCryptoPaymentRequestsException if something goes wrong.
     */
    List<CryptoPayment> listCryptoPaymentRequests(String  walletPublicKey,
                                                  Integer max            ,
                                                  Integer offset         ) throws CantListCryptoPaymentRequestsException;

    /**
     * Throw the method <code>listCryptoPaymentRequestsByState</code> you can get the list of the requests related with a wallet,
     * having in count the state passed through parameters.
     *
     * @param walletPublicKey  public key of the wallet who needs to list the crypto payment requests.
     * @param state            element of CryptoPaymentState indicating the state of the requests that you need.
     * @param max              quantity of CryptoPaymentRequests you want to return.
     * @param offset           the point of start in the list that you're trying to bring.
     *
     * @return the list of requests.
     *
     * @throws CantListCryptoPaymentRequestsException if something goes wrong.
     */
    List<CryptoPayment> listCryptoPaymentRequestsByState(String             walletPublicKey,
                                                         CryptoPaymentState state          ,
                                                         Integer            max            ,
                                                         Integer            offset         ) throws CantListCryptoPaymentRequestsException;

    /**
     * Throw the method <code>listCryptoPaymentRequestsByState</code> you can get the list of the requests related with a wallet,
     * having in count the state passed through parameters.
     *
     * @param walletPublicKey  public key of the wallet who needs to list the crypto payment requests.
     * @param type             element of CryptoPaymentType indicating the type of the requests that you need.
     * @param max              quantity of CryptoPaymentRequests you want to return.
     * @param offset           the point of start in the list that you're trying to bring.
     *
     * @return the list of requests.
     *
     * @throws CantListCryptoPaymentRequestsException if something goes wrong.
     */
    List<CryptoPayment> listCryptoPaymentRequestsByType(String            walletPublicKey,
                                                        CryptoPaymentType type           ,
                                                        BlockchainNetworkType blockchainNetworkType,
                                                        Integer           max            ,
                                                        Integer           offset         ) throws CantListCryptoPaymentRequestsException;


    /**
     * Throw the method <code>listCryptoPaymentRequestsByTypeAndState</code> you can get the list of the requests related with a wallet.
     * having in count the type and state passed through parameters.
     *
     * @param walletPublicKey  public key of the wallet who needs to list the crypto payment requests.
     * @param state            element of CryptoPaymentState indicating the state of the requests that you need.
     * @param type             element of CryptoPaymentType indicating the type of the requests that you need.
     * @param max              quantity of CryptoPaymentRequests you want to return.
     * @param offset           the point of start in the list that you're trying to bring.
     *
     * @return the list of requests.
     *
     * @throws CantListCryptoPaymentRequestsException if something goes wrong.
     */
    List<CryptoPayment> listCryptoPaymentRequestsByTypeAndState(String             walletPublicKey,
                                                                CryptoPaymentState state          ,
                                                                CryptoPaymentType  type           ,
                                                                Integer            max            ,
                                                                Integer            offset         ) throws CantListCryptoPaymentRequestsException;


    /**
     * Throw the method <code>acceptIncomingRequest</code> you can update request payment sent state to accepted.
     *
     * @param requestId
     */
    void acceptIncomingRequest(UUID requestId) throws CantUpdateRequestPaymentStateException;

    /**
     * Throw the method <code>revertIncomingRequest</code> you can update request payment sent state error revert.
     *
     * @param requestId
     */

    void revertOutgoingRequest(UUID requestId) throws CantUpdateRequestPaymentStateException;

    /**
     *
     * @param walletPublicKey
     * @param type
     * @param blockchainNetworkType
     * @param max
     * @param offset
     * @return
     * @throws CantListCryptoPaymentRequestsException
     */
    List<CryptoPayment> listCryptoPaymentRequestsByTypeAndNetwork(String walletPublicKey,
                                                                         CryptoPaymentType type           ,
                                                                         BlockchainNetworkType blockchainNetworkType,
                                                                         Integer           max            ,
                                                                         Integer           offset         ) throws CantListCryptoPaymentRequestsException ;

    }
