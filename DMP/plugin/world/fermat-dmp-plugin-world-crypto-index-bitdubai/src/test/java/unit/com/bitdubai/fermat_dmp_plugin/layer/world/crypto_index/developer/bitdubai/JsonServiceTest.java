package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.JsonService;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by francisco on 14/08/15.
 */
public class JsonServiceTest extends TestCase {

    @Test
    public void testGetJson() throws Exception {
        JsonService jsonService = new JsonService();
        JSONObject jsonObject = jsonService.getJSONFromUrl("http://api.cryptocoincharts.info/tradingPair/btc_usd");
        String s = jsonObject.get("price").toString();
        System.out.println("Precio----->  " + s);
    }
}