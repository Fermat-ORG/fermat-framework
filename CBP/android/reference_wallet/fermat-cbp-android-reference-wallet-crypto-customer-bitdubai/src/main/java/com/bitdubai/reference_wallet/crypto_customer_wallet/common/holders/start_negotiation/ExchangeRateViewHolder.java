package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.text.NumberFormat;
import java.util.Map;


/**
 * Created by nelson on 10/01/16.
 */
public class ExchangeRateViewHolder extends ClauseViewHolder implements TextWatcher {
    private TextView markerRateReference;
    private TextView yourExchangeRateValueLeftSide;
    private TextView yourExchangeRateValueRightSide;
    private EditText yourExchangeRateValue;


    public ExchangeRateViewHolder(View itemView) {
        super(itemView);

        try {
            yourExchangeRateValueLeftSide = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_value_left_side);
            yourExchangeRateValueRightSide = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_value_right_side);
            yourExchangeRateValue = (EditText) itemView.findViewById(R.id.ccw_exchange_rate_value);
            markerRateReference = (TextView) itemView.findViewById(R.id.ccw_market_rate_value);
            yourExchangeRateValue.addTextChangedListener(this);
        }catch (Exception ex){
            Log.e("ExchangeRateViewHolder", ex.getMessage(), ex);
        }
    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation negotiationInformation, ClauseInformation clause, int clausePosition) {
        super.bindData(negotiationInformation, clause, clausePosition);

        final Map<ClauseType, ClauseInformation> clauses = negotiationInformation.getClauses();
        final ClauseInformation currencyToBuy = clauses.get(ClauseType.CUSTOMER_CURRENCY);
        final ClauseInformation currencyToPay = clauses.get(ClauseType.BROKER_CURRENCY);

        double marketRate = 212.48; // TODO cambiar por valor que devuelve el proveedor asociado a la wallet para este par de monedas

        String formattedMarketRate = NumberFormat.getInstance().format(marketRate);

        markerRateReference.setText(String.format("1 %1$s / %2$s %3$s", currencyToBuy.getValue(), formattedMarketRate, currencyToPay.getValue()));
        yourExchangeRateValueLeftSide.setText(String.format("1 %1$s /", currencyToBuy.getValue()));
        yourExchangeRateValue.setText(clause.getValue());
        yourExchangeRateValueRightSide.setText(String.format("%1$s", currencyToPay.getValue()));
    }

    @Override
    public void setViewResources(int titleRes, int positionImgRes, int... stringResources) {
        titleTextView.setText(titleRes);
        clauseNumberImageView.setImageResource(positionImgRes);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        final Editable text = yourExchangeRateValue.getText();
        listener.onClauseValueChanged(yourExchangeRateValue, clause, text.toString(), clausePosition);
    }

    @Override
    protected int getConfirmButtonRes() {
        return R.id.ccw_confirm_button;
    }

    @Override
    protected int getClauseNumberImageViewRes() {
        return R.id.ccw_clause_number;
    }

    @Override
    protected int getTitleTextViewRes() {
        return R.id.ccw_card_view_title;
    }
}
