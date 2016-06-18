package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.CryptoIndexDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDatabaseFactory;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by francisco on 09/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateDatabaseTest {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTable mockTable;

    @Mock
    private DatabaseTableFactory mockCryptoIndexTableFactory;
    @Mock
    private DatabaseTableRecord mockTableRecord;

    private UUID testOwnerId;

    @Mock
    private CryptoIndexDatabaseFactory cryptoIndexDatabaseFactory;

    private void setUpIds() {
        testOwnerId = UUID.randomUUID();
    }

    private void setUpMockitoGeneralRules() throws Exception {
        when(mockPluginDatabaseSystem.createDatabase(testOwnerId, CryptoIndexDatabaseConstants.CRYPTO_INDEX_DATABASE_NAME)).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(testOwnerId, CryptoIndexDatabaseConstants.CRYPTO_INDEX_TABLE_NAME)).thenReturn(mockCryptoIndexTableFactory);
        when(mockDatabase.getTable(CryptoIndexDatabaseConstants.CRYPTO_INDEX_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getEmptyRecord()).thenReturn(mockTableRecord);
    }

    @Before
    public void setUp() throws Exception {
        setUpIds();
        setUpMockitoGeneralRules();
    }

    @Test
    public void TestCreateDatabase() throws Exception {
        // testOwnerId=UUID.randomUUID();
        cryptoIndexDatabaseFactory = new CryptoIndexDatabaseFactory(mockPluginDatabaseSystem);
        Database checkDatabase = cryptoIndexDatabaseFactory.createDatabase(testOwnerId, "CryptoIndexList");
        System.out.println(checkDatabase);
        Assertions.assertThat(checkDatabase).isEqualTo(mockDatabase);

    }
}
