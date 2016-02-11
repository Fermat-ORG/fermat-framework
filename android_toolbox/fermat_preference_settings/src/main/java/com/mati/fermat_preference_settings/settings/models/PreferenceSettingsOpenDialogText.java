package com.mati.fermat_preference_settings.settings.models;


import com.mati.fermat_preference_settings.settings.interfaces.PreferenceSettingsItem;

import java.util.List;

/**
 * Created by mati on 2016.02.08..
 */
public interface PreferenceSettingsOpenDialogText extends PreferenceSettingsItem {

    String getText();

    List<PreferenceSettingsDialogItem> getOptionList();
}
