package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.earnings;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsDetailsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPairDetail;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSearch;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.EarningsOverviewAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets.CBP_CRYPTO_BROKER_WALLET;
import static com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
import static com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 */
public class EarningsDetailsFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, WalletResourcesProviderManager> {

    // Constants
    private static final String TAG = "EarningsDetailsFrag";

    // Data
    private EarningsPair earningsPair;
    private TimeFrequency frequency;
    private List<EarningsPairDetail> data;

    // Fermat Managers
    private ErrorManager errorManager;


    public static EarningsDetailsFragment newInstance() {
        return new EarningsDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            errorManager = appSession.getErrorManager();

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
        final View layout = inflater.inflate(R.layout.cbw_fragment_earnings_details, container, false);

        configureToolbar();

        final NumberFormat instance = DecimalFormat.getInstance();
        final EarningsPairDetail currentEarning = data.isEmpty() ? null : data.get(0);
        final EarningsPairDetail previousEarning = data.isEmpty() ? null : data.get(1);

        final FermatTextView currentEarningValue = (FermatTextView) layout.findViewById(R.id.cbw_current_earning_value);
        currentEarningValue.setText(currentEarning != null ? instance.format(currentEarning.getAmount()) : "No current earnings");

        final FermatTextView currentEarningText = (FermatTextView) layout.findViewById(R.id.cbw_current_earning_text);
        currentEarningText.setText(getCurrentEarningText(frequency));

        final FermatTextView previousEarningValue = (FermatTextView) layout.findViewById(R.id.cbw_previous_earning_value);
        previousEarningValue.setText(previousEarning != null ? instance.format(previousEarning.getAmount()) : "No previous earnings");

        final FermatTextView previousEarningText = (FermatTextView) layout.findViewById(R.id.cbw_previous_earning_text);
        previousEarningText.setText(getPreviousEarningText(frequency));

        final FermatTextView timeFieldTextView = (FermatTextView) layout.findViewById(R.id.cbw_earning_time_field);
        timeFieldTextView.setText(frequency.getFriendlyName());

        final EarningsOverviewAdapter earningsOverviewAdapter = new EarningsOverviewAdapter(getActivity(), data, earningsPair.getEarningCurrency());
        final RecyclerView earningsOverviewRecyclerView = (RecyclerView) layout.findViewById(R.id.earning_overview_recycler_view);
        earningsOverviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        earningsOverviewRecyclerView.setAdapter(earningsOverviewAdapter);

        return layout;
    }

    private void bindData(EarningsPair earningsPair, TimeFrequency frequency) {
        this.earningsPair = earningsPair;
        this.frequency = frequency;

        final Currency earningCurrency = earningsPair.getEarningCurrency();

        final EarningsSearch search = earningsPair.getSearch();
        search.setTimeFrequency(frequency);

        try {
            data = search.listResults();
        } catch (CantListEarningsDetailsException e) {
            //TODO: just for test purposes
            data = TestData.getEarnings(earningCurrency, frequency);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            else
                Log.e(TAG, e.getMessage(), e);
        }
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

    private String getCurrentEarningText(TimeFrequency frequency) {
        switch (frequency) {
            case DAILY:
                return "Today";
            case WEEKLY:
                return "This Week";
            case MONTHLY:
                return "This Month";
            case YEARLY:
                return "This Year";
        }
        return "";
    }

    private String getPreviousEarningText(TimeFrequency frequency) {
        switch (frequency) {
            case DAILY:
                return "Yesterday";
            case WEEKLY:
                return "Last Week";
            case MONTHLY:
                return "Last Month";
            case YEARLY:
                return "Last Year";
        }
        return "";
    }
}
