package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDao;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDao;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 09/05/16.
 */
public class UpdateIdentityArtistUserTest {

    @Mock
    User user;
    @Mock
    String password;
    @Mock
    UUID id;
    @Mock
    String publickey;
    @Mock
    byte[] profileImage;
    @Mock
    ExternalPlatform externalPlatform;
    @Mock
    ExposureLevel exposureLevel;
    @Mock
    ArtistAcceptConnectionsType artistAcceptConnectionsType;
    @Test
    public void updateIdentityArtistUserTest() throws CantUpdateArtistIdentityException {
        TokenlyArtistIdentityDao tokenlyArtistIdentityDao = Mockito.mock(TokenlyArtistIdentityDao.class);
        doCallRealMethod().when(tokenlyArtistIdentityDao).updateIdentityArtistUser(user,
                                                                                    password,
                                                                                    id,
                                                                                    publickey,
                                                                                    profileImage,
                                                                                    externalPlatform,
                                                                                    exposureLevel,
                                                                                    artistAcceptConnectionsType);
    }
}
