package com.bitdubai.sub_app.wallet_manager.fragment.settings;

import android.os.Bundle;
import android.preference.Preference;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatSettingsFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSession;

/**
 * Created by Matias Furszyfer on 2016.05.26..
 */
public class MoreSettingsFragment extends AbstractFermatSettingsFragment<DesktopSession,ResourceProviderManager> implements Preference.OnPreferenceClickListener {

    private final String PREFERENCE_IMPORT = "preference_import_key";
    private final String PREFERENCE_EXPORT = "preference_export_key";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.more_settings);

        findPreference("preference_import_key").setOnPreferenceClickListener(this);

        findPreference("preference_export_key").setOnPreferenceClickListener(this);
    }


    public static AbstractFermatFragmentInterface newInstance() {
        return new MoreSettingsFragment();
    }


    @Override
    public boolean onPreferenceClick(Preference preference) {

        switch (preference.getKey()){
            case PREFERENCE_IMPORT:
                changeActivity(Activities.DESKTOP_SETTING_IMPORT_KEY);
                break;
            case PREFERENCE_EXPORT:
                changeActivity(Activities.DESKTOP_SETTING_EXPORT_KEY);
                break;
        }

        return false;
    }
}
