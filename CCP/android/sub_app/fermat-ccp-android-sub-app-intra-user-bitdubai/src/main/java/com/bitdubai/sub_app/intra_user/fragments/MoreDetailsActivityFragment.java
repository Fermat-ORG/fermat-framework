package com.bitdubai.sub_app.intra_user.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.ccp_module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_api.layer.ccp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.intra_user.session.IntraUserSubAppSession;
import com.intra_user.bitdubai.R;

/**
 * Fragment que luce como un Activity donde se muestra mas informacion sobre la Wallet mostrada en DetailsActivityFragment
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class MoreDetailsActivityFragment extends FermatFragment {
    // MANAGERS
    private IntraUserModuleManager moduleManager;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static MoreDetailsActivityFragment newInstance() {
        MoreDetailsActivityFragment f = new MoreDetailsActivityFragment();
        return f;
    }


    @Override
    public void setSubAppsSession(SubAppsSession subAppsSession) {
        super.setSubAppsSession(subAppsSession);

        IntraUserSubAppSession session = (IntraUserSubAppSession) subAppsSession;
        moduleManager = session.getIntraUserModuleManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.wallet_store_fragment_more_details_activity, container, false);
    }
}
