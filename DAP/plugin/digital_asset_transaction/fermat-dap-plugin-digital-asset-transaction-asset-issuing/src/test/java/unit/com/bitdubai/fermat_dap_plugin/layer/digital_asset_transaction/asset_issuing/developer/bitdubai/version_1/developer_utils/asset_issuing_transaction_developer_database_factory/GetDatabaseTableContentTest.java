package unit.com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.developer_utils.asset_issuing_transaction_developer_database_factory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.developer_utils.AssetIssuingTransactionDeveloperDatabaseFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by frank on 05/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableContentTest {
    List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordsExpected;
    List<DatabaseTableRecord> records;
    List<DatabaseRecord> databaseRecords;
    List<String> developerRow;
    String databaseRecordValue = "databaseRecordValue";

    @Mock
    DeveloperObjectFactory developerObjectFactory;

    @Mock
    Database database;

    @Mock
    DeveloperDatabaseTable developerDatabaseTable;

    @Mock
    DatabaseTable databaseTable;

    @Mock
    DatabaseTableRecord databaseTableRecord;

    @Mock
    DatabaseRecord databaseRecord;

    @Mock
    DeveloperDatabaseTableRecord developerDatabaseTableRecord;

    @Before
    public void setUp() throws Exception {
        records = new LinkedList<>();
        records.add(databaseTableRecord);

        databaseRecords = new LinkedList<>();
        databaseRecords.add(databaseRecord);

        developerRow = new LinkedList<>();
        developerRow.add(databaseRecordValue);

        developerDatabaseTableRecordsExpected = new LinkedList<>();
        developerDatabaseTableRecordsExpected.add(developerDatabaseTableRecord);

        mockitoRules();
    }

    private void mockitoRules() throws Exception {
        when(database.getTable(developerDatabaseTable.getName())).thenReturn(databaseTable);
        when(databaseTable.getRecords()).thenReturn(records);
        when(databaseTableRecord.getValues()).thenReturn(databaseRecords);
        when(databaseRecord.getValue()).thenReturn(databaseRecordValue);
        when(developerObjectFactory.getNewDeveloperDatabaseTableRecord(developerRow)).thenReturn(developerDatabaseTableRecord);
    }

    @Test
    public void test_OK() throws Exception {
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecords = AssetIssuingTransactionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable);
        assertThat(developerDatabaseTableRecords).isNotNull().isNotEmpty();
        assertThat(developerDatabaseTableRecords).isEqualTo(developerDatabaseTableRecordsExpected);
    }
}
