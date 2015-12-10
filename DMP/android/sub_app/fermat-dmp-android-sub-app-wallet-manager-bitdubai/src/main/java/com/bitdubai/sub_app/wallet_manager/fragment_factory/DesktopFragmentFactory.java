package com.bitdubai.sub_app.wallet_manager.fragment_factory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.DesktopSettings;
import com.bitdubai.sub_app.wallet_manager.fragment.DesktopFragment;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSession;


/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class DesktopFragmentFactory extends FermatFragmentFactory<DesktopSession, DesktopSettings, ResourceProviderManager,IntraUserIdentityFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(IntraUserIdentityFragmentsEnumType fragments) throws FragmentNotFoundException {

        if (fragments.equals(IntraUserIdentityFragmentsEnumType.CCP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT))
            return DesktopFragment.newInstance();


        throw createFragmentNotFoundException(fragments);
    }

    @Override
    public IntraUserIdentityFragmentsEnumType getFermatFragmentEnumType(String key) {
        return IntraUserIdentityFragmentsEnumType.getValue(key);
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
