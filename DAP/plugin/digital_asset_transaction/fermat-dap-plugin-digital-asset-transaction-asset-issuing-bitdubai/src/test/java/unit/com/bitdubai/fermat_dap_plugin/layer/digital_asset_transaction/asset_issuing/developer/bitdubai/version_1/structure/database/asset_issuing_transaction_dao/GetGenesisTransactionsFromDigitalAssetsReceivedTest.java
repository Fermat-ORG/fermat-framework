package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.asset_issuing_transaction_dao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.TransactionStatus;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDatabaseConstants;

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
import static org.mockito.Mockito.when;

/**
 * Created by frank on 02/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetGenesisTransactionsFromDigitalAssetsReceivedTest {
    AssetIssuingTransactionDao assetIssuingTransactionDao;
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
    TransactionStatus transactionStatusExpected = TransactionStatus.FORMING_GENESIS;
    List<DatabaseTableRecord> records;
    List<DatabaseTableRecord> recordsForException;
    List<String> genesisTransactionsExpected;
    String genesisTransactionExpected = "genesisTransactionExpected";

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE)).thenReturn(database);
        assetIssuingTransactionDao = new AssetIssuingTransactionDao(pluginDatabaseSystem, pluginId);

        records = new LinkedList<>();
        records.add(databaseTableRecord);

        recordsForException = new LinkedList<>();
        recordsForException.add(databaseTableRecord);
        recordsForException.add(databaseTableRecord);

        genesisTransactionsExpected = new LinkedList<>();
        genesisTransactionsExpected.add(genesisTransactionExpected);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(database.getTable(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_TABLE_NAME)).thenReturn(databaseTable);
        when(databaseTable.getRecords()).thenReturn(records);
        when(databaseTableRecord.getStringValue(AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_ASSET_ISSUING_GENESIS_TRANSACTION_COLUMN_NAME)).thenReturn(genesisTransactionExpected);
    }

    @Test
    public void test_OK() throws Exception {
        List<String> genesisTransactions = assetIssuingTransactionDao.getGenesisTransactionsFromDigitalAssetsReceived();
        assertThat(genesisTransactions).isEqualTo(genesisTransactionsExpected);
    }

    @Test
    public void test_Throws_CantCheckAssetIssuingProgressException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, AssetIssuingTransactionDatabaseConstants.DIGITAL_ASSET_TRANSACTION_DATABASE)).thenThrow(new CantOpenDatabaseException("error"));
        catchException(assetIssuingTransactionDao).getGenesisTransactionsFromDigitalAssetsReceived();
        Exception thrown = caughtException();
        assertThat(thrown)
                .isNotNull()
                .isInstanceOf(CantCheckAssetIssuingProgressException.class);
        assertThat(thrown.getCause())
                .isNotNull()
                .isInstanceOf(CantExecuteDatabaseOperationException.class);
    }
}
