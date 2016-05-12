package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders.negotiation_details.clauseViewHolder;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.Quote;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.NegotiationWrapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;


/**
 * Created by Yordin Alayn on 22.01.16.
 * Based in ExchangeRateViewHolder of Star_negotiation by nelson
 */
public class ExchangeRateViewHolder extends ClauseViewHolder implements View.OnClickListener {

    private final FermatTextView markerRateReferenceText;
    public final FermatTextView exchangeRateReferenceValue;
    private final FermatTextView exchangeRateReferenceText;
    public final FermatTextView markerRateReference;
    private final FermatTextView yourExchangeRateValueLeftSide;
    private final FermatTextView yourExchangeRateValueRightSide;
    private final FermatTextView yourExchangeRateText;
    private final FermatButton yourExchangeRateValue;
    private List<IndexInfoSummary> marketRateList;
    private Quote suggestedRate;
    private float spread;
    private boolean suggestedRateLoaded;
    private NumberFormat formatter;



    public ExchangeRateViewHolder(View itemView, int holderType) {
        super(itemView, holderType);



        exchangeRateReferenceText = (FermatTextView) itemView.findViewById(R.id.cbw_exchange_rate_reference_text);
        exchangeRateReferenceValue = (FermatTextView) itemView.findViewById(R.id.cbw_exchange_rate_reference_value);
        markerRateReferenceText = (FermatTextView) itemView.findViewById(R.id.cbw_market_exchange_rate_reference_text);
        markerRateReference = (FermatTextView) itemView.findViewById(R.id.cbw_market_exchange_rate_reference_value);
        yourExchangeRateText = (FermatTextView) itemView.findViewById(R.id.cbw_your_exchange_rate_text);
        yourExchangeRateValueLeftSide = (FermatTextView) itemView.findViewById(R.id.cbw_your_exchange_rate_value_left_side);
        yourExchangeRateValueRightSide = (FermatTextView) itemView.findViewById(R.id.cbw_your_exchange_rate_value_right_side);
        yourExchangeRateValue = (FermatButton) itemView.findViewById(R.id.cbw_your_exchange_rate_value);
        yourExchangeRateValue.setOnClickListener(this);

        formatter = DecimalFormat.getInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setRoundingMode(RoundingMode.DOWN);
    }

    @Override
    public void bindData(NegotiationWrapper data, ClauseInformation clause, int clausePosition) {
        super.bindData(data, clause, clausePosition);

        final Map<ClauseType, ClauseInformation> clauses = data.getClauses();
        final ClauseInformation currencyToBuy = clauses.get(ClauseType.CUSTOMER_CURRENCY);
        final ClauseInformation currencyToPay = clauses.get(ClauseType.BROKER_CURRENCY);


        yourExchangeRateValueLeftSide.setText(String.format("1 %1$s /", currencyToBuy.getValue()));
        yourExchangeRateValue.setText(clause.getValue());
        yourExchangeRateValueRightSide.setText(String.format("%1$s", currencyToPay.getValue()));

        BigDecimal marketRateReferenceValue = getMarketRateValue(clauses);

        String marketExchangeRateStr = formatter.format(marketRateReferenceValue.doubleValue());
        String suggestedMaxExchangeRateStr = formatter.format(marketRateReferenceValue.doubleValue() * (1+(spread/100)));


        if (marketRateList != null) {
            markerRateReference.setText(String.format("1 %1$s / %2$s %3$s",
                    currencyToBuy.getValue(), marketExchangeRateStr, currencyToPay.getValue()));
            exchangeRateReferenceValue.setText(String.format("Min: %1$s %3$s    Max: %2$s %3$s",
                    marketExchangeRateStr, suggestedMaxExchangeRateStr, suggestedRate.getFiatCurrency().getCode()));
        }
        else {
            markerRateReference.setText("Can't get Market Exchange Rate");
            exchangeRateReferenceValue.setText("Can't get suggested Exchange Rate");
        }

//
//
//        if (suggestedRate != null)
//            exchangeRateReferenceValue.setText(String.format("1 %1$s / %2$.3f %3$s",
//                    suggestedRate.getMerchandise().getCode(), suggestedRate.getPriceReference(), suggestedRate.getFiatCurrency().getCode()));
//        else if (suggestedRateLoaded)
//            exchangeRateReferenceValue.setText("Can't get suggested Exchange Rate");
//        else
//            exchangeRateReferenceValue.setText("Loading...");
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClauseClicked(yourExchangeRateValue, clause, clausePosition);
    }

