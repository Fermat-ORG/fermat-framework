package com.bitdubai.sub_app.wallet_factory.FragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatSubAppFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.EditableWalletFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.MainFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.ManagerFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.ProjectsFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.SendFragment;
import com.bitdubai.sub_app.wallet_factory.preference_settings.WalletFactoryPreferenceSettings;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */

public class WalletFactoryFragmentFactory extends FermatSubAppFragmentFactory<WalletFactorySubAppSession,WalletFactoryPreferenceSettings,WaletFactoryFragmentsEnumType> {


    public WalletFactoryFragmentFactory(){}


    @Override
    public FermatFragment getFermatFragment(WaletFactoryFragmentsEnumType fragments) throws FragmentNotFoundException {
        FermatFragment currentFragment = null;

        switch (fragments){
            /**
             * Executing fragments for BITCOIN REQUESTED.
             */
            case CWP_WALLET_FACTORY_PROJECTS_FRAGMENT:
                currentFragment =  ProjectsFragment.newInstance(0, null);
                break;
            case CWP_WALLET_FACTORY_MAIN_FRAGMENT:
                currentFragment = MainFragment.newInstance(0, null);
                break;
            case CWP_WALLET_FACTORY_MANAGER_FRAGMENT:
                currentFragment =  ManagerFragment.newInstance(0, null);
                break;

            case CWP_WALLET_FACTORY_SEND_FRAGMENT:
                currentFragment =  SendFragment.newInstance(0,null);
                break;
            case CWP_WALLET_FACTORY_EDIT_MODE:
                currentFragment = EditableWalletFragment.newInstance(0, null, false, null);
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found",new Exception(),fragments.getKey(),"Swith failed");
        }
        return currentFragment;
    }

    @Override
    public WaletFactoryFragmentsEnumType getFermatFragmentEnumType(String key) {
        return WaletFactoryFragmentsEnumType.getValue(key);
    }


}
