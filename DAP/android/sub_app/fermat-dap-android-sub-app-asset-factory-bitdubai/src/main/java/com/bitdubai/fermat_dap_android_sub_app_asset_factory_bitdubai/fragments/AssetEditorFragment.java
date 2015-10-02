package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.sessions.AssetFactorySession;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.dap_middleware.dap_asset_factory.interfaces.AssetFactory;
import com.bitdubai.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

/**
 * Asset Editor Fragment
 *
 * @author Francisco Vasquez
 */
public class AssetEditorFragment extends FermatFragment {

    private final String TAG = "AssetEditor";

    private AssetFactoryModuleManager manager;
    private ErrorManager errorManager;

    private AssetFactory asset;

    private View rootView;

    public static AssetEditorFragment newInstance(AssetFactory asset) {
        AssetEditorFragment fragment = new AssetEditorFragment();
        fragment.setAsset(asset);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            manager = ((AssetFactorySession) subAppsSession).getManager();
            errorManager = subAppsSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.asset_editor_fragment, container, false);

        return rootView;
    }

    public void setAsset(AssetFactory asset) {
        this.asset = asset;
    }
}
