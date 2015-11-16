package com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.IndexInfoSummary;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home.MarketRateStatisticsFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * {@link FragmentStatePagerAdapter} for the fragment that show the market rates of different currencies
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 16/11/15.
 */
public class MarketExchangeRatesPageAdapter extends FragmentStatePagerAdapter {

    private final List<IndexInfoSummary> summaryList;

    public MarketExchangeRatesPageAdapter(FragmentManager fragmentManager, Collection<IndexInfoSummary> summaryList) {
        super(fragmentManager);


        this.summaryList = new ArrayList<>();
        this.summaryList.addAll(summaryList);
    }

    @Override
    public int getCount() {
        return summaryList.size();
    }

    @Override
    public Fragment getItem(int position) {
        MarketRateStatisticsFragment fragment = MarketRateStatisticsFragment.newInstance();
        IndexInfoSummary summary = summaryList.get(position);
        fragment.bind(summary);

        return fragment;
    }
}
