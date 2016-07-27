package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/01/16.
 * Modified by Alejandro Bicelis on 22/02/2016
 */
public class ContractDetail {


    private int contractStep;
    private ContractStatus contractStatus;
    private String contractId;
    private UUID negotiationId;

    private String paymentOrMerchandiseAmount;
    private String paymentOrMerchandiseTypeOfPayment;
    private String paymentOrMerchandiseCurrencyCode;
    private long paymentOrMerchandiseDeliveryDate;

    private MoneyType paymentMethodType;

    public ContractDetail(int contractStep, ContractStatus contractStatus, String contractId, UUID negotiationId,
                          String amount, String typeOfPayment, String currencyCode, long deliveryDate, MoneyType paymentMethodType) {
        this.contractStep = contractStep;
        this.contractStatus = contractStatus;
        this.contractId = contractId;
        this.negotiationId = negotiationId;
        this.paymentOrMerchandiseAmount = amount;
        this.paymentOrMerchandiseTypeOfPayment = typeOfPayment;
        this.paymentOrMerchandiseCurrencyCode = currencyCode;
        this.paymentOrMerchandiseDeliveryDate = deliveryDate;
        this.paymentMethodType = paymentMethodType;
    }

    public int getContractStep() {
        return contractStep;
    }
    //public void setContractStep(int contractStep) {this.contractStep = contractStep;}

    public ContractStatus getContractStatus() {
        return contractStatus;
    }
    //public void setContractStatus(ContractStatus contractStatus) {this.contractStatus = contractStatus;}

    public String getContractId() {
        return contractId;
    }
    //public void setContractId(UUID contractId) {this.contractId = contractId;}

    public UUID getNegotiationId() {
        return negotiationId;
    }
    //public void setNegotiationId(UUID negotiationId) {this.negotiationId = negotiationId;}


    public String getPaymentOrMerchandiseAmount() {
        return paymentOrMerchandiseAmount;
    }
    //public void setPaymentOrMerchandiseAmount(String paymentOrMerchandiseAmount) {this.paymentOrMerchandiseAmount = paymentOrMerchandiseAmount;}


    public String getPaymentOrMerchandiseTypeOfPayment() {
        return paymentOrMerchandiseTypeOfPayment;
    }
    //public void setPaymentOrMerchandiseMoneyType(MoneyType paymentOrMerchandiseTypeOfPayment) {this.paymentOrMerchandiseTypeOfPayment = paymentOrMerchandiseTypeOfPayment;}


    public String getPaymentOrMerchandiseCurrencyCode() {
        return paymentOrMerchandiseCurrencyCode;
    }
    //public void setPaymentOrMerchandiseCurrencyCode(String paymentOrMerchandiseCurrencyCode) {this.paymentOrMerchandiseCurrencyCode = paymentOrMerchandiseCurrencyCode;}


    public long getPaymentOrMerchandiseDeliveryDate() {
        return paymentOrMerchandiseDeliveryDate;
    }
    //public void setPaymentOrMerchandiseDeliveryDate(long paymentOrMerchandiseDeliveryDate) {this.paymentOrMerchandiseDeliveryDate = paymentOrMerchandiseDeliveryDate;}


    public MoneyType getPaymentMethodType() {
        return paymentMethodType;
    }
}
