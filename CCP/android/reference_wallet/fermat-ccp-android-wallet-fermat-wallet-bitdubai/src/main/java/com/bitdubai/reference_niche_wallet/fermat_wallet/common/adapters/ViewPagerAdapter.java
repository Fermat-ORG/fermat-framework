package com.bitdubai.reference_niche_wallet.fermat_wallet.common.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_ccp_api.all_definition.ExchangeRateProvider;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version.ViewPagerFragment;

import java.util.List;

/**
 * Created by root on 28/06/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int NUM_ITEMS = 0;
    private List<ExchangeRateProvider> exchangeRateProvidersList;
    private FermatWallet moduleManager;
    private ReferenceAppFermatSession<FermatWallet> fermatWalletSession;

    public ViewPagerAdapter(FragmentManager fragmentManager, List<ExchangeRateProvider> exchangeRateProvidersList, ReferenceAppFermatSession<FermatWallet> fermatWalletSession) {
        super(fragmentManager);
        this.exchangeRateProvidersList = exchangeRateProvidersList;
        this.fermatWalletSession = fermatWalletSession;

    }

    // Returns total number of pages
    @Override
    public int getCount() {
        NUM_ITEMS = exchangeRateProvidersList.size();
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

        if (position>=0)
            return ViewPagerFragment.newInstance(
                    position,
                    exchangeRateProvidersList.get(position).getProviderName(),
                    exchangeRateProvidersList.get(position).getProviderId(),
                    FiatCurrency.US_DOLLAR.getCode(),
                    this.fermatWalletSession
                    );
        else return null;
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return exchangeRateProvidersList.get(position).getProviderName();
    }
}
