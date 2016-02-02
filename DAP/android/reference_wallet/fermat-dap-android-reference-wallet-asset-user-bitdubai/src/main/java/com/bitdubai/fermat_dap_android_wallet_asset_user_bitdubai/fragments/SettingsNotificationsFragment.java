package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;


import static android.widget.Toast.makeText;


/**
 *
 * Modified by Penelope Quintero for Asset User Wallet on 2016.02.02
 */
public class SettingsNotificationsFragment extends AbstractFermatFragment {

    private View rootView;
    private AssetUserSession assetUserSession;
    private Spinner spinner;
    private Switch notificationSwitch;


    public static SettingsNotificationsFragment newInstance() {
        return new SettingsNotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetUserSession = (AssetUserSession) appSession;
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.dap_wallet_asset_user_settings_notifications, container, false);
            setUpUi();
            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            assetUserSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;
    }

    public void setUpUi() {
        notificationSwitch = (Switch) rootView.findViewById(R.id.switch_notification);
    }

}
