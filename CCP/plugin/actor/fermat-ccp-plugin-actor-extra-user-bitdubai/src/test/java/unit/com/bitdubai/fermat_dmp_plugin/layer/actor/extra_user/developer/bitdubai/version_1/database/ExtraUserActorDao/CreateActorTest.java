package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDao;

import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDatabaseConstants;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 03/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateActorTest extends TestCase {

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

    @Mock
    private List<DatabaseTableRecord> mockRecords;

    private UUID pluginId;

    private ExtraUserActorDao extraUserActorDao;

    private String actorPublicKey;

    private void setUpMockitoGeneralRules() throws Exception {
        when(mockPluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(pluginId, ExtraUserActorDatabaseConstants.EXTRA_USER_TABLE_NAME)).thenReturn(mockIntraUserTableFactory);
        when(mockDatabase.getTable(ExtraUserActorDatabaseConstants.EXTRA_USER_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getEmptyRecord()).thenReturn(mockTableRecord);
        when(mockTable.getRecords()).thenReturn(mockRecords);
    }

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();

        actorPublicKey = UUID.randomUUID().toString();

        when(mockPluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);
        extraUserActorDao = new ExtraUserActorDao(mockPluginDatabaseSystem, pluginId);
        extraUserActorDao.initialize();
        setUpMockitoGeneralRules();
    }

    @Test
    public void createActorTest_CreateSucefuly_throwsCantCreateExtraUserException() throws Exception {

        catchException(extraUserActorDao).createActor("actor", actorPublicKey, 12562);
        assertThat(caughtException()).isNull();

    }

    @Test
    public void createActorTest_CreateError_throwsCantCreateExtraUserException() throws Exception {

        catchException(extraUserActorDao).createActor(null, actorPublicKey, 12562);
        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantCreateExtraUserException.class);
    }
}
