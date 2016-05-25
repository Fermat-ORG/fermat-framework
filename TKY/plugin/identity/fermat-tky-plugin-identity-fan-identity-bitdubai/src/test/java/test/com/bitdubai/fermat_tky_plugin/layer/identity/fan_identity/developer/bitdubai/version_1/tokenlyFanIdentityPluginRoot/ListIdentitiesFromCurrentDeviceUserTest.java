package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.tokenlyFanIdentityPluginRoot;

import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
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
public class ListIdentitiesFromCurrentDeviceUserTest {

    @Mock
    List<Fan> fans;
    @Test
    public void listIdentitiesFromCurrentDeviceUserTest() throws CantListFanIdentitiesException {
        TokenlyFanIdentityPluginRoot tokenlyFanIdentityPluginRoot = Mockito.mock(TokenlyFanIdentityPluginRoot.class);

        when(tokenlyFanIdentityPluginRoot.listIdentitiesFromCurrentDeviceUser()).thenReturn(fans);

    }
}
