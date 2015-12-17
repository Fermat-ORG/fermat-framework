package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.AmountToSellStep;

public class AmountToSellStepImp implements AmountToSellStep {


    private String currencyToSell;
    private String currencyToReceive;
    private String amountToSell;
    private String amountToReceive;
    private int stepNumber;
    private String exchangeRateValue;
    private NegotiationStepStatus status;

    public AmountToSellStepImp(int stepNumber, String currencyToSell, String currencyToReceive, String amountToSell, String amountToReceive, String exchangeRateValue) {
        this.currencyToSell = currencyToSell;
        this.currencyToReceive = currencyToReceive;
        this.amountToSell = amountToSell;
        this.amountToReceive = amountToReceive;
        this.stepNumber = stepNumber;
        this.exchangeRateValue = exchangeRateValue;
        status = NegotiationStepStatus.CONFIRM;
    }

    @Override
    public String getCurrencyToSell() {
        return currencyToSell;
    }

    @Override
    public String getCurrencyToReceive() {
        return currencyToReceive;
    }

    @Override
    public String getAmountToSell() {
        return amountToSell;
    }

    @Override
    public String getAmountToReceive() {
        return amountToReceive;
    }

    @Override
    public String getExchangeRateValue() {
        return exchangeRateValue;
    }

    @Override
    public NegotiationStepType getType() {
        return NegotiationStepType.AMOUNT_TO_SALE;
    }

    @Override
    public NegotiationStepStatus getStatus() {
        return status;
    }

    @Override
    public int getStepNumber() {
        return stepNumber;
    }

    public void setAmountToReceive(String amountToReceive) {
        this.amountToReceive = amountToReceive;
    }

    public void setExchangeRateValue(String exchangeRateValue) {
        this.exchangeRateValue = exchangeRateValue;
    }

    public void setAmountToSell(String amountToSell) {
        this.amountToSell = amountToSell;
    }

    public void setStatus(NegotiationStepStatus status) {
        this.status = status;
    }
}
