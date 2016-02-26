package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractDetailType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;

import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 22/02/2016
 */
public class ContractMerchandiseDeliveryDetail extends ContractDetail {


    ContractDetailType contractDetailType;
    String currencyTypeCode;
    String currencyCode;
    float currencyAmount;
    String cryptoCustomerAlias;
    byte[] cryptoCustomerImage;
    long lastUpdate;
    float exchangeRateAmount;

    public ContractMerchandiseDeliveryDetail(int contractStep, ContractStatus contractStatus, ContractDetailType contractDetailType,
                                             String currencyType, String currencyCode, float currencyAmount, String cryptoCustomerAlias,
                                             byte[] cryptoCustomerImage, long lastUpdate, float exchangeRateAmount, UUID contractId, UUID negotiationId) {

        super(contractStep, contractStatus, contractId, negotiationId);

        this.contractDetailType = contractDetailType;
        this.currencyTypeCode = currencyType;
        this.currencyCode = currencyCode;
        this.currencyAmount = currencyAmount;
        this.cryptoCustomerAlias = cryptoCustomerAlias;
        this.cryptoCustomerImage = cryptoCustomerImage;
        this.lastUpdate = lastUpdate;
        this.exchangeRateAmount = exchangeRateAmount;
    }


    public String getCurrencyTypeCode() {return currencyTypeCode;}
    public void setCurrencyTypeCode(String currencyTypeCode) {this.currencyTypeCode = currencyTypeCode;}



    public float getExchangeRateAmount() {return exchangeRateAmount;}
    public void setExchangeRateAmount(float exchangeRateAmount) {this.exchangeRateAmount = exchangeRateAmount;}


    public ContractDetailType getContractDetailType() {return contractDetailType;}
    public void setContractDetailType(ContractDetailType contractDetailType) {this.contractDetailType = contractDetailType;}


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


    public float getCurrencyAmount() {return currencyAmount;}
    public void setCurrencyAmount(float currencyAmount) {this.currencyAmount = currencyAmount;}


    public String getCryptoCustomerAlias() {return cryptoCustomerAlias;}
    public void setCryptoCustomerAlias(String cryptoCustomerAlias) {this.cryptoCustomerAlias = cryptoCustomerAlias;}


    public byte[] getCryptoCustomerImage() {return cryptoCustomerImage;}
    public void setCryptoCustomerImage(byte[] cryptoCustomerImage) {this.cryptoCustomerImage = cryptoCustomerImage;}


    public long getLastUpdate() {
        return lastUpdate;
    }
    public void setLastUpdate(long lastUpdate) {this.lastUpdate = lastUpdate;}


}
