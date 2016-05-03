package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.util.Map;


/**
 * Created by nelson on 10/01/16.
 */
public class AmountToBuyViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private TextView currencyToBuyTextValue;
    private TextView buyingText;
    private FermatButton buyingValue;
    private boolean paymentBuy;

    public AmountToBuyViewHolder(View itemView) {
        super(itemView);

        this.paymentBuy = Boolean.TRUE;

        currencyToBuyTextValue  = (TextView) itemView.findViewById(R.id.ccw_currency_to_buy);
        buyingText              = (TextView) itemView.findViewById(R.id.ccw_buying_text);
        buyingValue             = (FermatButton) itemView.findViewById(R.id.ccw_buying_value);
        buyingValue.setOnClickListener(this);
    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation data, ClauseInformation clause, int position) {
        super.bindData(data, clause, position);

        final Map<ClauseType, ClauseInformation> clauses = data.getClauses();

        ClauseType currencyType = ClauseType.CUSTOMER_CURRENCY;
        int buyingTextValue = R.string.buying_text;

        if (!paymentBuy) {
            currencyType = ClauseType.BROKER_CURRENCY;
            buyingTextValue = R.string.paying_text;
        }

        final ClauseInformation currencyToBuy = clauses.get(currencyType);

        currencyToBuyTextValue.setText(currencyToBuy.getValue());
        buyingText.setText(buyingTextValue);
        buyingValue.setText(clause.getValue());
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClauseCLicked(buyingValue, clause, clausePosition);
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

    public boolean setPaymentBuy(boolean paymentBuy){
        return this.paymentBuy = paymentBuy;
    }
}
