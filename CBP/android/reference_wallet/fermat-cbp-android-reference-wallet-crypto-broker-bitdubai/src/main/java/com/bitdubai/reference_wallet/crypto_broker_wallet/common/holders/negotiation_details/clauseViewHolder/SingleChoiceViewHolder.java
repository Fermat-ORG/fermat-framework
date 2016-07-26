package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationWrapper;


/**
 * Created by Yordin Alayn on 22.01.16.
 * Based in AmountToBuyViewHolder of Star_negotiation by nelson
 */
public class SingleChoiceViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private Button buttonValue;
    private TextView descriptionTextView;
    private View separatorLineUp;
    private View separatorLineDown;

    public SingleChoiceViewHolder(View itemView, int holderType) {
        super(itemView, holderType);

        descriptionTextView = (TextView) itemView.findViewById(R.id.cbw_description_text);
        buttonValue = (Button) itemView.findViewById(R.id.cbw_single_choice_value);
        separatorLineDown = itemView.findViewById(R.id.cbw_line_down);
        separatorLineUp = itemView.findViewById(R.id.cbw_line_up);
        buttonValue.setOnClickListener(this);
    }

    @Override
    public void bindData(NegotiationWrapper data, ClauseInformation clause, int clausePosition) {
        super.bindData(data, clause, clausePosition);

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
        separatorLineDown.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
        separatorLineUp.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
    }

    @Override
    protected void setChangedStatus() {
        descriptionTextView.setTextColor(getColor(R.color.card_title_color_status_changed));
        separatorLineDown.setBackgroundColor(getColor(R.color.card_title_color_status_changed));
        separatorLineUp.setBackgroundColor(getColor(R.color.card_title_color_status_changed));
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
        StringBuilder stringBuilder = new StringBuilder();
        final ClauseType type = clause.getType();
        try {
            if (type == ClauseType.CUSTOMER_CURRENCY || type == ClauseType.BROKER_CURRENCY) {
                if (FiatCurrency.codeExists(clauseValue))
                    friendlyValue = stringBuilder.append(FiatCurrency.getByCode(clauseValue).getFriendlyName()).append("(").append(clauseValue).append(")").toString();
                else if (CryptoCurrency.codeExists(clauseValue))
                    friendlyValue = stringBuilder.append(CryptoCurrency.getByCode(clauseValue).getFriendlyName()).append("(").append(clauseValue).append(")").toString();

            } else if (type == ClauseType.CUSTOMER_PAYMENT_METHOD || type == ClauseType.BROKER_PAYMENT_METHOD) {
                friendlyValue = MoneyType.getByCode(clauseValue).getFriendlyName();

            } else if (type == ClauseType.BROKER_BANK_ACCOUNT || type == ClauseType.CUSTOMER_BANK_ACCOUNT) {
                friendlyValue = clauseValue.isEmpty() ? "No Bank Account" : clauseValue;

            } else if (type == ClauseType.BROKER_PLACE_TO_DELIVER || type == ClauseType.CUSTOMER_PLACE_TO_DELIVER) {
                friendlyValue = clauseValue.isEmpty() ? "No Locations" : clauseValue;
            }

        } catch (FermatException ignore) {
        }

        return friendlyValue;
    }
}
