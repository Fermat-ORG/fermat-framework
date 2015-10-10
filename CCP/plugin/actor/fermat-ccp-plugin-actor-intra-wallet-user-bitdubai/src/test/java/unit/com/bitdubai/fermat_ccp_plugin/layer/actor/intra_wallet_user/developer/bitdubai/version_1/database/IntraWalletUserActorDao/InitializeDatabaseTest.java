package unit.com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserActorDao;


import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserActorDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraWalletUserActorDatabaseException;
import com.googlecode.catchexception.CatchException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 24/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class InitializeDatabaseTest extends TestCase
{

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

    /**
     * UsesFileSystem Interface member variables.
     */
    @Mock
    private PluginFileSystem mockPluginFileSystem;

    private UUID pluginId;

    private IntraWalletUserActorDao intraWalletUserActorDao;



    private void setUpMockitoGeneralRules() throws Exception{
        when(mockPluginDatabaseSystem.createDatabase(pluginId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME)).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(pluginId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME)).thenReturn(mockIntraUserTableFactory);
        when(mockDatabase.getTable(IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getEmptyRecord()).thenReturn(mockTableRecord);
    }

    @Before
    public void setUp() throws Exception
    {
        pluginId= UUID.randomUUID();

         when(mockPluginDatabaseSystem.openDatabase(pluginId, IntraWalletUserActorDatabaseConstants.INTRA_WALLET_USER_DATABASE_NAME)).thenReturn(mockDatabase);

        setUpMockitoGeneralRules();
    }

    @Test
    public void initializeDatabaseTest_InitError_throwsCantInitializeIntraUserActorDatabaseException() throws Exception {

        intraWalletUserActorDao = new IntraWalletUserActorDao(null, mockPluginFileSystem, pluginId);

        catchException(intraWalletUserActorDao).initializeDatabase();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantInitializeIntraWalletUserActorDatabaseException.class);

        }

   @Test
    public void initializeDatabaseTest_InitSucefuly_throwsCantInitializeIntraUserActorDatabaseException() throws Exception {

        intraWalletUserActorDao = new IntraWalletUserActorDao(mockPluginDatabaseSystem, mockPluginFileSystem, pluginId);

        catchException(intraWalletUserActorDao).initializeDatabase();
        assertThat(CatchException.<Exception>caughtException()).isNull();


    }
}
