package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 22/02/2016
 */
public class ContractPaymentReceptionDetail extends ContractDetail {

    float paymentAmount;
    String paymentCurrencyCode;
    long paymentDate;

    public ContractPaymentReceptionDetail(int contractStep, ContractStatus contractStatus, UUID contractId, UUID negotiationId,
                                         float paymentAmount, String paymentCurrencyCode, long paymentDeliveryDate) {

        super(contractStep, contractStatus, contractId, negotiationId);

        this.paymentAmount = paymentAmount;
        this.paymentCurrencyCode = paymentCurrencyCode;
        this.paymentDate = paymentDeliveryDate;
    }


    public float getPaymentAmount() {return paymentAmount;}
    public void setPaymentAmount(float paymentAmount) {this.paymentAmount = paymentAmount;}


    public String getPaymentCurrencyCode() {return paymentCurrencyCode;}
    public void setPaymentCurrencyCode(String paymentCurrencyCode) {this.paymentCurrencyCode = paymentCurrencyCode;}


    public long getPaymentDate() {return paymentDate;}
    public void setPaymentDate(long paymentDate) {this.paymentDate = paymentDate;}

}
