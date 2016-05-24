package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.asset_issuing_transaction_dao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;

import org.fermat.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.exceptions.CantCheckAssetIssuingProgressException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDAO;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.database.AssetIssuingDatabaseConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 31/10/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetPendingDigitalAssetsTransactionIdByPublicKeyTest {
    AssetIssuingDAO assetIssuingDAO;
    UUID pluginId;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database database;

    @Mock
    DatabaseTable databaseTable;

    @Mock
    DatabaseTableRecord databaseTableRecord;

    String transactionId = "transactionId";
    List<DatabaseTableRecord> records;
    List<DatabaseTableRecord> recordsForException;
    String publicKey = "publicKey";
    String digitalAssetTransactionIdExpected = "digitalAssetTransactionIdExpected";
    List<String> pendingDigitalTransactionPublicIdsExpected;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE)).thenReturn(database);
        assetIssuingDAO = new AssetIssuingDAO(pluginDatabaseSystem, pluginId);

        records = new LinkedList<>();
        records.add(databaseTableRecord);

        recordsForException = new LinkedList<>();
        recordsForException.add(databaseTableRecord);
        recordsForException.add(databaseTableRecord);

        pendingDigitalTransactionPublicIdsExpected = new LinkedList<>();
        pendingDigitalTransactionPublicIdsExpected.add(digitalAssetTransactionIdExpected);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(database.getTable(AssetIssuingDatabaseConstants.ASSET_ISSUING_METADATA_TABLE)).thenReturn(databaseTable);
        when(databaseTable.getRecords()).thenReturn(records);
        //return must be differente to ISSUED
        when(databaseTableRecord.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_TRANSACTION_STATE_COLUMN_NAME)).thenReturn(TransactionStatus.DELIVERED.getCode());
        when(databaseTableRecord.getStringValue(AssetIssuingDatabaseConstants.ASSET_ISSUING_TRANSACTION_ID_COLUMN_NAME)).thenReturn(digitalAssetTransactionIdExpected);
    }

    @Test
    public void test_OK() throws Exception {
        List<String> pendingDigitalTransactionPublicIds = assetIssuingDAO.getPendingDigitalAssetsTransactionIdByPublicKey(publicKey);
        assertThat(pendingDigitalTransactionPublicIds).isEqualTo(pendingDigitalTransactionPublicIdsExpected);
    }

    @Test
    public void test_Throws_CantCheckAssetIssuingProgressException() throws Exception {
        doThrow(new CantOpenDatabaseException("error")).when(pluginDatabaseSystem).openDatabase(pluginId, AssetIssuingDatabaseConstants.ASSET_ISSUING_DATABASE);
        catchException(assetIssuingDAO).getPendingDigitalAssetPublicKeys();
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantCheckAssetIssuingProgressException.class);
    }
}
