package com.bitdubai.sub_app.customers.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.customers.fragments.MainFragment;
import com.bitdubai.sub_app.customers.preference_settings.CustomersPreferenceSettings;
import com.bitdubai.sub_app.customers.session.CustomersSubAppSession;


public class CustomersFragmentFactory extends FermatSubAppFragmentFactory<CustomersSubAppSession, CustomersPreferenceSettings, CustomersFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(CustomersFragmentsEnumType fragments) throws FragmentNotFoundException {
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
    public CustomersFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CustomersFragmentsEnumType.getValue(key);
    }

}
