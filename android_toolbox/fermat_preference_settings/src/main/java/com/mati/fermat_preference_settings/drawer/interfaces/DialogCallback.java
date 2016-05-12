package com.mati.fermat_preference_settings.drawer.interfaces;

/**
 * Created by mati on 2016.02.10
 */
public interface DialogCallback {

    void optionSelected(PreferenceSettingsItem preferenceSettingsItem,int position);

    void dialogOptionSelected(String item, int position);

    void optionChanged(PreferenceSettingsItem preferenceSettingsItem,int position,boolean isChecked);

}
