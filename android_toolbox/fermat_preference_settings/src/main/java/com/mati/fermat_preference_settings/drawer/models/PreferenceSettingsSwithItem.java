package com.mati.fermat_preference_settings.drawer.models;


import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;

/**
 * Created by mati on 2016.02.08..
 */
public class PreferenceSettingsSwithItem extends PreferenceSettingsItem {


    private String text;
    private boolean isSwitchChecked;

    public PreferenceSettingsSwithItem(int id,String text, boolean isSwitchChecked) {
        super(id);
        this.text = text;
        this.isSwitchChecked = isSwitchChecked;
    }

    public boolean getSwitchChecked(){
        return this.isSwitchChecked;
    }

    public String getText(){
        return text;
    }
}
