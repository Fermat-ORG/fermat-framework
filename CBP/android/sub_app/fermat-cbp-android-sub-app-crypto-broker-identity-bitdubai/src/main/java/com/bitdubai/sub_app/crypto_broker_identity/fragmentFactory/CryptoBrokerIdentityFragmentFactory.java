package com.bitdubai.sub_app.crypto_broker_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.crypto_broker_identity.preference_settings.CryptoBrokerIdentityPreferenceSettings;
import com.bitdubai.sub_app.crypto_broker_identity.session.CryptoBrokerIdentitySubAppSession;
import com.bitdubai.sub_app.crypto_broker_identity.fragments.MainFragment;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoBrokerIdentityFragmentFactory extends FermatSubAppFragmentFactory<CryptoBrokerIdentitySubAppSession, CryptoBrokerIdentityPreferenceSettings, CryptoBrokerIdentityFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(CryptoBrokerIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {
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
    public CryptoBrokerIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoBrokerIdentityFragmentsEnumType.getValue(key);
    }

}
