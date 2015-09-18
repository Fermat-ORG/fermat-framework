package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.providers.CcexServiceAPI;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CcexServiceAPI;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by francisco on 03/09/15.
 */
public class CcexServiceAPIGetUrlAPITest {

    CcexServiceAPI ccexServiceAPI = new CcexServiceAPI();
    @Test
    public void TestGetUrlAPI_successful(){
        String expectedValue="https://c-cex.com/t/btc-usd.json";
        String url;
        url=ccexServiceAPI.getUrlAPI("BTC","USD");
        Assertions.assertThat(url).isEqualTo(expectedValue);
    }
}
