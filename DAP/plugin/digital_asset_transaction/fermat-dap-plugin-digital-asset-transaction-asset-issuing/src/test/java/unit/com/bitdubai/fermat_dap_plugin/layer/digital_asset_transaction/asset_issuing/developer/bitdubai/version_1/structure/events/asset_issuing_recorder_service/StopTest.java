package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.asset_issuing_recorder_service;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.events.AssetIssuingRecorderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 05/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class StopTest {
    AssetIssuingRecorderService assetIssuingRecorderService;
    AssetIssuingDAO assetIssuingDAO;
    UUID pluginId;
    ServiceStatus serviceStatusExpected = ServiceStatus.STOPPED;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    @Mock
    EventManager eventManager;

    @Mock
    FermatEventListener fermatEventListener1;

    @Mock
    FermatEventListener fermatEventListener2;

    @Mock
    FermatEventListener fermatEventListener3;

    @Mock
    FermatEventListener fermatEventListener4;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE)).thenReturn(database);

        assetIssuingDAO = new AssetIssuingDAO(pluginDatabaseSystem, pluginId);
        assetIssuingRecorderService = new AssetIssuingRecorderService(assetIssuingDAO, eventManager);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener1);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener2);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener3);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener4);
    }

    @Test
    public void test_OK() throws Exception {
        assetIssuingRecorderService.start();
        assetIssuingRecorderService.stop();
        ServiceStatus serviceStatus = assetIssuingRecorderService.getStatus();
        assertThat(serviceStatus).isEqualTo(serviceStatusExpected);
    }

    @Test
    public void test_Throws_CantStartServiceException() throws Exception {
        //TODO implement exception test
    }
}
