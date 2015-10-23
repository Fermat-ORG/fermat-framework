package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

/**
 * Main Fragment
 *
 * @author Francisco Vásquez
 * @version 1.0
 */
public class MainFragment extends FermatWalletFragment {


    private AssetUserWalletSubAppModuleManager manager;

    /**
     * UI
     */
    private View rootView;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            manager = ((AssetUserSession) walletSession).getWalletManager();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (manager == null)
            Toast.makeText(getActivity(), "AssetUserWalletSubAppModuleManager is null", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(getActivity(), "AssetUserWalletSubAppModuleManager is connected", Toast.LENGTH_SHORT).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_dap_reference_wallet_user_fragment, container, false);

        return rootView;
    }

}
