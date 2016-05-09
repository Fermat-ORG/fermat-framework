package com.mati.fermat_preference_settings.drawer.models;


import android.os.Bundle;

import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;

import java.util.List;

/**
 * Created by mati on 2016.02.08..
 */
public class PreferenceSettingsOpenDialogText extends PreferenceSettingsItem {


    private String text;
    private List<PreferenceSettingsTextPlusRadioItem> lstItems;
    private Bundle data;


    public PreferenceSettingsOpenDialogText(int id, String text, List<PreferenceSettingsTextPlusRadioItem> lstItems) {
        super(id);
        this.text = text;
        this.lstItems = lstItems;
    }

    public PreferenceSettingsOpenDialogText(int id, String text, Bundle data) {
        super(id);
        this.text = text;
        this.data = data;
    }

    public String getText(){
        return text;
    }

    public List<PreferenceSettingsTextPlusRadioItem> getOptionList() {
        return lstItems;
    }

    public Bundle getDialogData() {
        return data;
    }
}
