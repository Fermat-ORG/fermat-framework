package com.mati.fermat_preference_settings.settings.models;


import com.mati.fermat_preference_settings.settings.interfaces.PreferenceSettingsItem;

/**
 * Created by mati on 2016.02.08..
 */
public interface PreferenceSettingsSwithItem extends PreferenceSettingsItem {


    boolean getSwitchChecked();

    String getText();
}
