package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.tokenlyFanIdentityDao;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.identity.fan.exceptions.CantUpdateFanIdentityException;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDao;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 06/05/16.
 */
public class UpdateIdentityFanUserTest {
    @Mock
    User user;
    @Mock
    String password;
    @Mock
    UUID id;
    @Mock
    String publicKey;
    @Mock
    byte[] profileImage;
    @Mock
    ExternalPlatform externalPlatform;

    @Test
    public void updateIdentityFanUserTest() throws CantUpdateFanIdentityException {
        TokenlyFanIdentityDao tokenlyFanIdentityDao = Mockito.mock(TokenlyFanIdentityDao.class);

        doCallRealMethod().when(tokenlyFanIdentityDao).updateIdentityFanUser(user,
                                                                                password,
                                                                                id,
                                                                                publicKey,
                                                                                profileImage,
                                                                                externalPlatform);

    }
}
