package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.asset_issuing_transaction_developer_database_factory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 05/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableListTest {
    UUID pluginId;
    List<String> eventsRecorderColumns;
    List<String> assetIssuingColumns;
    List<DeveloperDatabaseTable> tablesExpected;
    List<String> digitalAssetTransactionColumns;

    @Mock
    DeveloperObjectFactory developerObjectFactory;

    @Mock
    DeveloperDatabaseTable digitalAssetTransactionTable;

    @Mock
    DeveloperDatabaseTable eventsRecorderTable;

    @Mock
    DeveloperDatabaseTable assetIssuingTable;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        digitalAssetTransactionColumns = new ArrayList<>();
        digitalAssetTransactionColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_LOCAL_STORAGE_PATH_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_TO_GENERATE_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_ASSETS_COMPLETED_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_NETWORK_TYPE_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_BTC_WALLET_PUBLIC_KEY_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_ISSUING_STATUS_COLUMN_NAME);

        eventsRecorderColumns = new ArrayList<String>();
        eventsRecorderColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_ID_COLUMN_NAME);
        eventsRecorderColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_EVENT_COLUMN_NAME);
        eventsRecorderColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_SOURCE_COLUMN_NAME);
        eventsRecorderColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_STATUS_COLUMN_NAME);
        eventsRecorderColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TIMESTAMP_COLUMN_NAME);

        assetIssuingColumns=new ArrayList<>();
        assetIssuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_GENESIS_ADDRESS_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_CRYPTO_STATUS_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_PROTOCOL_STATUS_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_PUBLIC_KEY_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingDatabaseConstants.ASSET_ISSUING_OUTGOING_ID_COLUMN_NAME);

        tablesExpected = new LinkedList<>();
        tablesExpected.add(digitalAssetTransactionTable);
        tablesExpected.add(eventsRecorderTable);
        tablesExpected.add(assetIssuingTable);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_TABLE_NAME, digitalAssetTransactionColumns)).thenReturn(digitalAssetTransactionTable);
        when(developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_EVENTS_RECORDED_TABLE_NAME, eventsRecorderColumns)).thenReturn(eventsRecorderTable);
        when(developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_TABLE, assetIssuingColumns)).thenReturn(assetIssuingTable);
    }

    @Test
    public void test_OK() throws Exception {
        List<DeveloperDatabaseTable> tables = AssetIssuingTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
        assertThat(tables).isNotNull().isNotEmpty().hasSize(3);
        assertThat(tables).isEqualTo(tablesExpected);
    }
}
