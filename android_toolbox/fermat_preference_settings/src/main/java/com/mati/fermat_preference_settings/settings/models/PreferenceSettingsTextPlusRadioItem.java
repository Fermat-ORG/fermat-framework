package com.mati.fermat_preference_settings.settings.models;

import com.mati.fermat_preference_settings.settings.interfaces.PreferenceSettingsItem;

/**
 * Created by mati on 2016.02.10..
 */
public class PreferenceSettingsTextPlusRadioItem extends PreferenceSettingsItem {

    private String text;
    private boolean isRadioTouched;


    public PreferenceSettingsTextPlusRadioItem(int id,String text, boolean isRadioTouched) {
        super(id);
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