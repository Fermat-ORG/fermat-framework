package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.tokenlyFanIdentityImp;

import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.structure.TokenlyFanIdentityImp;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

/**
 * Created by gianco on 06/05/16.
 */
public class GetArtistsConnectedStringListTest {

    @Test
    public void getArtistsConnectedStringListTest(){

        TokenlyFanIdentityImp tokenlyFanIdentityImp = Mockito.mock(TokenlyFanIdentityImp.class);

        String artistsConnectedList = "test";

        when(tokenlyFanIdentityImp.getArtistsConnectedStringList()).thenReturn(artistsConnectedList);

    }
}
