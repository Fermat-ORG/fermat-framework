package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableListTest extends TestCase {

    @Mock
    DeveloperObjectFactory developerObjectFactory;

    UUID pluginId;

    DeveloperDatabase developerDatabase;

    ActorAddressBookCryptoModulePluginRoot actorAddressBookCryptoModulePluginRoot;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        actorAddressBookCryptoModulePluginRoot = new ActorAddressBookCryptoModulePluginRoot();
        actorAddressBookCryptoModulePluginRoot.setId(pluginId);
        List<DeveloperDatabase> developerDatabaseList = actorAddressBookCryptoModulePluginRoot.getDatabaseList(developerObjectFactory);
        developerDatabase = developerDatabaseList.get(0);
    }

    @Test
    public void testGetDatabaseTableListTest_NotNull() throws Exception {
        List<DeveloperDatabaseTable> developerDatabaseTableList = actorAddressBookCryptoModulePluginRoot.getDatabaseTableList(developerObjectFactory, developerDatabase);
        assertNotNull(developerDatabaseTableList);
    }
}

