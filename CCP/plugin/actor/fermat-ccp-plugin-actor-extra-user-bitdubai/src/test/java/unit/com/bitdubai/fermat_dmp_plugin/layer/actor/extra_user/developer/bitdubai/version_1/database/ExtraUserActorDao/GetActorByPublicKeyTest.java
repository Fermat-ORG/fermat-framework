package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDao;

import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantGetExtraUserException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDao;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDatabaseConstants;

import junit.framework.TestCase;

import org.fest.assertions.api.Assertions;
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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 03/09/15.
 */


@RunWith(MockitoJUnitRunner.class)
public class GetActorByPublicKeyTest extends TestCase {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTable mockTable;

    @Mock
    private DatabaseTableFactory mockExtraUserTableFactory;
    @Mock
    private DatabaseTableRecord mockTableRecord;

    @Mock
    private List<DatabaseTableRecord> mockRecords;

    private UUID pluginId;

    private ExtraUserActorDao extraUserActorDao;

    private String actorPublicKey;

    private void setUpMockitoGeneralRules() throws Exception {
        when(mockPluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(ExtraUserActorDatabaseConstants.EXTRA_USER_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(mockRecords);
        when(mockRecords.get(anyInt())).thenReturn(mockTableRecord);
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
    public void getActorByPublicKeyTest_GetSucefuly_throwsCantGetExtraUserExceptionExtraUserNotFoundException() throws Exception {

        Actor actor = extraUserActorDao.getActorByPublicKey(actorPublicKey);

        Assertions.assertThat(actor)
                .isNotNull();
    }

    @Test
    public void getActorByPublicKeyTest_GetError_throwsCantGetExtraUserException() throws Exception {

        catchException(extraUserActorDao).getActorByPublicKey(null);
        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantGetExtraUserException.class);
    }
}
