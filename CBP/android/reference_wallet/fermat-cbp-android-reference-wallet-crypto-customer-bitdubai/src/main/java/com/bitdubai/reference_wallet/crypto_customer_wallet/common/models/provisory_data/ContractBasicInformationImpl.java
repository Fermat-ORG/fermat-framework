package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.provisory_data;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;


/**
 * Created by nelson on 11/11/15.
 */
public class ContractBasicInformationImpl implements ContractBasicInformation, Serializable {
    private static Random random = new Random(321515131);
    private static Calendar instance = Calendar.getInstance();

    private String customerAlias;
    private byte[] customerImage;
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
    private Boolean nearExpirationDatetime;
    private String brokerAlias;


    public ContractBasicInformationImpl(String brokerAlias, String merchandise, String typeOfPayment, String paymentCurrency, ContractStatus status, boolean nearExpirationDatetime) {
        this.customerAlias = "Customer 1";

        this.brokerAlias = brokerAlias;
        this.merchandise = merchandise;
        this.typeOfPayment = typeOfPayment;
        this.paymentCurrency = paymentCurrency;
        this.nearExpirationDatetime = nearExpirationDatetime;

        amount = random.nextFloat() * 100; //Cantidad de mercancia que recibe el customer
        exchangeRateAmount = random.nextFloat(); //tasa de cambio
        this.cancellationReason = ""; //Negotiation del objeto como tal
        date = instance.getTimeInMillis(); //
        negotiationId = UUID.randomUUID(); //Negociacion
        contractId = ""; //Contrato

        customerImage = new byte[0]; //Actor customer
        brokerImage = new byte[0];
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
