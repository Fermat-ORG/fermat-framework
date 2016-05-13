package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.tokenlyFanIdentityPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.TokenlyFanIdentityPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseListTest {
    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    List<DeveloperDatabase> developerDatabases;

    @Test
    public void getDatabaseListTest() {
        TokenlyFanIdentityPluginRoot tokenlyFanIdentityPluginRoot = Mockito.mock(TokenlyFanIdentityPluginRoot.class);

        when(tokenlyFanIdentityPluginRoot.getDatabaseList(developerObjectFactory)).thenReturn(developerDatabases);

    }
}
