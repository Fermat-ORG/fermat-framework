package com.mati.fermat_preference_settings.drawer.models;

import com.mati.fermat_preference_settings.drawer.Util.TypefaceEnumType;
import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;

/**
 * Created by natalia on 11/04/16.
 */
public class PreferenceSettingsLinkText extends PreferenceSettingsItem {

    private String TextViewtitle;
    private String editTextHint;
    private float textSize;
    private int textColor;
    private TypefaceEnumType itemTypeface;

    public PreferenceSettingsLinkText(int id,String textViewtitle,String editTextHint,float textSize,int textColor) {
        super(id);
        this.editTextHint = editTextHint;
        this.TextViewtitle = textViewtitle;
        this.textSize = textSize;
    }

    public String getTitleText(){
        return TextViewtitle;
    }

    public String getEditHint(){
        return editTextHint;
    }


    /**
     * get the item text size
     */
    public float getItemTextSize() {
        return textSize;
    }

    /**
     * Set the item type face
     *
     * @param type - TypefaceEnumType (E.g TypefaceEnumType.CAVIAR_DREAMS_BOLD)
     */
    public void getItemTypeFace(TypefaceEnumType type) {
        itemTypeface = type;
    }

    /**
     * Get the item text font color
     *
     */
    public int getItemTextColor() {
        return textColor;
    }


}
