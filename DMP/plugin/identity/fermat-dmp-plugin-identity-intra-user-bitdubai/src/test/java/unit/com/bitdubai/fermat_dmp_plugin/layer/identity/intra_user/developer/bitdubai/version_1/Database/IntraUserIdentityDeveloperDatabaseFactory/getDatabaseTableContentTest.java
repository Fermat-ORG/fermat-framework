package unit.com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.Database.IntraUserIdentityDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraUserIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraUserIdentityDatabaseException;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by angel on 20/8/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class getDatabaseTableContentTest extends TestCase {

    @Mock
    private Database mockDatabase;

    @Mock
    private DeveloperObjectFactory developerObjectFactory;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    private IntraUserIdentityDeveloperDatabaseFactory DatabaseFactory;

    @Test
    public void initializeDatabase() throws CantOpenDatabaseException, DatabaseNotFoundException, CantInitializeIntraUserIdentityDatabaseException {
        UUID testOwnerId = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        DatabaseFactory = new IntraUserIdentityDeveloperDatabaseFactory(mockPluginDatabaseSystem, testOwnerId);

        DatabaseFactory.initializeDatabase();

        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("Name1");
        fieldNames.add("Name2");
        fieldNames.add("Name3");
/*
        DeveloperDatabaseTable DeveloperDatabaseTable = new DeveloperModuleDeveloperDatabaseTable("tabla", fieldNames);

        assertThat(DatabaseFactory.getDatabaseTableContent(developerObjectFactory, DeveloperDatabaseTable)).isInstanceOf(List.class);*/
    }

}