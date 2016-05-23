package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.tokenlyFanIdentityPluginRoot;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.TokenlyFanIdentityPluginRoot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateFanIdentityTest {


    String userName;
    String password;
    UUID id;
    String publicKey;
    byte[] profileImage;
    ExternalPlatform externalPlatform;
    Fan fan = null;

    @Test
    public void updateFanIdentityTest() throws CantUpdateFanIdentityException, WrongTokenlyUserCredentialsException {

        TokenlyFanIdentityPluginRoot tokenlyFanIdentityPluginRoot = Mockito.mock(TokenlyFanIdentityPluginRoot.class);

        when(tokenlyFanIdentityPluginRoot.updateFanIdentity(userName,
                                                            password,
                                                            id,
                                                            publicKey,
                                                            profileImage,
                                                            externalPlatform)).thenReturn(fan);

    }

    @Test
    public void updateFanIdentity() throws CantUpdateFanIdentityException{
        TokenlyFanIdentityPluginRoot tokenlyFanIdentityPluginRoot = Mockito.mock(TokenlyFanIdentityPluginRoot.class);

        doCallRealMethod().when(tokenlyFanIdentityPluginRoot).updateFanIdentity(fan);

    }
}
