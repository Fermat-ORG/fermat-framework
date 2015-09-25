package unit.com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.version_1.providers.BtceServiceAPI;

import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.BtceServiceAPI;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by francisco on 03/09/15.
 */
public class BtceServiceAPIGetUrlAPITest {

    BtceServiceAPI btceServiceAPI = new BtceServiceAPI();
    @Test
    public void TestGetUrlAPI_successful(){
        String expectedValue="https://btc-e.com/api/3/ticker/btc_usd";
        String url;
        url=btceServiceAPI.getUrlAPI("BTC","USD");
        Assertions.assertThat(url).isEqualTo(expectedValue);
    }
}
