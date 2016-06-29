package unit.com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWalletDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateTableException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletDatabaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.UUID;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.googlecode.catchexception.CatchException.*;
/**
 * Created by jorgegonzalez on 2015.07.13..
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
    private DatabaseTableFactory mockWalletTableFactory;
    @Mock
    private DatabaseTableFactory mockBalanceTableFactory;
    @Mock
    private DatabaseTableRecord mockTableRecord;

    private UUID testOwnerId;
    private UUID testWalletId;
    private BitcoinWalletDatabaseFactory testDatabaseFactory;

    private void setUpIds(){
        testOwnerId = UUID.randomUUID();
        testWalletId = UUID.randomUUID();
    }

    private void setUpMockitoGeneralRules() throws Exception{
        when(mockPluginDatabaseSystem.createDatabase(testOwnerId, testWalletId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(testOwnerId, BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME)).thenReturn(mockWalletTableFactory);
        when(mockDatabaseFactory.newTableFactory(testOwnerId, BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME)).thenReturn(mockBalanceTableFactory);
        when(mockDatabase.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME)).thenReturn(mockTable);
        when(mockDatabase.getTable(BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getEmptyRecord()).thenReturn(mockTableRecord);
    }

    @Before
    public void setUp() throws Exception{
        setUpIds();
        setUpMockitoGeneralRules();
    }

    @Test
    public void CreateDatabase_DatabaseAndTablesProperlyCreated_ReturnsDatabase() throws Exception{
        testDatabaseFactory = new BitcoinWalletDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        Database checkDatabase = testDatabaseFactory.createDatabase(testOwnerId, testWalletId);

        assertThat(checkDatabase).isEqualTo(mockDatabase);
    }

    @Test
    public void CreateDatabase_PluginSystemCantCreateDatabase_ThrowsCantCreateDatabaseException() throws Exception{
        when(mockPluginDatabaseSystem.createDatabase(testOwnerId, testWalletId.toString())).thenThrow(new CantCreateDatabaseException("MOCK", null, null, null));

        testDatabaseFactory = new BitcoinWalletDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testWalletId);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }

    @Test
    public void CreateDatabase_CantCreateBitcoinWalletTable_ThrowsCantCreateDatabaseException() throws Exception{
        doThrow(new CantCreateTableException("MOCK", null, null, null)).when(mockDatabaseFactory).createTable(mockWalletTableFactory);
        testDatabaseFactory = new BitcoinWalletDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testWalletId);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }

    @Test
    public void CreateDatabase_CantCreateBitcoinWalletBalancesTable_ThrowsCantCreateDatabaseException() throws Exception{
        doThrow(new CantCreateTableException("MOCK", null, null, null)).when(mockDatabaseFactory).createTable(mockBalanceTableFactory);
        testDatabaseFactory = new BitcoinWalletDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testWalletId);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }

    @Test
    public void CreateDatabase_CantInsertInitialBalancesRecord_ThrowsCantCreateDatabaseException() throws Exception{
        doThrow(new CantInsertRecordException("MOCK", null, null, null)).when(mockTable).insertRecord(mockTableRecord);
        testDatabaseFactory = new BitcoinWalletDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testWalletId);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }
    @Test
    public void CreateDatabase_ConflictedIdWhenCreatingWalletTable_ThrowsCantCreateDatabaseException() throws Exception{
        when(mockDatabaseFactory.newTableFactory(testOwnerId, BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_NAME)).thenThrow(new InvalidOwnerIdException("MOCK", null, null, null));
        testDatabaseFactory = new BitcoinWalletDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testWalletId);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }

    @Test
    public void CreateDatabase_ConflictedIdWhenCreatingBalancesTable_ThrowsCantCreateDatabaseException() throws Exception{
        when(mockDatabaseFactory.newTableFactory(testOwnerId, BitcoinWalletDatabaseConstants.BITCOIN_WALLET_BALANCE_TABLE_NAME)).thenThrow(new InvalidOwnerIdException("MOCK", null, null, null));
        testDatabaseFactory = new BitcoinWalletDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testWalletId);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }

    @Test
    public void CreateDatabase_GeneralExceptionThrown_ThrowsCantCreateDatabaseException() throws Exception{
        when(mockDatabase.getDatabaseFactory()).thenReturn(null);
        testDatabaseFactory = new BitcoinWalletDatabaseFactory();
        testDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);

        catchException(testDatabaseFactory).createDatabase(testOwnerId, testWalletId);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateDatabaseException.class);
    }
}
