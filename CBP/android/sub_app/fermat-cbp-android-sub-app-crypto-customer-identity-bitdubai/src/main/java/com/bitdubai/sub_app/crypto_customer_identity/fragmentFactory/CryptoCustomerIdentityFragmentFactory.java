package com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.crypto_customer_identity.fragments.CreateCryptoCustomerIdentityFragment;
import com.bitdubai.sub_app.crypto_customer_identity.fragments.CryptoCustomerIdentityListFragment;
import com.bitdubai.sub_app.crypto_customer_identity.fragments.EditCryptoCustomerIdentityFragment;
import com.bitdubai.sub_app.crypto_customer_identity.preference_settings.CryptoCustomerIdentityPreferenceSettings;
import com.bitdubai.sub_app.crypto_customer_identity.session.CryptoCustomerIdentitySubAppSession;

import static com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory.CryptoCustomerIdentityFragmentsEnumType.*;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoCustomerIdentityFragmentFactory extends FermatSubAppFragmentFactory<CryptoCustomerIdentitySubAppSession, CryptoCustomerIdentityPreferenceSettings, CryptoCustomerIdentityFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(CryptoCustomerIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {

        if (fragments == CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT)
            return CryptoCustomerIdentityListFragment.newInstance();

        if (fragments == CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT)
            return CreateCryptoCustomerIdentityFragment.newInstance();

        if (fragments == CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT)
            return EditCryptoCustomerIdentityFragment.newInstance();

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
