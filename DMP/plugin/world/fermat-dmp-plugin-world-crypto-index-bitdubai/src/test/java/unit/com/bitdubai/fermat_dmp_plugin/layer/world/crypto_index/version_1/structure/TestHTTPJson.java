package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.HTTPJson;

import org.json.JSONObject;
import org.junit.Test;

/**
 * Created by francisco on 02/09/15.
 */
public class TestHTTPJson {

    HTTPJson jsonParser = new HTTPJson();
    @Test
    public void getJSONFromUrlTest(){
        //String url = "http://data.bter.com/api/1/ticker/BTC_USD";
        //String url = "https://cex.io/api/ticker/BTC/USD";
        String url = "https://c-cex.com/t/btc-usd.json";
        JSONObject object = jsonParser.getJSONFromUrl(url);
        // String content=object.getJSONObject("ticker").getString("price");
        String content=object.getJSONObject("ticker").get("lastbuy").toString();
        //String content=object.getString("last");
        System.out.println(content);
    }
}
