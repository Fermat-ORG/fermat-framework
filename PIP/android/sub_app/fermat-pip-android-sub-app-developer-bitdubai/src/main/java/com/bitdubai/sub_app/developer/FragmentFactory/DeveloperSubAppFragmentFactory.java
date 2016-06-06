package com.bitdubai.sub_app.developer.FragmentFactory;

import com.bitdubai.fermat_android_api.engine.FermatFragmentFactory;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.exceptions.FragmentNotFoundException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsDatabaseListFragment;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsDatabaseTableListFragment;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsDatabaseTableRecordListFragment;
import com.bitdubai.sub_app.developer.fragment.DatabaseToolsFragment;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragment;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragmentLevel1;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragmentLevel2;
import com.bitdubai.sub_app.developer.fragment.LogToolsFragmentLevel3;
import com.bitdubai.sub_app.developer.session.DeveloperSubAppSessionReferenceApp;

/**
 * Created by Matias Furszyfer on 2015.19.22..
 */

public class DeveloperSubAppFragmentFactory extends FermatFragmentFactory<DeveloperSubAppSessionReferenceApp, SubAppResourcesProviderManager, DeveloperFragmentsEnumType> {

    public DeveloperSubAppFragmentFactory() {
    }

    @Override
    public AbstractFermatFragment getFermatFragment(DeveloperFragmentsEnumType fragments) throws FragmentNotFoundException {

        switch (fragments) {
            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT:
                return DatabaseToolsDatabaseListFragment.newInstance();

            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT:
                return DatabaseToolsDatabaseTableListFragment.newInstance();

            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT:
                return DatabaseToolsDatabaseTableRecordListFragment.newInstance();

            case CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT:
                return DatabaseToolsFragment.newInstance();

            case CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT:
                return LogToolsFragment.newInstance();

            case CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT:
                return LogToolsFragmentLevel1.newInstance();

            case CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT:
                return LogToolsFragmentLevel2.newInstance();

            case CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT:
                return LogToolsFragmentLevel3.newInstance();

            default:
                throw new FragmentNotFoundException("Fragment not found", new Exception(), fragments.getKey(), "Swith failed");
        }
    }

    @Override
    public DeveloperFragmentsEnumType getFermatFragmentEnumType(String key) {
        return DeveloperFragmentsEnumType.getValue(key);
    }
}
