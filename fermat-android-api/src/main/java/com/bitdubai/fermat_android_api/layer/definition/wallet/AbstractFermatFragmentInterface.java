package com.bitdubai.fermat_android_api.layer.definition.wallet;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatFragment;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;

/**
 * Created by Matias Furszyfer on 2016.02.08..
 */
public interface AbstractFermatFragmentInterface<S extends FermatSession, R extends ResourceProviderManager> {


    void setAppSession(S appSession);

    void setAppResourcesProviderManager(R appResourcesProviderManager);

    void setFragmentType(FermatFragment fermatFragmentType);

    /**
     * Methods for view updates
     */
    void onUpdateViewOnUIThread(FermatBundle code);

    void onUpdateView(FermatBundle code);

    void onUpdateViewOnUIThread(String code);

    /**
     * This method is called when the user press the back button
     */
    void onBackPressed();

    /**
     * This method is called when the fragment is on user's focus
     */
    void setFragmentFocus(boolean isVisible);
}
