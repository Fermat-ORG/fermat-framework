package com.bitdubai.sub_app.crypto_broker_community.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.crypto_broker_community.fragments.MainFragment;
import com.bitdubai.sub_app.crypto_broker_community.preference_settings.CryptoBrokerCommunityPreferenceSettings;
import com.bitdubai.sub_app.crypto_broker_community.session.CryptoBrokerCommunitySubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoBrokerCommunityFragmentFactory extends FermatSubAppFragmentFactory<CryptoBrokerCommunitySubAppSession, CryptoBrokerCommunityPreferenceSettings, CryptoBrokerCommunityFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(CryptoBrokerCommunityFragmentsEnumType fragments) throws FragmentNotFoundException {
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
    public CryptoBrokerCommunityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoBrokerCommunityFragmentsEnumType.getValue(key);
    }

}
