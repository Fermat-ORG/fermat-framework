package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.tokenlyFanIdentityDao;

import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantGetFanIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDao;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
public class GetIdentityFanTest {

    @Mock
    Fan fan;
    @Test
    public void getIdentityFanTest() throws CantGetFanIdentityException {
        TokenlyFanIdentityDao tokenlyFanIdentityDao = Mockito.mock(TokenlyFanIdentityDao.class);

        when(tokenlyFanIdentityDao.getIdentityFan()).thenReturn(fan);

    }
}
