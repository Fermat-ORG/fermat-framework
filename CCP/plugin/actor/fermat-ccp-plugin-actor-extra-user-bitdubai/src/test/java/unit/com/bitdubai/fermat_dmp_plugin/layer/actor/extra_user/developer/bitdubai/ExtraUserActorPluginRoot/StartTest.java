package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.ExtraUserActorPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
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
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 03/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class StartTest extends TestCase {

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


    DatabaseTable mockDatabaseTable = Mockito.mock(DatabaseTable.class);
    DatabaseTableRecord mockDatabaseTableRecord = Mockito.mock(DatabaseTableRecord.class);
    Database mockDatabase = Mockito.mock(Database.class);


    private ExtraUserActorPluginRoot extraUserActorPluginRoot;


    UUID pluginId;

    @Before
    public void setUp() throws Exception {

        pluginId = UUID.randomUUID();


        extraUserActorPluginRoot = new ExtraUserActorPluginRoot();
        extraUserActorPluginRoot.setErrorManager(mockErrorManager);
        extraUserActorPluginRoot.setLogManager(mocklogManager);

        extraUserActorPluginRoot.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        extraUserActorPluginRoot.setId(pluginId);

        setUpMockitoRules();
    }

    public void setUpMockitoRules() throws Exception {

        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseTable.getEmptyRecord()).thenReturn(mockDatabaseTableRecord);
        when(mockDatabase.getTable(ExtraUserActorDatabaseConstants.EXTRA_USER_TABLE_NAME)).thenReturn(mockDatabaseTable);
        when(mockPluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);
        when(mockExtraUserActorDatabaseFactory.createDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);
    }

    @Test
    public void pauseTest_StopOk_ThrowsCantStartPluginException() throws Exception {

        extraUserActorPluginRoot.pause();
        ServiceStatus serviceStatus = extraUserActorPluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.PAUSED);
    }

    @Test
    public void stopTest_StopOk_ThrowsCantStartPluginException() throws Exception {

        extraUserActorPluginRoot.stop();
        ServiceStatus serviceStatus = extraUserActorPluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STOPPED);
    }

    @Test
    public void startTest_StartedOk_ThrowsCantStartPluginException() throws Exception {

        extraUserActorPluginRoot.start();
        ServiceStatus serviceStatus = extraUserActorPluginRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STARTED);
    }

    @Test
    public void startTest_CanStarted_throwsCantStartPluginException() throws Exception {

        extraUserActorPluginRoot.setPluginDatabaseSystem(null);

        catchException(extraUserActorPluginRoot).start();

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CantStartPluginException.class);
    }
}
