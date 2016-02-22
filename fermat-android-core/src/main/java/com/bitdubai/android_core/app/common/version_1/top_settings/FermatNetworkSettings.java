package com.bitdubai.android_core.app.common.version_1.top_settings;

import com.mati.fermat_preference_settings.settings.FermatPreferenceFragment;
import com.mati.fermat_preference_settings.settings.interfaces.PreferenceSettingsItem;
import com.mati.fermat_preference_settings.settings.models.PreferenceSettingsDialogItem;
import com.mati.fermat_preference_settings.settings.models.PreferenceSettingsEditText;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mati on 2016.02.19..
 */
public class FermatNetworkSettings extends FermatPreferenceFragment{


    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected List<PreferenceSettingsItem> setSettingsItems() {
        PreferenceSettingsEditText preferenceSettingsEditText = new PreferenceSettingsEditText("IP","enter new IP");
        PreferenceSettingsEditText preferenceSettingsEditText2 = new PreferenceSettingsEditText("Port","enter new Port");
        PreferenceSettingsDialogItem preferenceSettingsDialogItem = new PreferenceSettingsDialogItem() {
            @Override
            public String getText() {
                return "toca";
            }
        };
        return Arrays.asList(new PreferenceSettingsItem[]{preferenceSettingsEditText, preferenceSettingsEditText2,preferenceSettingsDialogItem});
    }

    @Override
    public void onSettingsTouched(PreferenceSettingsItem preferenceSettingsItem, int position) {


    }


}
