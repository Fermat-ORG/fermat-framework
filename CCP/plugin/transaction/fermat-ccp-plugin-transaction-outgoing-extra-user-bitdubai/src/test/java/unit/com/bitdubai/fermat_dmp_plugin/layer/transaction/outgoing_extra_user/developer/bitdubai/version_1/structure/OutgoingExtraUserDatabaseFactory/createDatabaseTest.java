package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.outgoing_extra_user.developer.bitdubai.version_1.structure.OutgoingExtraUserDatabaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 10/07/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class createDatabaseTest {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTableFactory mockTableFactory;
    @Mock
    private Database mockDatabase;

    private UUID testId;
    private String testDataBaseName;
    private OutgoingExtraUserDatabaseFactory testOutgoingExtraUserDataBaseFactory;

    public void setUpTestValues(){
        testId = UUID.randomUUID();
        testDataBaseName = OutgoingExtraUserDatabaseConstants.OUTGOING_EXTRA_USER_DATABASE_NAME;
    }

    public void setUpGeneralMockitoRules() throws Exception{

        when(mockPluginDatabaseSystem.createDatabase(testId, testDataBaseName)).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);

        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTableFactory);
    }

    @Before
    public void setUp() throws Exception{
        setUpTestValues();
        setUpGeneralMockitoRules();
    }

    @Test
    public void CreateDatabase_SuccessfulInvocation_ReturnsDatabase() throws Exception{
        testOutgoingExtraUserDataBaseFactory = new OutgoingExtraUserDatabaseFactory();
        testOutgoingExtraUserDataBaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
         Database checkDatabase = testOutgoingExtraUserDataBaseFactory.createDatabase(testId, testDataBaseName);
        assertThat(checkDatabase).isNotNull();
    }
}

