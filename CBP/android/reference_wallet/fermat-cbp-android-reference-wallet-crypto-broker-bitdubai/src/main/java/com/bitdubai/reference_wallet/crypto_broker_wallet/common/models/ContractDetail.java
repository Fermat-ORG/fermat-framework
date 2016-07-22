package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/02/16.
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
    //public void setPaymentOrMerchandiseAmount(float paymentOrMerchandiseAmount) {this.paymentOrMerchandiseAmount = paymentOrMerchandiseAmount;}


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



    /*ContractDetailType contractDetailType;
    String currencyTypeCode;
    String currencyCode;
    float currencyAmount;
    ContractStatus contractStatus;
    String cryptoCustomerAlias;
    byte[] cryptoCustomerImage;
    long lastUpdate;
    float exchangeRateAmount;
    UUID contractId;

    public ContractDetail(
            ContractDetailType contractDetailType,
            String currencyType,
            String currencyCode,
            float currencyAmount,
            ContractStatus contractStatus,
            String cryptoCustomerAlias,
            byte[] cryptoCustomerImage,
            long lastUpdate,
            float exchangeRateAmount,
            UUID contractId) {
        this.contractDetailType = contractDetailType;
        this.currencyTypeCode = currencyType;
        this.currencyCode = currencyCode;
        this.currencyAmount = currencyAmount;
        this.contractStatus = contractStatus;
        this.cryptoCustomerAlias = cryptoCustomerAlias;
        this.cryptoCustomerImage = cryptoCustomerImage;
        this.lastUpdate = lastUpdate;
        this.exchangeRateAmount = exchangeRateAmount;
        this.contractId = contractId;
    }

    public String getCurrencyTypeCode() {
        return currencyTypeCode;
    }

    public void setCurrencyTypeCode(String currencyTypeCode) {
        this.currencyTypeCode = currencyTypeCode;
    }

    public UUID getContractId() {
        return contractId;
    }

    public void setContractId(UUID contractId) {
        this.contractId = contractId;
    }

    public float getExchangeRateAmount() {
        return exchangeRateAmount;
    }

    public void setExchangeRateAmount(float exchangeRateAmount) {
        this.exchangeRateAmount = exchangeRateAmount;
    }

    public ContractDetailType getContractDetailType() {
        return contractDetailType;
    }

    public void setContractDetailType(ContractDetailType contractDetailType) {
        this.contractDetailType = contractDetailType;
    }

    public String getFiatCurrency() {
        return currencyTypeCode;
    }

    public void setFiatCurrency(String currencyType) {
        this.currencyTypeCode = currencyType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public float getCurrencyAmount() {
        return currencyAmount;
    }

    public void setCurrencyAmount(float currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public String getCryptoCustomerAlias() {
        return cryptoCustomerAlias;
    }

    public void setCryptoCustomerAlias(String cryptoCustomerAlias) {
        this.cryptoCustomerAlias = cryptoCustomerAlias;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public byte[] getCryptoCustomerImage() {
        return cryptoCustomerImage;
    }

    public void setCryptoCustomerImage(byte[] cryptoCustomerImage) {
        this.cryptoCustomerImage = cryptoCustomerImage;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "ContractDetail{" +
                "contractDetailType=" + contractDetailType +
                ", currencyTypeCode='" + currencyTypeCode + '\'' +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyAmount=" + currencyAmount +
                ", contractStatus=" + contractStatus +
                ", cryptoCustomerAlias='" + cryptoCustomerAlias + '\'' +
                ", cryptoCustomerImage=" + Arrays.toString(cryptoCustomerImage) +
                ", lastUpdate=" + lastUpdate +
                ", exchangeRateAmount=" + exchangeRateAmount +
                ", contractId=" + contractId +
                '}';
    }*/

}

