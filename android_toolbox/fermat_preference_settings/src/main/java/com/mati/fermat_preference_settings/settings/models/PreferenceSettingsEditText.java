package com.mati.fermat_preference_settings.settings.models;


import com.mati.fermat_preference_settings.settings.interfaces.PreferenceSettingsItem;

/**
 * Created by mati on 2016.02.08..
 */
public class PreferenceSettingsEditText implements PreferenceSettingsItem {

    private String TextViewtitle;
    private String editTextHint;
    private String editTextTitle;


    public PreferenceSettingsEditText(String textViewtitle,String editTextHint) {
        this.editTextHint = editTextHint;
        TextViewtitle = textViewtitle;
    }

    public String getTitleText(){
        return TextViewtitle;
    }

    public String getEditHint(){
        return editTextHint;
    }
    public String getEditText(){
        return editTextTitle;
    }
}
