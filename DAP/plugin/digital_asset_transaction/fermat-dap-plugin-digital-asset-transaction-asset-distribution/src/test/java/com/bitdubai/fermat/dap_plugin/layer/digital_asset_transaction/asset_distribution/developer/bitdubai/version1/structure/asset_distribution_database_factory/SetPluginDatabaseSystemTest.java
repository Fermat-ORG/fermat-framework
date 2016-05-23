package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_database_factory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 29/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SetPluginDatabaseSystemTest {
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private AssetDistributionDatabaseFactory assetDistributionDatabaseFactory;


    @Before
    public void init() throws Exception{
        assetDistributionDatabaseFactory = new AssetDistributionDatabaseFactory(mockPluginDatabaseSystem);
    }

    @Test
    public void setPluginDatabaseSystemTest() throws Exception{
        assetDistributionDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
    }

}
