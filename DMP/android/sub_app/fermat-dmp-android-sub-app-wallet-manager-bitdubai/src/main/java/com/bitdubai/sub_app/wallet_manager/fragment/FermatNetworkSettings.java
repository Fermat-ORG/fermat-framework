package com.bitdubai.sub_app.wallet_manager.fragment;

import android.graphics.drawable.Drawable;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSession;
import com.mati.fermat_preference_settings.drawer.FermatPreferenceFragment;
import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2016.02.19..
 */
public class FermatNetworkSettings<S extends DesktopSession,RE extends ResourceProviderManager> extends FermatPreferenceFragment<S,RE> {


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
    public void onSettingsTouched(PreferenceSettingsItem preferenceSettingsItem, int position) {



    }

    @Override
    public Drawable getBackground() {
        return null;
    }


}
