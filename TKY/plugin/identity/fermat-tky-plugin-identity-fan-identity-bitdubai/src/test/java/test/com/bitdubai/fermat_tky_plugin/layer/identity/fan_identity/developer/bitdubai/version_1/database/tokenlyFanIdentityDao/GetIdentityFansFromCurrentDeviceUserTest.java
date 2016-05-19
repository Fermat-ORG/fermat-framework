package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.tokenlyFanIdentityDao;

import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDao;

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
public class GetIdentityFansFromCurrentDeviceUserTest {

    @Mock
    DeviceUser deviceUser;
    @Mock
    List<Fan> fan;

    @Test
    public void getIdentityFansFromCurrentDeviceUserTest() throws CantListFanIdentitiesException {
        TokenlyFanIdentityDao tokenlyFanIdentityDao = Mockito.mock(TokenlyFanIdentityDao.class);

        when(tokenlyFanIdentityDao.getIdentityFansFromCurrentDeviceUser(deviceUser)).thenReturn(fan);

    }


}
