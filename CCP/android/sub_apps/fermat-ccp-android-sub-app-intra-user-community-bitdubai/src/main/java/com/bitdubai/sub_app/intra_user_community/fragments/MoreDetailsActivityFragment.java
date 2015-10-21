package com.bitdubai.sub_app.intra_user_community.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user_community.R;

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
