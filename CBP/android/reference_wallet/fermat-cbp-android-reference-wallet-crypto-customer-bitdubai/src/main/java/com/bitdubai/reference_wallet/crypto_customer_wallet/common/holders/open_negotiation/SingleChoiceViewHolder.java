package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

/**
 * Created by Yordin Alayn on 22.01.16.
 * Based in SingleChoiceViewHolder of Star_negotiation by nelson
 */
public class SingleChoiceViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private Button buttonValue;
    private TextView descriptionTextView;
    private View separatorLineUp;
    private View separatorLineDown;

    public SingleChoiceViewHolder(View itemView) {
        super(itemView);

        descriptionTextView = (TextView) itemView.findViewById(R.id.ccw_description_text);
        buttonValue = (Button) itemView.findViewById(R.id.ccw_single_choice_value);
        separatorLineDown = itemView.findViewById(R.id.ccw_line_down);
        separatorLineUp = itemView.findViewById(R.id.ccw_line_up);
        buttonValue.setOnClickListener(this);
    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation negotiationInformation, ClauseInformation clause, int clausePosition) {
        super.bindData(negotiationInformation, clause, clausePosition);

        buttonValue.setText(getFriendlyValue(clause));
    }

    @Override
    public void setViewResources(int titleRes, int positionImgRes, int... stringResources) {
        titleTextView.setText(titleRes);
        clauseNumberImageView.setImageResource(positionImgRes);
        descriptionTextView.setText(stringResources[0]);
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClauseClicked(buttonValue, clause, clausePosition);
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

    @Override
    public void setStatus(NegotiationStepStatus clauseStatus) {
        super.setStatus(clauseStatus);

        switch (clauseStatus) {
            case ACCEPTED:
                separatorLineDown.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
                separatorLineUp.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
                break;
            case CHANGED:
                separatorLineDown.setBackgroundColor(getColor(R.color.card_title_color_status_changed));
                separatorLineUp.setBackgroundColor(getColor(R.color.card_title_color_status_changed));
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
                break;
            case CONFIRM:
                descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
                break;
        }
    }

    /**
     * @param clause the clause with the value
     * @return A friendly reading value for the clause or the clause's value itself
     */
    private String getFriendlyValue(ClauseInformation clause) {
        final String clauseValue = clause.getValue();
        String friendlyValue = clauseValue;

        final ClauseType type = clause.getType();
        try {
            if (type == ClauseType.CUSTOMER_CURRENCY || type == ClauseType.BROKER_CURRENCY) {
                if (FiatCurrency.codeExists(clauseValue))
                    friendlyValue = new StringBuilder().append(FiatCurrency.getByCode(clauseValue).getFriendlyName()).append("(").append(clauseValue).append(")").toString();
                else if (CryptoCurrency.codeExists(clauseValue))
                    friendlyValue = new StringBuilder().append(CryptoCurrency.getByCode(clauseValue).getFriendlyName()).append("(").append(clauseValue).append(")").toString();

            } else if (type == ClauseType.CUSTOMER_PAYMENT_METHOD || type == ClauseType.BROKER_PAYMENT_METHOD) {
                friendlyValue = MoneyType.getByCode(clauseValue).getFriendlyName();
            }
        } catch (FermatException ignore) {
        }

        return friendlyValue;
    }
}
