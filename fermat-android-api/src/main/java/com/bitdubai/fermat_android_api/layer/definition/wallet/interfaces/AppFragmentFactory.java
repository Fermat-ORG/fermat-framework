package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by mati on 2015.11.21..
 */
public interface AppFragmentFactory<S extends FermatSession,R extends ResourceProviderManager> {

    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code the reference used to identify the fragment
     * @return the fragment referenced
     */
    android.app.Fragment getFragment(String code, S AppsSession,R resourceProviderManager) throws FragmentNotFoundException;

}
