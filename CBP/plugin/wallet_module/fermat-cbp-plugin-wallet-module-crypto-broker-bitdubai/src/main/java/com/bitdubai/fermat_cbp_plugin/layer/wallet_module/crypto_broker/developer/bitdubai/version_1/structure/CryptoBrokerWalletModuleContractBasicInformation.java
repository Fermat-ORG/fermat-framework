package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.exceptions.CantGetListClauseException;
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

    public CryptoBrokerWalletModuleContractBasicInformation(String customerAlias, String merchandise, String typeOfPayment, String paymentCurrency, ContractStatus status, CustomerBrokerContractSale customerBrokerContractSale, CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation) {
        this.customerAlias = customerAlias;
        this.merchandise = merchandise;
        this.typeOfPayment = typeOfPayment;
        this.paymentCurrency = paymentCurrency;

        if (customerBrokerSaleNegotiation != null) {
            this.cancellationReason = customerBrokerSaleNegotiation.getCancelReason(); //Negotiation del objeto como tal
            date = customerBrokerSaleNegotiation.getLastNegotiationUpdateDate(); //instance.getTimeInMillis(); //
            negotiationId = customerBrokerSaleNegotiation.getNegotiationId(); //UUID.fromString(customerBrokerContractSale.getNegotiatiotId()); //Contrato
            try {
                for (Clause clause : customerBrokerSaleNegotiation.getClauses()) {
                    if (clause.getType().getCode() == ClauseType.CUSTOMER_CURRENCY_QUANTITY.getCode()) {
                        amount = Float.valueOf(clause.getValue());
                    }
                    if (clause.getType().getCode() == ClauseType.EXCHANGE_RATE.getCode()) {
                        exchangeRateAmount = Float.valueOf(clause.getValue());
                    }
                }
            } catch (CantGetListClauseException e) {
                e.printStackTrace();
            }
        }else{
            amount = random.nextFloat() * 100; //Cantidad de mercancia que recibe el customer
            exchangeRateAmount = random.nextFloat(); //tasa de cambio
            this.cancellationReason = ""; //Negotiation del objeto como tal
            date = instance.getTimeInMillis(); //
            negotiationId = UUID.randomUUID(); //Contrato
        }

        imageBytes = new byte[0]; //Actor customer
        if (customerBrokerContractSale != null){
            this.status =  customerBrokerContractSale.getStatus(); //getLastNegotiationUpdateDate del Negotiation
        }else this.status = status;
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
