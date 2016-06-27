package com.bitdubai.reference_wallet.crypto_customer_wallet.common.header;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bitdubai.fermat_android_api.engine.HeaderViewPainter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.MarketExchangeRatesPageAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;
import com.viewpagerindicator.LinePageIndicator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by nelson on 17/12/15.
 */
public class CryptoCustomerWalletHeaderPainter implements HeaderViewPainter {
    private final String TAG = "CustomerWalletHeader";

    private final ReferenceAppFermatSession<CryptoCustomerWalletModuleManager> session;
    private final WeakReference<Context> activity;
    private CryptoCustomerWalletModuleManager moduleManager;
    private ErrorManager errorManager;


    public CryptoCustomerWalletHeaderPainter(Context activity, ReferenceAppFermatSession<CryptoCustomerWalletModuleManager> session) {
        this.session = session;
        this.activity = new WeakReference<>(activity);

        try {
            errorManager = session.getErrorManager();
            moduleManager = session.getModuleManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            else
                Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void addExpandableHeader(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) activity.get().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View container = (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) ?
                layoutInflater.inflate(R.layout.ccw_header_layout_pre_lollipop, viewGroup, true) :
                layoutInflater.inflate(R.layout.ccw_header_layout, viewGroup, true);

        ProgressBar progressBar = (ProgressBar) container.findViewById(R.id.ccw_header_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        getAndShowMarketExchangeRateData(container, progressBar);
    }

    private void getAndShowMarketExchangeRateData(final View container, final ProgressBar progressBar) {

        //TODO: hacerlo con el contexto
        FermatWorker fermatWorker = new FermatWorker(activity.get()) {
            @Override
            protected Object doInBackground() throws Exception {
                List<IndexInfoSummary> data = new ArrayList<>();
                data.addAll(moduleManager.getProvidersCurrentExchangeRates(session.getAppPublicKey()));

                return data;
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                if (result != null && result.length > 0) {
                    List<IndexInfoSummary> summaries = (List<IndexInfoSummary>) result[0];
                    session.setData(FragmentsCommons.EXCHANGE_RATES, summaries);

                    progressBar.setVisibility(View.GONE);

                    if (summaries.isEmpty()) {
                        FermatTextView noMarketRateTextView = (FermatTextView) container.findViewById(R.id.ccw_no_market_rate);
                        noMarketRateTextView.setVisibility(View.VISIBLE);

                    } else {
                        View marketRateViewPagerContainer = container.findViewById(R.id.ccw_market_rate_view_pager_container);
                        marketRateViewPagerContainer.setVisibility(View.VISIBLE);

                        final FragmentManager fragmentManager = ((Activity) activity.get()).getFragmentManager();
                        MarketExchangeRatesPageAdapter pageAdapter = new MarketExchangeRatesPageAdapter(fragmentManager, summaries);

                        ViewPager viewPager = (ViewPager) container.findViewById(R.id.ccw_exchange_rate_view_pager);
                        viewPager.setOffscreenPageLimit(3);
                        viewPager.setAdapter(pageAdapter);

                        LinePageIndicator indicator = (LinePageIndicator) container.findViewById(R.id.ccw_exchange_rate_view_pager_indicator);
                        indicator.setViewPager(viewPager);
                    }
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                progressBar.setVisibility(View.GONE);

                FermatTextView noMarketRateTextView = (FermatTextView) container.findViewById(R.id.ccw_no_market_rate);
                noMarketRateTextView.setVisibility(View.VISIBLE);

                ErrorManager errorManager = session.getErrorManager();
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                else
                    Log.e(TAG, ex.getMessage(), ex);
            }
        });

        fermatWorker.execute();
    }
}
