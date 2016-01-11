package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation;

import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.util.Map;


/**
 * Created by nelson on 10/01/16.
 */
public class AmountToBuyViewHolder extends ClauseViewHolder {

    private TextWatcher listener;
    private TextView youWillPayTextValue;
    private TextView currencyToBuyTextValue;
    private EditText buyingValue;


    public AmountToBuyViewHolder(View itemView) {
        super(itemView);

        currencyToBuyTextValue = (TextView) itemView.findViewById(R.id.ccw_currency_to_buy);
        youWillPayTextValue = (TextView) itemView.findViewById(R.id.ccw_you_will_pay_text_value);
        buyingValue = (EditText) itemView.findViewById(R.id.ccw_buying_value);
        buyingValue.addTextChangedListener(listener);
    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation data, ClauseInformation clause) {
        final Map<ClauseType, ClauseInformation> clauses = data.getClauses();
        final ClauseInformation currencyToBuy = clauses.get(ClauseType.CUSTOMER_CURRENCY);
        final ClauseInformation amountToPay = clauses.get(ClauseType.BROKER_CURRENCY_QUANTITY);
        final ClauseInformation currencyToPay = clauses.get(ClauseType.BROKER_CURRENCY);

        buyingValue.setText(clause.getValue());
        currencyToBuyTextValue.setText(currencyToBuy.getValue());
        youWillPayTextValue.setText(String.format("%1$s %2$s", amountToPay.getValue(), currencyToPay.getValue()));
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
