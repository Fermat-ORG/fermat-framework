package com.bitdubai.sub_app.crypto_customer_community.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.crypto_customer_community.fragments.MainFragment;
import com.bitdubai.sub_app.crypto_customer_community.preference_settings.CryptoCustomerCommunityPreferenceSettings;
import com.bitdubai.sub_app.crypto_customer_community.session.CryptoCustomerCommunitySubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerCommunityFragmentFactory extends FermatSubAppFragmentFactory<CryptoCustomerCommunitySubAppSession, CryptoCustomerCommunityPreferenceSettings, CryptoCustomerCommunityFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(CryptoCustomerCommunityFragmentsEnumType fragments) throws FragmentNotFoundException {
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
    public CryptoCustomerCommunityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoCustomerCommunityFragmentsEnumType.getValue(key);
    }

}
