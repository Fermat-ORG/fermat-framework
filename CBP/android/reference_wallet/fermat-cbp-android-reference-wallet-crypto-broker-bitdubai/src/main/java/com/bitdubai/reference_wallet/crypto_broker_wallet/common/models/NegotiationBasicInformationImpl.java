package com.bitdubai.reference_wallet.crypto_broker_wallet.common.models;

import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.NegotiationBasicInformation;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 * Custom child list item.
 * <p/>
 * This is for demo purposes, although it is recommended having a separate
 * child list item from your parent list item.
 *
 * @author Ryan Brooks
 * @version 1.0
 * @since 5/27/2015
 */
public class NegotiationBasicInformationImpl implements NegotiationBasicInformation {
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
    private String negotiationStatus;


    public NegotiationBasicInformationImpl(String customerAlias, String merchandise, String typeOfPayment, String paymentCurrency, String negotiationStatus) {
        this.customerAlias = customerAlias;
        this.merchandise = merchandise;
        this.typeOfPayment = typeOfPayment;
        this.paymentCurrency = paymentCurrency;

        amount = random.nextFloat() * 100;
        exchangeRateAmount = random.nextFloat();

        imageBytes = new byte[0];
        negotiationId = UUID.randomUUID();

        date = instance.getTimeInMillis();
        this.negotiationStatus = negotiationStatus;
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
    public UUID getNegotiationId() {
        return negotiationId;
    }

    @Override
    public float getAmount() {
        return amount;
    }

    @Override
    public String getMerchandise() {
        return merchandise;
    }

    @Override
    public String getTypeOfPayment() {
        return typeOfPayment;
    }

    @Override
    public String getStatus() {
        return negotiationStatus;
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