package com.bitdubai.fermat_android_api.ui.fragments;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by mati on 2015.12.28..
 */
public interface FermatFragmentBasics<S extends FermatSession,R extends ResourceProviderManager>{

    S getAppSession();

    R getResources();

}
