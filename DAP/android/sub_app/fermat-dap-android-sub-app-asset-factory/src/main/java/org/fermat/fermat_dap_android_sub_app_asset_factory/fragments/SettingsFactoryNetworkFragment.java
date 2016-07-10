package org.fermat.fermat_dap_android_sub_app_asset_factory.fragments;

import android.graphics.Color;
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

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;

import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.AssetFactorySettings;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by Nerio on 01/02/16.
 */
public class SettingsFactoryNetworkFragment extends AbstractFermatFragment<ReferenceAppFermatSession<AssetFactoryModuleManager>, ResourceProviderManager> implements AdapterView.OnItemSelectedListener {

    private View rootView;
    private Spinner spinner;
    List<BlockchainNetworkType> listElementSpinner;
    // Fermat Managers
    private AssetFactoryModuleManager moduleManager;
    private ErrorManager errorManager;
    AssetFactorySettings settings = null;

    public static SettingsFactoryNetworkFragment newInstance() {
        return new SettingsFactoryNetworkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        moduleManager = appSession.getModuleManager();
        errorManager = appSession.getErrorManager();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//        } catch (CantGetCryptoWalletException e) {
//            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.dap_factory_settings_main_network, container, false);

            try {
                settings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
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
//            configureToolbar();

            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;
    }


    @Override
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);
//        menu.add(0, SessionConstantsAssetFactory.IC_ACTION_SETTINGS_NETWORK, 0, "help").setIcon(R.drawable.dap_asset_factory_help_icon)
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            switch (id) {
                //case IC_ACTION_SETTINGS_NETWORK:
                case 2:
                    setUpFactorySettingsNetwork(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
//                case 1:
//                    changeActivity(Activities.CHT_CHAT_GEOLOCATION_IDENTITY, appSession.getAppPublicKey());
//                    break;
            }

//            if (id == SessionConstantsAssetFactory.IC_ACTION_SETTINGS_NETWORK) {
//                setUpFactorySettingsNetwork(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset Issuer system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpFactorySettingsNetwork(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_factory)
                    .setIconRes(R.drawable.asset_factory)
                    .setVIewColor(R.color.dap_asset_factory_view_color)
                    .setTitleTextColor(R.color.dap_asset_factory_view_color)
                    .setSubTitle(R.string.dap_asset_factory_editor_subTitle)
                    .setBody(R.string.dap_asset_factory_editor_body)
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
            toolbar.setBackgroundColor(getResources().getColor(R.color.redeem_home_bar_color));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.redeem_home_bar_color));
            }
        }
    }

    public void setUpUi() {
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
//        listElementSpinner = new ArrayList<String>();
//        listElementSpinner.add("MainNet");
//        listElementSpinner.add("TestNet");
//        listElementSpinner.add("RegTest");
        ArrayAdapter<BlockchainNetworkType> dataAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.dap_factory_list_item_spinner, listElementSpinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(settings.getBlockchainNetworkPosition());
    }

    private void managerSettings(BlockchainNetworkType dataSet, int position) {
        try {
            settings.setBlockchainNetworkPosition(position);

            if (moduleManager != null) {
                moduleManager.persistSettings(appSession.getAppPublicKey(), settings);
                moduleManager.changeNetworkType(dataSet);
            }
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
