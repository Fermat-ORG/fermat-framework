package org.fermat.fermat_dap_android_wallet_asset_issuer.fragments;

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
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;

import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_issuer.interfaces.AssetIssuerWalletSupAppModuleManager;

import static android.widget.Toast.makeText;

/**
 * Created by Nerio on 01/02/16.
 */
public class SettingsAssetIssuerFragment extends AbstractFermatFragment<ReferenceAppFermatSession<AssetIssuerWalletSupAppModuleManager>, ResourceProviderManager> implements View.OnClickListener {

    private View rootView;

    private FermatTextView networkAction;
    private FermatTextView notificationAction;

    // Fermat Managers
    private AssetIssuerWalletSupAppModuleManager moduleManager;
    private ErrorManager errorManager;

    public static SettingsAssetIssuerFragment newInstance() {
        return new SettingsAssetIssuerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

//        try {
        errorManager = appSession.getErrorManager();
        moduleManager = appSession.getModuleManager();

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
            rootView = inflater.inflate(R.layout.dap_wallet_asset_issuer_settings_fragment, container, false);
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
                changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_SETTINGS_MAIN_NETWORK_ACTIVITY, appSession.getAppPublicKey());
            }
        });
        notificationAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.DAP_WALLET_ASSET_ISSUER_SETTINGS_NOTIFICATIONS_ACTIVITY, appSession.getAppPublicKey());
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
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            switch (id) {
                case 1://IC_ACTION_ISSUER_HELP_SETTINGS
                    setUpSettings(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
            }

//            if (id == SessionConstantsAssetIssuer.IC_ACTION_ISSUER_HELP_SETTINGS) {
//                setUpSettings(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_issuer_wallet_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpSettings(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_issuer_wallet)
                    .setIconRes(R.drawable.asset_issuer)
                    .setVIewColor(R.color.dap_issuer_view_color)
                    .setTitleTextColor(R.color.dap_issuer_view_color)
                    .setSubTitle(R.string.dap_issuer_wallet_detail_subTitle)
                    .setBody(R.string.dap_issuer_wallet_detail_body)
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
            toolbar.setBackgroundColor(getResources().getColor(R.color.dap_issuer_wallet_v3_toolbar));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.dap_issuer_wallet_v3_toolbar));
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

    }
}
