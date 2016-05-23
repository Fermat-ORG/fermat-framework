package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.tokenlyArtistIdentityPluginRoot;

import com.bitdubai.fermat_tky_api.all_definitions.exceptions.IdentityNotFoundException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
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
public class GetArtistIdentityTest {

    @Mock
    Artist artist;
    @Mock
    UUID id;

    @Test
    public void getArtistIdentityTest() throws CantGetArtistIdentityException, IdentityNotFoundException {
        TokenlyArtistIdentityPluginRoot tokenlyArtistIdentityPluginRoot = Mockito.mock(TokenlyArtistIdentityPluginRoot.class);
        when(tokenlyArtistIdentityPluginRoot.getArtistIdentity(id)).thenReturn(artist);
    }
}
