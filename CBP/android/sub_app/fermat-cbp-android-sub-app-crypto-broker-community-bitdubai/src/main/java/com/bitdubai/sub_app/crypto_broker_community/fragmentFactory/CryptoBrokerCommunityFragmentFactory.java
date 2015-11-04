package com.bitdubai.sub_app.crypto_broker_community.fragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.crypto_broker_community.fragments.MainFragment;
import com.bitdubai.sub_app.crypto_broker_community.preference_settings.CryptoBrokerCommunityPreferenceSettings;
import com.bitdubai.sub_app.crypto_broker_community.session.CryptoBrokerCommunitySubAppSession;

import static com.bitdubai.sub_app.crypto_broker_community.fragmentFactory.CryptoBrokerCommunityFragmentsEnumType.MAIN_FRAGMET;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class CryptoBrokerCommunityFragmentFactory extends FermatSubAppFragmentFactory<CryptoBrokerCommunitySubAppSession, CryptoBrokerCommunityPreferenceSettings, CryptoBrokerCommunityFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(CryptoBrokerCommunityFragmentsEnumType fragments) throws FragmentNotFoundException {

        if (fragments == MAIN_FRAGMET) {
            return MainFragment.newInstance();
        }

        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public CryptoBrokerCommunityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return CryptoBrokerCommunityFragmentsEnumType.getValue(key);
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
