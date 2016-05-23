package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.negotiation_details;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.StepItemGetter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;


public class AmountToBuyStepViewHolder extends StepViewHolder
        implements TextWatcher, ExchangeRateStepViewHolder.OnExchangeValueChangeListener {

    private CryptoCustomerWalletModuleManager walletManager;
    private RecyclerView.Adapter adapter;

    private final DecimalFormat decimalFormat;

    private TextView youWillPayTextValue;
    private TextView currencyToBuyTextValue;
    private EditText buyingValue;
    private TextView buyingText;
    private TextView youWillPayText;

    private String sourceAmountToBuy;
    private String actualAmountToBuy;
    private String actualAmountToPay;
    private String exchangeRateValue;
    private String currencyToPay;


    public AmountToBuyStepViewHolder(RecyclerView.Adapter adapter, View itemView, CryptoCustomerWalletModuleManager walletManager) {
        super(itemView, (StepItemGetter) adapter);

        this.adapter = adapter;
        this.walletManager = walletManager;

        decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        buyingText = (TextView) itemView.findViewById(R.id.ccw_buying_text);
        buyingValue = (EditText) itemView.findViewById(R.id.ccw_buying_value);
        currencyToBuyTextValue = (TextView) itemView.findViewById(R.id.ccw_currency_to_buy);
        youWillPayText = (TextView) itemView.findViewById(R.id.ccw_you_will_pay_text);
        youWillPayTextValue = (TextView) itemView.findViewById(R.id.ccw_you_will_pay_text_value);

        buyingValue.addTextChangedListener(this);
    }

    @Override
    public void exchangeValueChanged(String newValue) {
        valuesHasChanged = true;
        exchangeRateValue = newValue;
        actualAmountToPay = calculateBrokerCurrencyAmount(actualAmountToBuy, exchangeRateValue);
        youWillPayTextValue.setText(String.format("%1$s %2$s", actualAmountToPay, currencyToPay));
    }

    @Override
    public void beforeTextChanged(CharSequence text, int start, int count, int after) {
        // DO NOTHING..
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        // DO NOTHING..
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() > 0 && sourceAmountToBuy != null) {

            actualAmountToBuy = buyingValue.getText().toString();

            actualAmountToPay = calculateBrokerCurrencyAmount(actualAmountToBuy, exchangeRateValue);
            youWillPayTextValue.setText(String.format("%1$s %2$s", actualAmountToPay, currencyToPay));

            if (!valuesHasChanged) {
                valuesHasChanged = !actualAmountToBuy.equals(sourceAmountToBuy);
            }
        }
    }

    @Override
    public void setStatus(NegotiationStepStatus clauseStatus) {
        super.setStatus(clauseStatus);

        switch (clauseStatus) {
            case ACCEPTED:
                buyingText.setTextColor(getColor(R.color.description_text_status_accepted));
                buyingValue.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                currencyToBuyTextValue.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                youWillPayText.setTextColor(getColor(R.color.description_text_status_accepted));
                youWillPayTextValue.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                break;
            case CHANGED:
                buyingText.setTextColor(getColor(R.color.description_text_status_changed));
                buyingValue.setTextColor(getColor(R.color.text_value_status_changed));
                currencyToBuyTextValue.setTextColor(getColor(R.color.text_value_status_changed));
                youWillPayText.setTextColor(getColor(R.color.description_text_status_changed));
                youWillPayTextValue.setTextColor(getColor(R.color.text_value_status_changed));
                break;
            case CONFIRM:
                buyingText.setTextColor(getColor(R.color.description_text_status_confirm));
                buyingValue.setTextColor(getColor(R.color.text_value_status_confirm));
                currencyToBuyTextValue.setTextColor(getColor(R.color.text_value_status_confirm));
                youWillPayText.setTextColor(getColor(R.color.description_text_status_confirm));
                youWillPayTextValue.setTextColor(getColor(R.color.text_value_status_confirm));
                break;
        }
    }

    public void bind(int stepNumber, String currencyToSell, String currencyToReceive, String amountToSell, String amountToReceive, String exchangeRateValue) {
        super.bind(stepNumber);

        this.currencyToPay = currencyToReceive;
        actualAmountToBuy = sourceAmountToBuy = amountToSell;
        actualAmountToPay = amountToReceive;
        this.exchangeRateValue = exchangeRateValue;

        currencyToBuyTextValue.setText(currencyToSell);
        youWillPayTextValue.setText(String.format("%1$s %2$s", actualAmountToPay, currencyToReceive));
        buyingValue.setText(amountToSell);
    }

    @Override
    protected void modifyData(NegotiationStepStatus stepStatus) {
        super.modifyData(stepStatus);

        NegotiationStep step = stepItemGetter.getItem(itemPosition);
        // TODO walletManager.modifyNegotiationStepValues(step, stepStatus, actualAmountToBuy, actualAmountToPay, exchangeRateValue);
        adapter.notifyItemChanged(itemPosition);
    }

    private String calculateBrokerCurrencyAmount(String customerCurrencyAmount, String exchangeRateValue) {
        try {
            double amount = decimalFormat.parse(customerCurrencyAmount).doubleValue();
            double exchangeRate = decimalFormat.parse(exchangeRateValue).doubleValue();
            return decimalFormat.format(amount * exchangeRate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "0";
    }
}
