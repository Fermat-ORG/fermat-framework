package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.tokenlyIdentityFanManagerImpl;

import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantListFanIdentitiesException;
import com.bitdubai.fermat_tky_api.layer.identity.fan.interfaces.Fan;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.TokenlyIdentityFanManagerImpl;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
public class GetIdentityFanFromCurrentDeviceUserTest {
    @Mock
    List<Fan> fanList;
    @Test
    public void getIdentityFanFromCurrentDeviceUserTest() throws CantListFanIdentitiesException {
        TokenlyIdentityFanManagerImpl tokenlyIdentityFanManager = Mockito.mock(TokenlyIdentityFanManagerImpl.class);

        when(tokenlyIdentityFanManager.getIdentityFanFromCurrentDeviceUser()).thenReturn(fanList);

    }
}
