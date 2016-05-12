package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.tokenlyArtistIdentityDao;

import com.bitdubai.fermat_tky_api.layer.identity.artist.exceptions.CantListArtistIdentitiesException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.database.TokenlyArtistIdentityDao;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.exceptions.CantGetTokenlyArtistIdentityProfileImageException;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 09/05/16.
 */
public class GetArtistProfileImagePrivateKeyTest {

    @Mock
    String publicKey;
    @Mock
    byte[] profileImage;
    @Test
    public void getArtistProfileImagePrivateKeyTest() throws CantGetTokenlyArtistIdentityProfileImageException {
        TokenlyArtistIdentityDao tokenlyArtistIdentityDao = Mockito.mock(TokenlyArtistIdentityDao.class);
        when(tokenlyArtistIdentityDao.getArtistProfileImagePrivateKey(publicKey)).thenReturn(profileImage);
    }

}
