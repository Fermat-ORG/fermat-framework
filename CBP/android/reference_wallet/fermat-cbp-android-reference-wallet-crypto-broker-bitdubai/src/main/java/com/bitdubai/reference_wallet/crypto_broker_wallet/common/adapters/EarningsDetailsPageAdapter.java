package com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.earnings.EarningsDetailsFragment;

import java.util.HashMap;


/**
 * Created by nelson on 26/02/16.
 */
public class EarningsDetailsPageAdapter extends FragmentStatePagerAdapter {

    private HashMap<Integer, EarningsDetailsFragment> map;
    private EarningsPair earningsPair;
    private ReferenceAppFermatSession<CryptoBrokerWalletModuleManager> session;
    private boolean earningsPairChanged;


    public EarningsDetailsPageAdapter(FragmentManager fragmentManager,
                                      EarningsPair earningsPair,
                                      ReferenceAppFermatSession<CryptoBrokerWalletModuleManager> session) {
        super(fragmentManager);

        this.earningsPair = earningsPair;
        this.session = session;
        map = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        EarningsDetailsFragment earningsDetailsFragment = map.get(position);
        if (earningsDetailsFragment == null) {
            earningsDetailsFragment = EarningsDetailsFragment.newInstance(session);
            map.put(position, earningsDetailsFragment);
        }

        switch (position) {
            case 0:
                earningsDetailsFragment.bindData(earningsPair, TimeFrequency.DAILY);
                break;
            case 1:
                earningsDetailsFragment.bindData(earningsPair, TimeFrequency.WEEKLY);
                break;
            case 2:
                earningsDetailsFragment.bindData(earningsPair, TimeFrequency.MONTHLY);
                break;
            case 3:
                earningsDetailsFragment.bindData(earningsPair, TimeFrequency.YEARLY);
                earningsPairChanged = false;
                break;
            default:
                throw new IndexOutOfBoundsException();
        }

        return earningsDetailsFragment;
    }

    public void changeDataSet(EarningsPair earningsPair) {
        this.earningsPair = earningsPair;
        earningsPairChanged = true;

        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        if (earningsPairChanged)
            return POSITION_NONE;

        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
