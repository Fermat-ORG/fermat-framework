package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_dao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.DigitalAssetMetadataTransaction;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure.database.AssetDistributionDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 02/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class IsPendingNetworkLayerEventsTest {
    private AssetDistributionDao mockAssetDistributionDao;
    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    private DigitalAssetMetadataTransaction digitalAssetMetadataTransaction;

    @Before
    public void init () throws Exception {
        mockAssetDistributionDao = mock(AssetDistributionDao.class);
    }


    @Test
    public void isPendingNetworkLayerEventsTest () throws CantExecuteQueryException {
        mockAssetDistributionDao.isPendingNetworkLayerEvents();
    }
}
