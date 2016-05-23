package test.com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.tokenlyFanIdentityPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_tky_plugin.layer.identity.fan_identity.developer.bitdubai.version_1.TokenlyFanIdentityPluginRoot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus.STARTED;

/**
 * Created by gianco on 05/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {

    TokenlyFanIdentityPluginRoot tokenlyFanIdentityPluginRoot = new TokenlyFanIdentityPluginRoot();

    @Test
    public void startTest() throws CantStartPluginException {

        try {
            tokenlyFanIdentityPluginRoot.start();
        } catch (CantStartPluginException exception) {
            Assert.assertNotNull(exception);
        } catch (Exception exception) {
            Assert.assertNotNull(exception);
        }

        Assert.assertEquals(STARTED, tokenlyFanIdentityPluginRoot.getStatus());
    }


}
