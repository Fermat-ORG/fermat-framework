package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.ExtraUserActorPluginRoot;

import com.bitdubai.fermat_ccp_api.layer.actor.Actor;
import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantCreateExtraUserException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserActorPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.database.ExtraUserActorDatabaseFactory;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import junit.framework.TestCase;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 03/09/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateActorTest extends TestCase {

    /**
     * DealsWithEvents Interface member variables.
     */
    @Mock
    private EventManager mockEventManager;

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    private ErrorManager mockErrorManager;

    @Mock
    private LogManager mocklogManager;

    /**
     * PluginDatabaseSystem Interface member variables.
     */
    @Mock
    PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private ExtraUserActorDatabaseFactory mockExtraUserActorDatabaseFactory;

    @Mock
    DatabaseFactory mockDatabaseFactory;

    /**
     * UsesFileSystem Interface member variables.
     */
    @Mock
    private PluginFileSystem mockPluginFileSystem;

    @Mock
    private PluginTextFile mockFile;

    @Mock
    private PluginBinaryFile mockBinaryFile;

    DatabaseTable mockDatabaseTable = Mockito.mock(DatabaseTable.class);
    DatabaseTableRecord mockDatabaseTableRecord = Mockito.mock(DatabaseTableRecord.class);
    Database mockDatabase = Mockito.mock(Database.class);


    private ExtraUserActorPluginRoot extraUserActorPluginRoot;
    private byte[] extraUserImageProfile = new byte[0];

    UUID pluginId;

    @Before
    public void setUp() throws Exception {

        pluginId = UUID.randomUUID();

        extraUserActorPluginRoot = new ExtraUserActorPluginRoot();
        extraUserActorPluginRoot.setErrorManager(mockErrorManager);
        extraUserActorPluginRoot.setLogManager(mocklogManager);
        extraUserActorPluginRoot.setPluginFileSystem(mockPluginFileSystem);
        extraUserActorPluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        extraUserActorPluginRoot.setId(pluginId);

        setUpMockitoRules();

        extraUserActorPluginRoot.start();
    }

    public void setUpMockitoRules() throws Exception {

        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.getTable(ExtraUserActorDatabaseConstants.EXTRA_USER_TABLE_NAME)).thenReturn(mockDatabaseTable);
        when(mockPluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);

        when(mockExtraUserActorDatabaseFactory.createDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);

        when(mockPluginFileSystem.createTextFile(any(UUID.class),
                anyString(),
                anyString(),
                any(FilePrivacy.class),
                any(FileLifeSpan.class))).thenReturn(mockFile);

        when(mockPluginFileSystem.createBinaryFile(any(UUID.class),
                anyString(),
                anyString(),
                any(FilePrivacy.class),
                any(FileLifeSpan.class))).thenReturn(mockBinaryFile);
    }

    @Test
    public void createActorTest_CreateOk_ThrowsCantCreateExtraUserException() throws Exception {

        Actor actor = extraUserActorPluginRoot.createActor("actor", extraUserImageProfile);
        Assertions.assertThat(actor)
                .isNotNull();
    }

    @Test
    public void createActorTest1_CreateOk_ThrowsCantCreateExtraUserException() throws Exception {

        Actor actor = extraUserActorPluginRoot.createActor("actor");
        Assertions.assertThat(actor)
                .isNotNull();
    }

    @Test
    public void createActorTest_CanCreate_throwsCantCreateExtraUserExceptionn() throws Exception {

        catchException(extraUserActorPluginRoot).createActor(null, extraUserImageProfile);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantCreateExtraUserException.class);
    }
}
