package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.tokenlyFanIdentityImp;

import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.TokenlyFanIdentityImp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetUsernameTest {

    @Test
    public void getUsernameTest(){

        TokenlyFanIdentityImp tokenlyFanIdentityImp = Mockito.mock(TokenlyFanIdentityImp.class);

        String externalUsername = "test";

        when(tokenlyFanIdentityImp.getUsername()).thenReturn(externalUsername);

    }
}
