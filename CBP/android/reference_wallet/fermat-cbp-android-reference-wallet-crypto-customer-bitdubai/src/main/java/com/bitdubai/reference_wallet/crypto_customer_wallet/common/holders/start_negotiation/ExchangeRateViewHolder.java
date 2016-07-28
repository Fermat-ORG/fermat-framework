package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.start_negotiation;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map;


/**
 * Created by nelson
 * on 10/01/16.
 */
public class ExchangeRateViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private View markerRateReferenceContainer;
    private TextView markerRateReference;
    private TextView yourExchangeRateValueLeftSide;
    private TextView yourExchangeRateValueRightSide;
    private FermatButton yourExchangeRateValue;
    private List<IndexInfoSummary> marketRateList;
    private NumberFormat numberFormat = DecimalFormat.getInstance();

    public ExchangeRateViewHolder(View itemView) {
        super(itemView);

        yourExchangeRateValueLeftSide = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_value_left_side);
        yourExchangeRateValueRightSide = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_value_right_side);
        yourExchangeRateValue = (FermatButton) itemView.findViewById(R.id.ccw_exchange_rate_value);
        markerRateReferenceContainer = itemView.findViewById(R.id.ccw_market_rate_container);
        markerRateReference = (TextView) itemView.findViewById(R.id.ccw_market_rate_value);
        yourExchangeRateValue.setOnClickListener(this);

    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation negotiationInformation, ClauseInformation clause, int clausePosition) {
        super.bindData(negotiationInformation, clause, clausePosition);

        final Map<ClauseType, ClauseInformation> clauses = negotiationInformation.getClauses();

        final String currencyToBuy = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();
        final String currencyToPay = clauses.get(ClauseType.BROKER_CURRENCY).getValue();

        //String marketRate = getMarketRateValue(clauses);
        //markerRateReference.setText(String.format("1 %1$s / %2$s %3$s", currencyToBuy, marketRate, currencyToPay));

        markerRateReference.setText(String.format("1 %1$s / %2$s %3$s", currencyToBuy,
                fixFormat(clause.getValue()), currencyToPay));
        yourExchangeRateValueLeftSide.setText(String.format("1 %1$s /", currencyToBuy));
        yourExchangeRateValue.setText(fixFormat(clause.getValue()));
        yourExchangeRateValueRightSide.setText(String.format("%1$s", currencyToPay));
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClauseCLicked(yourExchangeRateValue, clause, clausePosition);
    }

    @Override
    public void setViewResources(int titleRes, int positionImgRes, int... stringResources) {
        titleTextView.setText(titleRes);
        clauseNumberImageView.setImageResource(positionImgRes);
    }

    public View getMarkerRateReferenceContainer() {
        return markerRateReferenceContainer;
    }

    public void setMarketRateList(List<IndexInfoSummary> marketRateList) {
        this.marketRateList = marketRateList;
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



    /*private String getMarketRateValue(Map<ClauseType, ClauseInformation> clauses) {

        String currencyOver = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();
        String currencyUnder = clauses.get(ClauseType.BROKER_CURRENCY).getValue();

        ExchangeRate currencyQuotation = getExchangeRate(currencyOver, currencyUnder);
        String exchangeRateStr = "0.0";

        if (currencyQuotation == null) {
            currencyQuotation = getExchangeRate(currencyUnder, currencyOver);
            if (currencyQuotation != null) {
                BigDecimal exchangeRate = new BigDecimal(currencyQuotation.getSalePrice());
                exchangeRate = (new BigDecimal(1)).divide(exchangeRate, 8, RoundingMode.HALF_UP);
                exchangeRateStr = DecimalFormat.getInstance().format(exchangeRate.doubleValue());
            }
        } else {
            BigDecimal exchangeRate = new BigDecimal(currencyQuotation.getSalePrice());
            exchangeRateStr = DecimalFormat.getInstance().format(exchangeRate.doubleValue());
        }

        return exchangeRateStr;
    }*/

/*    private ExchangeRate getExchangeRate(String currencyAlfa, String currencyBeta) {

        if (marketRateList != null)
            for (IndexInfoSummary item : marketRateList) {
                final ExchangeRate exchangeRateData = item.getExchangeRateData();
                final String toCurrency = exchangeRateData.getToCurrency().getCode();
                final String fromCurrency = exchangeRateData.getFromCurrency().getCode();

                if (fromCurrency.equals(currencyAlfa) && toCurrency.equals(currencyBeta))
                    return exchangeRateData;
            }

        return null;
    }*/


    private String fixFormat(String value) {

        try {
            if (compareLessThan1(value)) {
                numberFormat.setMaximumFractionDigits(8);
            } else {
                numberFormat.setMaximumFractionDigits(2);
            }
            return numberFormat.format(new BigDecimal(numberFormat.parse(value).toString()));
        } catch (ParseException e) {
            e.printStackTrace();
            return "0";
        }

    }

    private Boolean compareLessThan1(String value) {
        Boolean lessThan1 = true;
        try {
            if (BigDecimal.valueOf(numberFormat.parse(value).doubleValue()).
                    compareTo(BigDecimal.ONE) == -1) {
                lessThan1 = true;
            } else {
                lessThan1 = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return lessThan1;
    }

}
