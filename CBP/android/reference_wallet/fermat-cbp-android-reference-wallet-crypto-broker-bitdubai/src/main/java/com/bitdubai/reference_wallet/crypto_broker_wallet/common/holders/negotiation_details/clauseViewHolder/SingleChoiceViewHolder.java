package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by Yordin Alayn on 22.01.16.
 * Based in AmountToBuyViewHolder of Star_negotiation by nelson
 */
public class SingleChoiceViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private Button buttonValue;
    private TextView descriptionTextView;

    public SingleChoiceViewHolder(View itemView, int holderType) {
        super(itemView, holderType);

        descriptionTextView = (TextView) itemView.findViewById(R.id.cbw_description_text);
        buttonValue = (Button) itemView.findViewById(R.id.cbw_single_choice_value);
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
        return R.id.cbw_confirm_button;
    }

    @Override
    protected int getClauseNumberImageViewRes() {
        return R.id.cbw_clause_number;
    }

    @Override
    protected int getTitleTextViewRes() {
        return R.id.cbw_card_view_title;
    }

    @Override
    protected void onAcceptedStatus() {
        descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_accepted));
    }

    @Override
    protected void setChangedStatus() {
        descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
    }

    @Override
    protected void onToConfirmStatus() {
        descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_confirm));
    }

    /**
     * @param clause the clause with the value
     * @return A friendly reading value for the clause or the clause's value itself
     */
    private String getFriendlyValue(ClauseInformation clause) {
        final String clauseValue = clause.getValue();
        String friendlyValue = clauseValue;

        final ClauseType type = clause.getType();
        if (type.equals(ClauseType.CUSTOMER_CURRENCY) || type.equals(ClauseType.BROKER_CURRENCY)) {
            try {
                if (FiatCurrency.codeExists(clauseValue))
                    friendlyValue = FiatCurrency.getByCode(clauseValue).getFriendlyName() + "(" + clauseValue + ")";
                else if (CryptoCurrency.codeExists(clauseValue))
                    friendlyValue = CryptoCurrency.getByCode(clauseValue).getFriendlyName() + "(" + clauseValue + ")";

            } catch (FermatException ignore) {
            }
        }

        return friendlyValue;
    }
}
