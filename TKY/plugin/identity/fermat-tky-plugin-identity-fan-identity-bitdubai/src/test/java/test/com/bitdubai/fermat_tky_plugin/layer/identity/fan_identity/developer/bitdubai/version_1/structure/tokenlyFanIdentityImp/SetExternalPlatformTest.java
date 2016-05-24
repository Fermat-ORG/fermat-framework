package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.tokenlyFanIdentityImp;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.TokenlyFanIdentityImp;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 06/05/16.
 */
public class SetExternalPlatformTest {
    @Test
    public void setExternalPlatformTest(){
        TokenlyFanIdentityImp tokenlyFanIdentityImp = Mockito.mock(TokenlyFanIdentityImp.class);

        ExternalPlatform externalPlatform = ExternalPlatform.DEFAULT_EXTERNAL_PLATFORM;

        doCallRealMethod().when(tokenlyFanIdentityImp).setExternalPlatform(externalPlatform);

    }
}
