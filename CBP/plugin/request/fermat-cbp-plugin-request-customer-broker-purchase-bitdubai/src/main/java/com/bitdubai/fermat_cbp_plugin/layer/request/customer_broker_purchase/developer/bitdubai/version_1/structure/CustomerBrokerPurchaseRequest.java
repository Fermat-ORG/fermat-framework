package com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_purchase.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.RequestStatus;
import com.bitdubai.fermat_cbp_api.layer.request.customer_broker_purchase.interfaces.RequestCustomerBrokerPurchase;

import java.util.UUID;

/**
 * Created by angel on 1/10/15.
 */
public class CustomerBrokerPurchaseRequest implements RequestCustomerBrokerPurchase {

    private UUID requestId;
    private String requestSenderPublicKey;
    private String requestDestinationPublicKey;
    private MoneyType merchandiseCurrency;
    private float merchandiseAmount;
    private MoneyType paymentCurrency;
    private RequestStatus requestStatus;

    public CustomerBrokerPurchaseRequest(
            UUID requestId,
            String requestSenderPublicKey,
            String requestDestinationPublicKey,
            MoneyType merchandiseCurrency,
            float merchandiseAmount,
            MoneyType paymentCurrency,
            RequestStatus requestStatus){

        this.requestId = requestId;
        this.requestSenderPublicKey = requestSenderPublicKey;
        this.requestDestinationPublicKey = requestDestinationPublicKey;
        this.merchandiseCurrency = merchandiseCurrency;
        this.merchandiseAmount = merchandiseAmount;
        this.paymentCurrency = paymentCurrency;
        this.requestStatus = requestStatus;
    }

    @Override
    public UUID getRequestId() {
        return requestId;
    }

    @Override
    public String getRequestSenderPublicKey() {
        return requestSenderPublicKey;
    }

    @Override
    public String getRequestDestinationPublicKey() {
        return requestDestinationPublicKey;
    }

    @Override
    public MoneyType getMerchandiseCurrency() {
        return merchandiseCurrency;
    }

    @Override
    public float getMerchandiseAmount() {
        return merchandiseAmount;
    }

    @Override
    public MoneyType getPaymentCurrency() {
        return paymentCurrency;
    }

    @Override
    public RequestStatus getRequestStatus() {
        return requestStatus;
    }
}
