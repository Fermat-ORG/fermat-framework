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


public class ExchangeRateStepViewHolder extends StepViewHolder implements TextWatcher {
    private final DecimalFormat decimalFormat;
    private final CryptoCustomerWalletModuleManager walletManager;
    private RecyclerView.Adapter adapter;

    private TextView yourExchangeRateValueLeftSide;
    private TextView yourExchangeRateValueRightSide;
    private TextView markerRateReference;
    private EditText yourExchangeRateValue;
    private TextView markerRateReferenceText;
    private TextView yourExchangeRateText;

    private OnExchangeValueChangeListener listener;

    private String sourceValue;
    private String actualValue;


    public ExchangeRateStepViewHolder(RecyclerView.Adapter adapter, View itemView, CryptoCustomerWalletModuleManager walletManager) {
        super(itemView, (StepItemGetter) adapter);

        this.adapter = adapter;
        this.walletManager = walletManager;

        decimalFormat = (DecimalFormat) NumberFormat.getInstance();

        markerRateReferenceText = (TextView) itemView.findViewById(R.id.ccw_market_rate_text);
        markerRateReference = (TextView) itemView.findViewById(R.id.ccw_market_rate_value);
        yourExchangeRateText = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_text);
        yourExchangeRateValueLeftSide = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_value_left_side);
        yourExchangeRateValueRightSide = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_value_right_side);
        yourExchangeRateValue = (EditText) itemView.findViewById(R.id.ccw_exchange_rate_value);
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
                markerRateReferenceText.setTextColor(getColor(R.color.description_text_status_accepted));
                markerRateReference.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                yourExchangeRateText.setTextColor(getColor(R.color.description_text_status_accepted));
                yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                yourExchangeRateValueRightSide.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                yourExchangeRateValue.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                break;
            case CHANGED:
                markerRateReferenceText.setTextColor(getColor(R.color.description_text_status_changed));
                markerRateReference.setTextColor(getColor(R.color.text_value_status_changed));
                yourExchangeRateText.setTextColor(getColor(R.color.description_text_status_changed));
                yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.text_value_status_changed));
                yourExchangeRateValueRightSide.setTextColor(getColor(R.color.text_value_status_changed));
                yourExchangeRateValue.setTextColor(getColor(R.color.text_value_status_changed));
                break;
            case CONFIRM:
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

        NegotiationStep step = stepItemGetter.getItem(itemPosition);
        // TODO walletManager.modifyNegotiationStepValues(step, stepStatus, actualValue);
        adapter.notifyItemChanged(itemPosition);
    }


    public interface OnExchangeValueChangeListener {
        void exchangeValueChanged(String newValue);
    }
}
