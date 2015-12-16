package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.NegotiationDetailsAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;


public class AmountToSellStepViewHolder extends StepViewHolder
        implements TextWatcher, ExchangeRateStepViewHolder.OnExchangeValueChangeListener {

    private final DecimalFormat decimalFormat;

    private TextView youWillReceiveTextValue;
    private TextView currencyToSellTextValue;
    private EditText sellingValue;
    private TextView sellingText;
    private TextView youWillReceiveText;

    private String sourceAmountToSell;
    private String actualAmountToSell;
    private String actualAmountToReceive;
    private String exchangeRateValue;
    private String currencyToReceive;


    public AmountToSellStepViewHolder(NegotiationDetailsAdapter adapter,View itemView) {
        super(itemView, adapter);

        decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        sellingText = (TextView) itemView.findViewById(R.id.cbw_selling_text);
        sellingValue = (EditText) itemView.findViewById(R.id.cbw_selling_value);
        currencyToSellTextValue = (TextView) itemView.findViewById(R.id.cbw_currency_to_sell);
        youWillReceiveText = (TextView) itemView.findViewById(R.id.cbw_you_will_receive_text);
        youWillReceiveTextValue = (TextView) itemView.findViewById(R.id.cbw_you_will_receive_text_value);

        sellingValue.addTextChangedListener(this);
    }

    @Override
    public void exchangeValueChanged(String newValue) {
        valuesHasChanged = true;
        exchangeRateValue = newValue;
        actualAmountToReceive = calculateBrokerCurrencyAmount(actualAmountToSell, exchangeRateValue);
        youWillReceiveTextValue.setText(String.format("%1$s %2$s", actualAmountToReceive, currencyToReceive));
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
        if (editable.length() > 0 && sourceAmountToSell != null) {

            actualAmountToSell = sellingValue.getText().toString();

            actualAmountToReceive = calculateBrokerCurrencyAmount(actualAmountToSell, exchangeRateValue);
            youWillReceiveTextValue.setText(String.format("%1$s %2$s", actualAmountToReceive, currencyToReceive));

            if (!valuesHasChanged) {
                valuesHasChanged = !actualAmountToSell.equals(sourceAmountToSell);
            }
        }
    }

    @Override
    public void setStatus(NegotiationStepStatus clauseStatus) {
        super.setStatus(clauseStatus);

        switch (clauseStatus) {
            case ACCEPTED:
                sellingText.setTextColor(getColor(R.color.description_text_status_accepted));
                sellingValue.setTextColor(getColor(R.color.text_value_status_accepted));
                currencyToSellTextValue.setTextColor(getColor(R.color.text_value_status_accepted));
                youWillReceiveText.setTextColor(getColor(R.color.description_text_status_accepted));
                youWillReceiveTextValue.setTextColor(getColor(R.color.text_value_status_accepted));
                break;
            case CHANGED:
                sellingText.setTextColor(getColor(R.color.description_text_status_changed));
                sellingValue.setTextColor(getColor(R.color.text_value_status_changed));
                currencyToSellTextValue.setTextColor(getColor(R.color.text_value_status_changed));
                youWillReceiveText.setTextColor(getColor(R.color.description_text_status_changed));
                youWillReceiveTextValue.setTextColor(getColor(R.color.text_value_status_changed));
                break;
            case CONFIRM:
                sellingText.setTextColor(getColor(R.color.description_text_status_confirm));
                sellingValue.setTextColor(getColor(R.color.text_value_status_confirm));
                currencyToSellTextValue.setTextColor(getColor(R.color.text_value_status_confirm));
                youWillReceiveText.setTextColor(getColor(R.color.description_text_status_confirm));
                youWillReceiveTextValue.setTextColor(getColor(R.color.text_value_status_confirm));
                break;
        }
    }

    public void bind(int stepNumber, String currencyToSell, String currencyToReceive, String amountToSell, String amountToReceive, String exchangeRateValue) {
        super.bind(stepNumber);

        this.currencyToReceive = currencyToReceive;
        actualAmountToSell = sourceAmountToSell = amountToSell;
        actualAmountToReceive = amountToReceive;
        this.exchangeRateValue = exchangeRateValue;

        currencyToSellTextValue.setText(currencyToSell);
        youWillReceiveTextValue.setText(String.format("%1$s %2$s", actualAmountToReceive, currencyToReceive));
        sellingValue.setText(amountToSell);
    }

    @Override
    protected void modifyData(NegotiationStepStatus stepStatus) {
        super.modifyData(stepStatus);

        NegotiationStep step = adapter.getItem(itemPosition);
        ModuleManager.modifyNegotiationStepValues(step, stepStatus, actualAmountToSell, actualAmountToReceive, exchangeRateValue);
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
