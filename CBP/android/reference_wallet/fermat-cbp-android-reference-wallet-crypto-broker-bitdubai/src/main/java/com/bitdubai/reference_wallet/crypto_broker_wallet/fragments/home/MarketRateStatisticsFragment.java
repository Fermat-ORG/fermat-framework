package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.ExchangeRate;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetExchangeRateException;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets.CBP_CRYPTO_BROKER_WALLET;


/**
 * A simple {@link Fragment} subclass.
 */
public class MarketRateStatisticsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>, ResourceProviderManager> {
    private final String TAG = "MarketRateStatistics";

    private String buy, sell, currencyPair, providerName;
    private IndexInfoSummary indexInfo;
    private ReferenceAppFermatSession session;
    private Activity activity;
    private NumberFormat numberFormat = DecimalFormat.getInstance();

    public static MarketRateStatisticsFragment newInstance() {
        return new MarketRateStatisticsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.cbw_fragment_header_market_rate, container, false);

        final FermatTextView buyPrice = (FermatTextView) rootView.findViewById(R.id.cbw_buy_price);
        final FermatTextView sellPrice = (FermatTextView) rootView.findViewById(R.id.cbw_sell_price);
        final FermatTextView currencies = (FermatTextView) rootView.findViewById(R.id.cbw_currencies);
        final FermatTextView providerName = (FermatTextView) rootView.findViewById(R.id.cbw_provider_name);

        String buyAmount = buy.split(" ")[1];
        String buyCurrency = buy.split(" ")[0];
        String sellAmount = sell.split(" ")[1];
        String sellCurrency = sell.split(" ")[0];
        String buyWithFormat = fixFormat(buyAmount);
        String sellWithFormat = fixFormat(sellAmount);
        if (buyAmount.equals("0") && sellAmount.equals("0")) {
            providerName.setText(new StringBuilder().append(this.providerName).append(" is down").toString());
            providerName.setTextColor(ContextCompat.getColor(getActivity(), R.color.cbw_provider_is_down));
        } else {
            providerName.setText(this.providerName);
        }
        currencies.setText(currencyPair);
        buyPrice.setText(new StringBuilder().append(buyCurrency).append(" ").append(buyWithFormat).toString());
        sellPrice.setText(new StringBuilder().append(sellCurrency).append(" ").append(sellWithFormat).toString());

        configChart(rootView);

        return rootView;
    }

    /**
     * Set the necessary data to the fragment
     *
     * @param indexInfo info for the chart
     * @param session   the app session
     */
    public void bind(IndexInfoSummary indexInfo, ReferenceAppFermatSession session, Activity activity) {
        this.indexInfo = indexInfo;
        this.session = session;
        this.activity = activity;

        sell = indexInfo.getSalePriceAndCurrency();
        currencyPair = indexInfo.getCurrencyAndReferenceCurrency();
        buy = indexInfo.getPurchasePriceAndCurrency();
        providerName = indexInfo.getProviderName();
    }

    /**
     * Get the data and load the chart with that
     *
     * @param rootView the Root view
     */
    private void configChart(final View rootView) {
        final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.cbw_market_rate_line_chart_progress_bar);
        final View noDataMsg = rootView.findViewById(R.id.cbw_market_rate_line_chart_no_data_msg);
        final LineChart mChart = (LineChart) rootView.findViewById(R.id.cbw_market_rate_line_chart);

        progressBar.setVisibility(View.VISIBLE);

        FermatWorker fermatWorker = new FermatWorker(activity) {
            @Override
            protected Object doInBackground() throws Exception {
                CryptoBrokerWalletModuleManager moduleManager = (CryptoBrokerWalletModuleManager) session.getModuleManager();
                List<ExchangeRate> exchangeRates = new ArrayList<>();
                exchangeRates.addAll(moduleManager.getDailyExchangeRatesFromCurrentDate(indexInfo, 7));

                return exchangeRates;
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                if (result != null && result.length > 0) {
                    List<ExchangeRate> exchangeRates = (List<ExchangeRate>) result[0];

                    progressBar.setVisibility(View.GONE);

                    if (exchangeRates.isEmpty()) {
                        noDataMsg.setVisibility(View.VISIBLE);

                    } else {
                        mChart.setVisibility(View.VISIBLE);

                        mChart.setDescription("");
                        mChart.setData(getData(exchangeRates));
                        mChart.setDragEnabled(true);
                        mChart.setScaleEnabled(true);
                        mChart.setDrawGridBackground(false);
                        mChart.getLegend().setEnabled(false);
                        mChart.getXAxis().setEnabled(false);
                        mChart.getAxisRight().setEnabled(false);
                        mChart.setAutoScaleMinMaxEnabled(true);

                        YAxis yAxis = mChart.getAxisLeft();
                        yAxis.setDrawAxisLine(true);
                        yAxis.setStartAtZero(true);
                        yAxis.setDrawGridLines(true);
                        yAxis.setGridColor(Color.BLACK);
                        yAxis.setTextColor(Color.WHITE);

                        mChart.invalidate();
                    }
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                progressBar.setVisibility(View.GONE);
                noDataMsg.setVisibility(View.VISIBLE);

                final boolean isCantGetExchangeRateException = (ex instanceof CantGetExchangeRateException);

                if (!isCantGetExchangeRateException) {
                    ErrorManager errorManager = session.getErrorManager();
                    if (errorManager != null)
                        errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET, DISABLES_THIS_FRAGMENT, ex);
                    else
                        Log.e(TAG, ex.getMessage(), ex);
                }
            }
        });

        fermatWorker.execute();
    }

    /**
     * Get the LineData for the chart based on the ExchangeRate list
     *
     * @param exchangeRates the exchange rate list
     * @return the ListData object
     */
    private LineData getData(List<ExchangeRate> exchangeRates) {
        ArrayList<Entry> entryList = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        final int size = exchangeRates.size();
        for (int i = 0; i < size; i++) {
            ExchangeRate exchangeRate = exchangeRates.get(i);
            entryList.add(new Entry((float) exchangeRate.getSalePrice(), i));
            xValues.add(String.valueOf(i));
        }

        LineDataSet dataSet = new LineDataSet(entryList, "dataSet");
        dataSet.setColor(Color.WHITE);
        dataSet.setDrawCircles(false);
        dataSet.setLineWidth(2.0f);
        dataSet.setDrawValues(false);

        return new LineData(xValues, dataSet);
    }

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
