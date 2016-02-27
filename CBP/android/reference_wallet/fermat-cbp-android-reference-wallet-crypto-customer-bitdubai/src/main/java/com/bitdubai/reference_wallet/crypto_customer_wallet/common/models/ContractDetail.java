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
    private UUID contractId;
    private UUID negotiationId;

    private float paymentOrMerchandiseAmount;
    private MoneyType paymentOrMerchandiseMoneyType;
    private String paymentOrMerchandiseCurrencyCode;
    private long paymentOrMerchandiseDeliveryDate;

    public ContractDetail(int contractStep, ContractStatus contractStatus, UUID contractId, UUID negotiationId,
                          float amount, MoneyType moneyType, String currencyCode, long deliveryDate) {
        this.contractStep = contractStep;
        this.contractStatus = contractStatus;
        this.contractId = contractId;
        this.negotiationId = negotiationId;
        this.paymentOrMerchandiseAmount = amount;
        this.paymentOrMerchandiseMoneyType = moneyType;
        this.paymentOrMerchandiseCurrencyCode = currencyCode;
        this.paymentOrMerchandiseDeliveryDate = deliveryDate;
    }

    public int getContractStep() {return contractStep;}
    //public void setContractStep(int contractStep) {this.contractStep = contractStep;}

    public ContractStatus getContractStatus() {return contractStatus;}
    //public void setContractStatus(ContractStatus contractStatus) {this.contractStatus = contractStatus;}

    public UUID getContractId() {return contractId;}
    //public void setContractId(UUID contractId) {this.contractId = contractId;}

    public UUID getNegotiationId() {return negotiationId;}
    //public void setNegotiationId(UUID negotiationId) {this.negotiationId = negotiationId;}


    public float getPaymentOrMerchandiseAmount() {return paymentOrMerchandiseAmount;}
    //public void setPaymentOrMerchandiseAmount(float paymentOrMerchandiseAmount) {this.paymentOrMerchandiseAmount = paymentOrMerchandiseAmount;}


    public MoneyType getPaymentOrMerchandiseMoneyType() {return paymentOrMerchandiseMoneyType;}
    //public void setPaymentOrMerchandiseMoneyType(MoneyType paymentOrMerchandiseMoneyType) {this.paymentOrMerchandiseMoneyType = paymentOrMerchandiseMoneyType;}


    public String getPaymentOrMerchandiseCurrencyCode() {return paymentOrMerchandiseCurrencyCode;}
    //public void setPaymentOrMerchandiseCurrencyCode(String paymentOrMerchandiseCurrencyCode) {this.paymentOrMerchandiseCurrencyCode = paymentOrMerchandiseCurrencyCode;}


    public long getPaymentOrMerchandiseDeliveryDate() {return paymentOrMerchandiseDeliveryDate;}
    //public void setPaymentOrMerchandiseDeliveryDate(long paymentOrMerchandiseDeliveryDate) {this.paymentOrMerchandiseDeliveryDate = paymentOrMerchandiseDeliveryDate;}


}
