package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;

import java.util.Arrays;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/01/16.
 */
public class ContractDetail {

    ContractDetailType contractDetailType;
    String currencyTypeCode;
    String currencyCode;
    float currencyAmount;
    ContractStatus contractStatus;
    String cryptoCustomerAlias;
    byte[] cryptoCustomerImage;
    long lastUpdate;
    float exchangeRateAmount;

    public ContractDetail(
            ContractDetailType contractDetailType,
            String currencyType,
            String currencyCode,
            float currencyAmount,
            ContractStatus contractStatus,
            String cryptoCustomerAlias,
            byte[] cryptoCustomerImage,
            long lastUpdate,
            float exchangeRateAmount) {
        this.contractDetailType = contractDetailType;
        this.currencyTypeCode = currencyType;
        this.currencyCode = currencyCode;
        this.currencyAmount = currencyAmount;
        this.contractStatus = contractStatus;
        this.cryptoCustomerAlias = cryptoCustomerAlias;
        this.cryptoCustomerImage = cryptoCustomerImage;
        this.lastUpdate = lastUpdate;
        this.exchangeRateAmount = exchangeRateAmount;
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

    public String getCurrencyType() {
        return currencyTypeCode;
    }

    public void setCurrencyType(String currencyType) {
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
                ", currencyType=" + currencyTypeCode +
                ", currencyCode='" + currencyCode + '\'' +
                ", currencyAmount=" + currencyAmount +
                ", contractStatus=" + contractStatus +
                ", cryptoCustomerAlias='" + cryptoCustomerAlias + '\'' +
                ", cryptoCustomerImage=" + Arrays.toString(cryptoCustomerImage) +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

}
