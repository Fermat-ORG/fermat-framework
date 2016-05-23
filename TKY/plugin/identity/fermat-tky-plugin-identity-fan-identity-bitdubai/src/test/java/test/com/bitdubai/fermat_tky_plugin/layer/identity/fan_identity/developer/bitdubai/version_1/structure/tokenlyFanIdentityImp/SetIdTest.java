package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.tokenlyFanIdentityImp;

import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.TokenlyFanIdentityImp;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 06/05/16.
 */
public class SetIdTest {
    @Test
    public void setIdTest(){
        TokenlyFanIdentityImp tokenlyFanIdentityImp = Mockito.mock(TokenlyFanIdentityImp.class);

        UUID id = new UUID(0,0);

        doCallRealMethod().when(tokenlyFanIdentityImp).setId(id);

    }
}
