package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CurrencyTypes;
import com.bitdubai.fermat_api.layer.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.StockStatisticsData;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class StockStatisticsFragment extends AbstractFermatFragment implements CBPBroadcasterConstants {

    private NumberFormat numberFormat = DecimalFormat.getInstance();
    private Currency currency;
    private List<CryptoBrokerStockTransaction> stockTransactions;
    private float limitVal = 0.4f * 100;
    private int lastItemPosition;
    private Map<Integer, CryptoBrokerStockTransaction> map = new HashMap<>();
    private TextView startIndicator;
    private TextView endIndicator;
    private float balance;


    public static StockStatisticsFragment newInstance() {
        return new StockStatisticsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.cbw_fragment_stock_bar_chart, container, false);

        final TextView currencyTextView = (TextView) layout.findViewById(R.id.currency);
        currencyTextView.setText(currency.getCode());

        final TextView currencyBottomTextView = (TextView) layout.findViewById(R.id.currency_bottom);
        currencyBottomTextView.setText(currency.getFriendlyName());

        final TextView currencyBottomValTextView = (TextView) layout.findViewById(R.id.cbw_currency_bottom_value);
        currencyBottomValTextView.setText(String.format("%s %s", numberFormat.format(balance), currency.getCode()));

        startIndicator = (TextView) layout.findViewById(R.id.start_indicator_text);
        endIndicator = (TextView) layout.findViewById(R.id.end_indicator_text);

        final BarChart barChart = (BarChart) layout.findViewById(R.id.bar_chart);

        BarData BD = getChartData();

        if( BD != null){
            barChart.setData(BD);
        }

        barChart.setDescription("");
        barChart.setDragEnabled(true);
        barChart.setDrawGridBackground(false);
        barChart.setPinchZoom(false);
        barChart.setVisibleXRangeMaximum(7);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisLeft().addLimitLine(getLimitLine());
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setTextSize(16f);
        barChart.getXAxis().setTextColor(Color.parseColor("#2a2f44"));
        barChart.getXAxis().setSpaceBetweenLabels(0);

        Calendar calendar = Calendar.getInstance();

        if(lastItemPosition > 3) {
            barChart.moveViewToX(lastItemPosition - 3);
            barChart.highlightValue(lastItemPosition, 0);
        }


        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int dataSetIndex, Highlight highlight) {

                putDataInIndicators(entry.getXIndex());
            }

            @Override
            public void onNothingSelected() {

            }
        });

        String[] meses = {"JAN", "FEB", "MAR", "MAY", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

        TextView month = (TextView) layout.findViewById(R.id.month);
        TextView year = (TextView) layout.findViewById(R.id.year);

        String idyear = ""+calendar.get(Calendar.YEAR);

        month.setText(meses[calendar.get(Calendar.MONTH)]);
        year.setText(idyear);

        return layout;
    }

    public void bind(StockStatisticsData data) {
        currency = data.getCurrency();
        stockTransactions = data.getStockTransactions();
        balance = data.getBalance();
    }

    private BarData getChartData() {

        if (stockTransactions != null) {

            if ( !stockTransactions.isEmpty() ) {

                List<BarEntry> entries = new ArrayList<>();
                List<String> xVals = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();

                Calendar calendar = Calendar.getInstance();

                int d = calendar.getMaximum(Calendar.DAY_OF_MONTH);

                // creando los dias del mes
                for (int i = 0; i <= d; i++) {
                    if (i == 0) {
                        BarEntry entry = new BarEntry(0, 0);
                        entries.add(entry);
                        xVals.add("");
                        colors.add(Color.parseColor("#2A2F44"));
                    } else {
                        BarEntry entry = new BarEntry(0, i);
                        entries.add(entry);
                        xVals.add(String.valueOf(i));
                        colors.add(Color.parseColor("#2A2F44"));
                    }
                }

                BarEntry entry = new BarEntry(0, (d + 1));
                entries.add(entry);
                xVals.add("");

                // poniendo los valores en los dias adecuados

                for (CryptoBrokerStockTransaction transaction : stockTransactions) {

                    calendar.setTimeInMillis(transaction.getTimestamp());

                    lastItemPosition = calendar.get(Calendar.DAY_OF_MONTH);
                    int index = lastItemPosition;

                    float runningAvailableBalance = transaction.getRunningAvailableBalance().floatValue();

                    if (currency.getType() == CurrencyTypes.CRYPTO && CryptoCurrency.BITCOIN.getCode().equals(currency.getCode()))
                        runningAvailableBalance = (float) BitcoinConverter.convert(runningAvailableBalance, BitcoinConverter.Currency.SATOSHI, BitcoinConverter.Currency.BITCOIN);

                    entries.get(index).setVal(runningAvailableBalance);

                    map.put(lastItemPosition, transaction);

                    if (runningAvailableBalance > limitVal)
                        colors.set(index, Color.parseColor("#FF3E4664"));
                }

                putDataInIndicators(lastItemPosition);

                // configurando el DataSet
                BarDataSet dataSet = new BarDataSet(entries, "stock");
                dataSet.setDrawValues(false);
                dataSet.setColors(colors);
                dataSet.setHighLightColor(Color.WHITE);
                dataSet.setHighLightAlpha(200);
                dataSet.setBarSpacePercent(50);

                BarData barData = new BarData(xVals, dataSet);
                barData.setHighlightEnabled(true);
                return barData;
            }
        }

        return null;
    }

    private LimitLine getLimitLine() {
        int limitLineColor = Color.parseColor("#36b7c8");

        LimitLine limitLine = new LimitLine(limitVal, String.format("Target: %s %s", limitVal, currency.getCode()));
        limitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        limitLine.setLineWidth(1f);
        limitLine.setLineColor(limitLineColor);
        limitLine.setTextSize(10f);
        limitLine.setTextColor(limitLineColor);

        return limitLine;
    }


    private void putDataInIndicators(int xIndex) {

        if( !map.isEmpty()) {
            CryptoBrokerStockTransaction transaction = map.get(xIndex);

            if (transaction != null) {
                endIndicator.setText(numberFormat.format(transaction.getRunningAvailableBalance()));
            } else {
                endIndicator.setText(numberFormat.format(""));
            }

            CryptoBrokerStockTransaction transaction2 = null;

            if (xIndex > 0) {
                transaction2 = map.get(xIndex - 1);
            } else {
                transaction2 = transaction;
            }

            if (transaction2 != null) {
                startIndicator.setText(numberFormat.format(transaction2.getRunningAvailableBalance()));
            } else {
                startIndicator.setText(numberFormat.format(""));
            }
        }else{
            startIndicator.setText(numberFormat.format(""));
            endIndicator.setText(numberFormat.format(""));

        }

    }
/*
    @Override
    public void onUpdateViewOnUIThread(String code) {
        switch (code){
            case CBW_OPERATION_DEBIT_OR_CREDIT_UPDATE_VIEW:

                break;
        }
    }*/
}
