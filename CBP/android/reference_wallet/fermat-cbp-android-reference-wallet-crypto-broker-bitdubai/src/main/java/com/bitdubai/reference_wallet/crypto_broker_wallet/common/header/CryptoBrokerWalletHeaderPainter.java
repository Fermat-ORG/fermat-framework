package com.bitdubai.reference_wallet.crypto_broker_wallet.common.header;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.MarketExchangeRatesPageAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Paint the header of the home, this is, a chart with the market exchange rate for the selected providers
 *
 * @author Nelson Ramirez
 * @version 1.1
 * @since 17/12/2015
 */
public class CryptoBrokerWalletHeaderPainter implements HeaderViewPainter {
    private final String TAG = "BrokerWalletHeader";

    private final CryptoBrokerWalletSession session;
    private final Activity activity;


    public CryptoBrokerWalletHeaderPainter(Activity activity, CryptoBrokerWalletSession fullyLoadedSession) {
        this.activity = activity;
        session = fullyLoadedSession;
    }

    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        View container = activity.getLayoutInflater().inflate(R.layout.cbw_header_layout, viewGroup, true);
        ProgressBar progressBar = (ProgressBar) container.findViewById(R.id.cbw_header_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        getAndShowMarketExchangeRateData(container, progressBar);
    }

    private void getAndShowMarketExchangeRateData(final View container, final ProgressBar progressBar) {
        final CryptoBrokerWalletModuleManager moduleManager = session.getModuleManager();

        FermatWorker fermatWorker = new FermatWorker(activity) {
            @Override
            protected Object doInBackground() throws Exception {
                List<IndexInfoSummary> data = new ArrayList<>();
                CryptoBrokerWalletManager wallet = moduleManager.getCryptoBrokerWallet(session.getAppPublicKey());
                data.addAll(wallet.getCurrentIndexSummaryForStockCurrencies(session.getAppPublicKey()));

                return data;
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                if (result != null && result.length > 0) {
                    List<IndexInfoSummary> summaries = (List<IndexInfoSummary>) result[0];

                    progressBar.setVisibility(View.GONE);

                    if (summaries.isEmpty()) {
                        FermatTextView noMarketRateTextView = (FermatTextView) container.findViewById(R.id.cbw_no_market_rate);
                        noMarketRateTextView.setVisibility(View.VISIBLE);

                    } else {
                        View marketRateViewPagerContainer = container.findViewById(R.id.cbw_market_rate_view_pager_container);
                        marketRateViewPagerContainer.setVisibility(View.VISIBLE);

                        ViewPager viewPager = (ViewPager) container.findViewById(R.id.cbw_exchange_rate_view_pager);
                        viewPager.setOffscreenPageLimit(3);
                        MarketExchangeRatesPageAdapter pageAdapter = new MarketExchangeRatesPageAdapter(activity.getFragmentManager(), summaries);
                        viewPager.setAdapter(pageAdapter);

                        LinePageIndicator indicator = (LinePageIndicator) container.findViewById(R.id.cbw_exchange_rate_view_pager_indicator);
                        indicator.setViewPager(viewPager);
                    }
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                progressBar.setVisibility(View.GONE);

                FermatTextView noMarketRateTextView = (FermatTextView) container.findViewById(R.id.cbw_no_market_rate);
                noMarketRateTextView.setVisibility(View.VISIBLE);

                ErrorManager errorManager = session.getErrorManager();
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                else
                    Log.e(TAG, ex.getMessage(), ex);
            }
        });

        Executors.newSingleThreadExecutor().execute(fermatWorker);
    }
}
