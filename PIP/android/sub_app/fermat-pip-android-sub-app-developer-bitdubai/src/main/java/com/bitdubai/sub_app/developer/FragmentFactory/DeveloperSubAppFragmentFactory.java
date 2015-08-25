package com.bitdubai.sub_app.developer.FragmentFactory;

import android.app.Fragment;

import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces.SubAppSettingsManager;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsDatabaseListFragment;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsDatabaseTableListFragment;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsDatabaseTableRecordListFragment;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsFragment;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragment;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragmentLevel1;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragmentLevel2;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragmentLevel3;
import com.bitdubai.sub_app.developer.session.DeveloperSubAppSession;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */

public class DeveloperSubAppFragmentFactory implements com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppFragmentFactory {


    public DeveloperSubAppFragmentFactory(){

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
            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT:
                currentFragment = DatabaseToolsDatabaseListFragment.newInstance(0,(DeveloperSubAppSession) subAppsSession);
                break;
            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT:
                currentFragment = DatabaseToolsDatabaseTableListFragment.newInstance(0, subAppsSession);
                break;
            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT:
                currentFragment = DatabaseToolsDatabaseTableRecordListFragment.newInstance(0, subAppsSession);
                break;

            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT:
                currentFragment =  DatabaseToolsFragment.newInstance(0, subAppsSession);
                break;
            case CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT:
                currentFragment = LogToolsFragment.newInstance(0, subAppsSession);
                break;
            case CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT:
                currentFragment = LogToolsFragmentLevel1.newInstance(0, subAppsSession);
                break;
            case CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT:
                currentFragment = LogToolsFragmentLevel2.newInstance(0, subAppsSession);
                break;
            case CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT:
                currentFragment = LogToolsFragmentLevel3.newInstance(0, subAppsSession);
                break;
            default:
                throw new FragmentNotFoundException("Fragment not found",new Exception(),code,"Swith failed");
        }
        return currentFragment;
    }

}
