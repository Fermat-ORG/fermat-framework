package com.bitdubai.desktop.sub_app_manager.fragmentFactory;

import com.bitdubai.desktop.sub_app_manager.fragments.MainFragment;
import com.bitdubai.desktop.sub_app_manager.preference_settings.SubAppManagerPreferenceSettings;
import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.desktop.sub_app_manager.session.SubAppManagerSubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class SubAppManagerFragmentFactory extends FermatSubAppFragmentFactory<SubAppManagerSubAppSession, SubAppManagerPreferenceSettings, SubAppManagerFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(SubAppManagerFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;

        switch (fragments) {
            case MAIN_FRAGMET:
                currentFragment = MainFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.toString(), "Swith failed");
        }
        return currentFragment;
    }

    @Override
    public SubAppManagerFragmentsEnumType getFermatFragmentEnumType(String key) {
        return SubAppManagerFragmentsEnumType.getValue(key);
    }

}
