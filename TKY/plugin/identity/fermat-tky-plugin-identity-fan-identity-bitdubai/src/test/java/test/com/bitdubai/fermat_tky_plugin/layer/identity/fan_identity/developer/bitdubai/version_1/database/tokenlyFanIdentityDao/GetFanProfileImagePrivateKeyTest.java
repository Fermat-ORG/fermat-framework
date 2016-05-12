package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.tokenlyFanIdentityDao;

import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDao;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.exceptions.CantGetTokenlyFanIdentityProfileImageException;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
public class GetFanProfileImagePrivateKeyTest {

    @Mock
    String publicKey;

    @Mock
    byte[] profileImage;

    @Test
    public void getFanProfileImagePrivateKeyTest() throws CantGetTokenlyFanIdentityProfileImageException {
        TokenlyFanIdentityDao tokenlyFanIdentityDao = Mockito.mock(TokenlyFanIdentityDao.class);

        when(tokenlyFanIdentityDao.getFanProfileImagePrivateKey(publicKey)).thenReturn(profileImage);

    }
}
