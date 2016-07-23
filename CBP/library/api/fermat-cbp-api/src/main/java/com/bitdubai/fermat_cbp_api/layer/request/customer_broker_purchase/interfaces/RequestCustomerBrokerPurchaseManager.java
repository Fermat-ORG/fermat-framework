package com.bitdubai.fermat_cbp_api.layer.request.customer_broker_purchase.interfaces;

import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.RequestStatus;
import com.bitdubai.fermat_cbp_api.layer.request.customer_broker_purchase.exceptions.CantGetRequestListException;
import com.bitdubai.fermat_cbp_api.layer.request.customer_broker_purchase.exceptions.RequestPurchaseRejectFailedException;
import com.bitdubai.fermat_cbp_api.layer.request.customer_broker_purchase.exceptions.RequestUnexpectedErrorException;

import java.util.List;
import java.util.UUID;

/**
 * Created by angel on 17/9/15.
 */

public interface RequestCustomerBrokerPurchaseManager {

    List<RequestCustomerBrokerPurchase> getRequestPurchaseSent(String requestSenderPublicKey) throws CantGetRequestListException;

    List<RequestCustomerBrokerPurchase> getReceivedRequestPurchase(String requestSenderPublicKey) throws CantGetRequestListException;

    void sendRequestPurchase(
            String requestSenderPublicKey,
            String requestDestinationPublicKey,
            MoneyType merchandiseCurrency,
            float merchandiseAmount,
            MoneyType paymentCurrency,
            RequestStatus requestStatus
    ) throws RequestUnexpectedErrorException;

    void rejectRequestPurchase(UUID requestId) throws RequestPurchaseRejectFailedException;

    RequestCustomerBrokerPurchase createRequestCustomerBrokerPurchase(
            String requestSenderPublicKey,
            String requestDestinationPublicKey,
            MoneyType merchandiseCurrency,
            float merchandiseAmount,
            MoneyType paymentCurrency
    ) throws com.bitdubai.fermat_cbp_api.layer.request.customer_broker_purchase.exceptions.CantRequestCustomerBrokerPurchaseException;
}

