package test.com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.tokenlyArtistIdentityPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.version_1.TokenlyArtistIdentityPluginRoot;

import org.junit.Assert;
import org.junit.Test;

import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTED;

/**
 * Created by gianco on 09/05/16.
 */
public class StartTest {

    TokenlyArtistIdentityPluginRoot tokenlyArtistIdentityPluginRoot = new TokenlyArtistIdentityPluginRoot();

    @Test
    public void startTest() throws CantStartPluginException {

        try {
            tokenlyArtistIdentityPluginRoot.start();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        } catch (Exception exception) {
            Assert.assertNotNull(exception);
        }

        Assert.assertEquals(STARTED, tokenlyArtistIdentityPluginRoot.getStatus());
    }

}
