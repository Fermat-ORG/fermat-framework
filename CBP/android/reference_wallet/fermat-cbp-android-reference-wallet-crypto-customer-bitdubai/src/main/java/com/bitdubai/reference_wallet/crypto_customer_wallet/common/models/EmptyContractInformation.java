package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/01/16.
 */
public class EmptyContractInformation implements ContractBasicInformation {

    private float amount;
    private String cancellationReason;
    private UUID contractId;
    private String cryptoCustomerAlias;
    private byte[] cryptoCustomerImage;
    private float exchangeRateAmount;
    private long lastUpdate;
    private String merchandise;
    private UUID negotiationId;
    private String paymentCurrency;
    private ContractStatus status;
    private String typeOfPayment;

    public EmptyContractInformation(
            float amount,
            String cancellationReason,
            UUID contractId,
            String cryptoCustomerAlias,
            byte[] cryptoCustomerImage,
            float exchangeRateAmount,
            long lastUpdate,
            String merchandise,
            UUID negotiationId,
            String paymentCurrency,
            ContractStatus status,
            String typeOfPayment) {
        this.amount = amount;
        this.cancellationReason = cancellationReason;
        this.contractId = contractId;
        this.cryptoCustomerAlias = cryptoCustomerAlias;
        this.cryptoCustomerImage = cryptoCustomerImage;
        this.exchangeRateAmount = exchangeRateAmount;
        this.lastUpdate = lastUpdate;
        this.merchandise = merchandise;
        this.negotiationId = negotiationId;
        this.paymentCurrency = paymentCurrency;
        this.status = status;
        this.typeOfPayment = typeOfPayment;
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
    public UUID getContractId() {
        return contractId;
    }

    @Override
    public String getCryptoCustomerAlias() {
        return cryptoCustomerAlias;
    }

    @Override
    public byte[] getCryptoCustomerImage() {
        return cryptoCustomerImage;
    }

    @Override
    public float getExchangeRateAmount() {
        return exchangeRateAmount;
    }

    @Override
    public long getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public String getMerchandise() {
        return merchandise;
    }

    @Override
    public UUID getNegotiationId() {
        return negotiationId;
    }

    @Override
    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    @Override
    public ContractStatus getStatus() {
        return status;
    }

    @Override
    public String getTypeOfPayment() {
        return typeOfPayment;
    }
}
