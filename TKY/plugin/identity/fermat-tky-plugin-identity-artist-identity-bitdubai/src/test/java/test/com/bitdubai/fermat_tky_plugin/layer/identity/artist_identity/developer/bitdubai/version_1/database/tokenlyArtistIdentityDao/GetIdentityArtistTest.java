package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDao;

import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantGetArtistIdentityException;
import com.bitdubai.fermat_tky_api.layer.identity.artist.interfaces.Artist;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDao;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 09/05/16.
 */
public class GetIdentityArtistTest {

    @Mock
    Artist artist;
    @Mock
    UUID id;
    @Test
    public void getIdentityArtistTest() throws CantGetArtistIdentityException {
        TokenlyArtistIdentityDao tokenlyArtistIdentityDao = Mockito.mock(TokenlyArtistIdentityDao.class);
        when(tokenlyArtistIdentityDao.getIdentityArtist(id)).thenReturn(artist);
    }
}
