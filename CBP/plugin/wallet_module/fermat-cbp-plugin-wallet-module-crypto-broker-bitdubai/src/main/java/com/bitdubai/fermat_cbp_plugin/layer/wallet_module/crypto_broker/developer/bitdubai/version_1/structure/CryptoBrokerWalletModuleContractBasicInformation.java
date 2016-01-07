package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
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

    public CryptoBrokerWalletModuleContractBasicInformation(String customerAlias, String merchandise, String typeOfPayment, String paymentCurrency, ContractStatus status) {
        this.customerAlias = customerAlias;
        this.merchandise = merchandise;
        this.typeOfPayment = typeOfPayment;
        this.paymentCurrency = paymentCurrency;
        this.cancellationReason = ""; //Negotiation del objeto como tal

        amount = random.nextFloat() * 100; //Cantidad de mercancia que recibe el customer
        exchangeRateAmount = random.nextFloat(); //tasa de cambio

        imageBytes = new byte[0]; //Actor customer
        negotiationId = UUID.randomUUID(); //Contrato

        date = instance.getTimeInMillis(); //
        this.status = status; //getLastNegotiationUpdateDate del Negotiation
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
        return null;
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

    public void setCustomerAlias(String customerAlias) {
        this.customerAlias = customerAlias;
    }

    public void setNegotiationId(UUID negotiationId) {
        this.negotiationId = negotiationId;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setMerchandise(String merchandise) {
        this.merchandise = merchandise;
    }

    public void setTypeOfPayment(String typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }

    public void setExchangeRateAmount(float exchangeRateAmount) {
        this.exchangeRateAmount = exchangeRateAmount;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public void setLastUpdate(long date) {
        this.date = date;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
}
