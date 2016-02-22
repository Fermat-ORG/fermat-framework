package com.bitdubai.android_core.app.common.version_1.top_settings;

import com.mati.fermat_preference_settings.settings.FermatPreferenceFragment;
import com.mati.fermat_preference_settings.settings.interfaces.PreferenceSettingsItem;
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
        return Arrays.asList(new PreferenceSettingsItem[]{preferenceSettingsEditText, preferenceSettingsEditText2});
    }

    @Override
    public void onSettingsTouched(PreferenceSettingsItem preferenceSettingsItem, int position) {


    }


}
