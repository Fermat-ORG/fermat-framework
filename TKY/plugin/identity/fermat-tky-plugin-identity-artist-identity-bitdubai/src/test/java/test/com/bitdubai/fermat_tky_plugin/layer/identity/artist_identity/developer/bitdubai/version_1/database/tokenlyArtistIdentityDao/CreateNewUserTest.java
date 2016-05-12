package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDao;

import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDao;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions.CantCreateNewDeveloperException;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 09/05/16.
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
    @Mock
    ExposureLevel exposureLevel;
    @Mock
    ArtistAcceptConnectionsType artistAcceptConnectionsType;

    @Test
    public void createNewUserTest() throws CantCreateNewDeveloperException {
        TokenlyArtistIdentityDao tokenlyArtistIdentityDao = Mockito.mock(TokenlyArtistIdentityDao.class);
        doCallRealMethod().when(tokenlyArtistIdentityDao).createNewUser(user,
                                                                        id,
                                                                        publicKey,
                                                                        privateKey,
                                                                        deviceUser,
                                                                        profileImage,
                                                                        password,
                                                                        externalPlatform,
                                                                        exposureLevel,
                                                                        artistAcceptConnectionsType);
    }
}
