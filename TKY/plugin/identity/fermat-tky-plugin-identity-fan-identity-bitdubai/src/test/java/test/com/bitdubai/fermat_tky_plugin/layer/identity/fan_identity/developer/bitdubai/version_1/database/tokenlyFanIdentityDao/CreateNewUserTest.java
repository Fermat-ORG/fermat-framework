package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.tokenlyFanIdentityDao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.database.TokenlyFanIdentityDao;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.exceptions.CantCreateNewDeveloperException;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 06/05/16.
 */
public class CreateNewUserTest {

    @Mock
    User user;
    @Mock
    UUID id;
    @Mock
    String publicKey;
    @Mock
    String privateKey;
    @Mock
    DeviceUser deviceUser;
    @Mock
    byte[] profileImage;
    @Mock
    String password;
    @Mock
    ExternalPlatform externalPlatform;

    @Test
    public void createNewUserTest() throws CantCreateNewDeveloperException {
        TokenlyFanIdentityDao tokenlyFanIdentityDao = Mockito.mock(TokenlyFanIdentityDao.class);

        doCallRealMethod().when(tokenlyFanIdentityDao).createNewUser(user,
                                                                        id,
                                                                        publicKey,
                                                                        privateKey,
                                                                        deviceUser,
                                                                        profileImage,
                                                                        password,
                                                                        externalPlatform);
    }
}
