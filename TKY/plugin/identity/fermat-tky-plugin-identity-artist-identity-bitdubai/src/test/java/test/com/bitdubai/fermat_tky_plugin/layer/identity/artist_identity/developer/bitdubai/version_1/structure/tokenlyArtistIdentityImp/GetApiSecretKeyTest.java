package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.tokenlyArtistIdentityImp;

import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.TokenlyArtistIdentityImp;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 09/05/16.
 */
public class GetApiSecretKeyTest {
    @Mock
    String apiSecretKey;
    @Test
    public void getApiSecretKeyTest(){
        TokenlyArtistIdentityImp tokenlyArtistIdentityImp = Mockito.mock(TokenlyArtistIdentityImp.class);
        when(tokenlyArtistIdentityImp.getApiSecretKey()).thenReturn(apiSecretKey);
    }
}
