package com.bitdubai.sub_app.wallet_manager.fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.mati.fermat_preference_settings.settings.FermatPreferenceFragment;
import com.mati.fermat_preference_settings.settings.interfaces.PreferenceSettingsItem;
import com.mati.fermat_preference_settings.settings.models.PreferenceSettingsEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2016.02.19..
 */
public class FermatNetworkSettings<S extends FermatSession,RE extends ResourceProviderManager> extends FermatPreferenceFragment<S,RE>{


    public static AbstractFermatFragment newInstance() {
        return new FermatNetworkSettings();
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected List<PreferenceSettingsItem> setSettingsItems() {
        PreferenceSettingsEditText preferenceSettingsEditText = new PreferenceSettingsEditText("IP","enter new IP");
        PreferenceSettingsEditText preferenceSettingsEditText2 = new PreferenceSettingsEditText("Port","enter new Port");
        List<PreferenceSettingsItem> list = new ArrayList<>();
        list.add(preferenceSettingsEditText);
        list.add(preferenceSettingsEditText2);

        return list;
    }


    @Override
    public void onSettingsTouched(PreferenceSettingsItem preferenceSettingsItem, int position) {


    }



}
