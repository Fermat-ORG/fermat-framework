package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.HTTPJson;

import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.structure.HTTPJson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.mock;

/**
 * Created by francisco on 03/09/15.
 */
public class GetBufferedReaderTest {

   private InputStream inputStreamTest;
   private BufferedReader bufferedReaderTest;
   HTTPJson httpJson = new HTTPJson();

    @Before
    public void setValues() {
       inputStreamTest =httpJson.getInputStream("https://www.google.com");
    }
    @Test
    public void TestGetBufferedReader_successful() {

        bufferedReaderTest = httpJson.getBufferedReader(inputStreamTest);
        Assertions.assertThat(bufferedReaderTest).isNotNull();
    }
}
