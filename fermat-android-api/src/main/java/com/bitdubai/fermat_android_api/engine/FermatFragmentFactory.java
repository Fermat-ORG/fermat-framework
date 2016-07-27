package com.bitdubai.fermat_android_api.engine;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by Matias Furszyfer on 2015.11.21..
 */
public abstract class FermatFragmentFactory<S extends FermatSession, R extends ResourceProviderManager, F extends FermatFragmentsEnumType> implements AppFragmentFactory<S, R> {

    @Override
    public <T extends Fragment & AbstractFermatFragmentInterface, FF extends FermatFragment> T getFragment(String code, S AppsSession, R resourceProviderManager, FF fermatFragmentType) throws FragmentNotFoundException {
        F fragments = getFermatFragmentEnumType(code);
        T fermatFragment = getFermatFragment(fragments);
        fermatFragment.setAppSession(AppsSession);
        fermatFragment.setAppResourcesProviderManager(resourceProviderManager);
        fermatFragment.setFragmentType(fermatFragmentType);
        return fermatFragment;
    }

    protected abstract <T extends Fragment & AbstractFermatFragmentInterface> T getFermatFragment(F fragments) throws FragmentNotFoundException;

    public abstract F getFermatFragmentEnumType(String key);


}
