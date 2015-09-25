package unit.com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.version_1.providers.BterServiceAPI;

import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.BterServiceAPI;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by francisco on 03/09/15.
 */
public class BterServiceAPIGetUrlAPITest {

    BterServiceAPI bterServiceAPI = new BterServiceAPI();

    @Test
    public void TestGetUrlAPI_successful(){
        String expectedValue="http://data.bter.com/api/1/ticker/BTC_USD";
        String url;
        url=bterServiceAPI.getUrlAPI("BTC","USD");
        Assertions.assertThat(url).isEqualTo(expectedValue);
    }
}
