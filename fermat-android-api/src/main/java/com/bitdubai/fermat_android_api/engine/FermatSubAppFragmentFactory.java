package com.bitdubai.fermat_android_api.engine;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.SubAppSettings;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Created by mati on 2015.08.24..
 */
public abstract class FermatSubAppFragmentFactory <S extends SubAppsSession,J extends SubAppSettings,F extends FermatFragmentsEnumType> implements SubAppFragmentFactory<S,J> {

    protected FermatFragment fermatFragment;


    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code                           the reference used to identify the fragment
     * @param subAppsSession
     * @param subAppSettingsManager
     * @param subAppResourcesProviderManager @return the fragment referenced
     */
    @Override
    public Fragment getFragment(String code, S subAppsSession, J subAppSettingsManager, SubAppResourcesProviderManager subAppResourcesProviderManager) throws FragmentNotFoundException {

        F fragments = getFermatFragmentEnumType(code);

        fermatFragment = getFermatFragment(fragments);

        fermatFragment.setSubAppsSession(subAppsSession);
        fermatFragment.setSubAppSettings(subAppSettingsManager);
        fermatFragment.setSubAppResourcesProviderManager(subAppResourcesProviderManager);
        return fermatFragment;
    }

    public abstract FermatFragment getFermatFragment(F fragments) throws FragmentNotFoundException;

    public abstract F getFermatFragmentEnumType(String key);
}
