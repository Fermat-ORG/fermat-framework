package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.JsonService;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.JsonService;

import org.fest.assertions.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by francisco on 21/08/15.
 */
public class TestReadAll {

    JsonService jsonService = new JsonService();
    private InputStream is;
    private BufferedReader rd=null;
    private String sRd=null;

   // @Before
    private void setValue() throws IOException {
       is = new URL("http://api.cryptocoincharts.info/tradingPair/btc_usd").openStream();
       rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
       sRd = jsonService.readAll(rd);
    }
    @Test
    public void readAllTest() throws IOException {
        setValue();
        System.out.println(rd);
        Assertions.assertThat(rd).isNotEqualTo(null);
    }

   /* @Test
    public void testReadAllTest1() throws Exception {

    }*/
}
