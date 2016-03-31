package com.bitdubai.reference_wallet.crypto_customer_wallet.common.holders.open_negotiation;

import android.view.View;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.all_definition.enums.NegotiationStepStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.BrokerCurrencyQuotation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;


/**
 *Created by Yordin Alayn on 22.01.16.
 * Based in ExchangeRateViewHolder of Star_negotiation by nelson
 */
public class ExchangeRateViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private TextView markerRateReference;
    private TextView yourExchangeRateValueLeftSide;
    private TextView yourExchangeRateValueRightSide;
    private TextView yourExchangeRateText;
    private FermatButton yourExchangeRateValue;
    private List<IndexInfoSummary> marketRateList;


    public ExchangeRateViewHolder(View itemView) {
        super(itemView);

        yourExchangeRateValueLeftSide = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_value_left_side);
        yourExchangeRateValueRightSide = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_value_right_side);
        yourExchangeRateValue = (FermatButton) itemView.findViewById(R.id.ccw_exchange_rate_value);
        yourExchangeRateText = (TextView) itemView.findViewById(R.id.ccw_exchange_rate_text);
        markerRateReference = (TextView) itemView.findViewById(R.id.ccw_market_rate_value);
        yourExchangeRateValue.setOnClickListener(this);
    }

    @Override
    public void bindData(CustomerBrokerNegotiationInformation negotiationInformation, ClauseInformation clause, int clausePosition) {
        super.bindData(negotiationInformation, clause, clausePosition);

        final Map<ClauseType, ClauseInformation> clauses = negotiationInformation.getClauses();
        final ClauseInformation currencyToBuy = clauses.get(ClauseType.CUSTOMER_CURRENCY);
        final ClauseInformation currencyToPay = clauses.get(ClauseType.BROKER_CURRENCY);

//        double marketRate = 212.48; // TODO cambiar por valor que devuelve el proveedor asociado a la wallet para este par de monedas
//        String formattedMarketRate = NumberFormat.getInstance().format(marketRate);

        //final BigDecimal marketRate = new BigDecimal(getMarketRate(clauses).replace("," ,""));
        //String formattedMarketRate  = DecimalFormat.getInstance().format(marketRate.doubleValue());

        markerRateReference.setText(String.format("1 %1$s / %2$s %3$s", currencyToBuy.getValue(), getMarketRateValue(clauses), currencyToPay.getValue()));
        yourExchangeRateValueLeftSide.setText(String.format("1 %1$s /", currencyToBuy.getValue()));
        yourExchangeRateValue.setText(clause.getValue());
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

    public void setMarketRateList(List<IndexInfoSummary> marketRateList){
        this.marketRateList = marketRateList;
    }

    @Override
    public void setStatus(NegotiationStepStatus clauseStatus) {
        super.setStatus(clauseStatus);

        switch (clauseStatus) {
            case ACCEPTED:
                markerRateReference.setTextColor(getColor(R.color.text_value_status_accepted));
                yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.text_value_status_accepted));
                yourExchangeRateValueRightSide.setTextColor(getColor(R.color.text_value_status_accepted));
                yourExchangeRateValue.setTextColor(getColor(R.color.text_value_status_accepted));
                yourExchangeRateText.setTextColor(getColor(R.color.text_value_status_accepted));
                break;
            case CHANGED:
                markerRateReference.setTextColor(getColor(R.color.text_value_status_changed));
                yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.text_value_status_changed));
                yourExchangeRateValueRightSide.setTextColor(getColor(R.color.text_value_status_changed));
                yourExchangeRateValue.setTextColor(getColor(R.color.text_value_status_changed));
                yourExchangeRateText.setTextColor(getColor(R.color.text_value_status_changed));
                break;
            case CONFIRM:
                markerRateReference.setTextColor(getColor(R.color.text_value_status_confirm));
                yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.text_value_status_confirm));
                yourExchangeRateValueRightSide.setTextColor(getColor(R.color.text_value_status_confirm));
                yourExchangeRateValue.setTextColor(getColor(R.color.text_value_status_confirm));
                yourExchangeRateText.setTextColor(getColor(R.color.text_value_status_confirm));
                break;
        }
    }
    private String getMarketRate(Map<ClauseType, ClauseInformation> clauses){

        BrokerCurrencyQuotation brokerCurrencyQuotation = new BrokerCurrencyQuotation(marketRateList);
        String brokerMarketRate = brokerCurrencyQuotation.getExchangeRate(
                clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue(),
                clauses.get(ClauseType.BROKER_CURRENCY).getValue()
        );

        return brokerMarketRate;
    }

    private String getMarketRateValue(Map<ClauseType, ClauseInformation> clauses) {

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
    }

    private ExchangeRate getExchangeRate(String currencyAlfa, String currencyBeta) {

        if (marketRateList != null)
            for (IndexInfoSummary item : marketRateList) {
                final ExchangeRate exchangeRateData = item.getExchangeRateData();
                final String toCurrency = exchangeRateData.getToCurrency().getCode();
                final String fromCurrency = exchangeRateData.getFromCurrency().getCode();

                if (fromCurrency.equals(currencyAlfa) && toCurrency.equals(currencyBeta))
                    return exchangeRateData;
            }

        return null;
    }
}
