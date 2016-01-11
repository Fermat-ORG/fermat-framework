package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.SingleChoiceDialogFragment;

/**
 * Created by nelson on 10/01/16.
 */
public class SingleChoiceViewHolder extends ClauseViewHolder {

    private View.OnClickListener listener;
    private Button buttonValue;
    private TextView descriptionTextView;

    public SingleChoiceViewHolder(View itemView) {
        super(itemView);

        descriptionTextView = (TextView) itemView.findViewById(R.id.ccw_description_text);
        buttonValue = (Button) itemView.findViewById(R.id.ccw_single_choice_value);
        buttonValue.setOnClickListener(listener);
    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation data, ClauseInformation clause) {
        buttonValue.setText(clause.getValue());
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void setViewResources(int titleRes, int positionImgRes, int... stringResources) {
        titleTextView.setText(titleRes);
        clauseNumberImageView.setImageResource(positionImgRes);
        descriptionTextView.setText(stringResources[0]);
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
