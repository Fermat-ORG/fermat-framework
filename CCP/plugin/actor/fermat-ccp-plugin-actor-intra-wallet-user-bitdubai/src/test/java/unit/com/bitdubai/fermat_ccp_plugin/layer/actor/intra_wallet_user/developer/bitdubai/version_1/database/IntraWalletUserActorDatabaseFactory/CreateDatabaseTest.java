package unit.com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserActorDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.InvalidOwnerIdException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserActorDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserActorDatabaseFactory;

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
 * Created by natalia on 21/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateDatabaseTest{

@Mock
private PluginDatabaseSystem mockPluginDatabaseSystem;
@Mock
private Database mockDatabase;
@Mock
private DatabaseFactory mockDatabaseFactory;
@Mock
private DatabaseTable mockTable;

@Mock
private DatabaseTableFactory mockIntraUserTableFactory;
@Mock
private DatabaseTableRecord mockTableRecord;

private UUID testOwnerId;


private IntraWalletUserActorDatabaseFactory testDatabaseFactory;

        private void setUpIds(){
            testOwnerId = UUID.randomUUID();

        }

        private void setUpMockitoGeneralRules() throws Exception{
            when(mockPluginDatabaseSystem.createDatabase(testOwnerId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME)).thenReturn(mockDatabase);
            when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
            when(mockDatabaseFactory.newTableFactory(testOwnerId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME)).thenReturn(mockIntraUserTableFactory);
            when(mockDatabase.getTable(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME)).thenReturn(mockTable);
            when(mockTable.getEmptyRecord()).thenReturn(mockTableRecord);
        }

        @Before
        public void setUp() throws Exception{
            setUpIds();
            setUpMockitoGeneralRules();
        }

        @Test
        public void CreateDatabase_DatabaseAndTablesProperlyCreated_ReturnsDatabase() throws Exception{
            testDatabaseFactory = new IntraWalletUserActorDatabaseFactory(mockPluginDatabaseSystem);

            Database checkDatabase = testDatabaseFactory.createDatabase(testOwnerId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME);

            assertThat(checkDatabase).isEqualTo(mockDatabase);
        }

        @Test
        public void CreateDatabase_PluginSystemCantCreateDatabase_ThrowsCantCreateDatabaseException() throws Exception{
            when(mockPluginDatabaseSystem.createDatabase(testOwnerId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME)).thenThrow(new CantCreateDatabaseException("MOCK", null, null, null));

            testDatabaseFactory = new IntraWalletUserActorDatabaseFactory(mockPluginDatabaseSystem);

            catchException(testDatabaseFactory).createDatabase(testOwnerId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME);

            assertThat(caughtException())
                    .isNotNull()
                    .isInstanceOf(CantCreateDatabaseException.class);
        }

        @Test
        public void CreateDatabase_CantCreateIntraUserTable_ThrowsCantCreateDatabaseException() throws Exception{

            when(mockDatabaseFactory.newTableFactory(testOwnerId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME)).thenThrow(new InvalidOwnerIdException("MOCK", null, null, null));
            testDatabaseFactory = new IntraWalletUserActorDatabaseFactory(mockPluginDatabaseSystem);

            catchException(testDatabaseFactory).createDatabase(testOwnerId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME);

            assertThat(caughtException())
                    .isNotNull()
                    .isInstanceOf(CantCreateDatabaseException.class);
        }



        @Test
        public void CreateDatabase_ConflictedIdWhenCreatingIntraUserTable_ThrowsCantCreateDatabaseException() throws Exception{
            when(mockDatabaseFactory.newTableFactory(testOwnerId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME)).thenThrow(new InvalidOwnerIdException("MOCK", null, null, null));
            testDatabaseFactory = new IntraWalletUserActorDatabaseFactory(mockPluginDatabaseSystem);

            catchException(testDatabaseFactory).createDatabase(testOwnerId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME);

            assertThat(caughtException())
                    .isNotNull()
                    .isInstanceOf(CantCreateDatabaseException.class);
        }



        @Test
        public void CreateDatabase_GeneralExceptionThrown_ThrowsCantCreateDatabaseException() throws Exception{
            when(mockDatabase.getDatabaseFactory()).thenReturn(null);

            testDatabaseFactory = new IntraWalletUserActorDatabaseFactory(mockPluginDatabaseSystem);

            catchException(testDatabaseFactory).createDatabase(testOwnerId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME);

            assertThat(caughtException())
                    .isNotNull()
                    .isInstanceOf(CantCreateDatabaseException.class);
        }
}
