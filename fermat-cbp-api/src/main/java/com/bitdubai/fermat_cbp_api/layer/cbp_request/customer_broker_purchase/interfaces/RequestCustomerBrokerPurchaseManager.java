package com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_purchase.exceptions.CantGetRequestListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_purchase.exceptions.CantRequestCustomerBrokerPurchaseException;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_purchase.exceptions.RequestPurchaseRejectFailedException;
import com.bitdubai.fermat_cbp_api.layer.cbp_request.customer_broker_purchase.exceptions.RequestUnexpectedErrorException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 17/9/15.
 */

public interface RequestCustomerBrokerPurchaseManager {

    List<RequestCustomerBrokerPurchase> getRequestPurchaseSent(String walletPublicKey) throws CantGetRequestListException;

    List<RequestCustomerBrokerPurchase> getReceivedRequestPurchase(String walletPublicKey) throws CantGetRequestListException;

    public void sendRequestPurchase(String senderWalletPublicKey,
                                  String requestSenderPublicKey,
                                  String requestDestinationPublicKey,
                                  String requestDescription,
                                  CryptoAddress addressToSendThePayment,
                                  long cryptoAmount) throws RequestUnexpectedErrorException;

    public void rejectRequestPurchase(UUID requestId) throws RequestPurchaseRejectFailedException;

    RequestCustomerBrokerPurchase createRequestCustomerBrokerPurchase(String senderWalletPublicKey,
                                                                      String requestSenderPublicKey,
                                                                      String requestDestinationPublicKey,
                                                                      String requestDescription,
                                                                      CryptoAddress addressToSendThePayment,
                                                                      long cryptoAmount) throws CantRequestCustomerBrokerPurchaseException;
}
