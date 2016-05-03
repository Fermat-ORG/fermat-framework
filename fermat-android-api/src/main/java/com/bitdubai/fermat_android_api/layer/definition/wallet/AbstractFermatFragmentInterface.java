package com.bitdubai.fermat_android_api.layer.definition.wallet;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by mati on 2016.02.08..
 */
public interface AbstractFermatFragmentInterface<S extends FermatSession,R extends ResourceProviderManager> {


    void setAppSession(S appSession);

    void setAppResourcesProviderManager(R appResourcesProviderManager);

    void onUpdateViewOnUIThread(String code);
    void onUpdateView(String code);

}