    public void setSuggestedRate(Quote suggestedRate, boolean loaded) {
        this.suggestedRate = suggestedRate;
        this.suggestedRateLoaded = loaded;
    }

    public void setSpread(float spread) {
        this.spread = spread;
    }

    @Override
    public void setViewResources(int titleRes, int positionImgRes, int... stringResources) {
        titleTextView.setText(titleRes);
        clauseNumberImageView.setImageResource(positionImgRes);
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
        exchangeRateReferenceText.setTextColor(getColor(R.color.description_text_status_accepted));
        exchangeRateReferenceValue.setTextColor(getColor(R.color.description_text_status_accepted));
        markerRateReferenceText.setTextColor(getColor(R.color.description_text_status_accepted));
        markerRateReference.setTextColor(getColor(R.color.description_text_status_accepted));
        yourExchangeRateText.setTextColor(getColor(R.color.description_text_status_accepted));
        yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.description_text_status_accepted));
        yourExchangeRateValueRightSide.setTextColor(getColor(R.color.description_text_status_accepted));
    }

    @Override
    protected void setChangedStatus() {
        exchangeRateReferenceText.setTextColor(getColor(R.color.description_text_status_changed));
        exchangeRateReferenceValue.setTextColor(getColor(R.color.description_text_status_changed));
        markerRateReferenceText.setTextColor(getColor(R.color.description_text_status_changed));
        markerRateReference.setTextColor(getColor(R.color.description_text_status_changed));
        yourExchangeRateText.setTextColor(getColor(R.color.description_text_status_changed));
        yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.description_text_status_changed));
        yourExchangeRateValueRightSide.setTextColor(getColor(R.color.description_text_status_changed));
    }

    @Override
    protected void onToConfirmStatus() {
        exchangeRateReferenceText.setTextColor(getColor(R.color.description_text_status_confirm));
        exchangeRateReferenceValue.setTextColor(getColor(R.color.text_value_status_confirm));
        markerRateReferenceText.setTextColor(getColor(R.color.description_text_status_confirm));
        markerRateReference.setTextColor(getColor(R.color.text_value_status_confirm));
        yourExchangeRateText.setTextColor(getColor(R.color.description_text_status_confirm));
        yourExchangeRateValueLeftSide.setTextColor(getColor(R.color.text_value_status_confirm));
        yourExchangeRateValueRightSide.setTextColor(getColor(R.color.text_value_status_confirm));
    }

    public void setMarketRateList(List<IndexInfoSummary> marketRateList) {
        this.marketRateList = marketRateList;
    }

    private BigDecimal getMarketRateValue(Map<ClauseType, ClauseInformation> clauses) {

        String currencyOver = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();
        String currencyUnder = clauses.get(ClauseType.BROKER_CURRENCY).getValue();

        BigDecimal exchangeRate = new BigDecimal(0);

        ExchangeRate currencyQuotation = getExchangeRate(currencyOver, currencyUnder);

        if (currencyQuotation == null) {
            currencyQuotation = getExchangeRate(currencyUnder, currencyOver);
            if (currencyQuotation != null) {
                exchangeRate = new BigDecimal(currencyQuotation.getSalePrice());
                exchangeRate = (new BigDecimal(1)).divide(exchangeRate, 8, RoundingMode.HALF_UP);
            }
        } else {
            exchangeRate = new BigDecimal(currencyQuotation.getSalePrice());
        }

        return exchangeRate;
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
