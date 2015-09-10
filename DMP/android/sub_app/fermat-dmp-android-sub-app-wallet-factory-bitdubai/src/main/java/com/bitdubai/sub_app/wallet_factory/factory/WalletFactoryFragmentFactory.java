package com.bitdubai.sub_app.wallet_factory.factory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.wallet_factory.ui.fragments.EditableWalletFragment;
import com.bitdubai.sub_app.wallet_factory.ui.fragments.MainFragment;
import com.bitdubai.sub_app.wallet_factory.ui.fragments.ManagerFragment;
import com.bitdubai.sub_app.wallet_factory.ui.fragments.ProjectsFragment;
import com.bitdubai.sub_app.wallet_factory.ui.fragments.SendFragment;
import com.bitdubai.sub_app.wallet_factory.settings.WalletFactoryPreferenceSettings;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */

public class WalletFactoryFragmentFactory extends FermatSubAppFragmentFactory<WalletFactorySubAppSession, WalletFactoryPreferenceSettings, WalletFactoryFragmentsEnumType> {


    public WalletFactoryFragmentFactory() {
    }


    @Override
    public FermatFragment getFermatFragment(WalletFactoryFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;

        switch (fragments) {
            /**
             * Executing fragments for BITCOIN REQUESTED.
             */
            case CWP_WALLET_FACTORY_PROJECTS_FRAGMENT:
                currentFragment = ProjectsFragment.newInstance();
                break;
            case CWP_WALLET_FACTORY_MAIN_FRAGMENT:
                currentFragment = MainFragment.newInstance();
                break;
            case CWP_WALLET_FACTORY_MANAGER_FRAGMENT:
                currentFragment = ManagerFragment.newInstance();
                break;
            case CWP_WALLET_FACTORY_SEND_FRAGMENT:
                currentFragment = SendFragment.newInstance();
                break;
            case CWP_WALLET_FACTORY_EDIT_MODE:
                currentFragment = EditableWalletFragment.newInstance(false);
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.getKey(), "Swith failed");
        }
        return currentFragment;
    }

    @Override
    public WalletFactoryFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WalletFactoryFragmentsEnumType.getValue(key);
    }


}
