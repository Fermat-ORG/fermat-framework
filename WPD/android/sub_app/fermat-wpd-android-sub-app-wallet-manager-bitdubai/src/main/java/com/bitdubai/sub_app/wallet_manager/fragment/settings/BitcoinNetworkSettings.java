package com.bitdubai.sub_app.wallet_manager.fragment.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_wpd.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSessionReferenceApp;

/**
 * Created by mati on 2016.04.25..
 */
public class BitcoinNetworkSettings extends AbstractFermatFragment<DesktopSessionReferenceApp,ResourceProviderManager> {


    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.bitcoin_network_layout,container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
