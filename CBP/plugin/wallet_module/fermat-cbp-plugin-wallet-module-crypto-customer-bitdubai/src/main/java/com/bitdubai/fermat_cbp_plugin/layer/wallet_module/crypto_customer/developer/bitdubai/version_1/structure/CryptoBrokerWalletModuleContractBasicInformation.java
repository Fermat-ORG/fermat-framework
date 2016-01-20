package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 * Created by nelson on 11/11/15.
 */
public class CryptoBrokerWalletModuleContractBasicInformation implements ContractBasicInformation {
    private static Random random = new Random(321515131);
    private static Calendar instance = Calendar.getInstance();

    private String customerAlias;
    private byte[] imageBytes;
    private UUID negotiationId;
    private float amount;
    private String merchandise;
    private String typeOfPayment;
    private float exchangeRateAmount;
    private String paymentCurrency;
    private long date;
    private ContractStatus status;
    private String cancellationReason;

    public CryptoBrokerWalletModuleContractBasicInformation(String customerAlias, String merchandise, String typeOfPayment, String paymentCurrency, ContractStatus status, CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation) {
        this.customerAlias = customerAlias;
        this.merchandise = merchandise;
        this.typeOfPayment = typeOfPayment;
        this.paymentCurrency = paymentCurrency;
        if (customerBrokerPurchaseNegotiation != null) {
            this.cancellationReason = customerBrokerPurchaseNegotiation.getCancelReason();
            negotiationId = customerBrokerPurchaseNegotiation.getNegotiationId(); //UUID.randomUUID();
            date = customerBrokerPurchaseNegotiation.getLastNegotiationUpdateDate(); //instance.getTimeInMillis();
            try {
                for (Clause clause : customerBrokerPurchaseNegotiation.getClauses()) {
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY_QUANTITY.getCode()) {
                        amount = Float.valueOf(clause.getValue());
                    }
                    if (clause.getType().getCode() == ClauseType.EXCHANGE_RATE.getCode()) {
                        exchangeRateAmount = Float.valueOf(clause.getValue());
                    }
                }
            } catch (CantGetListClauseException e) {
                e.printStackTrace();
            }
        }
        else{
            this.cancellationReason = "";
            this.amount = 0;
            this.exchangeRateAmount = 0;
            negotiationId = UUID.randomUUID();
            date = instance.getTimeInMillis();
        }
        imageBytes = new byte[0];

        this.status = status;
    }

    @Override
    public String getCryptoCustomerAlias() {
        return customerAlias;
    }

    @Override
    public byte[] getCryptoCustomerImage() {

        return imageBytes;
    }

    @Override
    public UUID getContractId() {
        return negotiationId;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public String getCancellationReason() {
        return cancellationReason;
    }

    @Override
    public String getMerchandise() {
        return merchandise;
    }

    @Override
    public UUID getNegotiationId() {
        //TODO
        return negotiationId;
    }

    @Override
    public String getTypeOfPayment() {
        return typeOfPayment;
    }

    @Override
    public ContractStatus getStatus() {
        return status;
    }

    @Override
    public float getExchangeRateAmount() {
        return exchangeRateAmount;
    }

    @Override
    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    @Override
    public long getLastUpdate() {
        return date;
    }
}
