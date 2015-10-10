package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;

/**
 * Main Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class MainFragment extends FermatWalletFragment {


    private AssetIssuerWalletManager manager;
    /**
     * UI
     */
    private View rootView;
    private ListView assetsView;


    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            manager = ((AssetIssuerSession) walletSession).getManager();
        } catch (Exception ex) {
            CommonLogger.exception(this.getClass().getName(), ex.getMessage(), ex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment, container, false);
        assetsView = (ListView) rootView.findViewById(R.id.assets);
        ArrayAdapter<DigitalAsset> adapter = new ArrayAdapter<DigitalAsset>(getActivity(), R.layout.simple_row_asset, R.id.option, DigitalAsset.getAssets());
        assetsView.setAdapter(adapter);
        return rootView;
    }
}
