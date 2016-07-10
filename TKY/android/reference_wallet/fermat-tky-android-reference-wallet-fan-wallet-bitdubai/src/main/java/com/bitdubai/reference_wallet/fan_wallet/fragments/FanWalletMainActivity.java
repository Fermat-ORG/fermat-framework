package com.bitdubai.reference_wallet.fan_wallet.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_tky_api.layer.wallet_module.FanWalletPreferenceSettings;
import com.bitdubai.fermat_tky_api.layer.wallet_module.interfaces.FanWalletModule;
import com.bitdubai.reference_wallet.fan_wallet.R;
import com.bitdubai.reference_wallet.fan_wallet.session.FanWalletSessionReferenceApp;


/**
 * Created by Miguel Payarez on 16/03/16.
 */
public class FanWalletMainActivity extends AbstractFermatFragment<ReferenceAppFermatSession<FanWalletModule>, SubAppResourcesProviderManager>  {

    //FermatManager
    //private FanWalletSessionReferenceApp fanwalletSession;
    private FanWalletPreferenceSettings  fanWalletSettings;
    private ErrorManager errorManager;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            //fanwalletSession = ((FanWalletSessionReferenceApp) appSession);
            errorManager = appSession.getErrorManager();
            System.out.println("HERE START FAN WALLET");

            try {
                    fanWalletSettings =  appSession
                            .getModuleManager()
                            .loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                fanWalletSettings = null;
            }

            if (fanWalletSettings == null) {
                fanWalletSettings = new FanWalletPreferenceSettings();
                fanWalletSettings.setIsPresentationHelpEnabled(true);
                try {
                    appSession
                            .getModuleManager()
                            .persistSettings(appSession.getAppPublicKey(), fanWalletSettings);
                } catch (Exception e) {
                    errorManager.reportUnexpectedWalletException(
                            Wallets.TKY_FAN_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            e);
                }
            }


        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(
                        Wallets.TKY_FAN_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        e);
        }

    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (toolbar.getMenu() != null) toolbar.getMenu();
    }

    public static FanWalletMainActivity newInstance(){return new FanWalletMainActivity();}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        configureToolbar();
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.fanwallet_background_viewpager);

        return view;

    }


    }







