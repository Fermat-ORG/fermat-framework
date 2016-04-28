package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationWrapper;

import java.util.Map;

import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.BROKER_CURRENCY;
import static com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType.CUSTOMER_CURRENCY;
import static com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.OpenNegotiationDetailsAdapter.TYPE_AMOUNT_TO_SELL;


/**
 * Created by Nelson Ramirez
 *
 * @since 21-02-2016.
 */
public class AmountViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private FermatTextView currencyTextValue;
    private FermatTextView amountText;
    private FermatButton amountValue;

    public AmountViewHolder(View itemView, int holderType) {
        super(itemView, holderType);

        currencyTextValue = (FermatTextView) itemView.findViewById(R.id.cbw_amount_currency);
        amountText = (FermatTextView) itemView.findViewById(R.id.cbw_amount_text);
        amountValue = (FermatButton) itemView.findViewById(R.id.cbw_amount_value);
        amountValue.setOnClickListener(this);
    }

    @Override
    public void bindData(NegotiationWrapper data, ClauseInformation clause, int position) {
        super.bindData(data, clause, position);

        final ClauseType clauseType = (getHolderType() == TYPE_AMOUNT_TO_SELL) ? CUSTOMER_CURRENCY : BROKER_CURRENCY;
        final Map<ClauseType, ClauseInformation> clauses = data.getClauses();
        currencyTextValue.setText(clauses.get(clauseType).getValue());
        amountValue.setText(clause.getValue());
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClauseClicked(amountValue, clause, clausePosition);
    }

    @Override
    public void setViewResources(int titleRes, int positionImgRes, int... stringResources) {
        titleTextView.setText(titleRes);
        clauseNumberImageView.setImageResource(positionImgRes);
        amountText.setText(stringResources[0]);
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
        amountText.setTextColor(getColor(R.color.description_text_status_accepted));
        currencyTextValue.setTextColor(getColor(R.color.description_text_status_accepted));
    }

    @Override
    protected void setChangedStatus() {
        amountText.setTextColor(getColor(R.color.description_text_status_changed));
        currencyTextValue.setTextColor(getColor(R.color.description_text_status_changed));
    }

    @Override
    protected void onToConfirmStatus() {
        amountText.setTextColor(getColor(R.color.description_text_status_confirm));
        currencyTextValue.setTextColor(getColor(R.color.text_value_status_confirm));
    }
}
