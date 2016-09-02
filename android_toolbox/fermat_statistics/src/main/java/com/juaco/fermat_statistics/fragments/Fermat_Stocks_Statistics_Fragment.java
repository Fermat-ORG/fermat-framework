package com.juaco.fermat_statistics.fragments;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;

//import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerStockTransaction;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.juaco.fermat_statistics.R;
import com.juaco.fermat_statistics.models.StockStatisticsData;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Joaquin Carrasquero on 02/03/16.
 */

public abstract class Fermat_Stocks_Statistics_Fragment extends AbstractFermatFragment {

    private NumberFormat numberFormat = DecimalFormat.getInstance();
    private Currency currency;

//    private List<CryptoBrokerStockTransaction> stockTransactions;
    private float limitVal = 0.4f * 100;
    private int lastItemPosition;
//    private Map<Integer, CryptoBrokerStockTransaction> map = new HashMap<>();

    private TextView startIndicator;
    private TextView endIndicator;
    private float balance;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View layout = inflater.inflate(R.layout.fragment_stock_bar_chart, container, false);

        final TextView currencyTextView = (TextView) layout.findViewById(R.id.currency);
        currencyTextView.setText(currency.getCode());

        final TextView currencyBottomTextView = (TextView) layout.findViewById(R.id.currency_bottom);
        currencyBottomTextView.setText(currency.getFriendlyName());

        final TextView currencyBottomValTextView = (TextView) layout.findViewById(R.id.cbw_currency_bottom_value);
        currencyBottomValTextView.setText(String.format("%s %s", numberFormat.format(balance), currency.getCode()));

        startIndicator = (TextView) layout.findViewById(R.id.start_indicator_text);
        endIndicator = (TextView) layout.findViewById(R.id.end_indicator_text);

        final BarChart barChart = (BarChart) layout.findViewById(R.id.bar_chart);
        barChart.setDescription("");
        barChart.setData(getChartData());
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
        barChart.moveViewToX(lastItemPosition - 4);
        barChart.highlightValue(lastItemPosition, 0);
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int dataSetIndex, Highlight highlight) {

                putDataInIndicators(entry.getXIndex());
            }

            @Override
            public void onNothingSelected() {

            }
        });

        return layout;
    }

    public abstract StockStatisticsData setData();

    public void bind() {
        StockStatisticsData data = null;
        if (this.setData()!= null)
            data = this.setData();

        if (data != null) {
            currency = data.getCurrency();
<<<<<<< HEAD
           // stockTransactions = data.getStockTransactions();
=======
//            stockTransactions = data.getStockTransactions();
>>>>>>> 4e17147d69dd7732115f8b4dc8b6de8c5e7a9900
            balance = data.getBalance();
        }
    }

    private BarData getChartData() {

        List<BarEntry> entries = new ArrayList<>();
        List<String> xVals = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        // creando los dias del mes
        for (int i = 1; i <= 30; i++) {
            BarEntry entry = new BarEntry(0, i);
            entries.add(entry);
            xVals.add(String.valueOf(i));
            colors.add(Color.parseColor("#2A2F44"));
        }

        // poniendo los valores en los dias adecuados
<<<<<<< HEAD
       // if (stockTransactions != null) {
           /* for (CryptoBrokerStockTransaction transaction : stockTransactions) {
                calendar.setTimeInMillis(transaction.getTimestamp());

                lastItemPosition = calendar.get(Calendar.DAY_OF_MONTH);
                float runningAvailableBalance = transaction.getRunningAvailableBalance().floatValue();
                int index = lastItemPosition - 1;

                entries.get(index).setVal(runningAvailableBalance);

                map.put(lastItemPosition, transaction);

                if (runningAvailableBalance > limitVal)
                    colors.set(index, Color.parseColor("#FF3E4664"));
            }*/
        //}
=======
//        if (stockTransactions != null) {
//            for (CryptoBrokerStockTransaction transaction : stockTransactions) {
//                calendar.setTimeInMillis(transaction.getTimestamp());
//
//                lastItemPosition = calendar.get(Calendar.DAY_OF_MONTH);
//                float runningAvailableBalance = transaction.getRunningAvailableBalance().floatValue();
//                int index = lastItemPosition - 1;
//
//                entries.get(index).setVal(runningAvailableBalance);
//
//                map.put(lastItemPosition, transaction);
//
//                if (runningAvailableBalance > limitVal)
//                    colors.set(index, Color.parseColor("#FF3E4664"));
//            }
//        }
>>>>>>> 4e17147d69dd7732115f8b4dc8b6de8c5e7a9900

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
<<<<<<< HEAD
       /* CryptoBrokerStockTransaction transaction = map.get(xIndex);

        if (transaction != null) {
            startIndicator.setText(numberFormat.format(transaction.getPreviousAvailableBalance()));
            endIndicator.setText(numberFormat.format(transaction.getRunningAvailableBalance()));
        }*/
=======
        //CryptoBrokerStockTransaction transaction = map.get(xIndex);

//        if (transaction != null) {
//            startIndicator.setText(numberFormat.format(transaction.getPreviousAvailableBalance()));
//            endIndicator.setText(numberFormat.format(transaction.getRunningAvailableBalance()));
//        }
>>>>>>> 4e17147d69dd7732115f8b4dc8b6de8c5e7a9900
    }
}
