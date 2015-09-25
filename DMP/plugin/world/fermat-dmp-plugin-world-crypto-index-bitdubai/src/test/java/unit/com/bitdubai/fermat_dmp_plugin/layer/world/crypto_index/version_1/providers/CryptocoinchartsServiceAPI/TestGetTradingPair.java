package unit.com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.version_1.providers.CryptocoinchartsServiceAPI;

import com.bitdubai.fermat_ccp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptocoinchartsServiceAPI;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by francisco on 21/08/15.
 */
public class TestGetTradingPair {

    CryptocoinchartsServiceAPI cryptocoinchartsServiceAPI = new CryptocoinchartsServiceAPI();
    @Test
    public void TestGetUrlAPI_successful(){
        String expectedValue="http://api.cryptocoincharts.info/tradingPair/BTC_USD";
        String url;
        url=cryptocoinchartsServiceAPI.getUrlAPI("BTC","USD");
        Assertions.assertThat(url).isEqualTo(expectedValue);
    }

}
