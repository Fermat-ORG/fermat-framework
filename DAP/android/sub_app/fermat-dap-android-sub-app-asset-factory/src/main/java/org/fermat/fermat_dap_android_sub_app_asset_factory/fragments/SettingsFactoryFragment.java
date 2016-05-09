package org.fermat.fermat_dap_android_sub_app_asset_factory.fragments;

import android.annotation.TargetApi;
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
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.R;
import org.fermat.fermat_dap_android_sub_app_asset_factory.sessions.AssetFactorySession;
import org.fermat.fermat_dap_android_sub_app_asset_factory.sessions.SessionConstantsAssetFactory;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.AssetFactorySettings;
import org.fermat.fermat_dap_api.layer.dap_module.asset_factory.interfaces.AssetFactoryModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import static android.widget.Toast.makeText;

/**
 * Created by Nerio on 01/02/16.
 */
public class SettingsFactoryFragment extends AbstractFermatFragment implements View.OnClickListener {

    private View rootView;

    private FermatTextView networkAction;
    private FermatTextView notificationAction;

    // Fermat Managers
    private AssetFactoryModuleManager manager;
    private ErrorManager errorManager;
    SettingsManager<AssetFactorySettings> settingsManager;

    public static SettingsFactoryFragment newInstance() {
        return new SettingsFactoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        manager = ((AssetFactorySession) appSession).getModuleManager();
//        try {
            errorManager = appSession.getErrorManager();
            settingsManager = appSession.getModuleManager().getSettingsManager();
//            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
            rootView = inflater.inflate(R.layout.dap_factory_settings_fragment, container, false);
            setUpUI();
            setUpActions();
            setUpUIData();
            configureToolbar();

            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpUI() { //throws CantGetActiveLoginIdentityException {
        networkAction = (FermatTextView) rootView.findViewById(R.id.dap_wallet_asset_issuer_network_action);
        notificationAction = (FermatTextView) rootView.findViewById(R.id.dap_wallet_asset_issuer_notification_action);
    }

    private void setUpActions() {
        networkAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NETWORK_MAIN, appSession.getAppPublicKey());
            }
        });
        notificationAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NOTIFICATIONS, appSession.getAppPublicKey());
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
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsAssetFactory.IC_ACTION_SETTINGS, 0, "help").setIcon(R.drawable.dap_asset_factory_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetFactory.IC_ACTION_SETTINGS) {
                setUpFactorySettings(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Asset Issuer system error",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpFactorySettings(boolean checkButton) {
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
        Toolbar toolbar = getPaintActivtyFeactures().getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(Color.parseColor("#1d1d25"));
            toolbar.setTitleTextColor(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(Color.parseColor("#1d1d25"));
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }
}
