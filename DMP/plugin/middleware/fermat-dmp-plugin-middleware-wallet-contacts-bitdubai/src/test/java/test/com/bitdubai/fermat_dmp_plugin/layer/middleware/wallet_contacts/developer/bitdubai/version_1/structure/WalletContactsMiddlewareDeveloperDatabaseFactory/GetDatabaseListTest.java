package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDeveloperDatabaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by Nerio on 25/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest {
    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private UUID pluginId;

    private WalletContactsMiddlewareDeveloperDatabaseFactory testDatabaseFactory;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        testDatabaseFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(mockPluginDatabaseSystem,pluginId);
    }

    @Test
    public void testGetDatabaseListTest_NotNull() throws Exception {
        List<DeveloperDatabase> developerDatabaseList = testDatabaseFactory.getDatabaseList(developerObjectFactory);
        assertNotNull(developerDatabaseList);
    }
}
