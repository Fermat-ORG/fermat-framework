package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;


import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by Matias Furszyfer on 2015.11.21..
 */
public interface AppFragmentFactory<S extends FermatSession, R extends ResourceProviderManager> {

    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code the reference used to identify the fragment
     * @return the fragment referenced
     */
    <T extends Fragment & AbstractFermatFragmentInterface, FF extends FermatFragment> T getFragment(String code, S AppsSession, R resourceProviderManager, FF fermatFragmentType) throws FragmentNotFoundException;

}
