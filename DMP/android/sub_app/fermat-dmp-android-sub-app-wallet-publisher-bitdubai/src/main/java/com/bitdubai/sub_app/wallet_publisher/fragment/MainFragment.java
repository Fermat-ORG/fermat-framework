package com.bitdubai.sub_app.wallet_publisher.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;

/**
 * Created by natalia on 09/07/15.
 */
public class MainFragment extends FermatFragment {

    private static final String TAG = "WalletPublisher";
    /**
     * WalletPublisherModuleManager
     */
    private WalletPublisherModuleManager manager;
    /**
     * UI Reference
     */
    private View rootView;

    public static MainFragment newInstance() {
        MainFragment f = new MainFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            manager = ((WalletPublisherSubAppSession) subAppsSession).getWalletPublisherManager();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallet_publisher_main_fragment, container, false);

        return rootView;
    }
}
