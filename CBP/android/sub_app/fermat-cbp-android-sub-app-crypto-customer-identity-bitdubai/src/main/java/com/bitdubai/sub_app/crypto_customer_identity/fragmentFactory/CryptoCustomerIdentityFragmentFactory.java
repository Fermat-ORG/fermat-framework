package com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.crypto_customer_identity.fragments.MainFragment;
import com.bitdubai.sub_app.crypto_customer_identity.preference_settings.CryptoCustomerIdentityPreferenceSettings;
import com.bitdubai.sub_app.crypto_customer_identity.session.CryptoCustomerIdentitySubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerIdentityFragmentFactory extends FermatSubAppFragmentFactory<CryptoCustomerIdentitySubAppSession, CryptoCustomerIdentityPreferenceSettings, CryptoCustomerIdentityFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(CryptoCustomerIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {
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
    public CryptoCustomerIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoCustomerIdentityFragmentsEnumType.getValue(key);
    }

}
