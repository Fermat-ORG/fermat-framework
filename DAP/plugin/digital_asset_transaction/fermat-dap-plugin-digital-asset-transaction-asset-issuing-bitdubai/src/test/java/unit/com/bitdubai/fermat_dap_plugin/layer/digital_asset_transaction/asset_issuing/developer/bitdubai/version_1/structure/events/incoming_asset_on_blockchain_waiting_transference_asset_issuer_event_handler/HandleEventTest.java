package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.incoming_asset_on_blockchain_waiting_transference_asset_issuer_event_handler;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingRecorderService;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventHandler;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by frank on 05/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class HandleEventTest {
    IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventHandler incomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventHandler;
    AssetIssuingRecorderService assetIssuingRecorderService;
    AssetIssuingTransactionDao assetIssuingTransactionDao;
    UUID pluginId;
    String eventTypeCode = "eventType";

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    @Mock
    DatabaseTable databaseTable;

    @Mock
    DatabaseTableRecord databaseTableRecord;

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

    @Mock
    IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent event;

    @Mock
    EventType eventType;

    EventSource eventSource = EventSource.ACTOR_ASSET_USER;

    @Before
    public void setUp() throws Exception {

        pluginId = UUID.randomUUID();

        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE)).thenReturn(database);

        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener1);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener2);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_BLOCKCHAIN_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener3);
        when(eventManager.getNewListener(EventType.INCOMING_ASSET_REVERSED_ON_CRYPTO_NETWORK_WAITING_TRANSFERENCE_ASSET_ISSUER)).thenReturn(fermatEventListener4);

        assetIssuingTransactionDao = new AssetIssuingTransactionDao(pluginDatabaseSystem, pluginId);
        assetIssuingRecorderService = new AssetIssuingRecorderService(assetIssuingTransactionDao, eventManager);
        assetIssuingRecorderService.start();

        incomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventHandler = new IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventHandler();
        incomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventHandler.setAssetIssuingRecorderService(assetIssuingRecorderService);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(event.getEventType()).thenReturn(eventType);
        when(event.getSource()).thenReturn(eventSource);
        when(eventType.getCode()).thenReturn(eventTypeCode);

        when(database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_NAME)).thenReturn(databaseTable);
        when(databaseTable.getEmptyRecord()).thenReturn(databaseTableRecord);
    }

    @Test
    public void test_OK() throws Exception {
        incomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventHandler.handleEvent(event);
    }
}
