package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
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

import static org.junit.Assert.assertNotNull;

/**
 * Created by Nerio on 25/07/15.
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableListTest {

    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    UUID pluginId;

    WalletContactsMiddlewareDeveloperDatabaseFactory testDatabaseFactory;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        testDatabaseFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(mockPluginDatabaseSystem,pluginId);
    }

    @Test
    public void testGetDatabaseTableListTest_NotNull() throws Exception {
        List<DeveloperDatabaseTable> developerDatabaseTableList = testDatabaseFactory.getDatabaseTableList(developerObjectFactory);
        assertNotNull(developerDatabaseTableList);
    }
}
