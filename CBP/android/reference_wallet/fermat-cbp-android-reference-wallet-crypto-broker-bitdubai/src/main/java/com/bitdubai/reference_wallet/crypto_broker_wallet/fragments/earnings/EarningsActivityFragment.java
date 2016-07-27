package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.earnings;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantExtractEarningsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningTransactionsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningTransaction;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.EarningsCurrencyPairsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.EarningsDetailsPageAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.TestData;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets.CBP_CRYPTO_BROKER_WALLET;


/**
 * A simple {@link Fragment} subclass.
 */
public class EarningsActivityFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>, WalletResourcesProviderManager>
        implements FermatListItemListeners<EarningsPair> {

    // Constants
    private static final String TAG = "EarningsActivity";

    // Data
    private List<EarningsPair> earningsPairs;
    private EarningsPair selectedEarningsPair;

    // Fermat Managers
    private ErrorManager errorManager;
    private CryptoBrokerWalletModuleManager moduleManager;

    private FloatingActionButton floatingActionButton;
    private EarningsCurrencyPairsAdapter currencyPairsAdapter;
    private EarningsDetailsPageAdapter earningDetailsAdapter;
    private ViewPager earningDetailsViewPager;

    public static EarningsActivityFragment newInstance() {
        return new EarningsActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            earningsPairs = getEarningsPairs();

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET, DISABLES_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.cbw_fragment_earnings_activity, container, false);

        configureToolbar();

        if (earningsPairs.isEmpty()) {
            layout.findViewById(R.id.cbw_no_earnings_container).setVisibility(View.VISIBLE);
            layout.findViewById(R.id.cbw_earnings_container).setVisibility(View.GONE);
        } else {
            currencyPairsAdapter = new EarningsCurrencyPairsAdapter(getActivity(), earningsPairs);
            currencyPairsAdapter.setFermatListEventListener(this);

            floatingActionButton = (FloatingActionButton) layout.findViewById(R.id.cbw_extract_earnings_button);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    extractEarnings();
                }
            });

            final RecyclerView currencyPairsRecyclerView = (RecyclerView) layout.findViewById(R.id.cbw_earning_currency_pairs_recycler_view);
            currencyPairsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            currencyPairsRecyclerView.setAdapter(currencyPairsAdapter);


            final EarningsPair earningsPair = earningsPairs.get(0);
            earningDetailsAdapter = new EarningsDetailsPageAdapter(getFragmentManager(), earningsPair, appSession);

            earningDetailsViewPager = (ViewPager) layout.findViewById(R.id.cbw_earning_details_view_pager);
            earningDetailsViewPager.setOffscreenPageLimit(3);
            earningDetailsViewPager.setAdapter(earningDetailsAdapter);

            final LinePageIndicator indicator = (LinePageIndicator) layout.findViewById(R.id.cbw_earning_details_view_pager_indicator);
            indicator.setViewPager(earningDetailsViewPager);

            onItemClickListener(earningsPair, 0);
        }

        return layout;
    }

    @Override
    public void onItemClickListener(EarningsPair selectedEarningsPair, int position) {
        this.selectedEarningsPair = selectedEarningsPair;
        showOrHideExtractEarningsButton(selectedEarningsPair);

        currencyPairsAdapter.setSelectedItem(position);
        earningDetailsAdapter.changeDataSet(selectedEarningsPair);
        earningDetailsViewPager.setCurrentItem(0);
    }

    @Override
    public void onLongItemClickListener(EarningsPair data, int position) {
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    private List<EarningsPair> getEarningsPairs() {
        final List<EarningsPair> data = new ArrayList<>();

        try {
            //final List<EarningsPair> earningsPairs = TestData.getEarningsPairs(); // TODO: just for test purposes
            final List<EarningsPair> earningsPairs = moduleManager.getEarningsPairs(appSession.getAppPublicKey());
            data.addAll(earningsPairs);
        } catch (Exception ex) {
            data.addAll(TestData.getEarningsPairs());// TODO: just for test purposes
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

        return data;
    }

    private void showOrHideExtractEarningsButton(EarningsPair selectedEarningsPair) {
        boolean allExtracted = true;

        try {
            final List<EarningTransaction> earningTransactions = moduleManager.searchEarnings(selectedEarningsPair, EarningTransactionState.CALCULATED);
            allExtracted = earningTransactions.isEmpty();

        } catch (CantListEarningTransactionsException e) {
            errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            Toast.makeText(getActivity(), "Sorry. Cant Get the Earnings list", Toast.LENGTH_SHORT).show();
        }

        if (allExtracted)
            floatingActionButton.hide();
        else
            floatingActionButton.show();
    }

    private void extractEarnings() {
        if (selectedEarningsPair != null) {
            try {
                final List<EarningTransaction> earningTransactions = moduleManager.searchEarnings(selectedEarningsPair);
                boolean earningsExtracted = moduleManager.extractEarnings(selectedEarningsPair, earningTransactions);

                if (earningsExtracted)
                    Toast.makeText(getActivity(), "Earnings Extracted!", Toast.LENGTH_SHORT).show();

                showOrHideExtractEarningsButton(selectedEarningsPair);

            } catch (CantExtractEarningsException e) {
                errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                Toast.makeText(getActivity(), "Sorry. Cant Extract the Earnings", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                Toast.makeText(getActivity(), "Sorry. Unexpected Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
