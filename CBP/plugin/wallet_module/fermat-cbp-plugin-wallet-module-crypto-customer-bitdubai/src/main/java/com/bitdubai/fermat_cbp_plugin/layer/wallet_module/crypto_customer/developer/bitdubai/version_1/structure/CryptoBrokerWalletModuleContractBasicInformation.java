package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_customer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by nelson on 11/11/15.
 */
public class CryptoBrokerWalletModuleContractBasicInformation implements ContractBasicInformation, Serializable {

    private static Calendar instance = Calendar.getInstance();

    private String customerAlias;
    private byte[] customerImage;
    private String brokerAlias;
    private byte[] brokerImage;

    private UUID negotiationId;
    private String contractId;
    private float amount;
    private String merchandise;
    private String typeOfPayment;
    private float exchangeRateAmount;
    private String paymentCurrency;
    private long date;
    private ContractStatus status;
    private String cancellationReason;
    private boolean nearExpirationDatetime;

    public CryptoBrokerWalletModuleContractBasicInformation(String customerAlias, byte[] customerImage, String brokerAlias, byte[] brokerImage, String merchandise, String typeOfPayment,
                                                            String paymentCurrency, ContractStatus status, boolean nearExpirationDatetime, CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation,
                                                            String contractId,
                                                            String cancellationReason) {
        this.customerAlias = customerAlias;
        this.customerImage = customerImage;
        this.brokerAlias = brokerAlias;
        this.brokerImage = brokerImage;

        this.contractId = contractId;
        this.merchandise = merchandise;
        this.typeOfPayment = typeOfPayment;
        this.paymentCurrency = paymentCurrency;
        this.amount = 0;
        this.exchangeRateAmount = 0;
        this.nearExpirationDatetime = nearExpirationDatetime;

        if (customerBrokerPurchaseNegotiation != null) {
            this.cancellationReason = cancellationReason;
            negotiationId = customerBrokerPurchaseNegotiation.getNegotiationId(); //UUID.randomUUID();
            date = customerBrokerPurchaseNegotiation.getLastNegotiationUpdateDate(); //instance.getTimeInMillis();
            try {
                for (Clause clause : customerBrokerPurchaseNegotiation.getClauses()) {
                    if (clause.getType() == ClauseType.CUSTOMER_CURRENCY_QUANTITY) {
                        amount = Float.valueOf(clause.getValue().replace(",", ""));
                    }
                    if (clause.getType() == ClauseType.EXCHANGE_RATE) {
                        exchangeRateAmount = Float.valueOf(clause.getValue().replace(",", ""));
                    }
                }
            } catch (CantGetListClauseException e) {
                e.printStackTrace();
            }
        } else {
            this.cancellationReason = "";
            negotiationId = UUID.randomUUID();
            date = instance.getTimeInMillis();
        }

        this.status = status;
    }

    @Override
    public String getCryptoCustomerAlias() {
        return customerAlias;
    }

    @Override
    public byte[] getCryptoCustomerImage() {
        return customerImage;
    }

    @Override
    public String getCryptoBrokerAlias() {
        return brokerAlias;
    }

    @Override
    public byte[] getCryptoBrokerImage() {
        return brokerImage;
    }

    @Override
    public String getContractId() {
        return contractId;
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
    public Boolean getNearExpirationDatetime() {
        return nearExpirationDatetime;
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
