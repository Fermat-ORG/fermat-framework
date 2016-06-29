package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.HTTPJson;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.HTTPJson;

import org.fest.assertions.api.Assertions;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;

/**
 * Created by francisco on 03/09/15.
 */
public class GetJsonObjectTest {

    private BufferedReader bufferedReaderTest, bufferedReaderTestExceptios;
    private InputStream inputStreamTest;
    private JSONObject jsonObjectTest;
    HTTPJson htppJson = new HTTPJson();

    @Before
    public void setValues() throws Exception {
        inputStreamTest = htppJson.getInputStream("http://api.cryptocoincharts.info/tradingPair/btc_usd");
        bufferedReaderTest = htppJson.getBufferedReader(inputStreamTest);
    }

    @Test
    public void TestGetJsonObject_successful() throws Exception {
        jsonObjectTest = htppJson.getJsonObject(bufferedReaderTest);
        Assertions.assertThat(jsonObjectTest).isNotNull();
    }

    public void TestGetJsonObject_ThrowCantGetJsonObject() throws Exception {
        catchException(htppJson).getJsonObject(bufferedReaderTestExceptios);
        Assertions.assertThat((Throwable) caughtException()).isNotNull();
    }
}
