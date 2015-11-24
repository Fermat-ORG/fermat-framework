package com.bitdubai.fermat_android_api.engine;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.abstracts.AbstractFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.AppFragmentFactory;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.FermatSettings;

/**
 * Created by Matias Furszyfer on 2015.11.21..
 */
public abstract class FermatFragmentFactory  <S extends AbstractFermatSession,J extends FermatSettings,R extends ResourceProviderManager,F extends FermatFragmentsEnumType> implements AppFragmentFactory<S,J,R> {

    protected AbstractFermatFragment<S,J,R> fermatFragment;


    @Override
    public Fragment getFragment(String code, S AppsSession, J settingsManager, R resourceProviderManager) throws FragmentNotFoundException {
        F fragments = getFermatFragmentEnumType(code);
        fermatFragment = getFermatFragment(fragments);
        fermatFragment.setSubAppsSession(AppsSession);
        fermatFragment.setSubAppSettings(settingsManager);
        fermatFragment.setSubAppResourcesProviderManager(resourceProviderManager);
        return fermatFragment;
    }

    public abstract FermatFragment getFermatFragment(F fragments) throws FragmentNotFoundException;

    public abstract F getFermatFragmentEnumType(String key);


}
