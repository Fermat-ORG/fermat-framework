package com.bitdubai.desktop.sub_app_manager.fragmentFactory;

import com.bitdubai.desktop.sub_app_manager.fragments.MainFragment;
import com.bitdubai.desktop.sub_app_manager.preference_settings.SubAppManagerPreferenceSettings;
import com.bitdubai.desktop.sub_app_manager.session.SubAppManagerSubAppSession;
import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;

import static com.bitdubai.desktop.sub_app_manager.fragmentFactory.SubAppManagerFragmentsEnumType.MAIN_FRAGMET;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class SubAppManagerFragmentFactory extends FermatSubAppFragmentFactory<SubAppManagerSubAppSession, SubAppManagerPreferenceSettings, SubAppManagerFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(SubAppManagerFragmentsEnumType fragments) throws FragmentNotFoundException {

        if (fragments == MAIN_FRAGMET) {
            return MainFragment.newInstance();
        }

        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public SubAppManagerFragmentsEnumType getFermatFragmentEnumType(String key) {
        return SubAppManagerFragmentsEnumType.getValue(key);
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
