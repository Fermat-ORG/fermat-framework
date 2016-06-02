package com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.wallet_final_version;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;

/**
 * Created by mati on 2016.02.08..
 */
public class SettingsFragment extends PreferenceFragment {


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Load "dummy" (empty) preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);

        PreferenceScreen screen = this.getPreferenceScreen(); // "null". See onViewCreated.

        // Create the Preferences Manually - so that the key can be set programatically.
        PreferenceCategory category = new PreferenceCategory(screen.getContext());
        category.setTitle("Channel Configuration");
        screen.addPreference(category);

        CheckBoxPreference checkBoxPref = new CheckBoxPreference(screen.getContext());
        checkBoxPref.setKey(/*channelConfig.getName() + */"_ENABLED");
        checkBoxPref.setTitle(/*channelConfig.getShortname()+ */ "Enabled");
        checkBoxPref.setSummary("description");//channelConfig.getDescription());
        checkBoxPref.setChecked(false);//channelConfig.isEnabled());

        category.addPreference(checkBoxPref);
    }


}
