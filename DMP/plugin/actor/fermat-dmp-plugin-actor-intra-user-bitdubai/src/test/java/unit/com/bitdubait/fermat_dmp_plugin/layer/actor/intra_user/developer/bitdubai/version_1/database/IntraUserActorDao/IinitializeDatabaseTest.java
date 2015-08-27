package unit.com.bitdubait.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDao;


import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.enums.ContactState;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDao;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraUserActorDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraUserActorDatabaseException;
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
public class IinitializeDatabaseTest extends TestCase
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

    private IntraUserActorDao intraUserActorDao;



    private void setUpMockitoGeneralRules() throws Exception{
        when(mockPluginDatabaseSystem.createDatabase(pluginId, IntraUserActorDatabaseConstants.INTRA_USER_DATABASE_NAME)).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(pluginId, IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME)).thenReturn(mockIntraUserTableFactory);
        when(mockDatabase.getTable(IntraUserActorDatabaseConstants.INTRA_USER_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getEmptyRecord()).thenReturn(mockTableRecord);
    }

    @Before
    public void setUp() throws Exception
    {
        pluginId= UUID.randomUUID();

         when(mockPluginDatabaseSystem.openDatabase(pluginId, IntraUserActorDatabaseConstants.INTRA_USER_DATABASE_NAME)).thenReturn(mockDatabase);

        setUpMockitoGeneralRules();
    }

    @Test
    public void initializeDatabaseTest_InitError_throwsCantInitializeIntraUserActorDatabaseException() throws Exception {

        intraUserActorDao = new IntraUserActorDao(null, mockPluginFileSystem, pluginId);

        catchException(intraUserActorDao).initializeDatabase();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantInitializeIntraUserActorDatabaseException.class);

        }

  /*  @Test
    public void initializeDatabaseTest_InitSucefuly_throwsCantInitializeIntraUserActorDatabaseException() throws Exception {

        intraUserActorDao = new IntraUserActorDao(mockPluginDatabaseSystem, mockPluginFileSystem, pluginId);

        catchException(intraUserActorDao).initializeDatabase();
        assertThat(CatchException.<Exception>caughtException()).isNull();

        byte[] profileImage = new byte[10];

        catchException(intraUserActorDao).createNewIntraUser(intraUserPublicKey, intraUserAlias, intraUserLoggedPublicKey, profileImage, ContactState.CONNECTED);
        assertThat(CatchException.<Exception>caughtException()).isNotNull();
    }*/
}
