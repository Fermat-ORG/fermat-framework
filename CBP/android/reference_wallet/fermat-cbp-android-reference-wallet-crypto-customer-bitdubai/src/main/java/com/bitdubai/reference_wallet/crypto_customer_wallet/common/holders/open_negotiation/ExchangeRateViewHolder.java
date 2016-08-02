package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;


/**
 * Created by Yordin Alayn on 22.01.16.
 * Based in ExchangeRateViewHolder of Star_negotiation by nelson
 */
public class ExchangeRateViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private TextView markerRateReference;
    private TextView markerRateText;
    private TextView yourExchangeRateValueLeftSide;
    private TextView yourExchangeRateValueRightSide;
    private TextView yourExchangeRateText;
    private FermatButton yourExchangeRateValue;
    private NumberFormat numberFormat;
    private View separatorLineUp;
    private View separatorLineDown;


    public ExchangeRateViewHolder(View itemView) {
        super(itemView);

        yourExchangeRateValueLeftSide = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_value_left_side);
        yourExchangeRateValueRightSide = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_value_right_side);
        yourExchangeRateValue = (FermatButton) itemView.findViewById(R.id.ccw_exchange_rate_value);
        yourExchangeRateText = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_text);
        markerRateReference = (TextView) itemView.findViewById(R.id.ccw_market_rate_value);
        markerRateText = (TextView) itemView.findViewById(R.id.ccw_market_rate_text);
        separatorLineDown = itemView.findViewById(R.id.ccw_line_down);
        separatorLineUp = itemView.findViewById(R.id.ccw_line_up);
        yourExchangeRateValue.setOnClickListener(this);

        numberFormat = DecimalFormat.getInstance();
    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation negotiationInformation, ClauseInformation clause, int clausePosition) {
        super.bindData(negotiationInformation, clause, clausePosition);

        final Map<ClauseType, ClauseInformation> clauses = negotiationInformation.getClauses();
        final ClauseInformation currencyToBuy = clauses.get(ClauseType.CUSTOMER_CURRENCY);
        final ClauseInformation currencyToPay = clauses.get(ClauseType.BROKER_CURRENCY);

        markerRateReference.setText(String.format("1 %1$s / %2$s %3$s", currencyToBuy.getValue(), fixFormat(clause.getValue()), currencyToPay.getValue()));
        yourExchangeRateValueLeftSide.setText(String.format("1 %1$s /", currencyToBuy.getValue()));
        yourExchangeRateValue.setText(fixFormat(clause.getValue()));
        yourExchangeRateValueRightSide.setText(String.format("%1$s", currencyToPay.getValue()));
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClauseClicked(yourExchangeRateValue, clause, clausePosition);
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

    @Override
    public void setStatus(NegotiationStepStatus clauseStatus) {
        super.setStatus(clauseStatus);

        switch (clauseStatus) {
            case ACCEPTED:
                markerRateReference.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                markerRateText.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                yourExchangeRateValueRightSide.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                yourExchangeRateText.setTextColor(getColor(R.color.ccw_text_value_status_accepted));
                separatorLineDown.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
                separatorLineUp.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
                break;
            case CHANGED:
                markerRateReference.setTextColor(getColor(R.color.card_title_color_status_accepted));
                markerRateText.setTextColor(getColor(R.color.card_title_color_status_accepted));
                yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.card_title_color_status_accepted));
                yourExchangeRateValueRightSide.setTextColor(getColor(R.color.card_title_color_status_accepted));
                yourExchangeRateText.setTextColor(getColor(R.color.card_title_color_status_accepted));
                separatorLineDown.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
                separatorLineUp.setBackgroundColor(getColor(R.color.card_title_color_status_accepted));
                break;
            case CONFIRM:
                markerRateReference.setTextColor(getColor(R.color.text_value_status_confirm));
                markerRateText.setTextColor(getColor(R.color.text_value_status_confirm));
                yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.text_value_status_confirm));
                yourExchangeRateValueRightSide.setTextColor(getColor(R.color.text_value_status_confirm));
                yourExchangeRateText.setTextColor(getColor(R.color.text_value_status_confirm));
                break;
        }
    }

    private String fixFormat(String value) {


            if (compareLessThan1(value)) {
                numberFormat.setMaximumFractionDigits(8);
            } else {
                numberFormat.setMaximumFractionDigits(2);
            }
            return numberFormat.format(new BigDecimal(Double.valueOf(value)));


    }

    private Boolean compareLessThan1(String value) {
        return BigDecimal.valueOf(Double.valueOf(value)).compareTo(BigDecimal.ONE) == -1;
    }
}
