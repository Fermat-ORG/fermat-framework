package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.IndexInfoSummary;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarketRateStatisticsFragment extends FermatWalletFragment {
    private String buy, sell, currencyPair;

    public static MarketRateStatisticsFragment newInstance() {
        return new MarketRateStatisticsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.cbw_fragment_header_market_rate, container, false);

        FermatTextView buyPrice = (FermatTextView) rootView.findViewById(R.id.cbw_buy_price);
        FermatTextView sellPrice = (FermatTextView) rootView.findViewById(R.id.cbw_sell_price);
        FermatTextView currencies = (FermatTextView) rootView.findViewById(R.id.cbw_currencies);

        currencies.setText(currencyPair);
        String buyText = rootView.getResources().getString(R.string.buy_text_and_price, buy);
        buyPrice.setText(buyText);
        String sellText = rootView.getResources().getString(R.string.sell_text_and_price, sell);
        sellPrice.setText(sellText);

        configChart(rootView);

        return rootView;
    }

    private void configChart(View rootView) {
        final LineChart mChart = (LineChart) rootView.findViewById(R.id.cbw_market_rate_line_chart);

        mChart.setDescription("");
        mChart.setData(getData(7, 100));
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.getLegend().setEnabled(false);
        mChart.getXAxis().setEnabled(false);
        mChart.getAxisRight().setEnabled(false);

        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setDrawAxisLine(true);
        yAxis.setStartAtZero(true);
        yAxis.setDrawGridLines(true);
        yAxis.setGridColor(Color.BLACK);
        yAxis.setTextColor(Color.WHITE);

        mChart.invalidate();
    }

    private LineData getData(int count, float range) {
        ArrayList<Entry> entryList = new ArrayList<Entry>();
        ArrayList<String> xValues = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float multiplier = range / 2f;
            float val = (float) (Math.random() * multiplier) + 50;
            entryList.add(new Entry(val, i));
            xValues.add(String.valueOf(i));
        }

        LineDataSet dataSet = new LineDataSet(entryList, "dataSet");
        dataSet.setColor(Color.WHITE);
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(2.0f);
        dataSet.setDrawValues(false);

        return new LineData(xValues, dataSet);
    }

    public void bind(IndexInfoSummary indexInfo) {
        sell = indexInfo.getSalePriceAndCurrency();
        currencyPair = indexInfo.getCurrencyAndReferenceCurrency();
        buy = indexInfo.getPurchasePriceAndCurrency();
    }
}
