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
public class GetApiTokenTest {

    @Test
    public void GetApiTokenTest(){

        TokenlyFanIdentityImp tokenlyFanIdentityImp = Mockito.mock(TokenlyFanIdentityImp.class);

        String externalAccessToken = "test";

        when(tokenlyFanIdentityImp.getApiToken()).thenReturn(externalAccessToken);

    }
}
