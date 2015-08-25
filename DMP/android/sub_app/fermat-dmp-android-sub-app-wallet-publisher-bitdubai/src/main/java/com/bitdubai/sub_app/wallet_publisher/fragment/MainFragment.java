package com.bitdubai.sub_app.wallet_publisher.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.sub_app.wallet_publisher.R;

/**
 * Created by natalia on 09/07/15.
 */
public class MainFragment extends FermatFragment {

    private static final String ARG_POSITION = "position";

    WalletPublisherModuleManager walletPublisherManager;
    Typeface tf;

    public static MainFragment newInstance(int position,SubAppsSession subAppsSession) {
        MainFragment f = new MainFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.wallet_publisher_main_fragment, container, false);
    }

    public void setWalletPublisherManager(WalletPublisherModuleManager walletPublisherManager) {
        this.walletPublisherManager = walletPublisherManager;
    }
}
