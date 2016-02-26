package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.earnings.EarningsDetailsFragment;


/**
 * Created by nelson on 26/02/16.
 */
public class EarningsDetailsPageAdapter extends FragmentStatePagerAdapter {

    private EarningsPair earningsPair;

    public EarningsDetailsPageAdapter(FragmentManager fragmentManager, EarningsPair earningsPair) {
        super(fragmentManager);

        this.earningsPair = earningsPair;
    }

    @Override
    public Fragment getItem(int position) {
        final EarningsDetailsFragment earningsActivityFragment = EarningsDetailsFragment.newInstance();

        switch (position) {
            case 0:
                earningsActivityFragment.bindData(earningsPair, TimeFrequency.DAILY);
                break;
            case 1:
                earningsActivityFragment.bindData(earningsPair, TimeFrequency.WEEKLY);
                break;
            case 2:
                earningsActivityFragment.bindData(earningsPair, TimeFrequency.MONTHLY);
                break;
            case 3:
                earningsActivityFragment.bindData(earningsPair, TimeFrequency.YEARLY);
                break;
            default:
                throw new IndexOutOfBoundsException();
        }

        return earningsActivityFragment;
    }

    public void changeDataSet(EarningsPair earningsPair){
        this.earningsPair = earningsPair;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
