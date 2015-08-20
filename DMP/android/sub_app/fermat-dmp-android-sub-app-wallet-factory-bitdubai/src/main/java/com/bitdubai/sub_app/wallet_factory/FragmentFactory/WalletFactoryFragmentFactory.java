package com.bitdubai.sub_app.wallet_factory.FragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.SubAppSettingsManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.EditableWalletFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.MainFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.ManagerFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.ProjectsFragment;
import com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment.SendFragment;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */

public class WalletFactoryFragmentFactory implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory {


    public WalletFactoryFragmentFactory(){

    }


    /**
     * This method takes a reference (string) to a fragment and returns the corresponding fragment.
     *  @param code                           the reference used to identify the fragment
     * @param subAppsSession
     * @param subAppSettingsManager
     * @param subAppResourcesProviderManager @return the fragment referenced
     */
    @Override
    public Fragment getFragment(String code, SubAppsSession subAppsSession, SubAppSettingsManager subAppSettingsManager, SubAppResourcesProviderManager subAppResourcesProviderManager) throws FragmentNotFoundException {
        Fragment currentFragment = null;

        Fragments fragment = Fragments.getValueFromString(code);
        switch (fragment){
            /**
             * Executing fragments for BITCOIN REQUESTED.
             */
            case CWP_WALLET_FACTORY_PROJECTS_FRAGMENT:
                currentFragment =  ProjectsFragment.newInstance(0, subAppsSession);
                break;
            case CWP_WALLET_FACTORY_MAIN_FRAGMENT:
                currentFragment = MainFragment.newInstance(0, subAppsSession);
                break;
            case CWP_WALLET_FACTORY_MANAGER_FRAGMENT:
                currentFragment =  ManagerFragment.newInstance(0, subAppsSession);
                break;

            case CWP_WALLET_FACTORY_SEND_FRAGMENT:
                currentFragment =  SendFragment.newInstance(0,subAppsSession);
                break;
            case CWP_WALLET_FACTORY_EDIT_MODE:
                currentFragment = EditableWalletFragment.newInstance(0, subAppsSession, false, null);
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found",new Exception(),code,"Swith failed");
        }
        return currentFragment;
    }

}
