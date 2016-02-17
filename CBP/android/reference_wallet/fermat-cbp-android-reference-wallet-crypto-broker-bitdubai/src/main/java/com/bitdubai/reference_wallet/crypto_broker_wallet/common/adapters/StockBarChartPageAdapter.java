package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.StockStatisticsData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home.StockStatisticsFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StockBarChartPageAdapter extends FragmentStatePagerAdapter {
    private final List<StockStatisticsData> summaryList;

    public StockBarChartPageAdapter(FragmentManager fragmentManager, Collection<StockStatisticsData> testData) {
        super(fragmentManager);
        this.summaryList = new ArrayList<>();
        this.summaryList.addAll(testData);
    }

    @Override
    public int getCount() {
        return summaryList.size();
    }

    @Override
    public android.app.Fragment getItem(int position) {
        StockStatisticsFragment fragment = StockStatisticsFragment.newInstance();
        StockStatisticsData summary = summaryList.get(position);
        fragment.bind(summary);

        return fragment;
    }
}