package com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.exceptions.FailedToRejectTheRequestSaleException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 17/9/15.
 */

public interface RequestCustomerBrokerSaleManager {

    List<RequestCustomerBrokerSale> getRequestSaleSent(String walletPublicKey) throws com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.exceptions.CantGetRequestListException;

    List<RequestCustomerBrokerSale> getReceivedRequestSale(String walletPublicKey) throws com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.exceptions.CantGetRequestListException;

    void sendRequestSale(String senderWalletPublicKey,
                         String requestSenderPublicKey,
                         String requestDestinationPublicKey,
                         String requestDescription,
                         CryptoAddress addressToSendThePayment,
                         long cryptoAmount) throws com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.exceptions.RequestUnexpectedErrorException;

    void rejectRequestSale(UUID requestId) throws FailedToRejectTheRequestSaleException;

    RequestCustomerBrokerSale createRequestCustomerBrokerSale(String senderWalletPublicKey,
                                                              String requestSenderPublicKey,
                                                              String requestDestinationPublicKey,
                                                              String requestDescription,
                                                              CryptoAddress addressToSendThePayment,
                                                              long cryptoAmount) throws com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.exceptions.CantRequestCustomerBrokerSaleException;
}
