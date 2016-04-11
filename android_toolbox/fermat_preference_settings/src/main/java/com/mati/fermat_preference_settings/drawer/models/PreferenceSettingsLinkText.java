package com.mati.fermat_preference_settings.drawer.models;

import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;

/**
 * Created by natalia on 11/04/16.
 */
public class PreferenceSettingsLinkText extends PreferenceSettingsItem {

    private String TextViewtitle;
    private String editTextHint;


    public PreferenceSettingsLinkText(int id,String textViewtitle,String editTextHint) {
        super(id);
        this.editTextHint = editTextHint;
        this.TextViewtitle = textViewtitle;
    }

    public String getTitleText(){
        return TextViewtitle;
    }

    public String getEditHint(){
        return editTextHint;
    }

}
