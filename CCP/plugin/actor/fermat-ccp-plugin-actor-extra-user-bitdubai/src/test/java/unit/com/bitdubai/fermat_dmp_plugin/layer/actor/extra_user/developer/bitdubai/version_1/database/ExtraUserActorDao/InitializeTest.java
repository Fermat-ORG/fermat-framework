package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions.CantInitializeExtraUserActorDatabaseException;
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
 * Created by natalia on 03/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class InitializeTest extends TestCase
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

    private UUID pluginId;

    private ExtraUserActorDao extraUserActorDao;


    private void setUpMockitoGeneralRules() throws Exception{
        when(mockPluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(pluginId, ExtraUserActorDatabaseConstants.EXTRA_USER_TABLE_NAME)).thenReturn(mockIntraUserTableFactory);
        when(mockDatabase.getTable(ExtraUserActorDatabaseConstants.EXTRA_USER_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getEmptyRecord()).thenReturn(mockTableRecord);
    }

    @Before
    public void setUp() throws Exception
    {
        pluginId= UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);

        setUpMockitoGeneralRules();
    }

    @Test
    public void initializeDatabaseTest_InitError_throwsCantInitializeExtraUserActorDatabaseException() throws Exception {

        extraUserActorDao = new ExtraUserActorDao(null,  pluginId);

        catchException(extraUserActorDao).initialize();
        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantInitializeExtraUserActorDatabaseException.class);
    }

    @Test
    public void initializeDatabaseTest_InitSucefuly_throwsCantInitializeExtraUserActorDatabaseException() throws Exception {

        extraUserActorDao = new ExtraUserActorDao(mockPluginDatabaseSystem,  pluginId);

        catchException(extraUserActorDao).initialize();
        assertThat(CatchException.<Exception>caughtException()).isNull();
    }
}
