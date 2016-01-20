package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.NegotiationStep;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.NegotiationDetailsAdapter;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class ExchangeRateStepViewHolder extends StepViewHolder implements TextWatcher {
    private final DecimalFormat decimalFormat;
    private final CryptoBrokerWalletManager walletManager;

    private TextView yourExchangeRateValueLeftSide;
    private TextView yourExchangeRateValueRightSide;
    private TextView markerRateReference;
    private TextView exchangeRateReference;
    private EditText yourExchangeRateValue;
    private TextView exchangeRateReferenceText;
    private TextView markerRateReferenceText;
    private TextView yourExchangeRateText;

    private OnExchangeValueChangeListener listener;

    private String sourceValue;
    private String actualValue;


    public ExchangeRateStepViewHolder(NegotiationDetailsAdapter adapter, View itemView, CryptoBrokerWalletManager walletManager) {
        super(itemView, adapter);

        this.walletManager = walletManager;

        decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        exchangeRateReferenceText = (TextView) itemView.findViewById(R.id.cbw_exchange_rate_reference_text);
        exchangeRateReference = (TextView) itemView.findViewById(R.id.cbw_exchange_rate_reference_value);
        markerRateReferenceText = (TextView) itemView.findViewById(R.id.cbw_market_exchange_rate_reference_text);
        markerRateReference = (TextView) itemView.findViewById(R.id.cbw_market_exchange_rate_reference_value);
        yourExchangeRateText = (TextView) itemView.findViewById(R.id.cbw_your_exchange_rate_text);
        yourExchangeRateValueLeftSide = (TextView) itemView.findViewById(R.id.cbw_your_exchange_rate_value_left_side);
        yourExchangeRateValueRightSide = (TextView) itemView.findViewById(R.id.cbw_your_exchange_rate_value_right_side);
        yourExchangeRateValue = (EditText) itemView.findViewById(R.id.cbw_your_exchange_rate_value);
        yourExchangeRateValue.addTextChangedListener(this);
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
        if (editable.length() > 0 && sourceValue != null) {
            actualValue = yourExchangeRateValue.getText().toString();
            valuesHasChanged = !actualValue.equals(sourceValue);
            if (valuesHasChanged) {
                listener.exchangeValueChanged(actualValue);
            }
        }
    }

    @Override
    public void setStatus(NegotiationStepStatus clauseStatus) {
        super.setStatus(clauseStatus);

        switch (clauseStatus) {
            case ACCEPTED:
                exchangeRateReferenceText.setTextColor(getColor(R.color.description_text_status_accepted));
                exchangeRateReference.setTextColor(getColor(R.color.text_value_status_accepted));
                markerRateReferenceText.setTextColor(getColor(R.color.description_text_status_accepted));
                markerRateReference.setTextColor(getColor(R.color.text_value_status_accepted));
                yourExchangeRateText.setTextColor(getColor(R.color.description_text_status_accepted));
                yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.text_value_status_accepted));
                yourExchangeRateValueRightSide.setTextColor(getColor(R.color.text_value_status_accepted));
                yourExchangeRateValue.setTextColor(getColor(R.color.text_value_status_accepted));
                break;
            case CHANGED:
                exchangeRateReferenceText.setTextColor(getColor(R.color.description_text_status_changed));
                exchangeRateReference.setTextColor(getColor(R.color.text_value_status_changed));
                markerRateReferenceText.setTextColor(getColor(R.color.description_text_status_changed));
                markerRateReference.setTextColor(getColor(R.color.text_value_status_changed));
                yourExchangeRateText.setTextColor(getColor(R.color.description_text_status_changed));
                yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.text_value_status_changed));
                yourExchangeRateValueRightSide.setTextColor(getColor(R.color.text_value_status_changed));
                yourExchangeRateValue.setTextColor(getColor(R.color.text_value_status_changed));
                break;
            case CONFIRM:
                exchangeRateReferenceText.setTextColor(getColor(R.color.description_text_status_confirm));
                exchangeRateReference.setTextColor(getColor(R.color.text_value_status_confirm));
                markerRateReferenceText.setTextColor(getColor(R.color.description_text_status_confirm));
                markerRateReference.setTextColor(getColor(R.color.text_value_status_confirm));
                yourExchangeRateText.setTextColor(getColor(R.color.description_text_status_confirm));
                yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.text_value_status_confirm));
                yourExchangeRateValueRightSide.setTextColor(getColor(R.color.text_value_status_confirm));
                yourExchangeRateValue.setTextColor(getColor(R.color.text_value_status_confirm));
                break;
        }
    }

    public void bind(int stepNumber, String currencyToSell, String currencyToReceive, String yourExchangeRate, String suggestedExchangeRate) {
        super.bind(stepNumber);

        double marketRate = 212.48;
        String formattedMarketRate = decimalFormat.format(marketRate);

        exchangeRateReference.setText(String.format("1 %1$s / %2$s %3$s", currencyToSell, suggestedExchangeRate, currencyToReceive));
        markerRateReference.setText(String.format("1 %1$s / %2$s %3$s", currencyToSell, formattedMarketRate, currencyToReceive));
        yourExchangeRateValueLeftSide.setText(String.format("1 %1$s", currencyToSell));
        yourExchangeRateValue.setText(yourExchangeRate);
        yourExchangeRateValueRightSide.setText(String.format("%1$s", currencyToReceive));

        actualValue = sourceValue = yourExchangeRate;
    }

    public void setOnExchangeValueChangeListener(OnExchangeValueChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void modifyData(NegotiationStepStatus stepStatus) {
        super.modifyData(stepStatus);

        NegotiationStep step = adapter.getItem(itemPosition);
        walletManager.modifyNegotiationStepValues(step, stepStatus, actualValue);
        adapter.notifyItemChanged(itemPosition);
    }


    public interface OnExchangeValueChangeListener {
        void exchangeValueChanged(String newValue);
    }
}
