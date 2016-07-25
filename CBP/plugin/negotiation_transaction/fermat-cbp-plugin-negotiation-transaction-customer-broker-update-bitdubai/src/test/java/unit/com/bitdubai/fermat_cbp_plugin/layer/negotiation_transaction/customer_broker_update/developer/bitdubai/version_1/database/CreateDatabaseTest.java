package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_update.developer.bitdubai.version_1.database.CustomerBrokerUpdateNegotiationTransactionDatabaseFactory;

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
 * Created by Yordin Alayn on 02.01.16.
 */

@RunWith(MockitoJUnitRunner.class)
public class CreateDatabaseTest {

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
    private CustomerBrokerUpdateNegotiationTransactionDatabaseFactory testExtraUserDataBaseFactory;

    public void setUpTestValues() {
        testId = UUID.randomUUID();
        testDataBaseName = CustomerBrokerUpdateNegotiationTransactionDatabaseConstants.DATABASE_NAME;
    }

    public void setUpGeneralMockitoRules() throws Exception {
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockPluginDatabaseSystem.createDatabase(testId, testDataBaseName)).thenReturn(mockDatabase);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTableFactory);
    }

    @Before
    public void setUp() throws Exception {
        setUpTestValues();
        setUpGeneralMockitoRules();
    }

    @Test
    public void CreateDatabase_SuccessfulInvocation_ReturnsDatabase() throws Exception {
        testExtraUserDataBaseFactory = new CustomerBrokerUpdateNegotiationTransactionDatabaseFactory(mockPluginDatabaseSystem);
        Database checkDatabase = testExtraUserDataBaseFactory.createDatabase(testId, testDataBaseName);
        assertThat(checkDatabase).isNotNull();
    }


}
