package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.tokenlyFanIdentityDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDatabaseFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 06/05/16.
 */
public class SetPluginDatabaseSystemTest {

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;


    @Test
    public void setPluginDatabaseSystemTest() throws CantCreateDatabaseException {

        TokenlyFanIdentityDatabaseFactory tokenlyFanIdentityDatabaseFactory = Mockito.mock(TokenlyFanIdentityDatabaseFactory.class);

        doCallRealMethod().when(tokenlyFanIdentityDatabaseFactory).setPluginDatabaseSystem(pluginDatabaseSystem);

    }


}
