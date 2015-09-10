package com.bitdubai.sub_app.wallet_factory.ui.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;


public class ProjectsFragment extends FermatFragment {

    /**
     * STATIC
     */
    private static final String ARG_POSITION = "position";
    /**
     * UI
     */
    private View rootView;

    /**
     * Wallet Manager
     */
    private WalletFactoryManager manager;


    public static ProjectsFragment newInstance() {
        ProjectsFragment f = new ProjectsFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            /*setting up manager*/
            manager = ((WalletFactorySubAppSession) subAppsSession).getWalletFactoryManager();
        } catch (Exception ex) {
            Log.getStackTraceString(ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       /* rootView = inflater.inflate(R.layout.wallet_factory_main_fragment, container, false);
        rootView.findViewById(R.id.text_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWizard(WizardTypes.CWP_WALLET_FACTORY_CREATE_NEW_PROJECT);
            }
        });*/
        return rootView;
    }

}
