package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.tokenlyArtistIdentityPluginRoot;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ArtistAcceptConnectionsType;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExposureLevel;
import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_api.all_definitions.exceptions.WrongTokenlyUserCredentialsException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.ArtistIdentityAlreadyExistsException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantCreateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantUpdateArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.TokenlyArtistIdentityPluginRoot;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 09/05/16.
 */
public class UpdateArtistIdentityTest {

    @Mock
    String userName;
    @Mock
    byte[] profileImage;
    @Mock
    String password;
    @Mock
    UUID id;
    @Mock
    String publicKey;
    @Mock
    ExternalPlatform externalPlatform;
    @Mock
    ExposureLevel exposureLevel;
    @Mock
    ArtistAcceptConnectionsType artistAcceptConnectionsType;
    @Mock
    Artist artist;


    @Test
    public void updateArtistIdentityTest() throws CantUpdateArtistIdentityException, WrongTokenlyUserCredentialsException {

        TokenlyArtistIdentityPluginRoot tokenlyArtistIdentityPluginRoot = Mockito.mock(TokenlyArtistIdentityPluginRoot.class);

        when(tokenlyArtistIdentityPluginRoot.updateArtistIdentity(userName,
                                                                    password,
                                                                    id,
                                                                    publicKey,
                                                                    profileImage,
                                                                    externalPlatform,
                                                                    exposureLevel,
                                                                    artistAcceptConnectionsType)).thenReturn(artist);
    }
}
