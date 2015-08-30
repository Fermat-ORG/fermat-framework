package com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatScreenSwapper;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;

/**
 * Created by natalia on 09/07/15.
 */
public class MainFragment extends FermatFragment {

    private WalletFactoryManager manager;

    public static MainFragment newInstance() {
        MainFragment f = new MainFragment();
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
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.wallet_factory_main_fragment, container, false);
    }
}
