package com.bitdubai.android_core.app.common.version_1.settings_slider;

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
 * Created by mati on 2016.03.25..
 */
public class SettingsSliderProvisoryData {
    
    public static List<SettingsItem> getSettings(){
        List<SettingsItem> list = new ArrayList<>();
        
        SettingsItem settingsItemFermatNetwork = new SettingsItem(SettingsType.FERMAT_NETWORK,R.drawable.ic_drawable_p2p_network,"Fermat Network","1 Node");
        SettingsItem settingsItemBitcoinNetwork = new SettingsItem(SettingsType.BITCOIN_NETWORK,R.drawable.ic_drawable_btc_network,"Bitcoin Network","1 TestNet");
        SettingsItem settingsItemPrivateNetwork = new SettingsItem(SettingsType.PRIVATE_NETWORK,R.drawable.ic_drawable_private_network,"Private Network","");

        final AndroidCoreModule androidCoreModule = FermatSystemUtils.getAndroidCoreModule();

        int appStatusRes = 0;
        try {
            AndroidCoreSettings androidCoreSettings = (AndroidCoreSettings) androidCoreModule.getSettingsManager().loadAndGetSettings(ApplicationConstants.SETTINGS_CORE);
            switch (androidCoreSettings.getAppsStatus()){
                case RELEASE:
                    appStatusRes =R.drawable.relese_icon;
                    break;
                case BETA:
                    appStatusRes =R.drawable.beta_icon;
                    break;
                case ALPHA:
                    appStatusRes =R.drawable.alfa_icon;
                    break;
                case DEV:
                    appStatusRes =R.drawable.developer_icon;
                    break;
                default:
                    appStatusRes =R.drawable.relese_icon;
                    break;
            }
        } catch (CantGetSettingsException | SettingsNotFoundException e) {
            appStatusRes =R.drawable.relese_icon;
            // e.printStackTrace();
        }
        
        
        SettingsItem settingsItemSettings = new SettingsItem(SettingsType.APP_STATUS,appStatusRes,"App Filter","Release");

        SettingsItem settingsItemRecents = new SettingsItem(SettingsType.RECENTS,R.drawable.ic_menu_recent_history,"Recents","");


        list.add(settingsItemFermatNetwork);
        list.add(settingsItemBitcoinNetwork);
        list.add(settingsItemPrivateNetwork);
        list.add(settingsItemSettings);
        list.add(settingsItemRecents);


        return list;


    }
    
}
