package org.fermat.fermat_dap_android_wallet_redeem_point.fragments;

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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import org.fermat.fermat_dap_android_wallet_redeem_point.sessions.RedeemPointSession;
import org.fermat.fermat_dap_android_wallet_redeem_point.sessions.SessionConstantsRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;


import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * Created by Jinmy on 02/02/16.
 */
public class SettingsMainNetworkFragment extends AbstractFermatFragment implements AdapterView.OnItemSelectedListener {

    private View rootView;
    private Toolbar toolbar;

    private Spinner spinner;
    List<BlockchainNetworkType> listElementSpinner;

    private AssetRedeemPointWalletSubAppModule moduleManager;
    private ErrorManager errorManager;

    SettingsManager<RedeemPointSettings> settingsManager;
    RedeemPointSettings settings = null;

    public static SettingsMainNetworkFragment newInstance() {
        return new SettingsMainNetworkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        moduleManager = ((RedeemPointSession) appSession).getModuleManager();
        errorManager = appSession.getErrorManager();

        settingsManager = appSession.getModuleManager().getSettingsManager();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.dap_wallet_asset_redeempoint_settings_main_network, container, false);

            try {
                settings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                settings = null;
            }
            if (settings != null) {
                listElementSpinner = settings.getBlockchainNetwork();
            } else {
                listElementSpinner = new ArrayList<>();
                listElementSpinner.add(BlockchainNetworkType.getDefaultBlockchainNetworkType());
            }

            setUpUi();
            configureToolbar();

            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), R.string.dap_redeem_point_wallet_opps_system_error, Toast.LENGTH_SHORT).show();
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsRedeemPoint.IC_ACTION_REDEEM_SETTINGS_NOTIFICATIONS, 0, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsRedeemPoint.IC_ACTION_REDEEM_SETTINGS_NOTIFICATIONS) {
                setUpSettingsNetwork(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_redeem_point_wallet_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpSettingsNetwork(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_redeem_point_wallet)
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
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setTitleTextColor(Color.WHITE);
            Drawable drawable = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_redeem_point_action_bar_gradient_colors, null);
                toolbar.setElevation(0);
            } else {
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_redeem_point_action_bar_gradient_colors);
            }
            toolbar.setBackground(drawable);
        }
    }

    public void setUpUi() {
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
//        listElementSpinner = new ArrayList<String>();
//        listElementSpinner.add("MainNet");
//        listElementSpinner.add("TestNet");
//        listElementSpinner.add("RegTest");
        ArrayAdapter<BlockchainNetworkType> dataAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.dap_wallet_asset_redeempoint_list_item_spinner, listElementSpinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(settings.getBlockchainNetworkPosition());
    }

    private void managerSettings(BlockchainNetworkType dataSet, int position) {
        try {
            settings.setBlockchainNetworkPosition(position);

            settingsManager.persistSettings(appSession.getAppPublicKey(), settings);
            moduleManager.changeNetworkType(dataSet);
        } catch (CantPersistSettingsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        BlockchainNetworkType network;

        network = listElementSpinner.get(i);
        adapterView.setSelection(i);

        managerSettings(network, i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
