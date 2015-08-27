package unit.com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.Database.IntraUserIdentityDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.database.IntraUserIdentityDatabaseFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by angel on 20/8/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class IntraUserIdentityDatabaseFactoryTest{

    private IntraUserIdentityDatabaseFactory databaseFactory;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Test
    public void IntraUserIdentityDatabaseFactoryTest() throws CantOpenDatabaseException, DatabaseNotFoundException, CantCreateDatabaseException {
        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        databaseFactory = new IntraUserIdentityDatabaseFactory(mockPluginDatabaseSystem);
        databaseFactory.setPluginDatabaseSystem(mockPluginDatabaseSystem);

    }
}
