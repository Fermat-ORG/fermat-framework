package com.bitdubai.fermat.dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version1.structure.asset_distribution_recorder_service;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartServiceException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDao;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.database.AssetDistributionDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.version_1.structure.events.AssetDistributionRecorderService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by Luis Campo (campusprize@gmail.com) on 02/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StopTest {
    @Mock
    private EventManager eventManager;
    @Mock
    private FermatEventListener fermatEventListener1;
    @Mock
    private FermatEventListener fermatEventListener2;
    @Mock
    private FermatEventListener fermatEventListener3;
    @Mock
    private FermatEventListener fermatEventListener4;
    @Mock
    private FermatEventListener fermatEventListener5;
    @Mock
    private ErrorManager errorManager;
    private UUID pluginId;
    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;
    private AssetDistributionRecorderService assetDistributionRecorderService;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    private Database database = Mockito.mock(Database.class);
    private AssetDistributionDao assetDistributionDao = Mockito.mock(AssetDistributionDao.class);

    @Before
    public void init() throws Exception {
        pluginId = UUID.randomUUID();
        assetDistributionRecorderService = new AssetDistributionRecorderService(assetDistributionDao, eventManager);
        setUpMockitoRules();
    }

    private void setUpMockitoRules() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetDistributionDatabaseConstants.ASSET_DISTRIBUTION_DATABASE)).thenReturn(database);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER)).thenReturn(fermatEventListener1);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER)).thenReturn(fermatEventListener2);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_USER)).thenReturn(fermatEventListener3);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_USER)).thenReturn(fermatEventListener4);
        when(eventManager.getNewListener(EventType.RECEIVED_NEW_DIGITAL_ASSET_METADATA_NOTIFICATION)).thenReturn(fermatEventListener5);
    }

    @Test
    public void stopRecordServiceSucces() throws CantStartServiceException {
        assetDistributionRecorderService.start();
        assetDistributionRecorderService.stop();
        ServiceStatus serviceStatus = assetDistributionRecorderService.getStatus();
        Assert.assertEquals(ServiceStatus.STOPPED, serviceStatus);
    }
}
