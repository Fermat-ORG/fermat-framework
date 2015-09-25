package unit.com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.version_1.providers.CexioServiceAPI;

import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CexioServiceAPI;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by francisco on 03/09/15.
 */
public class CexioServiceAPIGetUrlAPITest {
    CexioServiceAPI cexioServiceAPI = new CexioServiceAPI();
    @Test
    public void TestGetUrlAPI_successful(){
        String expectedValue="https://cex.io/api/ticker/BTC/USD";
        String url;
        url=cexioServiceAPI.getUrlAPI("BTC","USD");
        Assertions.assertThat(url).isEqualTo(expectedValue);
    }
}
