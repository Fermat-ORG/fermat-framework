package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.Session;
import com.bitdubai.fermat_dap_api.layer.dap_sub_app_module.asset_user_community.interfaces.AssetUserCommunitySubAppModuleManager;

/**
 * Created by francisco on 14/10/15.
 */
public class HomeFragment extends FermatFragment {

    private AssetUserCommunitySubAppModuleManager manager;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            manager = ((Session) subAppsSession).getManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);

        return rootView;
    }
}
