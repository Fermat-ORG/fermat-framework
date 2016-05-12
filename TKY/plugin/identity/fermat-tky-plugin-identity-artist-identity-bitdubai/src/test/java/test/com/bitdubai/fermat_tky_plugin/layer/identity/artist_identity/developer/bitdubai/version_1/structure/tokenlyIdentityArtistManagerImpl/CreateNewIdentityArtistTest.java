package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.tokenlyIdentityArtistManagerImpl;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.TokenlyIdentityArtistManagerImpl;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 09/05/16.
 */
public class CreateNewIdentityArtistTest {
    @Mock
    User user;
    @Mock
    String password;
    @Mock
    byte[] profileImage;
    @Mock
    ExternalPlatform externalPlatform;
    @Mock
    ExposureLevel exposureLevel;
    @Mock
    ArtistAcceptConnectionsType artistAcceptConnectionsType;
    @Mock
    Artist artist;

    @Test
    public void createNewIdentityArtistTest() throws CantGetArtistIdentityException, CantCreateArtistIdentityException {
        TokenlyIdentityArtistManagerImpl tokenlyIdentityArtistManager = Mockito.mock(TokenlyIdentityArtistManagerImpl.class);
        when(tokenlyIdentityArtistManager.createNewIdentityArtist(user,
                                                                    password,
                                                                    profileImage,
                                                                    externalPlatform,
                                                                    exposureLevel,
                                                                    artistAcceptConnectionsType)).thenReturn(artist);
    }
}
