package com.bitdubai.fermat_cbp_plugin.layer.request.customer_broker_sale.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.RequestStatus;
import com.bitdubai.fermat_cbp_api.layer.request.customer_broker_sale.interfaces.RequestCustomerBrokerSale;

import java.util.UUID;

/**
 * Created by angel on 1/10/15.
 */
public class CustomerBrokerSaleRequest implements RequestCustomerBrokerSale {

    private UUID requestId;
    private String requestSenderPublicKey;
    private String requestDestinationPublicKey;
    private CurrencyType merchandiseCurrency;
    private float merchandiseAmount;
    private CurrencyType paymentCurrency;
    private RequestStatus requestStatus;

    public CustomerBrokerSaleRequest(
            UUID requestId,
            String requestSenderPublicKey,
            String requestDestinationPublicKey,
            CurrencyType merchandiseCurrency,
            float merchandiseAmount,
            CurrencyType paymentCurrency,
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
    public CurrencyType getMerchandiseCurrency() {
        return merchandiseCurrency;
    }

    @Override
    public float getMerchandiseAmount() {
        return merchandiseAmount;
    }

    @Override
    public CurrencyType getPaymentCurrency() {
        return paymentCurrency;
    }

    @Override
    public RequestStatus getRequestStatus() {
        return requestStatus;
    }
}
