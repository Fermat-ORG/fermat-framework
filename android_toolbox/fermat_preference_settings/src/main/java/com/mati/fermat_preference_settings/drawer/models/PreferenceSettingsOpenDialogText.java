package com.mati.fermat_preference_settings.drawer.models;


import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;

import java.util.List;

/**
 * Created by mati on 2016.02.08..
 */
public class PreferenceSettingsOpenDialogText implements PreferenceSettingsItem {


    private String text;
    private List<PreferenceSettingsTextPlusRadioItem> lstItems;


    public PreferenceSettingsOpenDialogText(String text, List<PreferenceSettingsTextPlusRadioItem> lstItems) {
        this.text = text;
        this.lstItems = lstItems;
    }

    public String getText(){
        return text;
    }

    public List<PreferenceSettingsTextPlusRadioItem> getOptionList(){
        return lstItems;
    }
}
