package com.bitdubai.sub_app.wallet_manager.fragment.settings;

import android.os.Bundle;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragmentInterface;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatSettingsFragment;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dmp.wallet_manager.R;
import com.bitdubai.sub_app.wallet_manager.session.DesktopSession;

/**
 * Created by Matias Furszyfer on 2016.05.26..
 */
public class MoreSettingsFragment extends AbstractFermatSettingsFragment<DesktopSession,ResourceProviderManager> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.more_settings);
    }


    public static AbstractFermatFragmentInterface newInstance() {
        return new MoreSettingsFragment();
    }
}
