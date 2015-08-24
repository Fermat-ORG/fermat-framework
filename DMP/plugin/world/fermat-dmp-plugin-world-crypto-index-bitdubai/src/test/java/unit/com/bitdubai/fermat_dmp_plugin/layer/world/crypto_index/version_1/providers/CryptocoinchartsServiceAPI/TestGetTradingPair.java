package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.providers.CryptocoinchartsServiceAPI;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.providers.CryptocoinchartsServiceAPI;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by francisco on 21/08/15.
 */
public class TestGetTradingPair {

    private String setUrl=null;

    private String urlResult;

    CryptocoinchartsServiceAPI cryptocoinchartsServiceAPI = new CryptocoinchartsServiceAPI();
    @Before
    public void setValues(){
        setUrl= "http://api.cryptocoincharts.info/tradingPair/btc_usd";
    }
    @Test
    public void getTradingPairTest(){
        urlResult=cryptocoinchartsServiceAPI.getTradingPair("btc","usd");
        Assertions.assertThat(setUrl).isEqualTo(urlResult);
    }

}
