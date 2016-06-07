package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.CryptoIndexDao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDao;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDatabaseFactory;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 14/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class InitializeDatabaseTest {
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
    @Mock
    private List<DatabaseTableRecord> mockRecords;
    @Mock
    private CryptoIndexDao cryptoIndexDao;

    private void setUpIds() {
        testOwnerId = UUID.randomUUID();
    }

    @Test
    public void TestInitializeDatabaseTest_successful() throws Exception {
        setUpIds();
        when(mockPluginDatabaseSystem.openDatabase(testOwnerId, CryptoIndexDatabaseConstants.CRYPTO_INDEX_DATABASE_NAME)).thenReturn(mockDatabase);
        cryptoIndexDao = new CryptoIndexDao(mockPluginDatabaseSystem, testOwnerId);
        catchException(cryptoIndexDao).initializeDatabase();
        Assertions.assertThat(caughtException()).isNull();
    }

    @Test
    public void TestInitializeDatabaseTest_ThrowCantInitializeCryptoIndexDatabaseException() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testOwnerId, CryptoIndexDatabaseConstants.CRYPTO_INDEX_DATABASE_NAME)).thenReturn(mockDatabase);
        cryptoIndexDao = new CryptoIndexDao(null, null);
        catchException(cryptoIndexDao).initializeDatabase();
        Assertions.assertThat(caughtException()).isNotNull();
    }
}
