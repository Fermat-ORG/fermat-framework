package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.tokenlyFanIdentityDeveloperDatabaseFactory;

import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDeveloperDatabaseFactory;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.exceptions.CantInitializeTokenlyFanIdentityDatabaseException;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 06/05/16.
 */
public class InitializeDatabaseTest {

    @Test
    public void initializeDatabaseTest() throws CantInitializeTokenlyFanIdentityDatabaseException {
        TokenlyFanIdentityDeveloperDatabaseFactory tokenlyFanIdentityDeveloperDatabaseFactory = Mockito.mock(TokenlyFanIdentityDeveloperDatabaseFactory.class);

        doCallRealMethod().when(tokenlyFanIdentityDeveloperDatabaseFactory).initializeDatabase();

    }
}
