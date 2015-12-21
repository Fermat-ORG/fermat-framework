package com.bitdubai.fermat_android_api.engine;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by Matias Furszyfer on 2015.11.21..
 */
public abstract class FermatFragmentFactory  <S extends FermatSession,R extends ResourceProviderManager,F extends FermatFragmentsEnumType> implements AppFragmentFactory<S,R> {

    protected AbstractFermatFragment<S,R> fermatFragment;

    @Override
    public Fragment getFragment(String code, S AppsSession, R resourceProviderManager) throws FragmentNotFoundException {
        F fragments = getFermatFragmentEnumType(code);
        fermatFragment = getFermatFragment(fragments);
        fermatFragment.setAppSession(AppsSession);
        fermatFragment.setAppResourcesProviderManager(resourceProviderManager);
        return fermatFragment;
    }

    protected abstract AbstractFermatFragment getFermatFragment(F fragments) throws FragmentNotFoundException;

    public abstract F getFermatFragmentEnumType(String key);


}
