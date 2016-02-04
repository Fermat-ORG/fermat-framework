package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions.RedeemPointSession;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions.SessionConstantsRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;


import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * Created by Jinmy on 02/02/16.
 */
public class SettingsMainNetworkFragment extends AbstractFermatFragment implements AdapterView.OnItemSelectedListener {

    private View rootView;
    private Toolbar toolbar;

    private RedeemPointSession redeemPointSession;
    private Spinner spinner;
    List<String> listElementSpinner;

    SettingsManager<RedeemPointSettings> settingsManager;
    RedeemPointSettings settings = null;

    public static SettingsMainNetworkFragment newInstance() {
        return new SettingsMainNetworkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        redeemPointSession = (RedeemPointSession) appSession;

        settingsManager = appSession.getModuleManager().getSettingsManager();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.dap_wallet_asset_redeempoint_settings_main_network, container, false);
            setUpUi();
            configureToolbar();

            try {
                settings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                settings = null;
            }
            if (settings != null)
                spinner.setSelection(settings.getBlockchainNetworkPosition());


            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            redeemPointSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsRedeemPoint.IC_ACTION_REDEEM_SETTINGS_NETWORK, 0, "help").setIcon(R.drawable.dap_asset_redeem_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsRedeemPoint.IC_ACTION_REDEEM_SETTINGS_NETWORK) {
                setUpSettingsNetwork(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            redeemPointSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset Redeem Point system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpSettingsNetwork(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_redeem_point)
                    .setIconRes(R.drawable.redeem_point)
                    .setVIewColor(R.color.dap_redeem_point_view_color)
                    .setTitleTextColor(R.color.dap_redeem_point_view_color)
                    .setSubTitle(R.string.dap_redeem_wallet_detail_subTitle)
                    .setBody(R.string.dap_redeem_wallet_detail_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureToolbar() {

        toolbar = getToolbar();
        if (toolbar != null) {

            toolbar.setBackgroundColor(Color.parseColor("#015581"));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(Color.parseColor("#015581"));
            }
        }
    }

    public void setUpUi() {
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        listElementSpinner = new ArrayList<String>();
        listElementSpinner.add("MainNet");
        listElementSpinner.add("TestNet");
        listElementSpinner.add("RegTest");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.dap_wallet_asset_redeempoint_list_item_spinner, listElementSpinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void managerSettings(String dataSet, int position) {
        try {
            settings.setBlockchainNetwork(dataSet);
            settings.setBlockchainNetworkPosition(position);

            settingsManager.persistSettings(appSession.getAppPublicKey(), settings);
        } catch (CantPersistSettingsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String network;

        network = listElementSpinner.get(i);
        adapterView.setSelection(i);

        managerSettings(network, i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
