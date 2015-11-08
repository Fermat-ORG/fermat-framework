package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.asset_issuing_transaction_developer_database_factory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants;

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
        digitalAssetTransactionColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_PUBLIC_KEY_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_LOCAL_STORAGE_PATH_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_TO_GENERATE_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ASSETS_GENERATED_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_WALLET_PUBLIC_KEY_COLUMN_NAME);
        digitalAssetTransactionColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DIGITAL_ASSET_ISSUING_STATUS_COLUMN_NAME);

        eventsRecorderColumns = new ArrayList<String>();
        eventsRecorderColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_ID_COLUMN);
        eventsRecorderColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_EVENT_COLUMN);
        eventsRecorderColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_SOURCE_COLUMN);
        eventsRecorderColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_STATUS_COLUMN);
        eventsRecorderColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TIMESTAMP_COLUMN);

        assetIssuingColumns=new ArrayList<>();
        assetIssuingColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_ADDRESS_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_CRYPTO_STATUS_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PROTOCOL_STATUS_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_DIGITAL_ASSET_HASH_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_PUBLIC_KEY_COLUMN_NAME);
        assetIssuingColumns.add(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_OUTGOING_ID_COLUMN_NAME);

        tablesExpected = new LinkedList<>();
        tablesExpected.add(digitalAssetTransactionTable);
        tablesExpected.add(eventsRecorderTable);
        tablesExpected.add(assetIssuingTable);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_TABLE_NAME, digitalAssetTransactionColumns)).thenReturn(digitalAssetTransactionTable);
        when(developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_EVENTS_RECORDED_TABLE_NAME, eventsRecorderColumns)).thenReturn(eventsRecorderTable);
        when(developerObjectFactory.getNewDeveloperDatabaseTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME,assetIssuingColumns)).thenReturn(assetIssuingTable);
    }

    @Test
    public void test_OK() throws Exception {
        List<DeveloperDatabaseTable> tables = AssetIssuingTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
        assertThat(tables).isNotNull().isNotEmpty().hasSize(3);
        assertThat(tables).isEqualTo(tablesExpected);
    }
}
