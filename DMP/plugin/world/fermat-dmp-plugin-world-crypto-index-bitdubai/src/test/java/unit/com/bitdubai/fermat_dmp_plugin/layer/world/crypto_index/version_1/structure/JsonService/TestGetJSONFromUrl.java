package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.JsonService;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.JsonService;
import junit.framework.TestCase;

import org.fest.assertions.api.Assertions;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by francisco on 14/08/15.
 */
public class TestGetJSONFromUrl {


    private JsonService jsonService = new JsonService();
    private JSONObject jsonObject;
    private String price=null;

    public void setValues() throws IOException {
        price = jsonService.getJSONFromUrl("http://api.cryptocoincharts.info/tradingPair/btc_usd").getString("price");
    }

    @Test
    public void getJSONFromUrlTest() throws Exception {
        setValues();
        System.out.println("price btc-usd " + price);
        Assertions.assertThat(price).isNotEqualTo(null);
    }


}