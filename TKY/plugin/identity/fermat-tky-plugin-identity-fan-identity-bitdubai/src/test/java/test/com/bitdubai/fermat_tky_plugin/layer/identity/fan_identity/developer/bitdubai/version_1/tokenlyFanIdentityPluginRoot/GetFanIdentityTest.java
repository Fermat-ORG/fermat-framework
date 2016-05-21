package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.tokenlyFanIdentityPluginRoot;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.TokenlyFanIdentityPluginRoot;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
public class GetFanIdentityTest {

    UUID id;
    Fan fan;
    @Test
    public void getFanIdentityTest() throws IdentityNotFoundException, CantGetFanIdentityException {
        TokenlyFanIdentityPluginRoot tokenlyFanIdentityPluginRoot = Mockito.mock(TokenlyFanIdentityPluginRoot.class);

        when(tokenlyFanIdentityPluginRoot.getFanIdentity(id)).thenReturn(fan);
    }
}
