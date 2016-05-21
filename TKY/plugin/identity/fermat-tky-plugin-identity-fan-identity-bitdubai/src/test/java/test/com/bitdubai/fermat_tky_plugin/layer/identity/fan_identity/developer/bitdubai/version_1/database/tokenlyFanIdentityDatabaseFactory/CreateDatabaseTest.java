package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.tokenlyFanIdentityDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDatabaseFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
public class CreateDatabaseTest {
    @Mock
    UUID pluginId;
    @Mock
    Database database;

    @Test
    public void createDatabaseTest() throws CantCreateDatabaseException {

        TokenlyFanIdentityDatabaseFactory tokenlyFanIdentityDatabaseFactory = Mockito.mock(TokenlyFanIdentityDatabaseFactory.class);

        when(tokenlyFanIdentityDatabaseFactory.createDatabase(pluginId)).thenReturn(database);

    }
}
