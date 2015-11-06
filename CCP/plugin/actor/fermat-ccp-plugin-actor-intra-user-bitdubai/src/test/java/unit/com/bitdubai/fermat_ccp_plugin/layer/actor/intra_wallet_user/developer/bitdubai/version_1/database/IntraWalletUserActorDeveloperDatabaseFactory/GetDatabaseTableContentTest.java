package unit.com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraWalletUserActorDeveloperDatabaseFactory;


import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;

import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.database.IntraWalletUserActorDeveloperDatabaseFactory;

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
 * Created by natalia on 24/08/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableContentTest extends TestCase {

    @Mock
    private Database mockDatabase;

    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private IntraWalletUserActorDeveloperDatabaseFactory intraWalletUserActorDeveloperDatabaseFactory;

    @Test
    public void initializeDatabase() throws Exception {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        intraWalletUserActorDeveloperDatabaseFactory = new IntraWalletUserActorDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        intraWalletUserActorDeveloperDatabaseFactory.initializeDatabase();

       /*  List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("Name1");
        fieldNames.add("Name2");
        fieldNames.add("Name3");

       DeveloperDatabaseTable DeveloperDatabaseTable = new DeveloperModuleDeveloperDatabaseTable("tabla", fieldNames);

        assertThat(intraWalletUserActorDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, DeveloperDatabaseTable)).isInstanceOf(List.class);*/
    }

}