package com.bitdubai.sub_app.wallet_manager.fragment;

import android.graphics.Color;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSessionReferenceApp;
import com.mati.fermat_preference_settings.drawer.FermatPreferenceFragment;
import com.mati.fermat_preference_settings.drawer.holders.SettingEditTextViewHolder;
import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2016.02.19..
 */
public class FermatNetworkSettings<S extends DesktopSessionReferenceApp,RE extends ResourceProviderManager> extends FermatPreferenceFragment<S,RE> {


    private String ip = "";
    private int port = 0;


    public static AbstractFermatFragment newInstance() {
        return new FermatNetworkSettings();
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected List<PreferenceSettingsItem> setSettingsItems() {
        PreferenceSettingsEditText preferenceSettingsEditText = new PreferenceSettingsEditText(1,"IP","enter new IP");
        PreferenceSettingsEditText preferenceSettingsEditText2 = new PreferenceSettingsEditText(2,"Port","enter new Port");
        List<PreferenceSettingsItem> list = new ArrayList<>();
        list.add(preferenceSettingsEditText);
        list.add(preferenceSettingsEditText2);

        return list;
    }

    @Override
    public void dialogOptionSelected(String item, int position) {

    }

    @Override
    public void optionSelected(PreferenceSettingsItem preferenceSettingsItem, int position) {

    }


    @Override
    public void onSettingsTouched(PreferenceSettingsItem preferenceSettingsItem, int position) {

    }

    @Override
    public void onSettingsChanged(PreferenceSettingsItem preferenceSettingsItem, int position, boolean isChecked) {

    }

    @Override
    public void onSettingsTouched(String item, int position) {

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getActivity(),"Settings has saved",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public int getBackgroundAlpha() {
        return 0;
    }

    public String[] getIpPort(){

        String ip = ((SettingEditTextViewHolder)findItemById(0)).getSettingsEditText().getText().toString();
        String port = ((SettingEditTextViewHolder)findItemById(1)).getSettingsEditText().getText().toString();

        return new String[]{ip,port};
    }
}
