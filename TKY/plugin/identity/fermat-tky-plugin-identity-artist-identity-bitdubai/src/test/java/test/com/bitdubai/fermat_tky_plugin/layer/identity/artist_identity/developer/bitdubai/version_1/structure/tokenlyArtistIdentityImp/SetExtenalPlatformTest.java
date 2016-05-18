package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.tokenlyArtistIdentityImp;

import com.bitdubai.fermat_tky_api.all_definitions.enums.ExternalPlatform;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.structure.TokenlyArtistIdentityImp;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.doCallRealMethod;

/**
 * Created by gianco on 09/05/16.
 */
public class SetExtenalPlatformTest {
    @Mock
    ExternalPlatform externalPlatform;
    @Test
    public void setExtenalPlatformTest(){
        TokenlyArtistIdentityImp tokenlyArtistIdentityImp = Mockito.mock(TokenlyArtistIdentityImp.class);
        doCallRealMethod().when(tokenlyArtistIdentityImp).setExternalPlatform(externalPlatform);
    }

}
