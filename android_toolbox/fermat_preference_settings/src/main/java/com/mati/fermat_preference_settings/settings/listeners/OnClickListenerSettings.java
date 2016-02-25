package com.mati.fermat_preference_settings.settings.listeners;

import android.view.View;

import com.mati.fermat_preference_settings.settings.interfaces.DialogCallback;
import com.mati.fermat_preference_settings.settings.interfaces.PreferenceSettingsItem;

/**
 * Created by mati on 2016.02.24..
 */
public class OnClickListenerSettings implements View.OnClickListener {

    private final DialogCallback dialogCallback;
    private PreferenceSettingsItem preferenceSettingsItem;
    private int position;

    public OnClickListenerSettings(DialogCallback dialogCallback,PreferenceSettingsItem preferenceSettingsItem, int position) {
        this.preferenceSettingsItem = preferenceSettingsItem;
        this.position = position;
        this.dialogCallback = dialogCallback;
    }

    @Override
    public void onClick(View v) {
        dialogCallback.optionSelected(preferenceSettingsItem,position);
    }
}
