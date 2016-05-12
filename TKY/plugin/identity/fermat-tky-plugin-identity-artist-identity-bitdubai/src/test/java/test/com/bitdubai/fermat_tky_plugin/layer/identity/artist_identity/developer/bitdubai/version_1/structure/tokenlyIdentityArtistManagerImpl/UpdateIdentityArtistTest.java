package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.tokenlyIdentityArtistManagerImpl;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.interfaces.User;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
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
public class UpdateIdentityArtistTest {
    @Mock
    Artist artist;
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
    @Mock
    ExposureLevel exposureLevel;
    @Mock
    ArtistAcceptConnectionsType artistAcceptConnectionsType;

    @Test
    public void updateIdentityArtistTest() throws CantUpdateArtistIdentityException {
        TokenlyIdentityArtistManagerImpl tokenlyIdentityArtistManager = Mockito.mock(TokenlyIdentityArtistManagerImpl.class);
        when(tokenlyIdentityArtistManager.updateIdentityArtist(user,
                                                                password,
                                                                id,
                                                                publicKey,
                                                                profileImage,
                                                                externalPlatform,
                                                                exposureLevel,
                                                                artistAcceptConnectionsType)).thenReturn(artist);
    }
}
