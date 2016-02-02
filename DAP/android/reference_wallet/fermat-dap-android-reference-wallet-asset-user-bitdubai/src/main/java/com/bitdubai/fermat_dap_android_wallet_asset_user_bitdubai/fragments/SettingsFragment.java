package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;


import static android.widget.Toast.makeText;

/**
 * Created by Matias Furszyfer on 2015.11.05..
 * Modified by Jose Manuel De Sousa Dos Santos on 2016.18.01
 * Modified by Penelope Quintero for Asset User Wallet on 2016.02.02
 */
public class SettingsFragment extends AbstractFermatFragment implements View.OnClickListener {


    /**
     * Plaform reference
     */
    private AssetUserSession assetUserSession;



    /**
     * UI
     */
    private View rootView;

    private ColorStateList mSwitchTrackStateList;
    private FermatTextView networkAction;
    private FermatTextView notificationAction;


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
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
            rootView = inflater.inflate(R.layout.dap_wallet_asset_user_settings_fragment_base, container, false);
            setUpUI();
            setUpActions();
            setUpUIData();
            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            assetUserSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpUI() throws CantGetActiveLoginIdentityException {
        networkAction = (FermatTextView) rootView.findViewById(R.id.network_action);
        notificationAction = (FermatTextView) rootView.findViewById(R.id.notification_action);
    }

    private void setUpActions() {
        networkAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.DAP_WALLET_ASSET_USER_SETTINGS_MAIN_NETWORK, assetUserSession.getAppPublicKey());
            }
        });
        notificationAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.DAP_WALLET_ASSET_USER_SETTINGS_NOTIFICATIONS, assetUserSession.getAppPublicKey());
            }
        });
    }

    private void setUpUIData() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            assetUserSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }


    @Override
    public void onClick(View v) {

        /*int id = v.getId();

        if (id == R.id.scan_qr) {
            IntentIntegrator integrator = new IntentIntegrator(getActivity(), (EditText) rootView.findViewById(R.id.address));
            integrator.initiateScan();
        } else if (id == R.id.send_button) {
            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getActivity().getCurrentFocus() != null && im.isActive(getActivity().getCurrentFocus())) {
                im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }

        } else if (id == R.id.imageView_contact) {
            // if user press the profile image
        }*/


    }

    private ColorStateList getSwitchTrackColorStateList() {
        if (mSwitchTrackStateList == null) {
            final int[][] states = new int[3][];
            final int[] colors = new int[3];
            int i = 0;

            // Disabled state
            states[i] = new int[]{-android.R.attr.state_enabled};
            colors[i] = Color.RED;
            i++;

            states[i] = new int[]{android.R.attr.state_checked};
            colors[i] = Color.BLUE;
            i++;

            // Default enabled state
            states[i] = new int[0];
            colors[i] = Color.YELLOW;
            i++;

            mSwitchTrackStateList = new ColorStateList(states, colors);
        }
        return mSwitchTrackStateList;
    }


}
