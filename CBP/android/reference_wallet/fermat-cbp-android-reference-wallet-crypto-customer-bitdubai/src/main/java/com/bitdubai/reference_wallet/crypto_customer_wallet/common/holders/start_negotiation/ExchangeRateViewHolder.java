package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation;

import android.text.TextWatcher;
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
public class ExchangeRateViewHolder extends ClauseViewHolder {
    private final TextView markerRateReference;
    private final TextView yourExchangeRateValueLeftSide;
    private final TextView yourExchangeRateValueRightSide;
    private final EditText yourExchangeRateValue;
    private TextWatcher listener;


    public ExchangeRateViewHolder(View itemView) {
        super(itemView);

        markerRateReference = (TextView) itemView.findViewById(R.id.ccw_market_exchange_rate_reference_value);
        yourExchangeRateValueLeftSide = (TextView) itemView.findViewById(R.id.ccw_your_exchange_rate_value_left_side);
        yourExchangeRateValueRightSide = (TextView) itemView.findViewById(R.id.ccw_your_exchange_rate_value_right_side);
        yourExchangeRateValue = (EditText) itemView.findViewById(R.id.ccw_your_exchange_rate_value);
        yourExchangeRateValue.addTextChangedListener(listener);
    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation data, ClauseInformation clause) {
        final Map<ClauseType, ClauseInformation> clauses = data.getClauses();
        final ClauseInformation currencyToBuy = clauses.get(ClauseType.CUSTOMER_CURRENCY);
        final ClauseInformation currencyToPay = clauses.get(ClauseType.BROKER_CURRENCY);

        double marketRate = 212.48; // TODO cambiar por valor que devuelve el proveedor asociado a la wallet para este par de monedas

        String formattedMarketRate = NumberFormat.getInstance().format(marketRate);

        markerRateReference.setText(String.format("1 %1$s / %2$s %3$s", currencyToBuy, formattedMarketRate, currencyToPay));
        yourExchangeRateValueLeftSide.setText(String.format("1 %1$s", currencyToBuy));
        yourExchangeRateValue.setText(clause.getValue());
        yourExchangeRateValueRightSide.setText(String.format("%1$s", currencyToPay));
    }

    public void setListener(TextWatcher listener) {
        this.listener = listener;
    }

    @Override
    public void setViewResources(int titleRes, int positionImgRes, int... stringResources) {
        titleTextView.setText(titleRes);
        clauseNumberImageView.setImageResource(positionImgRes);
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
