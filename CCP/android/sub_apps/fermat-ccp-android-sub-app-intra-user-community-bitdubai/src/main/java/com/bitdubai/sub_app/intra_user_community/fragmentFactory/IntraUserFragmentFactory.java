package com.bitdubai.sub_app.intra_user_community.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.intra_user.fragments.ConnectionsListFragment;
import com.bitdubai.sub_app.intra_user.fragments.ConnectionsRequestListFragment;
import com.bitdubai.sub_app.intra_user.fragments.ConnectionsWorldFragment;
import com.bitdubai.sub_app.intra_user.fragments.DetailsActivityFragment;
import com.bitdubai.sub_app.intra_user.fragments.HomeFragment;
import com.bitdubai.sub_app.intra_user.fragments.MoreDetailsActivityFragment;
import com.bitdubai.sub_app.intra_user.fragments.RegisterIntraUserFragment;
import com.bitdubai.sub_app.intra_user.preference_settings.IntraUserPreferenceSettings;
import com.bitdubai.sub_app.intra_user.session.IntraUserSubAppSession;


/**
 * Created by Matias Furszyfer on 31/8/2015.
 */

public class IntraUserFragmentFactory extends FermatSubAppFragmentFactory<IntraUserSubAppSession, IntraUserPreferenceSettings, IntraUserFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(IntraUserFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;

        switch (fragments) {
            case CWP_WALLET_STORE_MAIN_ACTIVITY:
                currentFragment = HomeFragment.newInstance(1);
                break;
            case CWP_WALLET_STORE_DETAIL_ACTIVITY:
                //currentFragment = ConnectionsListFragment.newInstance();
                break;
            case CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY:
                //currentFragment = ConnectionsListFragment.newInstance();
                break;
            case CWP_WALLET_STORE_ALL_FRAGMENT:
                currentFragment = ConnectionsWorldFragment.newInstance();
                //currentFragment = ConnectionsListFragment.newInstance();
                break;
            case CWP_WALLET_STORE_FREE_FRAGMENT:
                currentFragment = RegisterIntraUserFragment.newInstance();
                break;
            case CWP_WALLET_STORE_PAID_FRAGMENT:
                //currentFragment = ConnectionsRequestListFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.toString(), "Swith failed");
        }
        return currentFragment;
    }

    @Override
    public IntraUserFragmentsEnumType getFermatFragmentEnumType(String key) {
        return IntraUserFragmentsEnumType.getValue(key);
    }

}
