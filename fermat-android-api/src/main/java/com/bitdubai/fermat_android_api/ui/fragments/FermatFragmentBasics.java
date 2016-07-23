package com.bitdubai.fermat_android_api.ui.fragments;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by mati on 2015.12.28..
 */
public interface FermatFragmentBasics<S extends ReferenceAppFermatSession, R extends ResourceProviderManager> {

    S getAppSession();

    R getResources();

}
