package com.bitdubai.fermat_android_api.layer.definition.wallet;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;


import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WizardConfiguration;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.interfaces.FermatSettings;

/**
 * Created by Matias Furszyfer on 2015.11.21..
 */
public abstract class AbstractFermatFragment<S extends FermatSession,R extends ResourceProviderManager> extends Fragment{

    /**
     * FLAGS
     */
    protected boolean isAttached;

    /**
     * Platform
     */
    protected S appSession;
    protected R appResourcesProviderManager;


    /**
     * ViewInflater
     */
    protected ViewInflater viewInflater;
    private WizardConfiguration context;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            context = (WizardConfiguration) getActivity();
            viewInflater = new ViewInflater(getActivity(), appResourcesProviderManager);
        } catch (Exception ex) {
            throw new ClassCastException("cannot convert the current context to WizardConfiguration");
        }
    }


    /**
     * Start a configuration Wizard
     *
     * @param key  Enum Wizard registered type
     * @param args Object[] where you're be able to passing arguments like session, settings, resources, module, etc...
     */
    protected void startWizard(String key, Object... args) {
        if (context != null && isAttached) {
            context.showWizard(key, args);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        isAttached = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isAttached = false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    public void setAppSession(S appSession) {
        this.appSession = appSession;
    }

    public void setAppResourcesProviderManager(R appResourcesProviderManager) {
        this.appResourcesProviderManager = appResourcesProviderManager;
    }

}
