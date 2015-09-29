package com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.crypto_customer_identity.fragments.MainFragment;
import com.bitdubai.sub_app.crypto_customer_identity.preference_settings.CryptoCustomerIdentityPreferenceSettings;
import com.bitdubai.sub_app.crypto_customer_identity.session.CryptoCustomerIdentitySubAppSession;

import static com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory.CryptoCustomerIdentityFragmentsEnumType.MAIN_FRAGMET;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerIdentityFragmentFactory extends FermatSubAppFragmentFactory<CryptoCustomerIdentitySubAppSession, CryptoCustomerIdentityPreferenceSettings, CryptoCustomerIdentityFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(CryptoCustomerIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {

        if (fragments == MAIN_FRAGMET) {
            return MainFragment.newInstance();
        }

        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public CryptoCustomerIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoCustomerIdentityFragmentsEnumType.getValue(key);
    }


    private FragmentNotFoundException createFragmentNotFoundException(FermatFragmentsEnumType fragments) {
        String possibleReason, context;

        if (fragments == null) {
            possibleReason = "The parameter 'fragments' is NULL";
            context = "Null Value";
        } else {
            possibleReason = "Not found in switch block";
            context = fragments.toString();
        }

        return new FragmentNotFoundException("Fragment not found", new Exception(), context, possibleReason);
    }

}
