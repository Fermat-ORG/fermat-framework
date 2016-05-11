package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.ExtraUserActorPluginRoot;

import com.bitdubai.fermat_ccp_api.layer.actor.extra_user.exceptions.CantSetPhotoException;
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
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 03/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class SetPhotoTest extends TestCase {

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
    private DatabaseTable mockTable;


    @Mock
    private DatabaseTableRecord mockTableRecord;

    @Mock
    private List<DatabaseTableRecord> mockRecords;
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


    Database mockDatabase = Mockito.mock(Database.class);


    private ExtraUserActorPluginRoot extraUserActorPluginRoot;
    private String actorPublicKey;

    private byte[] extraUserImageProfile = new byte[0];

    UUID pluginId;

    @Before
    public void setUp() throws Exception {

        pluginId = UUID.randomUUID();
        actorPublicKey = UUID.randomUUID().toString();
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

        when(mockPluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);

        when(mockExtraUserActorDatabaseFactory.createDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);

        when(mockDatabase.getTable(ExtraUserActorDatabaseConstants.EXTRA_USER_TABLE_NAME)).thenReturn(mockTable);

        when(mockTable.getRecords()).thenReturn(mockRecords);
        when(mockRecords.get(anyInt())).thenReturn(mockTableRecord);

        when(mockPluginFileSystem.getBinaryFile(any(UUID.class),
                anyString(),
                anyString(),
                any(FilePrivacy.class),
                any(FileLifeSpan.class))).thenReturn(mockBinaryFile);

        when(mockPluginFileSystem.getTextFile(any(UUID.class),
                anyString(),
                anyString(),
                any(FilePrivacy.class),
                any(FileLifeSpan.class))).thenReturn(mockFile);
    }

    @Test
    public void getActorByPublicKeyTest_GetOk_ThrowsCantSetPhotoException() throws Exception {

        catchException(extraUserActorPluginRoot).setPhoto(actorPublicKey, extraUserImageProfile);
        assertThat(caughtException())
                .isNull();
    }

    @Test
    public void getActorByPublicKeyActorTest_CanGet_throwsCantSetPhotoException() throws Exception {

        catchException(extraUserActorPluginRoot).setPhoto(null, extraUserImageProfile);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantSetPhotoException.class);
    }
}
