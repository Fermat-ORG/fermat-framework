package unit.com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.database.WalletContactsMiddlewareDeveloperDatabaseFactory;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 10/09/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableContentTest extends TestCase {

    @Mock
    private Database mockDatabase;

    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private WalletContactsMiddlewareDeveloperDatabaseFactory developerDatabaseFactory;

    @Test
    public void initializeDatabase() throws Exception {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        developerDatabaseFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        developerDatabaseFactory.initializeDatabase();
    }
}