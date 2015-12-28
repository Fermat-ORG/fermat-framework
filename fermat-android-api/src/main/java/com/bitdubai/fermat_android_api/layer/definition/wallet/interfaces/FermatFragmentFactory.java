package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;

/**
 * Created by Matias Furszyfer on 2015.12.09..
 */
public interface FermatFragmentFactory <S extends FermatSession,J extends FermatSettings,R extends ResourceProviderManager>{

    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *
     * @param code the reference used to identify the fragment
     * @return the fragment referenced
     */
    public Fragment getFragment(String code,S walletSession,J WalletSettings,R walletResourcesProviderManager) throws FragmentNotFoundException;

}
