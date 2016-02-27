package com.mati.fermat_preference_settings.drawer.models;

import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;

/**
 * Created by mati on 2016.02.10..
 */
public class PreferenceSettingsTextPlusRadioItem implements PreferenceSettingsItem {

    private String text;
    private boolean isRadioTouched;


    public PreferenceSettingsTextPlusRadioItem(String text, boolean isRadioTouched) {
        this.text = text;
        this.isRadioTouched = isRadioTouched;
    }

    public String getText(){
        return text;
    }

    public boolean isRadioTouched() {
        return isRadioTouched;
    }

    public void setIsRadioTouched(boolean isRadioTouched) {
        this.isRadioTouched = isRadioTouched;
    }
}