package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.tokenlyFanIdentityImp;

import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.TokenlyFanIdentityImp;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
public class SetNewProfileImageTest {

    @Test
    public void setNewProfileImageTest(){

        TokenlyFanIdentityImp tokenlyFanIdentityImp = Mockito.mock(TokenlyFanIdentityImp.class);

        byte[] imageBytes = new byte[0];

        doCallRealMethod().when(tokenlyFanIdentityImp).setNewProfileImage(imageBytes);


    }
}
