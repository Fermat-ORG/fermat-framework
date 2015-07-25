package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDatabaseConstants;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDatabaseFactory;

import junit.framework.TestCase;

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
 * Created by Nerio on 24/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateDatabaseTest extends TestCase {

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    DatabaseTableFactory table;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTableFactory mockTableFactory;
    @Mock
    private Database mockDatabase;

    WalletContactsMiddlewareDatabaseFactory testWalletContactsMiddlewareDatabaseFactory;

    private UUID testId;
    private String testDataBaseName;

    public void setUpTestValues(){
        testId = UUID.randomUUID();
        testDataBaseName = WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME;
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
        testWalletContactsMiddlewareDatabaseFactory = new WalletContactsMiddlewareDatabaseFactory(mockPluginDatabaseSystem);
        testWalletContactsMiddlewareDatabaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        Database checkDatabase = testWalletContactsMiddlewareDatabaseFactory.createDatabase(testId, testDataBaseName);
        assertThat(checkDatabase).isNotNull();
    }
}
