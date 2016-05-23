package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.asset_issuing_recorder_service;

import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_bch_api.layer.definition.event_manager.events.IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantSaveEventException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.events.AssetIssuingRecorderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 05/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEventTest {
    AssetIssuingRecorderService assetIssuingRecorderService;
    AssetIssuingDAO assetIssuingDAO;
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
    IncomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent event;

    @Mock
    EventType eventType;

    EventSource eventSource = EventSource.ACTOR_ASSET_USER;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE)).thenReturn(database);

        assetIssuingDAO = new AssetIssuingDAO(pluginDatabaseSystem, pluginId);
        assetIssuingRecorderService = new AssetIssuingRecorderService(assetIssuingDAO, eventManager);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(event.getEventType()).thenReturn(eventType);
        when(event.getSource()).thenReturn(eventSource);
        when(eventType.getCode()).thenReturn(eventTypeCode);

        when(database.getTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TABLE_NAME)).thenReturn(databaseTable);
        when(databaseTable.getEmptyRecord()).thenReturn(databaseTableRecord);
    }

    @Test
    public void test_OK() throws Exception {
        assetIssuingRecorderService.incomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent(event);
    }

    @Test
    public void test_Throws_CantSaveEventException() throws Exception {
        doThrow(new CantInsertRecordException("error")).when(databaseTable).insertRecord(databaseTableRecord);
        catchException(assetIssuingRecorderService).incomingAssetOnBlockchainWaitingTransferenceAssetIssuerEvent(event);
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantSaveEventException.class);
        assertThat(thrown.getCause()).isInstanceOf(CantInsertRecordException.class);
    }
}
