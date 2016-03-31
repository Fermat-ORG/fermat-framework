package com.bitdubai.reference_wallet.fan_wallet.fragments;

import android.os.Bundle;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.fan_wallet.preference_settings.FanWalletSettings;
import com.bitdubai.reference_wallet.fan_wallet.session.FanWalletSession;

/**
 * Created by miguel on 14/03/16.
 */
public class SongFragment extends AbstractFermatFragment {

    //FermatManager
    private FanWalletSession fanwalletSession;
 //   private FanWalletModuleManager fanwalletmoduleManager;
    private FanWalletSettings fanWalletSettings;
    private ErrorManager errorManager;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            fanwalletSession = ((FanWalletSession) appSession);
            //fanwalletmoduleManager = fanwalletSession.getModuleManager();
            errorManager = appSession.getErrorManager();


            try {
            //    fanWalletSettings = fanwalletmoduleManager.getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
               fanWalletSettings = null;
            }

            if (fanWalletSettings == null) {
                fanWalletSettings = new FanWalletSettings();
                fanWalletSettings.setIsPresentationHelpEnabled(true);
                try {
                    //fanwalletmoduleManager.getSettingsManager().persistSettings(appSession.getAppPublicKey(), fanWalletSettings);
                } catch (Exception e) {
                    errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }


        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.TKY_FAN_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public static SongFragment newInstance() {
        return new SongFragment();
    }
}

