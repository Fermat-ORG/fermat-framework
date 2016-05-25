package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;


/**
 * Created by nelson on 11/11/15.
 */
public class CryptoBrokerWalletModuleContractBasicInformation implements ContractBasicInformation, Serializable {
    private static final Random random = new Random(321515131);
    private static final Calendar instance = Calendar.getInstance();
    private static final NumberFormat numberFormat = DecimalFormat.getInstance();

    private String customerAlias;
    private byte[] imageBytes;
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

    public CryptoBrokerWalletModuleContractBasicInformation(ActorIdentity customer, String merchandise, String typeOfPayment, String paymentCurrency, ContractStatus status, CustomerBrokerContractSale customerBrokerContractSale, CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) {
        this.customerAlias = customer.getAlias();
        imageBytes = customer.getProfileImage();
        this.merchandise = merchandise;
        this.typeOfPayment = typeOfPayment;
        this.paymentCurrency = paymentCurrency;
        nearExpirationDatetime = false;

        if (customerBrokerSaleNegotiation != null) {
            this.cancellationReason = customerBrokerSaleNegotiation.getCancelReason();
            date = customerBrokerSaleNegotiation.getLastNegotiationUpdateDate();
            negotiationId = customerBrokerSaleNegotiation.getNegotiationId();
            try {
                for (Clause clause : customerBrokerSaleNegotiation.getClauses()) {
                    if (clause.getType() == ClauseType.CUSTOMER_CURRENCY_QUANTITY)
                        amount = Float.valueOf(clause.getValue());
                    else if (clause.getType() == ClauseType.EXCHANGE_RATE)
                        exchangeRateAmount = Float.valueOf(clause.getValue());
                }
            } catch (CantGetListClauseException e) {
                e.printStackTrace();
            }
        } else {
            amount = random.nextFloat() * 100; //Cantidad de mercancia que recibe el customer
            exchangeRateAmount = random.nextFloat(); //tasa de cambio
            this.cancellationReason = ""; //Negotiation del objeto como tal
            date = instance.getTimeInMillis(); //
            negotiationId = UUID.randomUUID(); //Contrato
        }
        if (customerBrokerContractSale != null) {
            this.status = customerBrokerContractSale.getStatus(); //getLastNegotiationUpdateDate del Negotiation
        } else this.status = status;
    }

    public CryptoBrokerWalletModuleContractBasicInformation(ActorIdentity customer, CustomerBrokerSaleNegotiation saleNegotiation) {

        customerAlias = customer.getAlias();
        imageBytes = customer.getProfileImage();
        negotiationId = saleNegotiation.getNegotiationId();
        cancellationReason = saleNegotiation.getCancelReason();
        date = saleNegotiation.getLastNegotiationUpdateDate();
        status = ContractStatus.CANCELLED;
        merchandise = getClauseValue(saleNegotiation, ClauseType.CUSTOMER_CURRENCY);
        typeOfPayment = getClauseValue(saleNegotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);
        paymentCurrency = getClauseValue(saleNegotiation, ClauseType.BROKER_CURRENCY);
        amount = toFloatValue(saleNegotiation, ClauseType.CUSTOMER_CURRENCY_QUANTITY);
        exchangeRateAmount = toFloatValue(saleNegotiation, ClauseType.EXCHANGE_RATE);
    }

    public CryptoBrokerWalletModuleContractBasicInformation(ActorIdentity customer, CustomerBrokerContractSale saleContract, CustomerBrokerSaleNegotiation saleNegotiation) {
        customerAlias = customer.getAlias();
        imageBytes = customer.getProfileImage();
        negotiationId = saleNegotiation.getNegotiationId();
        contractId = saleContract.getContractId();
        cancellationReason = saleNegotiation.getCancelReason();
        date = saleNegotiation.getLastNegotiationUpdateDate();
        status = saleContract.getStatus();
        nearExpirationDatetime = saleContract.getNearExpirationDatetime();
        merchandise = getClauseValue(saleNegotiation, ClauseType.CUSTOMER_CURRENCY);
        typeOfPayment = getClauseValue(saleNegotiation, ClauseType.CUSTOMER_PAYMENT_METHOD);
        paymentCurrency = getClauseValue(saleNegotiation, ClauseType.BROKER_CURRENCY);
        amount = toFloatValue(saleNegotiation, ClauseType.CUSTOMER_CURRENCY_QUANTITY);
        exchangeRateAmount = toFloatValue(saleNegotiation, ClauseType.EXCHANGE_RATE);
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
    public String getCryptoBrokerAlias() {
        return null;
    }

    @Override
    public byte[] getCryptoBrokerImage() {
        return new byte[0];
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
        return this.nearExpirationDatetime;
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

    private String getClauseValue(CustomerBrokerSaleNegotiation saleNegotiation, ClauseType clauseType) {
        try {
            for (Clause clause : saleNegotiation.getClauses())
                if (clause.getType() == clauseType)
                    return clause.getValue();

        } catch (CantGetListClauseException e) {
            return "No value";
        }
        return "No value";
    }

    private float toFloatValue(CustomerBrokerSaleNegotiation saleNegotiation, ClauseType clauseType) {
        final String clauseValue = getClauseValue(saleNegotiation, clauseType);
        try {
            return numberFormat.parse(clauseValue).floatValue();
        } catch (ParseException e) {
            return 0.0f;
        }
    }
}
