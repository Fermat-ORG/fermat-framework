package com.bitdubai.desktop.wallet_manager.fragmentFactory;

import com.bitdubai.desktop.wallet_manager.fragments.MainFragment;
import com.bitdubai.desktop.wallet_manager.preference_settings.WalletManagerPreferenceSettings;
import com.bitdubai.desktop.wallet_manager.session.WalletManagerSubAppSession;
import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */
public class WalletManagerFragmentFactory extends FermatSubAppFragmentFactory<WalletManagerSubAppSession, WalletManagerPreferenceSettings, WalletManagerFragmentsEnumType> {


    @Override
    public FermatFragment getFermatFragment(WalletManagerFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;

        switch (fragments) {
            case MAIN_FRAGMET:
                currentFragment = MainFragment.newInstance();
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.toString(), "Swith failed");
        }
        return currentFragment;
    }

    @Override
    public WalletManagerFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletManagerFragmentsEnumType.getValue(key);
    }

}
