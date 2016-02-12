package com.bitdubai.fermat_cbp_plugin.layer.wallet_module.crypto_broker.developer.bitdubai.version_1.structure;


import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ExchangeRateStep;

/**
 * Created by nelson on 11/12/15.
 */
public class ExchangeRateStepImp implements ExchangeRateStep {

    private String currencyToSell;
    private String currencyToBuy;
    private String suggestedExchangeRate;
    private String exchangeRate;
    private int stepNumber;
    private NegotiationStepStatus status;

    public ExchangeRateStepImp(int stepNumber, String currencyToSell, String currencyToBuy, String suggestedExchangeRate, String exchangeRate) {
        this.currencyToSell = currencyToSell;
        this.currencyToBuy = currencyToBuy;
        this.suggestedExchangeRate = suggestedExchangeRate;
        this.exchangeRate = exchangeRate;
        this.stepNumber = stepNumber;
        status = NegotiationStepStatus.CONFIRM;
    }

    public String getCurrencyToSell() {
        return currencyToSell;
    }

    public String getCurrencyToReceive() {
        return currencyToBuy;
    }

    public String getSuggestedExchangeRate() {
        return suggestedExchangeRate;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Override
    public NegotiationStepType getType() {
        return NegotiationStepType.EXCHANGE_RATE;
    }

    @Override
    public NegotiationStepStatus getStatus() {
        return status;
    }

    @Override
    public int getStepNumber() {
        return stepNumber;
    }

    public void setStatus(NegotiationStepStatus status) {
        this.status = status;
    }
}
