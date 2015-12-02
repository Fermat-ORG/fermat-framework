package com.bitdubai.sub_app.intra_user_community.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;

/**
 * Created by josemanueldsds on 01/12/15.
 */
public class ConnectionSettingsFragment extends FermatFragment {


    private View rootView;
    private IntraUserSubAppSession intraUserSubAppSession;
    private IntraUserModuleManager moduleManager;
    private ErrorManager errorManager;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static ConnectionSettingsFragment newInstance() {
        return new ConnectionSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting up  module
        intraUserSubAppSession = ((IntraUserSubAppSession) subAppsSession);
        moduleManager = intraUserSubAppSession.getModuleManager();
        errorManager = subAppsSession.getErrorManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.intra_user_settings, container, false);
        return rootView;
    }
}
