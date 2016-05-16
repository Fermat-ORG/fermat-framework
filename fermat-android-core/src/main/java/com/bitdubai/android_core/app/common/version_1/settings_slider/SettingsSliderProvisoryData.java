package com.bitdubai.android_core.app.common.version_1.settings_slider;

import android.util.Log;

import com.bitdubai.android_core.app.common.version_1.ApplicationConstants;
import com.bitdubai.android_core.app.common.version_1.util.system.FermatSystemUtils;
import com.bitdubai.fermat.R;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreModule;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2016.03.25..
 */
public class SettingsSliderProvisoryData {

    private static final String TAG = "SliderData";

    public static List<SettingsItem> getSettings(){
        List<SettingsItem> list = new ArrayList<>();
        try {

            SettingsItem settingsItemFermatNetwork = new SettingsItem(SettingsType.FERMAT_NETWORK, R.drawable.ic_drawable_p2p_network, "Fermat Network", "1 Node");
            SettingsItem settingsItemBitcoinNetwork = new SettingsItem(SettingsType.BITCOIN_NETWORK, R.drawable.ic_drawable_btc_network, "Bitcoin Network", "TestNet");
            SettingsItem settingsItemPrivateNetwork = new SettingsItem(SettingsType.PRIVATE_NETWORK, R.drawable.private_network_hdpi, "Private Network", "");
            settingsItemPrivateNetwork.setIsBlock(true);

            final AndroidCoreModule androidCoreModule = FermatSystemUtils.getAndroidCoreModule();

            int appStatusRes = 0;
            String appStatusName = "";
            try {
                AndroidCoreSettings androidCoreSettings = androidCoreModule.loadAndGetSettings(ApplicationConstants.SETTINGS_CORE);
                if (androidCoreSettings != null) {
                    switch (androidCoreSettings.getAppsStatus()) {
                        case RELEASE:
                            appStatusRes = R.drawable.app_filter_hdpi;
                            break;
                        case BETA:
                            appStatusRes = R.drawable.beta_hdpi;
                            break;
                        case ALPHA:
                            appStatusRes = R.drawable.icon_alpha_hdpi;
                            break;
                        case DEV:
                            appStatusRes = R.drawable.icon_develop_hdpi;
                            break;
                        default:
                            appStatusRes = R.drawable.icon_alpha_hdpi;
                            break;
                    }
                    appStatusName = androidCoreSettings.getAppsStatus().getCode();
                }else{
                    Log.e(TAG,"core setting fail");
                }
                }catch(CantGetSettingsException | SettingsNotFoundException e){
                    appStatusRes = R.drawable.icon_alpha_hdpi;
                    // e.printStackTrace();
                }


            SettingsItem settingsItemSettings = new SettingsItem(SettingsType.APP_STATUS, appStatusRes, "App Filter", (appStatusName.equals("")) ? "Alpha" : appStatusName);

            SettingsItem settingsItemRecents = new SettingsItem(SettingsType.RECENTS, R.drawable.recent_buttom_hdpi, "Recents Apps", "");

            SettingsItem settingsHelp = new SettingsItem(SettingsType.HELP, R.drawable.help_buttom_hdpi, "Welcome Help", "");

            SettingsItem settingErrorReport = new SettingsItem(SettingsType.REPORT, R.drawable.icon_suport, "Report ", "");


            list.add(settingsItemFermatNetwork);
            list.add(settingsItemBitcoinNetwork);
            list.add(settingsItemPrivateNetwork);
            list.add(settingsItemSettings);
            list.add(settingsItemRecents);
            list.add(settingsHelp);
            list.add(settingErrorReport);

        }catch (Exception e){
            e.printStackTrace();
        }

        return list;


    }
    
}
